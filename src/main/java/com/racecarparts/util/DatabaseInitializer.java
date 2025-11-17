package com.racecarparts.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
    
    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            String createOrdersTable = 
                "CREATE TABLE IF NOT EXISTS orders (" +
                "    id SERIAL PRIMARY KEY," +
                "    invoice_number VARCHAR(20) NOT NULL," +
                "    invoice_date VARCHAR(50) NOT NULL," +
                "    customer_name VARCHAR(255) NOT NULL," +
                "    customer_email VARCHAR(255) NOT NULL," +
                "    billing_address TEXT NOT NULL," +
                "    customer_notes TEXT," +
                "    subtotal DECIMAL(10, 2) NOT NULL," +
                "    tax DECIMAL(10, 2) NOT NULL," +
                "    carrier DECIMAL(10, 2) NOT NULL," +
                "    total DECIMAL(10, 2) NOT NULL," +
                "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            
            stmt.execute(createOrdersTable);
            
            String createOrderItemsTable = 
                "CREATE TABLE IF NOT EXISTS order_items (" +
                "    id SERIAL PRIMARY KEY," +
                "    order_id INTEGER REFERENCES orders(id) ON DELETE CASCADE," +
                "    part_code VARCHAR(50) NOT NULL," +
                "    part_description TEXT NOT NULL," +
                "    quantity INTEGER NOT NULL," +
                "    unit_price DECIMAL(10, 2) NOT NULL," +
                "    total_price DECIMAL(10, 2) NOT NULL" +
                ")";
            
            stmt.execute(createOrderItemsTable);
            
            System.out.println("Database tables initialized successfully");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
