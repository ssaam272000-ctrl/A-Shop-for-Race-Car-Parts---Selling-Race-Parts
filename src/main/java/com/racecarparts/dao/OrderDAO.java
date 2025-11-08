package com.racecarparts.dao;

import com.racecarparts.shop.OrderLine;
import com.racecarparts.shop.RaceCarPart;
import com.racecarparts.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OrderDAO {
    
    public int saveOrder(String customerName, String billingAddress, String customerNotes, 
                         List<OrderLine> orderLines, double totalAmount) throws SQLException {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement itemStmt = null;
        ResultSet rs = null;
        int orderId = -1;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            String insertOrder = "INSERT INTO orders (customer_name, customer_email, total_amount) VALUES (?, ?, ?) RETURNING id";
            orderStmt = conn.prepareStatement(insertOrder);
            orderStmt.setString(1, customerName);
            orderStmt.setString(2, billingAddress);
            orderStmt.setDouble(3, totalAmount);
            
            rs = orderStmt.executeQuery();
            if (rs.next()) {
                orderId = rs.getInt("id");
            }
            
            String insertItem = "INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, total_price, decorators) VALUES (?, ?, ?, ?, ?, ?, ?)";
            itemStmt = conn.prepareStatement(insertItem);
            
            for (OrderLine line : orderLines) {
                RaceCarPart part = line.getEngineBlock();
                itemStmt.setInt(1, orderId);
                itemStmt.setNull(2, java.sql.Types.INTEGER);
                itemStmt.setString(3, part.getEngineName());
                itemStmt.setInt(4, line.getQuantity());
                itemStmt.setDouble(5, line.getAmount());
                itemStmt.setDouble(6, line.getOrderTotal());
                itemStmt.setString(7, getDecorators(part));
                itemStmt.addBatch();
            }
            
            itemStmt.executeBatch();
            conn.commit();
            
            System.out.println("Order saved successfully with ID: " + orderId);
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transaction rolled back due to error");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (orderStmt != null) try { orderStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (itemStmt != null) try { itemStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    DatabaseConnection.closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return orderId;
    }
    
    private String getDecorators(RaceCarPart part) {
        StringBuilder decorators = new StringBuilder();
        String description = part.getDescription();
        
        if (description.contains("Steel Coating")) {
            decorators.append("Steel Coating, ");
        }
        if (description.contains("Aluminum Coating")) {
            decorators.append("Aluminum Coating, ");
        }
        if (description.contains("Performance Tuning")) {
            decorators.append("Performance Tuning, ");
        }
        
        if (decorators.length() > 0) {
            decorators.setLength(decorators.length() - 2);
        }
        
        return decorators.toString();
    }
    
    public void printRecentOrders(int limit) throws SQLException {
        String query = "SELECT o.id, o.customer_name, o.total_amount, o.order_date, " +
                      "COUNT(oi.id) as item_count FROM orders o " +
                      "LEFT JOIN order_items oi ON o.id = oi.order_id " +
                      "GROUP BY o.id, o.customer_name, o.total_amount, o.order_date " +
                      "ORDER BY o.order_date DESC LIMIT ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("\n=== Recent Orders ===");
            while (rs.next()) {
                System.out.printf("Order #%d - %s - $%.2f - %d items - %s%n",
                    rs.getInt("id"),
                    rs.getString("customer_name"),
                    rs.getDouble("total_amount"),
                    rs.getInt("item_count"),
                    rs.getTimestamp("order_date"));
            }
        }
    }
}
