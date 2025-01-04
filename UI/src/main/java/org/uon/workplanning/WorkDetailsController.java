package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class WorkDetailsController {
    @FXML
    private TableView<Work> workTable;
    @FXML
    private TableColumn<Work, String> staffIdColumn;
    @FXML
    private TableColumn<Work, String> fullNameColumn;
    @FXML
    private TableColumn<Work, String> typeColumn;


    private Set<String> distinctTypes = new HashSet<>();

    public void initialize() {
        loadDistinctTypes();
        addTypeColumns();
    }

    // Method to load distinct types from activity.ser
    private void loadDistinctTypes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("activity.ser"))) {
            List<Activity> activities = (List<Activity>) ois.readObject();
            for (Activity activity : activities) {
                distinctTypes.add(activity.getType());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to add new columns for each distinct type
    private void addTypeColumns() {
        for (String type : distinctTypes) {
            TableColumn<Work, String> typeColumn = new TableColumn<>(type);
            typeColumn.setCellValueFactory(new PropertyValueFactory<>(type));
            typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            workTable.getColumns().add(typeColumn);
        }
    }

    @FXML
    private void handleEditWork() {
        Work selectedWork = workTable.getSelectionModel().getSelectedItem();
        if (selectedWork != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditWork.fxml"));
                Parent parent = loader.load();

                EditWorkController controller = loader.getController();
                controller.setWork(selectedWork);

                Stage stage = new Stage();
                stage.setTitle("Edit Work Details");
                stage.setScene(new Scene(parent));
                stage.showAndWait();

                saveWorkList();
                workTable.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Selection", "Please select a work to edit.");
        }
    }


    @FXML
    private void handleDeleteWork() {
        Work selectedWork = workTable.getSelectionModel().getSelectedItem();
        if (selectedWork != null) {
            workTable.getItems().remove(selectedWork);
            saveWorkList();
        } else {
            showAlert("No Selection", "Please select a work to delete.");
        }
    }
    @FXML
    private void handleAddNewWork() {
        WorkDetails.switchToNewWorkView();
    }
    private void saveWorkList() {
        List<Work> workList = new ArrayList<>(workTable.getItems());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("workdetails.ser"))) {
            oos.writeObject(workList);
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
