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

    @FXML
    private void initialize() {
        activityIdColumn.setCellValueFactory(new PropertyValueFactory<>("activityId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));

        activityTable.setItems(getActivityList());
    }

    private ObservableList<Activity> getActivityList() {
        List<Activity> activityList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("activity.ser"))) {
            activityList = (List<Activity>) ois.readObject();
        } catch (EOFException e) {
            // End of file reached, no more staff to read
        } catch (FileNotFoundException e) {
            // File not found, return empty list
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(activityList);
    }

    @FXML
    private void handleAddNewActivity() {
        ActivityCreate.switchToCreateView();
    }

    @FXML
    private void handleEditActivity() {
        Activity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditActivityDetails.fxml"));
                Parent parent = loader.load();

                EditActivityDetailsController controller = loader.getController();
                controller.setActivity(selectedActivity);

                Stage stage = new Stage();
                stage.setTitle("Edit Activity Details");
                stage.setScene(new Scene(parent));
                stage.showAndWait();

                saveActivityList();
                activityTable.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Selection", "Please select a activity to edit.");
        }
    }


    @FXML
    private void handleDeleteActivity() {
        Activity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            activityTable.getItems().remove(selectedActivity);
            saveActivityList();
        } else {
            showAlert("No Selection", "Please select a activity to delete.");
        }
    }

    private void saveActivityList() {
        List<Activity> activityList = new ArrayList<>(activityTable.getItems());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("activity.ser"))) {
            oos.writeObject(activityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
