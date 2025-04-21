package com.example.filmapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:MySQL://bc2anwjprqmkmyqwkuor-mysql.services.clever-cloud.com:3306/bc2anwjprqmkmyqwkuor";
    private static final String DB_USER = "uv4bzjiva6brbyfv";
    private static final String DB_PASSWORD = "vWxpBoXeSQ5qRLe3i3di";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Load the database driver (optional for modern JDBC drivers)
            // Class.forName("com.your_database_type.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            System.out.println("Connected to the database successfully!");

        } catch (SQLException e) {
            System.err.println("Database connection failed:" + e.getMessage());
            throw e; // Re-throw the exception to be handled by the caller
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the connection: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = getConnection();
            // Perform database operations here using the connection
        } catch (SQLException e) {
            // Handle connection or SQL exceptions
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }
}