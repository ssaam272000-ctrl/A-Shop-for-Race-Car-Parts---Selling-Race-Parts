package com.inverse.dao;

import com.inverse.util.DBConnection;
import com.inverse.util.LogUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for handling orders and order items.
 * The createOrder method performs a single DB transaction:
 *  - insert order header
 *  - decrement stock for each item (ProductDAO.decrementStock using same Connection)
 *  - insert order item rows
 *  - commit (or rollback on error)
 */
public class OrderDAO {

    /**
     * Creates a new order and its line items in a single transaction.
     * The method decrements product stock inside the same DB transaction.
     *
     * @param customerName the customer's name
     * @param items        list of items to insert (productId, qty, price)
     * @param totalAmount  total amount for the order
     * @return generated orderId
     * @throws SQLException on DB error or insufficient stock
     */
    public int createOrder(String customerName, List<OrderItem> items, double totalAmount) throws SQLException {
        String insertOrder = "INSERT INTO orders (customer_name, total_amount) VALUES (?, ?)";
        String insertItem = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        int orderId = -1;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // start transaction

            try (PreparedStatement psOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psItem = conn.prepareStatement(insertItem)) {

                // Insert order header
                psOrder.setString(1, customerName);
                psOrder.setDouble(2, totalAmount);
                psOrder.executeUpdate();

                try (ResultSet rs = psOrder.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    }
                }

                if (orderId == -1) {
                    throw new SQLException("Failed to generate order id.");
                }

                // For each item: decrement stock using same connection then insert item row
                ProductDAO prodDao = new ProductDAO();
                for (OrderItem it : items) {
                    boolean ok = prodDao.decrementStock(conn, it.getProductId(), it.getQuantity());
                    if (!ok) {
                        throw new SQLException("Insufficient stock for product id: " + it.getProductId());
                    }

                    psItem.setInt(1, orderId);
                    psItem.setInt(2, it.getProductId());
                    psItem.setInt(3, it.getQuantity());
                    psItem.setDouble(4, it.getPrice());
                    psItem.addBatch();
                }

                // execute batched inserts for order items
                psItem.executeBatch();

                // commit the whole transaction
                conn.commit();
            } catch (SQLException ex) {
                // rollback on any failure
                try {
                    conn.rollback();
                } catch (SQLException rbe) {
                    LogUtil.error("Rollback failed in createOrder", rbe);
                }
                LogUtil.error("Error creating order for customer: " + customerName, ex);
                throw ex;
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ignore) {
                    // nothing we can do
                }
            }
        } catch (SQLException outer) {
            // rethrow to caller
            throw outer;
        }

        return orderId;
    }

    /**
     * Reads full order details (including product names and quantities)
     *
     * @param orderId id of the order to read
     * @return Order object with items (or null if not found)
     * @throws SQLException on DB issues
     */
    public Order readOrder(int orderId) throws SQLException {
        Order order = null;
        String qOrder = "SELECT order_id, customer_name, order_date, total_amount FROM orders WHERE order_id = ?";
        String qItems = "SELECT oi.item_id, oi.product_id, oi.quantity, oi.price, p.name "
                + "FROM order_items oi "
                + "LEFT JOIN products p ON oi.product_id = p.product_id "
                + "WHERE oi.order_id = ?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps1 = c.prepareStatement(qOrder);
             PreparedStatement ps2 = c.prepareStatement(qItems)) {

            ps1.setInt(1, orderId);
            try (ResultSet r = ps1.executeQuery()) {
                if (r.next()) {
                    order = new Order();
                    order.orderId = r.getInt("order_id");
                    order.customerName = r.getString("customer_name");
                    order.orderDate = r.getTimestamp("order_date");
                    order.totalAmount = r.getDouble("total_amount");
                }
            }

            if (order != null) {
                ps2.setInt(1, orderId);
                try (ResultSet r2 = ps2.executeQuery()) {
                    while (r2.next()) {
                        OrderItemRow row = new OrderItemRow();
                        row.itemId = r2.getInt("item_id");
                        row.productId = r2.getInt("product_id");
                        row.quantity = r2.getInt("quantity");
                        row.price = r2.getDouble("price");
                        row.productName = r2.getString("name");
                        order.items.add(row);
                    }
                }
            }
        } catch (SQLException e) {
            LogUtil.error("Error reading order: " + orderId, e);
            throw e;
        }
        return order;
    }

    /**
     * Fetches all orders belonging to a specific customer (for order history)
     *
     * @param customerName customer's name
     * @return list of orders (may be empty)
     * @throws SQLException on DB error
     */
    public List<Order> getOrdersByCustomer(String customerName) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT order_id, customer_name, order_date, total_amount FROM orders WHERE customer_name = ? ORDER BY order_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customerName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.orderId = rs.getInt("order_id");
                    o.customerName = rs.getString("customer_name");
                    o.orderDate = rs.getTimestamp("order_date");
                    o.totalAmount = rs.getDouble("total_amount");
                    orders.add(o);
                }
            }
        } catch (SQLException e) {
            LogUtil.error("Error fetching orders for customer: " + customerName, e);
            throw e;
        }

        return orders;
    }

    // ------------------------------------------------------------------
    // DTO (Data Transfer Objects) for Order and Order Items
    // ------------------------------------------------------------------

    /** Represents a minimal item entry for creating an order */
    public static class OrderItem {
        private final int productId;
        private final int quantity;
        private final double price;

        public OrderItem(int productId, int quantity, double price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        public int getProductId() { return productId; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }
    }

    /** Represents a complete order including its item list */
    public static class Order {
        public int orderId;
        public String customerName;
        public java.sql.Timestamp orderDate;
        public double totalAmount;
        public List<OrderItemRow> items = new ArrayList<>();
    }

    /** Represents an item row joined with product info for display */
    public static class OrderItemRow {
        public int itemId;
        public int productId;
        public int quantity;
        public double price;
        public String productName;
    }
}
