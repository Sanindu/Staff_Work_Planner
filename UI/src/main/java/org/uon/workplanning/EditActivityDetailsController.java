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

    // Method to set the activity object and initialize the text fields with its data
    public void setActivity(Activity activity) {
        this.activity = activity;
        typeField.setText(activity.getType());
        activityField.setText(activity.getActivity());
    }

    @FXML
    private void initialize() {
    }

    // Handle save button click event
    @FXML
    private void handleSave() {
        // Update the activity object with the values from the text fields
        activity.setType(typeField.getText());
        activity.setActivity(activityField.getText());

        // Close the dialog
        Stage stage = (Stage) typeField.getScene().getWindow();
        stage.close();
    }

    // Handle cancel button click event
    @FXML
    private void handleCancel() {
        // Close the dialog without saving changes
        Stage stage = (Stage) typeField.getScene().getWindow();
        stage.close();
    }
}
