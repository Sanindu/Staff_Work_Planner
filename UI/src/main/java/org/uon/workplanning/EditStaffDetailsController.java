package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditStaffDetailsController {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactNumberField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<String> employmentTypeComboBox;
    @FXML
    private TextField subjectAreaField;
    @FXML
    private TextField lineManagerField;
    @FXML
    private ComboBox<String> roleComboBox;

    private Staff staff;

    // Method to set the staff object and initialize the text fields and combo boxes with its data
    public void setStaff(Staff staff) {
        this.staff = staff;
        fullNameField.setText(staff.getFullName());
        emailField.setText(staff.getEmail());
        contactNumberField.setText(staff.getContactNumber());
        addressField.setText(staff.getAddress());
        passwordField.setText(staff.getPassword());
        employmentTypeComboBox.setValue(Double.toString(staff.getEmploymentType()));
        subjectAreaField.setText(staff.getSubjectArea());
        lineManagerField.setText(staff.getLineManager());
        roleComboBox.setValue(staff.getRole());
    }

    @FXML
    private void initialize() {
        // Initialize the employment type and role combo boxes with options
        employmentTypeComboBox.getItems().addAll("Full Time", "Part Time");
        roleComboBox.getItems().addAll("Admin", "User");
    }

    // Handle save button click event
    @FXML
    private void handleSave() {
        // Update the staff object with the values from the text fields and combo boxes
        staff.setFullName(fullNameField.getText());
        staff.setEmail(emailField.getText());
        staff.setContactNumber(contactNumberField.getText());
        staff.setAddress(addressField.getText());
        staff.setPassword(passwordField.getText());
        // Determine the employment type as double
        double employmentTypeValue = "Full Time".equals(employmentTypeComboBox.getValue()) ? 1.0 : 0.5;
        staff.setEmploymentType((employmentTypeValue));
        staff.setSubjectArea(subjectAreaField.getText());
        staff.setLineManager(lineManagerField.getText());
        staff.setRole(roleComboBox.getValue());

        // Close the dialog
        Stage stage = (Stage) fullNameField.getScene().getWindow();
        stage.close();
    }

    // Handle cancel button click event
    @FXML
    private void handleCancel() {
        // Close the dialog without saving changes
        Stage stage = (Stage) fullNameField.getScene().getWindow();
        stage.close();
    }
}
