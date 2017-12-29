package com.github.dzieniu2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ResultController {

    @FXML
    private Label numberLabelSentence;

    @FXML
    private TextArea resultLabelSentence;

    @FXML
    private Label resultLabel;

    @FXML
    private Button closeButton;

    public void initialize() {};

    public void initData(String result) {
        resultLabel.setText(result);
    }

    public void initDataSentence(int number,String result) {
        System.out.println("Result\n"+result);
        resultLabelSentence.setText(result);
        numberLabelSentence.setText("Matching sentences found:"+number);
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
