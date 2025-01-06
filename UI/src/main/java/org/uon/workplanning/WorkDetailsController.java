package org.uon.workplanning;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.Properties;

public class WorkDetailsController {
    @FXML
    private TableView<Work> workTable;
    @FXML
    private TableColumn<Work, String> staffIdColumn;
    @FXML
    private TableColumn<Work, String> nameColumn;
    @FXML
    private TableColumn<Work, String> typeColumn;
    @FXML
    private TableColumn<Work, String> activityColumn;
    @FXML
    private TableColumn<Work, String> descriptionColumn;
    @FXML
    private TableColumn<Work, String> yearColumn;
    @FXML
    private TableColumn<Work, Integer> durationColumn;
    @FXML
    private  TableColumn<Work,Integer> instancesColumn;
    @FXML
    private TableColumn<Work, Integer> hoursColumn;
    @FXML
    private TableColumn<Work, Integer> t1Column;
    @FXML
    private TableColumn<Work, Integer> t2Column;
    @FXML
    private TableColumn<Work, Integer> t3Column;
    @FXML
    private TableColumn<Work, Integer> allYearColumn;
    @FXML
    private  TableColumn <Work,Double> sumColumn;

    private Set<String> distinctTypes = new HashSet<>();
    private Map<String, String> staffData = new HashMap<>();

    @FXML
    private TextField searchField;
    private ObservableList<Work> workList = FXCollections.observableArrayList();


    public void initialize() {
        loadStaffData();
        loadDistinctTypes();
        addTypeColumns();

        staffIdColumn.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("week"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        instancesColumn.setCellValueFactory(new PropertyValueFactory<>("instances"));
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));
        t1Column.setCellValueFactory(new PropertyValueFactory<>("t1"));
        t2Column.setCellValueFactory(new PropertyValueFactory<>("t2"));
        t3Column.setCellValueFactory(new PropertyValueFactory<>("t3"));
        allYearColumn.setCellValueFactory(new PropertyValueFactory<>("allYear"));
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        nameColumn.setCellValueFactory(cellData -> {
            Work work = cellData.getValue();
            String staffId = String.valueOf(work.getStaffId());
            return new SimpleStringProperty(staffData.getOrDefault(staffId, "Unknown"));
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterWorkList(newValue));
        workTable.setItems(getWorkList());
        handleRefresh();
    }
    @FXML
    private void handleSearch() {
        String searchText = searchField.getText();
        filterWorkList(searchText);
    }

    // Method to load staff data from staff.ser
    private void loadStaffData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff.ser"))) {
            List<Staff> staffList = (List<Staff>) ois.readObject();
            for (Staff staff : staffList) {
                staffData.put(String.valueOf(staff.getStaffId()), staff.getFullName());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        workList = getWorkList();
        workTable.setItems(workList);
    }

    // Loading the workdetails.ser file
    private ObservableList<Work> getWorkList() {
        List<Work> workList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("workdetails.ser"))) {
            workList = (List<Work>) ois.readObject();
            // Ensure typeValues map is initialized for each Work instance
            for (Work work : workList) {
                if (work.getTypeValue("dummy") == null) { // Checking with a dummy key
                    work.setTypeValue("dummy", ""); // Initialize the map
                    work.setTypeValue("dummy", null); // Remove the dummy entry
                }
            }
        } catch (EOFException e) {
            // End of file reached, no more work details to read
        } catch (FileNotFoundException e) {
            // File not found, return empty list
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(workList);
    }

    // Saving the workdetails.ser file
    private void saveWorkList() {
        List<Work> workList = new ArrayList<>(workTable.getItems());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("workdetails.ser"))) {
            oos.writeObject(workList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to load distinct types from workdetails.ser
    private void loadDistinctTypes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("workdetails.ser"))) {
            List<Work> activities = (List<Work>) ois.readObject();
            for (Work activity : activities) {
                if(activity.getType().equals("ATSR")){
                    distinctTypes.add("TS");
                    }
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
            typeColumn.setCellValueFactory(cellData -> {
                Work work = cellData.getValue();
                if (work.getType().equals("ATSR")) {
                    // Set the value of 'TS' column to 1.2 * hours value
                    double tsWeight = Double.parseDouble((ConfigLoader.getTSValue()));
                    double tsVale =(tsWeight * work.getHours());
                    String formattedValue = String.format("%.2f", tsVale);
                    work.setTypeValue("TS", formattedValue);
                }
                else{
                    work.setTypeValue("TS", "0");
                }
                return new SimpleStringProperty(work.getTypeValue(type));
            });
            typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            typeColumn.setOnEditCommit(event -> {
                Work work = event.getRowValue();
                work.setTypeValue(type, event.getNewValue());
                saveWorkList(); // Save changes to the file
            });
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
                if (selectedWork.getType().equals("ATSR")) {
                    double tsWeight = Double.parseDouble(ConfigLoader.getTSValue());
                    double tsVal = (tsWeight * selectedWork.getHours());
                    String formattedValue = String.format("%.2f", tsVal);
                    selectedWork.setTypeValue("TS", formattedValue);
                }
                else{
                    selectedWork.setTypeValue("TS", "0");
                }
                if (selectedWork.getType().equals("ATSR")){
                    double tsWeight = Double.parseDouble((ConfigLoader.getTSValue()));
                    double tsVale =(tsWeight * selectedWork.getHours());
                    selectedWork.setSum(selectedWork.getHours()+tsVale);
                }
                else {
                     selectedWork.setSum(selectedWork.getHours());
                }

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
    @FXML
    private void handleShowTotalHours() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TotalHours.fxml"));
            Parent parent = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Total Hours");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void filterWorkList(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            workTable.setItems(workList);
        } else {
            ObservableList<Work> filteredList = FXCollections.observableArrayList();
            String lowerCaseFilter = searchText.toLowerCase();

            for (Work work : workList) {
                String staffId = String.valueOf(work.getStaffId());
                String fullName = staffData.getOrDefault(staffId, "Unknown");

                if (staffId.contains(lowerCaseFilter) || fullName.toLowerCase().contains(lowerCaseFilter)) {
                    filteredList.add(work);
                }
            }
            workTable.setItems(filteredList);
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
