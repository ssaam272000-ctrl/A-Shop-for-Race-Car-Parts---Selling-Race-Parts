package com.racecarparts.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
        
        String envUrl = System.getenv("DATABASE_URL");
        String user = null;
        String password = null;
        String jdbcUrl = envUrl;
        
        if (envUrl != null && (envUrl.startsWith("postgresql://") || envUrl.startsWith("postgres://"))) {
            try {
                String urlWithoutScheme = envUrl.replaceFirst("^postgres(ql)?://", "");
                
                if (urlWithoutScheme.contains("@")) {
                    String[] parts = urlWithoutScheme.split("@", 2);
                    String credentials = parts[0];
                    String hostAndDb = parts[1];
                    
                    if (credentials.contains(":")) {
                        String[] credParts = credentials.split(":", 2);
                        user = credParts[0];
                        password = credParts[1];
                    }
                    
                    jdbcUrl = "jdbc:postgresql://" + hostAndDb;
                } else {
                    jdbcUrl = "jdbc:postgresql://" + urlWithoutScheme;
                }
            } catch (Exception e) {
                System.err.println("Warning: Could not parse DATABASE_URL, using as-is: " + e.getMessage());
            }
        }
        
        DB_URL = jdbcUrl;
        DB_USER = user;
        DB_PASSWORD = password;
    }
    
    public static Connection getConnection() throws SQLException {
        if (DB_URL == null || DB_URL.isEmpty()) {
            throw new SQLException("DATABASE_URL environment variable not set");
        }
        
        if (DB_USER != null && DB_PASSWORD != null) {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } else {
            return DriverManager.getConnection(DB_URL);
        }
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
