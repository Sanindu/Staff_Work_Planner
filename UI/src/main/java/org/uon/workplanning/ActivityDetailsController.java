package org.uon.workplanning;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDetailsController {

    @FXML
    private TableView<Activity> activityTable;
    @FXML
    private TableColumn<Activity, Integer> activityIdColumn;
    @FXML
    private TableColumn<Activity, String> typeColumn;
    @FXML
    private TableColumn<Activity, String> activityColumn;

    private ObservableList<Activity> activityList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up the table columns to use the properties of the Activity class
        activityIdColumn.setCellValueFactory(new PropertyValueFactory<>("activityId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));

        // Set the items for the table to be the list of activities
        activityTable.setItems(getActivityList());
    }

    private ObservableList<Activity> getActivityList() {
        List<Activity> activityList = new ArrayList<>();
        // Deserialize the list of activities from the file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("activity.ser"))) {
            activityList = (List<Activity>) ois.readObject();
        } catch (EOFException e) {
            // End of file reached, no more activities to read
        } catch (FileNotFoundException e) {
            // File not found, return empty list
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(activityList);
    }

    @FXML
    private void handleAddNewActivity() {
        try {
            // Load the FXML file for the "Add New Activity" window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewActivity.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add New Activity");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditActivity() {
        // Get the selected activity from the table
        Activity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            try {
                // Load the FXML file for the "Edit Activity Details" window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditActivityDetails.fxml"));
                Parent parent = loader.load();

                // Get the controller for the "Edit Activity Details" window and set the selected activity
                EditActivityDetailsController controller = loader.getController();
                controller.setActivity(selectedActivity);

                Stage stage = new Stage();
                stage.setTitle("Edit Activity Details");
                stage.setScene(new Scene(parent));
                stage.showAndWait();

                // Save the updated list of activities and refresh the table
                saveActivityList();
                activityTable.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show an alert if no activity is selected
            showAlert("No Selection", "Please select an activity to edit.");
        }
    }

    @FXML
    private void handleDeleteActivity() {
        // Get the selected activity from the table
        Activity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            // Remove the selected activity from the table and save the updated list
            activityTable.getItems().remove(selectedActivity);
            saveActivityList();
        } else {
            // Show an alert if no activity is selected
            showAlert("No Selection", "Please select an activity to delete.");
        }
    }

    private void saveActivityList() {
        // Serialize the updated list of activities to a file
        List<Activity> activityList = new ArrayList<>(activityTable.getItems());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("activity.ser"))) {
            oos.writeObject(activityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        // Refresh the table with the updated list of activities
        activityList = getActivityList();
        activityTable.setItems(activityList);
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
