package org.uon.workplanning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class WorkDetails extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        try {
            WorkDetails.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("WorkDetails.fxml"));
            primaryStage.setTitle("Work Management");
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
