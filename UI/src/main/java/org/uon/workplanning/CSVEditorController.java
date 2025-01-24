package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.beans.property.StringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CSVEditorController {

    @FXML
    private TableView<CsvRow> tableView;

    private List<String> headers;
    private File currentFile;

    // Handle file upload
    public void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            currentFile = file;
            loadCsv(file);
        }
    }

    // Load CSV file and display its content in the TableView
    private void loadCsv(File file) {
        List<CsvRow> rows = new ArrayList<>();
        headers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            headers.clear();
            String[] headerArray = br.readLine().split(",");
            for (String header : headerArray) {
                headers.add(header);
                TableColumn<CsvRow, String> column = new TableColumn<>(header);
                column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CsvRow, String>, javafx.beans.value.ObservableValue<String>>() {
                    public javafx.beans.value.ObservableValue<String> call(TableColumn.CellDataFeatures<CsvRow, String> param) {
                        return param.getValue().getColumn(header);
                    }
                });
                tableView.getColumns().add(column);
            }

            // Ensure all columns are added before reading data
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                CsvRow row = new CsvRow();
                for (int i = 0; i < headers.size(); i++) {
                    String value = (i < values.length) ? values[i] : ""; // Handle missing values
                    row.addColumn(headers.get(i), value);
                }
                rows.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tableView.getItems().setAll(rows);
    }

    // Handle editing of selected CSV row
    public void handleEdit(ActionEvent event) {
        CsvRow selectedRow = tableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCsv.fxml"));
                Parent parent = loader.load();

                EditCsvController controller = loader.getController();
                controller.setRow(selectedRow, headers);

                Stage stage = new Stage();
                stage.setTitle("Edit Record");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(parent));
                stage.showAndWait();

                tableView.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show warning alert if no row is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Record Selected");
            alert.setContentText("Please select a record in the table to edit.");
            alert.showAndWait();
        }
    }

    // Handle saving changes to the CSV file
    @FXML
    private void handleSaveChanges(ActionEvent event) {
        if (currentFile != null) {
            saveCsv(currentFile);
            // Show success alert
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Changes Saved Successfully");
            alert.showAndWait();
        } else {
            // Show error alert if no file is loaded
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No File Loaded");
            alert.setHeaderText("No CSV File Loaded");
            alert.setContentText("Please upload a CSV file before saving changes.");
            alert.showAndWait();
        }
    }

    // Save the updated data back to the CSV file
    private void saveCsv(File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(String.join(",", headers) + "\n");
            for (CsvRow row : tableView.getItems()) {
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    values.add(row.getColumn(header).get());
                }
                bw.write(String.join(",", values) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
