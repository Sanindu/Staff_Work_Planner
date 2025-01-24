package org.uon.workplanning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ActivityCreate extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            ActivityCreate.primaryStage = primaryStage;

            // Load the FXML file for the UI
            Parent root = FXMLLoader.load(getClass().getResource("ActivityDetails.fxml"));

            // Set the title of the primary stage (window)
            primaryStage.setTitle("Activity Management");

            // Set the scene (content) for the primary stage with specified dimensions
            primaryStage.setScene(new Scene(root, 800, 600));

            // Display the primary stage
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur
        }
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }

}
