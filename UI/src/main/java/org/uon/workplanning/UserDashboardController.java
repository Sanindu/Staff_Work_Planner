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
    @FXML
    public void initialize() {
        workDetailsTile.setOnMouseClicked(this::handleWorkDetailsTileClick);
    }



    private void handleWorkDetailsTileClick(MouseEvent event) {
        switchToView("WorkDetails.fxml", "Work Details");
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
