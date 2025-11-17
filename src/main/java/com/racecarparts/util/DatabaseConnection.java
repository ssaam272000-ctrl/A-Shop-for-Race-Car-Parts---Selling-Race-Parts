package com.racecarparts.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.net.URI;

public class DatabaseConnection {
    private static final String DB_URL = System.getenv("DATABASE_URL");
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
        
        if (DB_URL == null || DB_URL.isEmpty()) {
            throw new SQLException("DATABASE_URL environment variable is not set");
        }
        
        try {
            URI dbUri = new URI(DB_URL);
            String username = null;
            String password = null;
            
            if (dbUri.getUserInfo() != null) {
                String[] credentials = dbUri.getUserInfo().split(":");
                username = credentials[0];
                if (credentials.length > 1) {
                    password = credentials[1];
                }
            }
            
            String jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + 
                           (dbUri.getPort() != -1 ? ":" + dbUri.getPort() : "") +
                           dbUri.getPath() +
                           (dbUri.getQuery() != null ? "?" + dbUri.getQuery() : "");
            
            Properties props = new Properties();
            if (username != null) props.setProperty("user", username);
            if (password != null) props.setProperty("password", password);
            
            return DriverManager.getConnection(jdbcUrl, props);
            
        } catch (Exception e) {
            throw new SQLException("Error parsing DATABASE_URL: " + e.getMessage(), e);
        }
    }
}
