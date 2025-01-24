package org.uon.workplanning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CSVEditor extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Dynamic CSV Table Viewer");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("CSVEditor.fxml"))));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
