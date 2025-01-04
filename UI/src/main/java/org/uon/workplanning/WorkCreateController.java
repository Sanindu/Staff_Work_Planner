package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WorkCreateController {
    @FXML
    private TextField staffIdField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField activityField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<String> yearComboBox;

    @FXML
    private TextField activityDurationField;

    @FXML
    private TextField instancesField;

     @FXML
     private TextField hoursField;

    public void initialize() {
        yearComboBox.setValue("Trimester 1");
    }

    @FXML
    private void handleSave() {
        int newWorkId = getLastWorkId() + 1;
        int staffId = Integer.parseInt(staffIdField.getText());
        String type = typeField.getText();
        String activity = activityField.getText();
        String description = descriptionField.getText();
        String week = yearComboBox.getValue();
        int activityDuration = Integer.parseInt(activityDurationField.getText());
        int instances = Integer.parseInt(instancesField.getText());
        int hours = Integer.parseInt(hoursField.getText());

        Work work = new Work(newWorkId,staffId,type,activity,description,week,activityDuration, instances,hours);
        List<Work> workList = readWorkList();
        workList.add(work);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("workdetails.ser"))) {
            oos.writeObject(workList);
            showAlert("Success", "New work details added successfully!");
              WorkDetails.switchToDetailsView();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private int getLastWorkId() {
        List<Work> workList = readWorkList();
        return workList.stream().mapToInt(Work::getWorkId).max().orElse(0);
    }

    @SuppressWarnings("unchecked")
    private List<Work> readWorkList() {
        List<Work> workList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("workdetails.ser"))) {
            workList = (List<Work>) ois.readObject();
        } catch (EOFException e) {
            // End of file reached, return empty list
        } catch (FileNotFoundException e) {
            // File not found, return empty list
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workList;
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleCancel() {
        try {
            WorkDetails.switchToDetailsView();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
