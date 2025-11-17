package com.racecarparts.dao;

import com.racecarparts.shop.OrderLine;
import com.racecarparts.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDAO {
    
    public void saveOrder(String invoiceNumber, String invoiceDate, String customerName, 
                         String customerEmail, String billingAddress, String customerNotes,
                         List<OrderLine> orderLines, double subtotal, double tax, 
                         double carrier, double total) {
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            String insertOrderSQL = 
                "INSERT INTO orders (invoice_number, invoice_date, customer_name, " +
                "customer_email, billing_address, customer_notes, subtotal, tax, carrier, total) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL, 
                                                               PreparedStatement.RETURN_GENERATED_KEYS);
            orderStmt.setString(1, invoiceNumber);
            orderStmt.setString(2, invoiceDate);
            orderStmt.setString(3, customerName);
            orderStmt.setString(4, customerEmail);
            orderStmt.setString(5, billingAddress);
            orderStmt.setString(6, customerNotes);
            orderStmt.setDouble(7, subtotal);
            orderStmt.setDouble(8, tax);
            orderStmt.setDouble(9, carrier);
            orderStmt.setDouble(10, total);
            
            orderStmt.executeUpdate();
            
            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            int orderId = -1;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }
            
            String insertItemSQL = 
                "INSERT INTO order_items (order_id, part_code, part_description, " +
                "quantity, unit_price, total_price) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement itemStmt = conn.prepareStatement(insertItemSQL);
            
            for (OrderLine line : orderLines) {
                itemStmt.setInt(1, orderId);
                itemStmt.setString(2, line.getEngineBlock().getEngineName());
                itemStmt.setString(3, line.getEngineBlock().getDescription());
                itemStmt.setInt(4, line.getQuantity());
                itemStmt.setDouble(5, line.getEngineBlock().getPrice());
                itemStmt.setDouble(6, line.getOrderTotal());
                itemStmt.executeUpdate();
            }
            
            conn.commit();
            System.out.println("Order saved successfully with ID: " + orderId);
            
        } catch (SQLException e) {
            System.err.println("Error saving order: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
