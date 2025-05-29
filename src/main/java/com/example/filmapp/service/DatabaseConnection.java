package com.example.filmapp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to establish connection to mySQL database.
 */
/**
 * Manages the connection to the application's SQLite database.
 * Provides utility to establish and retrieve database connections.
 */
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://bc2anwjprqmkmyqwkuor-mysql.services.clever-cloud.com:3306/bc2anwjprqmkmyqwkuor";
    private static final String DB_USER = "uv4bzjiva6brbyfv";
    private static final String DB_PASSWORD = "vWxpBoXeSQ5qRLe3i3di";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
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