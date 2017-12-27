package com.github.dzieniu2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultController {

    @FXML
    private Label resultLabel;

    @FXML
    private Button closeButton;

    public void initialize() {};

    public void initData(String result) {
        resultLabel.setText(result);
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
