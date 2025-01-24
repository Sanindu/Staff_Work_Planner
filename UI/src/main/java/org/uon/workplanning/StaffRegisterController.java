package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StaffRegisterController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField addressField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> employmentComboBox;
    @FXML
    private TextField subjectField;
    @FXML
    private TextField lineManagerField;
    @FXML
    private ComboBox<String> roleComboBox;

    // Handle registration process
    @FXML
    private void handleRegister() {
        // Gather data from input fields
        String fullName = nameField.getText();
        String email = emailField.getText();
        String contactNumber = contactField.getText();
        String address = addressField.getText();
        String password = passwordField.getText();
        String employmentType = employmentComboBox.getValue();
        String subjectArea = subjectField.getText();
        String lineManager = lineManagerField.getText();
        String role = roleComboBox.getValue();

        // Determine the employment type as double
        double employmentTypeValue = "Full Time".equals(employmentType) ? 1.0 : 0.5;

        // Get the last saved staff ID and generate the new ID
        int newStaffId = getLastStaffId() + 1;

        // Create staff object
        Staff staff = new Staff(newStaffId, fullName, email, contactNumber, address, password, employmentTypeValue, subjectArea, lineManager, role);

        // Read existing staff list
        List<Staff> staffList = readStaffList();
        staffList.add(staff); // Add the new staff to the list

        // Serialize staff list
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("staff.ser"))) {
            oos.writeObject(staffList); // Write the list to the file
            showAlert("Success", "Staff registered successfully!");
            // Close the registration window
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            ex.printStackTrace(); // Print any exceptions that occur
        }
    }

    // Method to get the last saved staff ID
    private int getLastStaffId() {
        List<Staff> staffList = readStaffList();
        return staffList.stream().mapToInt(Staff::getStaffId).max().orElse(0);
    }

    // Method to read the staff list from the serialised file
    @SuppressWarnings("unchecked")
    private List<Staff> readStaffList() {
        List<Staff> staffList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff.ser"))) {
            staffList = (List<Staff>) ois.readObject();
        } catch (EOFException e) {
            // End of file reached, return empty list
        } catch (FileNotFoundException e) {
            // File not found, return empty list
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur
        }
        return staffList;
    }

    // Handle cancel button click event
    @FXML
    private void handleCancel() {
        // Close the registration window without saving
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }

    // Method to show an alert with the given title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
