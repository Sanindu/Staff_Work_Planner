package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UserDashboardController {

    @FXML
    private VBox workDetailsTile;

    // Initialisation method to set the click event handler for the work details tile
    @FXML
    public void initialize() {
        workDetailsTile.setOnMouseClicked(this::handleWorkDetailsTileClick);
    }

    // Handle click event for the work details tile
    private void handleWorkDetailsTileClick(MouseEvent event) {
        switchToView("WorkDetails.fxml", "Work Details");
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
