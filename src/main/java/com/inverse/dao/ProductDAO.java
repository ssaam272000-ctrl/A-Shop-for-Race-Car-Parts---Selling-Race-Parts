package com.inverse.dao;

import com.inverse.model.Product;
import com.inverse.util.DBConnection;
import com.inverse.util.LogUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for product lookup and stock management.
 */
public class ProductDAO {

    /**
     * Fetch all products from the database.
     *
     * @return list of Product (possibly empty)
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT product_id, name, description, image_url, price, cost, stock FROM products";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setImageUrl(rs.getString("image_url"));
                product.setPrice(rs.getDouble("price"));
                product.setCost(rs.getDouble("cost"));
                product.setStock(rs.getInt("stock"));
                products.add(product);
            }
        } catch (SQLException e) {
            LogUtil.error("Error fetching all products", e);
        }
        return products;
    }

    /**
     * Fetch a single product by its ID.
     *
     * @param id product id
     * @return Product or null if not found
     */
    public Product getProductById(int id) {
        Product product = null;
        String query = "SELECT product_id, name, description, image_url, price, cost, stock FROM products WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setImageUrl(rs.getString("image_url"));
                    product.setPrice(rs.getDouble("price"));
                    product.setCost(rs.getDouble("cost"));
                    product.setStock(rs.getInt("stock"));
                }
            }
        } catch (SQLException e) {
            LogUtil.error("Error fetching product by id: " + id, e);
        }

        return product;
    }

    /**
     * Decrement stock in the context of an existing transaction/connection.
     * Returns true if stock was sufficient and was decremented; false otherwise.
     * Uses SELECT ... FOR UPDATE to lock the row during the transaction.
     *
     * IMPORTANT: this method expects the caller to be using the same Connection
     * instance (and have setAutoCommit(false) on it) so rollback/locking work correctly.
     *
     * @param conn      existing DB connection (transactional)
     * @param productId id of the product to decrement
     * @param quantity  quantity to subtract
     * @return true if stock decremented; false if insufficient or product missing
     * @throws SQLException on DB error
     */
    public boolean decrementStock(Connection conn, int productId, int quantity) throws SQLException {
        String sqlCheck = "SELECT stock FROM products WHERE product_id = ? FOR UPDATE";
        String sqlUpdate = "UPDATE products SET stock = stock - ? WHERE product_id = ?";

        // 1) check & lock
        try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
            psCheck.setInt(1, productId);
            try (ResultSet rs = psCheck.executeQuery()) {
                if (!rs.next()) return false; // product not found
                int stock = rs.getInt("stock");
                if (stock < quantity) return false; // insufficient stock
            }
        }

        // 2) perform update
        try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
            psUpdate.setInt(1, quantity);
            psUpdate.setInt(2, productId);
            int updated = psUpdate.executeUpdate();
            return updated == 1;
        } catch (SQLException e) {
            LogUtil.error("Error updating stock for product: " + productId, e);
            throw e;
        }
    }
}
