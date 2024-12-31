package org.uon.workplanning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StaffRegister extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            StaffRegister.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("StaffDetails.fxml"));
            primaryStage.setTitle("Staff Management");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchToRegisterView() {
        try {
            Parent root = FXMLLoader.load(StaffRegister.class.getResource("StaffRegister.fxml"));
            primaryStage.setScene(new Scene(root, 400, 450));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchToDetailsView() {
        try {
            Parent root = FXMLLoader.load(StaffRegister.class.getResource("StaffDetails.fxml"));
            primaryStage.setScene(new Scene(root, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
