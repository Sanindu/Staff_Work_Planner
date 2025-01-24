package org.uon.workplanning;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Scanner;

public class ConsoleAppLogin {
    static List<Staff> staffList;

    public ConsoleAppLogin(List<Staff> staffList) {
        this.staffList = staffList;
        loadLoginData();
    }

    void loadLoginData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff.ser"))) {
            staffList = (List<Staff>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleLogin() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your Staff ID: ");
        int staffID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Staff staffMember = staffList.stream()
                .filter(staff -> staff.getStaffId() == staffID && staff.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (staffMember != null) {
            String role = staffMember.getRole();
            if (role.equals("Admin")) {
                System.out.println("Welcome, Admin! Redirecting to Admin Dashboard...");
                // Code to handle Admin dashboard logic here
            } else {
                System.out.println("Welcome, User! Redirecting to User Dashboard...");
                // Code to handle User dashboard logic here
            }
        } else {
            showAlert("Login Failed", "Invalid staff ID or password. Please try again.");
        }
    }

    private void showAlert(String title, String message) {
        System.out.println(title + ": " + message);
    }

    public static void main(String[] args) {
        // Sample data for testing
        List<Staff> staffList = List.of(
                new Staff(1, "admin123", "Admin","0","add","admin",1.0,"SE","LM","O"),
                new Staff(2, "user123", "User","0","add","user",1.0,"SE","LM","O")
        );

        ConsoleAppLogin app = new ConsoleAppLogin(staffList);
        app.handleLogin();
    }
}
