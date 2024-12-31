package org.example;

public class Session {
    private static User loggedInUser;

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static boolean isAdmin() {
        return loggedInUser != null && "admin".equalsIgnoreCase(loggedInUser.getRole());
    }
}

