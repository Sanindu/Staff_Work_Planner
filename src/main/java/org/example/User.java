package org.example;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = -675563349496622217L; // Use the serialVersionUID from the error message

    private String username;
    private String password;
    private String role; // "admin" or "user"

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
