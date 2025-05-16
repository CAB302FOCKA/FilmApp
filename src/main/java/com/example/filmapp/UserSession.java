package com.example.filmapp;

public class UserSession {
    private static String username;
    private static String email;
    private static String hashedPassword;

    public static void setUser(String user, String userEmail, String hashedPass) {
        username = user;
        email = userEmail;
        hashedPassword = hashedPass;
    }

    public static String getUsername() {
        return username;
    }

    public static String getEmail() {
        return email;
    }

    public static String getHashedPassword() {
        return hashedPassword;
    }

    public static void clearSession() {
        username = null;
        email = null;
        hashedPassword = null;
    }
}
