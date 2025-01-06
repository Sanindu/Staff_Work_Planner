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
            Parent root = FXMLLoader.load(getClass().getResource("ActivityDetails.fxml"));
            primaryStage.setTitle("Activity Management");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void switchToCreateView() {
//        try {
//            Parent root = FXMLLoader.load(StaffRegister.class.getResource("NewActivity.fxml"));
//            primaryStage.setScene(new Scene(root, 400, 450));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void switchToDetailsView() {
//        try {
//            Parent root = FXMLLoader.load(StaffRegister.class.getResource("ActivityDetails.fxml"));
//            primaryStage.setScene(new Scene(root, 800, 600));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public static void main(String[] args) {
        launch(args);
    }

}
