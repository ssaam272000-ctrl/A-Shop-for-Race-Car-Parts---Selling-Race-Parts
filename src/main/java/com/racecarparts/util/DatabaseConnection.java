package com.racecarparts.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = System.getenv("DATABASE_URL");
    
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        if (DB_URL == null || DB_URL.isEmpty()) {
            throw new SQLException("DATABASE_URL environment variable not set");
        }
        return DriverManager.getConnection(DB_URL);
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
