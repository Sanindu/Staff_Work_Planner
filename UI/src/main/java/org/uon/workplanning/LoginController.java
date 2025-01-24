package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class LoginController {
    @FXML
    private TextField staffIDField;

    @FXML
    private PasswordField passwordField;

    private List<Staff> staffList;

    @FXML
    private void initialize() {
        // Load login data when the controller is initialised
        loadLoginData();
    }

    // Method to load login data from the serialized file
    private void loadLoginData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff.ser"))) {
            staffList = (List<Staff>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Print any exceptions that occur
        }
    }

    // Method to handle the login process
    @FXML
    private void handleLogin() {
        int staffID = Integer.parseInt(staffIDField.getText());
        String password = passwordField.getText();

        // Find the staff member with the matching ID and password
        Staff staffMember = staffList.stream()
                .filter(staff -> staff.getStaffId() == staffID && staff.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (staffMember != null) {
            String role = staffMember.getRole();
            if(role.equals("Admin")){
                // Load and display the Admin Dashboard
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Admin Dashboard");
                    stage.setScene(new javafx.scene.Scene(parent));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                // Load and display the User Dashboard
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDashboard.fxml"));
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("User Dashboard");
                    stage.setScene(new javafx.scene.Scene(parent));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            // Show an alert if the login failed
            showAlert("Login Failed", "Invalid staff ID or password. Please try again.");
        }
    }

    // Method to show an alert with the given title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
