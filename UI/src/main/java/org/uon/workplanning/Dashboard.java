package org.uon.workplanning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Dashboard extends Application {

    private static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        try {
            Dashboard.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            primaryStage.setTitle("Dashboard");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
