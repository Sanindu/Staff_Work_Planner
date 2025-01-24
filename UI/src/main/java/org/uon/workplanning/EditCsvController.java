package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditCsvController {
    @FXML
    private GridPane editGridPane;

    private CsvRow row;
    private Map<String, TextField> textFields;

    // Method to set the row to be edited and initialize text fields with column values
    public void setRow(CsvRow row, List<String> headers) {
        this.row = row;
        this.textFields = new HashMap<>();

        editGridPane.getChildren().clear();
        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            TextField textField = new TextField(row.getColumn(header).get());
            textFields.put(header, textField);
            editGridPane.addRow(i, new Label(header), textField);
        }
    }

    @FXML
    private void handleSave() {
        // Update the row with the values from the text fields
        for (Map.Entry<String, TextField> entry : textFields.entrySet()) {
            row.addColumn(entry.getKey(), entry.getValue().getText());
        }
        // Close the dialog
        Stage stage = (Stage) editGridPane.getScene().getWindow();
        stage.close();
    }
}
