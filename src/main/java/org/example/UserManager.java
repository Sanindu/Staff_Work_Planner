package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class UserManager {

    private List<User> users;
    private static final String FILE_NAME = "users.ser";

    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
    }

    public void addUser(String username, String password, String role) {
        User newUser = new User(username, password, role);
        users.add(newUser);
        saveUsers();
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
            System.out.println("User details have been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            users = (List<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous user data found. A new file will be created.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void displayUsers() {
        for (User user : users) {
            System.out.println("Username: " + user.getUsername() + ", Role: " + user.getRole());
        }
    }
}
