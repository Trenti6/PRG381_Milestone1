package com.example.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Update the database name if needed
    private static final String URL = "jdbc:postgresql://localhost:5432/wellnessdb";
    private static final String USER = "postgres"; // your PostgreSQL username
    private static final String PASSWORD = "1234"; // your PostgreSQL password

    // Load the PostgreSQL driver and return the DB connection
    public static Connection initializeDatabase() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Optional method to test if DB connection works
    public static boolean isConnectionValid() {
        try (Connection conn = initializeDatabase()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connection to PostgreSQL is successful!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
        }
        return false;
    }
}
