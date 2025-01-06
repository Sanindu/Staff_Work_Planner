package org.uon.workplanning;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TotalHoursController {

    @FXML
    private TableView<StaffHours> totalHoursTable;
    @FXML
    private TableColumn<StaffHours, String> staffIdColumn;
    @FXML
    private TableColumn<StaffHours, String> fullNameColumn;
    @FXML
    private TableColumn<StaffHours, String> totalHoursColumn;

    private Map<String, String> staffData = new HashMap<>();

    @FXML
    public void initialize() {
        loadStaffData();
        staffIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStaffId()));
        fullNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        totalHoursColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalHours()));

        totalHoursTable.setItems(getTotalHoursList());
    }

    private void loadStaffData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff.ser"))) {
            List<Staff> staffList = (List<Staff>) ois.readObject();
            for (Staff staff : staffList) {
                staffData.put(String.valueOf(staff.getStaffId()), staff.getFullName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<StaffHours> getTotalHoursList() {
        Map<String, Double> staffHoursMap = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("workdetails.ser"))) {
            List<Work> workList = (List<Work>) ois.readObject();
            for (Work work : workList) {
                String staffId = String.valueOf(work.getStaffId());
                staffHoursMap.put(staffId, staffHoursMap.getOrDefault(staffId, 0.0) + work.getSum());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(staffHoursMap.entrySet().stream()
                .map(entry -> new StaffHours(entry.getKey(), staffData.getOrDefault(entry.getKey(), "Unknown"), String.valueOf(entry.getValue())))
                .collect(Collectors.toList()));
    }

    public static class StaffHours {
        private final String staffId;
        private final String fullName;
        private final String totalHours;

        public StaffHours(String staffId, String fullName, String totalHours) {
            this.staffId = staffId;
            this.fullName = fullName;
            this.totalHours = totalHours;
        }

        public String getStaffId() {
            return staffId;
        }

        public String getFullName() {
            return fullName;
        }

        public String getTotalHours() {
            return totalHours;
        }
    }
}
