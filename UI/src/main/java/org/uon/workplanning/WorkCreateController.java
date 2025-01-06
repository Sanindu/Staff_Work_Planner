package org.uon.workplanning;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkCreateController {
    @FXML
    private ComboBox<String> staffIdComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<String>  activityComboBox;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<String> yearComboBox;

    @FXML
    private TextField activityDurationField;

    @FXML
    private TextField instancesField;


    public void initialize() {
        yearComboBox.setValue("Trimester 1");
        loadStaffIds();
        loadTypes();
        loadActivity();
    }

    private void loadStaffIds() {
        List<String> staffIds = readStaffList().stream()
                .map(staff -> String.valueOf(staff.getStaffId()))
                .collect(Collectors.toList());
        ObservableList<String> observableStaffIds = FXCollections.observableArrayList(staffIds);
        staffIdComboBox.setItems(observableStaffIds);
    }

    private List<Staff> readStaffList() {
        List<Staff> staffList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("staff.ser"))) {
            staffList = (List<Staff>) ois.readObject();
        } catch (EOFException | FileNotFoundException e) {
            // Handle exceptions
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

    private void loadTypes() {
        List<String> typeIds = readTypeList().stream()
                .map(type -> String.valueOf(type.getType()))
                .collect(Collectors.toList());
        ObservableList<String> observableTypeIds = FXCollections.observableArrayList(typeIds);
        typeComboBox.setItems(observableTypeIds);
    }

    private List<Activity> readTypeList() {
        List<Activity> activityList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("activity.ser"))) {
            activityList = (List<Activity>) ois.readObject();
        } catch (EOFException | FileNotFoundException e) {
            // Handle exceptions
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityList;
    }

    private void loadActivity() {
        List<String> activityIds = readActivityList().stream()
                .map(activity -> String.valueOf(activity.getType()))
                .collect(Collectors.toList());
        ObservableList<String> observableActivityIds = FXCollections.observableArrayList(activityIds);
        activityComboBox.setItems(observableActivityIds);
    }

    private List<Activity> readActivityList() {
        List<Activity> activityList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("activity.ser"))) {
            activityList = (List<Activity>) ois.readObject();
        } catch (EOFException | FileNotFoundException e) {
            // Handle exceptions
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityList;
    }

    @FXML
    private void handleSave() {
        int newWorkId = getLastWorkId() + 1;
        int staffId = Integer.parseInt(staffIdComboBox.getValue());
        String type = typeComboBox.getValue();
        String activity = activityComboBox.getValue();
        String description = descriptionField.getText();
        String week = yearComboBox.getValue();
        int activityDuration = Integer.parseInt(activityDurationField.getText());
        int instances = Integer.parseInt(instancesField.getText());
        int hours = calHours();
int t1 =0;
int t2 =0;
int t3 = 0;
int allYear =0;
        switch (week){
            case "Trimester 1":
                 t1 = hours;
                break;
            case "Trimester 2":
                 t2 =hours;
                break;
            case "Trimester 3":
                 t3 = hours;
                break;
            case "All Year":
                 allYear = hours;
                break;
        }
        double sum = 0;
        if (type.equals("ATSR")){
            double tsWeight = Double.parseDouble((ConfigLoader.getTSValue()));
            double tsVale =(tsWeight * hours);
            sum = (hours+tsVale);
        }
        else {
            sum = hours;
        }

        Work work = new Work(newWorkId,staffId,type,activity,description,week,activityDuration, instances,hours,t1,t2,t3,allYear,sum);
        List<Work> workList = readWorkList();
        workList.add(work);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("workdetails.ser"))) {
            oos.writeObject(workList);
            showAlert("Success", "New work details added successfully!");
              WorkDetails.switchToDetailsView();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

private int calHours(){
        return (Integer.parseInt(activityDurationField.getText())*Integer.parseInt(instancesField.getText()));
}
    private int getLastWorkId() {
        List<Work> workList = readWorkList();
        return workList.stream().mapToInt(Work::getWorkId).max().orElse(0);
    }

    @SuppressWarnings("unchecked")
    private List<Work> readWorkList() {
        List<Work> workList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("workdetails.ser"))) {
            workList = (List<Work>) ois.readObject();
        } catch (EOFException e) {
            // End of file reached, return empty list
        } catch (FileNotFoundException e) {
            // File not found, return empty list
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workList;
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleCancel() {
        try {
            WorkDetails.switchToDetailsView();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
