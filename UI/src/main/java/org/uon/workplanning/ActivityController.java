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
        // Get the text from the type and activity fields
        String type = typeField.getText();
        String activity = activityField.getText();

        // Generate a new activity ID by getting the last activity ID and adding 1
        int newactivityId = getLastActivityId() + 1;

        // Create a new Activity object with the new ID, type, and activity
        Activity activityObj = new Activity(newactivityId, type, activity);

        // Read the existing list of activities from the serialized file
        List<Activity> activityList = readActivityList();
        activityList.add(activityObj); // Add the new activity to the list

        // Serialize the updated list of activities to a file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("activity.ser"))) {
            oos.writeObject(activityList); // Write the list to the file
            showAlert("Success", "Activity added successfully!"); // Show a success alert
            Stage stage = (Stage) typeField.getScene().getWindow();
            stage.close(); // Close the current window
        } catch (Exception ex) {
            ex.printStackTrace(); // Print any exceptions that occur
        }
    }

    private int getLastActivityId() {
        // Read the list of activities and get the maximum activity ID, or 0 if the list is empty
        List<Activity> activityList = readActivityList();
        return activityList.stream().mapToInt(Activity::getActivityId).max().orElse(0);
    }

    @SuppressWarnings("unchecked")
    private List<Activity> readActivityList() {
        List<Activity> activityList = new ArrayList<>();
        // Deserialize the list of activities from the file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("activity.ser"))) {
            activityList = (List<Activity>) ois.readObject(); // Read the list from the file
        } catch (EOFException e) {
            // End of file means the list is empty, so we do nothing
        } catch (FileNotFoundException e) {
            // File not found means the list is empty, so we do nothing
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur
        }
        return activityList;
    }

    @FXML
    private void handleCancel() {
        // Close the current window when the cancel button is clicked
        Stage stage = (Stage) typeField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        // Show an informational alert with the given title and message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
