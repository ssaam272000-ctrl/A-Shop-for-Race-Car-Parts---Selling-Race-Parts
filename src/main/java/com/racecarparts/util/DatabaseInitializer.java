package com.racecarparts.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
    
    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            String createProductsTable = 
                "CREATE TABLE IF NOT EXISTS products (" +
                "    id SERIAL PRIMARY KEY," +
                "    name VARCHAR(255) NOT NULL," +
                "    description TEXT," +
                "    base_price DECIMAL(10,2) NOT NULL," +
                "    image_url VARCHAR(255)," +
                "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            
            String createOrdersTable = 
                "CREATE TABLE IF NOT EXISTS orders (" +
                "    id SERIAL PRIMARY KEY," +
                "    customer_name VARCHAR(255)," +
                "    customer_email VARCHAR(255)," +
                "    total_amount DECIMAL(10,2) NOT NULL," +
                "    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            
            String createOrderItemsTable = 
                "CREATE TABLE IF NOT EXISTS order_items (" +
                "    id SERIAL PRIMARY KEY," +
                "    order_id INTEGER REFERENCES orders(id) ON DELETE CASCADE," +
                "    product_id INTEGER REFERENCES products(id)," +
                "    product_name VARCHAR(255) NOT NULL," +
                "    quantity INTEGER NOT NULL," +
                "    unit_price DECIMAL(10,2) NOT NULL," +
                "    total_price DECIMAL(10,2) NOT NULL," +
                "    decorators TEXT" +
                ")";
            
            stmt.execute(createProductsTable);
            stmt.execute(createOrdersTable);
            stmt.execute(createOrderItemsTable);
            
            System.out.println("Database tables created successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
