package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditWorkController {

    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<String> weekComboBox;
    @FXML
    private TextField durationField;
    @FXML
    private TextField instancesField;

    private Work work;

    // Method to set the work object and initialize the text fields and combo box with its data
    public void setWork(Work work) {
        this.work = work;
        descriptionField.setText(work.getDescription());
        weekComboBox.setValue(work.getWeek());
        durationField.setText(Integer.toString(work.getDuration()));
        instancesField.setText(Integer.toString(work.getInstances()));
    }

    @FXML
    private void initialize() {
        // Initialize the week combo box with options
        weekComboBox.getItems().addAll("Trimester 1", "Trimester 2", "Trimester 3", "All Year");
    }

    // Handle save button click event
    @FXML
    private void handleSave() {
        // Update the work object with the values from the text fields and combo box
        work.setDescription(descriptionField.getText());
        work.setDuration(Integer.parseInt(durationField.getText()));
        work.setInstances(Integer.parseInt(instancesField.getText()));
        int totalHours = Integer.parseInt(durationField.getText()) * Integer.parseInt(instancesField.getText());
        work.setHours(totalHours);
        work.setWeek(weekComboBox.getValue());

        // Set the trimester hours based on the selected week value
        switch (weekComboBox.getValue()) {
            case "Trimester 1":
                work.setT1(totalHours);
                work.setT2(0);
                work.setT3(0);
                work.setAllYear(0);
                break;
            case "Trimester 2":
                work.setT1(0);
                work.setT2(totalHours);
                work.setT3(0);
                work.setAllYear(0);
                break;
            case "Trimester 3":
                work.setT1(0);
                work.setT2(0);
                work.setT3(totalHours);
                work.setAllYear(0);
                break;
            case "All Year":
                work.setT1(0);
                work.setT2(0);
                work.setT3(0);
                work.setAllYear(totalHours);
                break;
        }
        // Close the dialog
        Stage stage = (Stage) descriptionField.getScene().getWindow();
        stage.close();
    }

    // Handle cancel button click event
    @FXML
    private void handleCancel() {
        // Close the dialog without saving changes
        Stage stage = (Stage) descriptionField.getScene().getWindow();
        stage.close();
    }
}
