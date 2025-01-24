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
    private TableView<Work> workTable; // TableView for displaying work details
    @FXML
    private TableColumn<Work, String> staffIdColumn; // Column for staff ID
    @FXML
    private TableColumn<Work, String> nameColumn; // Column for staff name
    @FXML
    private TableColumn<Work, String> typeColumn; // Column for work type
    @FXML
    private TableColumn<Work, String> activityColumn; // Column for work activity
    @FXML
    private TableColumn<Work, String> descriptionColumn; // Column for work description
    @FXML
    private TableColumn<Work, String> yearColumn; // Column for the year
    @FXML
    private TableColumn<Work, Integer> durationColumn; // Column for duration of work
    @FXML
    private TableColumn<Work, Integer> instancesColumn; // Column for number of instances
    @FXML
    private TableColumn<Work, Integer> hoursColumn; // Column for total hours
    @FXML
    private TableColumn<Work, Integer> t1Column; // Column for Term 1 hours
    @FXML
    private TableColumn<Work, Integer> t2Column; // Column for Term 2 hours
    @FXML
    private TableColumn<Work, Integer> t3Column; // Column for Term 3 hours
    @FXML
    private TableColumn<Work, Integer> allYearColumn; // Column for all-year hours
    @FXML
    private TableColumn<Work, Double> sumColumn; // Column for the sum of specific calculations

    private Set<String> distinctTypes = new HashSet<>(); // Set to store unique work types
    private Map<String, String> staffData = new HashMap<>(); // Map to store staff data (ID to name)

    @FXML
    private TextField searchField; // TextField for searching work records
    private ObservableList<Work> workList = FXCollections.observableArrayList(); // ObservableList for work data

    // This method is called automatically when the controller is loaded.
    public void initialize() {
        loadStaffData(); // Load staff data from serialised file
        loadDistinctTypes(); // Load distinct work types from serialised file
        addTypeColumns(); // Dynamically add columns for distinct work types

        // Set cell value factories for predefined columns
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

        // Set up the name column to display the staff name based on the staff ID
        nameColumn.setCellValueFactory(cellData -> {
            Work work = cellData.getValue();
            String staffId = String.valueOf(work.getStaffId());
            return new SimpleStringProperty(staffData.getOrDefault(staffId, "Unknown"));
        });

        // Add a listener to the search field to filter work records in real time
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterWorkList(newValue));

        // Set the work data to the table and refresh it
        workTable.setItems(getWorkList());
        handleRefresh(); // Ensure table data is updated
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText(); // Get text from the search field
        filterWorkList(searchText); // Filter the work list based on the search text
    }

    // Load staff data from a serialised file (staff.ser)
    private void loadStaffData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff.ser"))) {
            List<Staff> staffList = (List<Staff>) ois.readObject(); // Deserialize staff list
            for (Staff staff : staffList) {
                staffData.put(String.valueOf(staff.getStaffId()), staff.getFullName()); // Map staff ID to name
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle file not found or deserialisation issues
        }
    }

    @FXML
    private void handleRefresh() {
        workList = getWorkList(); // Refresh work list
        workTable.setItems(workList); // Update table with refreshed data
    }

    // Load work data from a serialised file (workdetails.ser)
    private ObservableList<Work> getWorkList() {
        List<Work> workList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("workdetails.ser"))) {
            workList = (List<Work>) ois.readObject(); // Deserialize work list
            for (Work work : workList) {
                if (work.getTypeValue("dummy") == null) { // Ensure typeValues map is initialised
                    work.setTypeValue("dummy", ""); // Add dummy entry to initialise map
                    work.setTypeValue("dummy", null); // Remove dummy entry after initialisation
                }
            }
        } catch (EOFException | FileNotFoundException e) {
            // Handle end of file or missing file silently
        } catch (Exception e) {
            e.printStackTrace(); // Log other exceptions
        }
        return FXCollections.observableArrayList(workList); // Return as observable list
    }

    // Save the current work list to a serialised file (workdetails.ser)
    private void saveWorkList() {
        List<Work> workList = new ArrayList<>(workTable.getItems()); // Get all work records from the table
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("workdetails.ser"))) {
            oos.writeObject(workList); // Serialize and save the work list
        } catch (Exception e) {
            e.printStackTrace(); // Handle saving issues
        }
    }

    // Load distinct work types from the serialised file
    private void loadDistinctTypes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("workdetails.ser"))) {
            List<Work> activities = (List<Work>) ois.readObject(); // Deserialize work list
            for (Work activity : activities) {
                if (activity.getType().equals("ATSR")) {
                    distinctTypes.add("TS"); // Add special type if condition is met
                }
                distinctTypes.add(activity.getType()); // Add general type
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle deserialisation or file issues
        }
    }

    // Add dynamic columns to the table for each distinct work type
    private void addTypeColumns() {
        for (String type : distinctTypes) {
            TableColumn<Work, String> typeColumn = new TableColumn<>(type); // Create a new column for the type
            typeColumn.setCellValueFactory(cellData -> {
                Work work = cellData.getValue();
                if (work.getType().equals("ATSR")) {
                    double tsWeight = Double.parseDouble(ConfigLoader.getTSValue()); // Load weight from configuration
                    double tsVal = tsWeight * work.getHours(); // Calculate weighted value
                    String formattedValue = String.format("%.2f", tsVal); // Format as a string
                    work.setTypeValue("TS", formattedValue); // Set calculated value for "TS"
                } else {
                    work.setTypeValue("TS", "0"); // Default value for non-"ATSR" types
                }
                return new SimpleStringProperty(work.getTypeValue(type)); // Return the type value
            });
            typeColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // Make the column editable
            typeColumn.setOnEditCommit(event -> {
                Work work = event.getRowValue(); // Get the edited work record
                work.setTypeValue(type, event.getNewValue()); // Update the type value
                saveWorkList(); // Save the updated work list
            });
            workTable.getColumns().add(typeColumn); // Add the column to the table
        }
    }
    @FXML
    private void handleEditWork() {
        // Get the currently selected work item from the table
        Work selectedWork = workTable.getSelectionModel().getSelectedItem();

        if (selectedWork != null) {
            try {
                // Load the "EditWork.fxml" file for the edit work screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditWork.fxml"));
                Parent parent = loader.load();

                // Retrieve the controller for the edit work screen and pass the selected work to it
                EditWorkController controller = loader.getController();
                controller.setWork(selectedWork);

                // Create a new stage (window) for the edit work screen
                Stage stage = new Stage();
                stage.setTitle("Edit Work Details");
                stage.setScene(new Scene(parent));
                stage.showAndWait(); // Wait for the window to close before continuing

                // If the type of work is "ATSR", calculate the "TS" value and set it
                if (selectedWork.getType().equals("ATSR")) {
                    double tsWeight = Double.parseDouble(ConfigLoader.getTSValue()); // Retrieve weight from config
                    double tsVal = (tsWeight * selectedWork.getHours()); // Calculate TS value
                    String formattedValue = String.format("%.2f", tsVal); // Format value to 2 decimal places
                    selectedWork.setTypeValue("TS", formattedValue); // Set the calculated value
                } else {
                    // If the type is not "ATSR", set the TS value to "0"
                    selectedWork.setTypeValue("TS", "0");
                }

                // Additional calculation for "ATSR" to set the sum of hours and TS value
                if (selectedWork.getType().equals("ATSR")) {
                    double tsWeight = Double.parseDouble((ConfigLoader.getTSValue()));
                    double tsVale = (tsWeight * selectedWork.getHours());
                    selectedWork.setSum(selectedWork.getHours() + tsVale); // Sum of hours and TS value
                } else {
                    // For other types, the sum is just the number of hours
                    selectedWork.setSum(selectedWork.getHours());
                }

                // Save the updated work list to persist the changes
                saveWorkList();
                // Refresh the table to reflect updated data
                workTable.refresh();
            } catch (IOException e) {
                // Print the exception stack trace if an error occurs
                e.printStackTrace();
            }
        } else {
            // Show an alert if no work item is selected
            showAlert("No Selection", "Please select a work to edit.");
        }
    }

    @FXML
    private void handleDeleteWork() {
        // Get the currently selected work item from the table
        Work selectedWork = workTable.getSelectionModel().getSelectedItem();

        if (selectedWork != null) {
            // Remove the selected work item from the table
            workTable.getItems().remove(selectedWork);
            // Save the updated work list to persist the changes
            saveWorkList();
        } else {
            // Show an alert if no work item is selected
            showAlert("No Selection", "Please select a work to delete.");
        }
    }

    @FXML
    private void handleAddNewWork() {
        try {
            // Load the "NewWork.fxml" file for the add new work screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewWork.fxml"));
            Parent parent = loader.load();

            // Create a new stage (window) for the add new work screen
            Stage stage = new Stage();
            stage.setTitle("Add New Work");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            // Print the exception stack trace if an error occurs
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowTotalHours() {
        try {
            // Load the "TotalHours.fxml" file to display total hours
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TotalHours.fxml"));
            Parent parent = loader.load();

            // Create a new stage (window) to show the total hours screen
            Stage stage = new Stage();
            stage.setTitle("Total Hours");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            // Print the exception stack trace if an error occurs
            e.printStackTrace();
        }
    }

    private void filterWorkList(String searchText) {
        // If no search text is provided, display the entire work list
        if (searchText == null || searchText.isEmpty()) {
            workTable.setItems(workList);
        } else {
            // Create a filtered list of work items based on the search text
            ObservableList<Work> filteredList = FXCollections.observableArrayList();
            String lowerCaseFilter = searchText.toLowerCase();

            // Iterate through the work list to find matches
            for (Work work : workList) {
                String staffId = String.valueOf(work.getStaffId()); // Get staff ID as a string
                String fullName = staffData.getOrDefault(staffId, "Unknown"); // Get staff name or default to "Unknown"

                // Check if the staff ID or name contains the search text
                if (staffId.contains(lowerCaseFilter) || fullName.toLowerCase().contains(lowerCaseFilter)) {
                    filteredList.add(work); // Add matching work items to the filtered list
                }
            }
            // Set the filtered list in the table
            workTable.setItems(filteredList);
        }
    }

    private void showAlert(String title, String message) {
        // Create an informational alert with the specified title and message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message); // Set the content text
        alert.showAndWait(); // Display the alert and wait for user action
    }

}
