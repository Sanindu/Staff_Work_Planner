package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditWorkController {

    @FXML
    private TextField typeField;
    @FXML
    private TextField activityField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<String> weekComboBox;
    @FXML
    private TextField durationField;
    @FXML
    private TextField instancesField;
    @FXML
    private TextField hoursField;


    private Work work;

    public void setWork(Work work) {
        this.work = work;
        typeField.setText(work.getType());
        activityField.setText(work.getActivity());
        descriptionField.setText(work.getDescription());
        weekComboBox.setValue(work.getWeek());
        durationField.setText(Integer.toString(work.getDuration()));
        instancesField.setText(Integer.toString(work.getInstances()));
        hoursField.setText(Integer.toString(work.getHours()));
    }

    @FXML
    private void initialize() {
        weekComboBox.getItems().addAll("Trimester 1", "Trimester 2", "Trimester 3", "All Year");
    }

    @FXML
    private void handleSave() {
        work.setType(typeField.getText());
        work.setActivity(activityField.getText());
        work.setDescription(descriptionField.getText());
        work.setDuration(Integer.parseInt(durationField.getText()));
        work.setInstances(Integer.parseInt(instancesField.getText()));
        work.setHours(Integer.parseInt(hoursField.getText()));
        work.setWeek(weekComboBox.getValue());

        // Close the dialog
        Stage stage = (Stage) typeField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        // Close the dialog without saving
        Stage stage = (Stage) typeField.getScene().getWindow();
        stage.close();
    }
}
