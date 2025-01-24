package org.uon.workplanning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginMain extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Login");

        // Load the FXML file for the login screen and set it as the root of the scene
        StackPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root, 400, 300);

        // Apply the external stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Set the scene on the primary stage and display it
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
