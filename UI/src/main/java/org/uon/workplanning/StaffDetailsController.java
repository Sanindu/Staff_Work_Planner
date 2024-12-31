package org.uon.workplanning;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @FXML
    private void handleAddNewStaff() {
        StaffRegister.switchToRegisterView();
    }

    @FXML
    private void handleEditStaff() {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            // Create and show an alert dialog with text fields for editing
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Edit Staff");
            dialog.setHeaderText("Editing Staff ID: " + selectedStaff.getStaffId());
            dialog.setContentText("Full Name:");
            dialog.getEditor().setText(selectedStaff.getFullName());

            dialog.showAndWait().ifPresent(fullName -> {
                selectedStaff.setFullName(fullName);
                // Update other fields similarly...
                // After updating, save the list again
                saveStaffList();
                staffTable.refresh();
            });
        } else {
            showAlert("No Selection", "Please select a staff to edit.");
        }
    }

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

    private void saveStaffList() {
        List<Staff> staffList = new ArrayList<>(staffTable.getItems());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("staff.ser"))) {
            oos.writeObject(staffList);
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
