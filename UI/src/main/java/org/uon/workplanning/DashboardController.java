package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private VBox staffTile;

    @FXML
    private VBox workDetailsTile;

    @FXML
    private VBox activityTile;

    @FXML
    private VBox csvUploadTile;

    // Initialize method to set click event handlers for the tiles
    @FXML
    public void initialize() {
        staffTile.setOnMouseClicked(this::handleStaffTileClick);
        workDetailsTile.setOnMouseClicked(this::handleWorkDetailsTileClick);
        activityTile.setOnMouseClicked(this::handleActivityTileClick);
        csvUploadTile.setOnMouseClicked(this::handleCsvUploadTileClick);
    }

    // Handle click event for the staff tile
    private void handleStaffTileClick(MouseEvent event) {
        switchToView("StaffDetails.fxml", "Staff");
    }

    // Handle click event for the work details tile
    private void handleWorkDetailsTileClick(MouseEvent event) {
        switchToView("WorkDetails.fxml", "Work Details");
    }

    // Handle click event for the activity tile
    private void handleActivityTileClick(MouseEvent event) {
        switchToView("ActivityDetails.fxml", "Activity");
    }

    // Handle click event for the CSV upload tile
    private void handleCsvUploadTileClick(MouseEvent event) {
        switchToView("CSVEditor.fxml", "CSV Upload");
    }

    // Switch to the specified view by loading the FXML file
    private void switchToView(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new javafx.scene.Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
