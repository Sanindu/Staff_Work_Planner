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

    @FXML
    public void initialize() {
        staffTile.setOnMouseClicked(this::handleStaffTileClick);
        workDetailsTile.setOnMouseClicked(this::handleWorkDetailsTileClick);
        activityTile.setOnMouseClicked(this::handleActivityTileClick);
        csvUploadTile.setOnMouseClicked(this::handleCsvUploadTileClick);
    }

    private void handleStaffTileClick(MouseEvent event) {
        switchToView("StaffDetails.fxml", "Staff");
    }

    private void handleWorkDetailsTileClick(MouseEvent event) {
        switchToView("WorkDetails.fxml", "Work Details");
    }

    private void handleActivityTileClick(MouseEvent event) {
        switchToView("ActivityDetails.fxml", "Activity");
    }

    private void handleCsvUploadTileClick(MouseEvent event) {
        switchToView("CSVEditor.fxml", "CSV Upload");
    }

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
