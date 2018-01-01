package com.github.dzieniu2.controller;

import com.github.dzieniu2.vo.TableItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.HashMap;

public class ResultController {

    @FXML
    private ComboBox comboBox;

    @FXML
    private TableColumn columnSentence,columnPatternBeginLine,columnPatternBeginIndex,
    columnSelectedBeginLine,columnSelectedBeginIndex;

    @FXML
    private Label numberLabelSentence,simLabel;

    @FXML
    private TableView resultLabelSentence;

    @FXML
    private Label resultLabel;

    @FXML
    private Button closeButton;

    private int similarSentences;

    private int sentencesCount;

    private ObservableList sentenceMatchResult;

    private int simWordsCount;

    private int allWordsCount;

    private HashMap<String,Integer> wordsContained;

    public void initialize() {};

    public void initData(String result) {
        resultLabel.setText(result);
    }

    public void initDataSentence(int number,int sentencesCount,ObservableList result,
                                 int simWordsCount, int allWordsCount, HashMap<String,Integer> wordsContained) {

        this.similarSentences = number;
        this.sentencesCount = sentencesCount;
        this.sentenceMatchResult = result;

        this.simWordsCount = simWordsCount;
        this.allWordsCount = allWordsCount;
        this.wordsContained = wordsContained;

        comboBox.getItems().addAll("sentence","word");
        comboBox.getSelectionModel().select(0);
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue observableValue, String oldValue, String newValue) {
                if(oldValue.toString().matches("word")) loadSentenceView();
                if(oldValue.toString().matches("sentence")) loadWordView();
            }
        });

        columnSentence.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("sentence"));

        columnPatternBeginLine.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("patternBeginLine"));
        columnPatternBeginIndex.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("patternBeginIndex"));
        columnSelectedBeginLine.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("selectedBeginLine"));
        columnSelectedBeginIndex.setCellValueFactory(
                new PropertyValueFactory<TableItem, String>("selectedBeginIndex"));

        resultLabelSentence.setItems(result);
        simLabel.setText("Sentence similiarity: "+((double) number/sentencesCount)*100 +"%");
        numberLabelSentence.setText("Matching sentences found:"+number);
    }

    public void loadSentenceView(){

        simLabel.setText("Sentence similiarity: "+((double) similarSentences/sentencesCount)*100 +"%");
        numberLabelSentence.setText("Matching sentences found:"+similarSentences);
    }

    public void loadWordView(){

        simLabel.setText("Word similiarity: "+((double) simWordsCount/allWordsCount)*100 +"%");
        numberLabelSentence.setText("Matching words found:"+simWordsCount);
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
