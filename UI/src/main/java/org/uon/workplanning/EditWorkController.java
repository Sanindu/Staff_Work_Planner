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


    private Work work;

    public void setWork(Work work) {
        this.work = work;
        typeField.setText(work.getType());
        activityField.setText(work.getActivity());
        descriptionField.setText(work.getDescription());
        weekComboBox.setValue(work.getWeek());
        durationField.setText(Integer.toString(work.getDuration()));
        instancesField.setText(Integer.toString(work.getInstances()));
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
        int totalHours = Integer.parseInt(durationField.getText())*Integer.parseInt(instancesField.getText());
        work.setHours(totalHours);
        work.setWeek(weekComboBox.getValue());
        switch (weekComboBox.getValue()){
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
