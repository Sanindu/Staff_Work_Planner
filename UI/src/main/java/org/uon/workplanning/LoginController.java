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
        loadLoginData();
    }

    private void loadLoginData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff.ser"))) {
            staffList = (List<Staff>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin() {
        int staffID = Integer.parseInt(staffIDField.getText());
        String password = passwordField.getText();

        Staff staffMember = staffList.stream()
                .filter(staff -> staff.getStaffId() == staffID && staff.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (staffMember != null) {
            String role = staffMember.getRole();
            if(role.equals("Admin")){
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
            showAlert("Login Failed", "Invalid staff ID or password. Please try again.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
