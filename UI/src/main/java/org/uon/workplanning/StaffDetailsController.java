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

public class StaffDetailsController {

    @FXML
    private TableView<Staff> staffTable;
    @FXML
    private TableColumn<Staff, Integer> staffIdColumn;
    @FXML
    private TableColumn<Staff, String> fullNameColumn;
    @FXML
    private TableColumn<Staff, String> emailColumn;
    @FXML
    private TableColumn<Staff, String> contactNumberColumn;
    @FXML
    private TableColumn<Staff, String> addressColumn;
    @FXML
    private TableColumn<Staff, Double> employmentTypeColumn;
    @FXML
    private TableColumn<Staff, String> subjectAreaColumn;
    @FXML
    private TableColumn<Staff, String> lineManagerColumn;
    @FXML
    private TableColumn<Staff, String> roleColumn;

    private ObservableList<Staff> staffList = FXCollections.observableArrayList();

    // Initialisation method to set up the table columns and load the staff list
    @FXML
    private void initialize() {
        staffIdColumn.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactNumberColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        employmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("employmentType"));
        subjectAreaColumn.setCellValueFactory(new PropertyValueFactory<>("subjectArea"));
        lineManagerColumn.setCellValueFactory(new PropertyValueFactory<>("lineManager"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        staffTable.setItems(getStaffList());
    }

    // Method to get the staff list from the serialized file
    private ObservableList<Staff> getStaffList() {
        List<Staff> staffList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff.ser"))) {
            staffList = (List<Staff>) ois.readObject();
        } catch (EOFException e) {
            // End of file reached, no more staff to read
        } catch (FileNotFoundException e) {
            // File not found, return empty list
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(staffList);
    }

    // Handle adding a new staff member
    @FXML
    private void handleAddNewStaff() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffRegister.fxml"));
            Parent parent = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(parent, 700, 500));
            stage.setTitle("Add New Staff");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle editing the selected staff member
    @FXML
    private void handleEditStaff() {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditStaffDetails.fxml"));
                Parent parent = loader.load();

                EditStaffDetailsController controller = loader.getController();
                controller.setStaff(selectedStaff);

                Stage stage = new Stage();
                stage.setTitle("Edit Staff Details");
                stage.setScene(new Scene(parent));
                stage.showAndWait();

                saveStaffList();
                staffTable.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Selection", "Please select a staff to edit.");
        }
    }

    // Handle deleting the selected staff member
    @FXML
    private void handleDeleteStaff() {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            staffTable.getItems().remove(selectedStaff);
            saveStaffList();
        } else {
            showAlert("No Selection", "Please select a staff to delete.");
        }
    }

    // Method to save the staff list to the serialized file
    private void saveStaffList() {
        List<Staff> staffList = new ArrayList<>(staffTable.getItems());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("staff.ser"))) {
            oos.writeObject(staffList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle refreshing the staff list
    @FXML
    private void handleRefresh() {
        staffList = getStaffList();
        staffTable.setItems(staffList);
    }

    // Method to show an alert with the given title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
