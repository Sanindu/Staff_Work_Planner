package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityController {

    @FXML
    private TextField typeField;
    @FXML
    private TextField activityField;
    @FXML
    private void handleCreate() {
        String type = typeField.getText();
        String activity = activityField.getText();

        int newactivityId = getLastActivityId() + 1;

        Activity activityObj = new Activity(newactivityId, type, activity);

        List<Activity> activityList = readActivityList();
        activityList.add(activityObj);

        // Serialize staff list
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("activity.ser"))) {
            oos.writeObject(activityList);
            showAlert("Success", "Activity added successfully!");
            Stage stage = (Stage) typeField.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int getLastActivityId() {
        List<Activity> activityList = readActivityList();
        return activityList.stream().mapToInt(Activity::getActivityId).max().orElse(0);
    }

    @SuppressWarnings("unchecked")
    private List<Activity> readActivityList() {
        List<Activity> activityList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("activity.ser"))) {
            activityList = (List<Activity>) ois.readObject();
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityList;
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) typeField.getScene().getWindow();
        stage.close();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
