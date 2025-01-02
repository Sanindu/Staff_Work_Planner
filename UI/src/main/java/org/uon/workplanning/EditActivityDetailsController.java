package org.uon.workplanning;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditActivityDetailsController {

    @FXML
    private TextField typeField;
    @FXML
    private TextField activityField;

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
        typeField.setText(activity.getType());
        activityField.setText(activity.getActivity());
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleSave() {
        activity.setType(typeField.getText());
        activity.setActivity(activityField.getText());

        // Close the dialog
        Stage stage = (Stage) typeField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        // Close the dialog without saving
        Stage stage = (Stage) typeField.getScene().getWindow();
        stage.close();
    }
}
