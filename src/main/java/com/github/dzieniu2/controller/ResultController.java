package com.github.dzieniu2.controller;

import com.github.dzieniu2.vo.SentenceRow;
import com.github.dzieniu2.vo.WordRow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ResultController {

    @FXML
    private ComboBox comboBox;

    @FXML
    private Label numberOfSimilarLabel, similarityPercentLabel;

    @FXML
    private TableView resultTableView;

    @FXML
    private Label resultLabel;

    @FXML
    private Button closeButton;

    private int similarSentences,allSentences,similarWords,allWords;

    private ObservableList sentenceRows, wordRows;

    public void initialize() {};

    public void initData(String result) {
        resultLabel.setText(result);
    }

    public void sentenceComparison(int similarSentences, int allSentences, ObservableList sentenceRows,
                                   int similarWords, int allWords, ObservableList wordRows) {

        this.similarSentences = similarSentences;
        this.allSentences = allSentences;
        this.sentenceRows = sentenceRows;

        this.similarWords = similarWords;
        this.allWords = allWords;
        this.wordRows = wordRows;

        configureView();

        comboBox.getSelectionModel().selectFirst();
        loadSentenceView();
    }

    public void loadSentenceView(){

        resultTableView.getColumns().clear();
        resultTableView.getColumns().addAll(columnSentence,columnPattern,columnSelected);

        resultTableView.getItems().clear();
        resultTableView.getItems().addAll(sentenceRows);

        similarityPercentLabel.setText("Sentence similiarity: "+((double) similarSentences/ allSentences)*100 +"%");
        numberOfSimilarLabel.setText("Matching sentences found:"+similarSentences);
    }

    public void loadWordView(){

        resultTableView.getColumns().clear();
        resultTableView.getColumns().addAll(columnWord,columnWordCount);

        resultTableView.getItems().clear();
        resultTableView.getItems().addAll(wordRows);

        similarityPercentLabel.setText("Word similiarity: "+((double) similarWords / allWords)*100 +"%");
        numberOfSimilarLabel.setText("Matching words found:"+ similarWords);
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private TableColumn

            columnSentence,
            columnPattern,
            columnSelected,
            columnPatternBeginLine,
            columnPatternBeginIndex,
            columnSelectedBeginLine,
            columnSelectedBeginIndex,
            columnWord,
            columnWordCount;

    public void configureView(){

        columnSentence = new TableColumn();
        columnSentence.setPrefWidth(205);
        columnSentence.setText("Sentence");

        columnPattern = new TableColumn();
        columnPattern.setPrefWidth(160);
        columnPattern.setText("Pattern");

        columnSelected = new TableColumn();
        columnSelected.setPrefWidth(160);
        columnSelected.setText("Selected");

        columnPatternBeginLine = new TableColumn();
        columnPatternBeginLine.setMinWidth(80);
        columnPatternBeginLine.setText("Line");

        columnPatternBeginIndex = new TableColumn();
        columnPatternBeginIndex.setMinWidth(80);
        columnPatternBeginIndex.setText("Index");

        columnSelectedBeginLine = new TableColumn();
        columnSelectedBeginLine.setMinWidth(80);
        columnSelectedBeginLine.setText("Line");

        columnSelectedBeginIndex = new TableColumn();
        columnSelectedBeginIndex.setMinWidth(80);
        columnSelectedBeginIndex.setText("Index");

        columnWord = new TableColumn();
        columnWord.setMinWidth(300);
        columnWord.setText("Word");

        columnWordCount = new TableColumn();
        columnWordCount.setMinWidth(225);
        columnWordCount.setText("Found");

        columnPattern.getColumns().addAll(columnPatternBeginLine,columnPatternBeginIndex);
        columnSelected.getColumns().addAll(columnSelectedBeginLine,columnSelectedBeginIndex);

        resultTableView.getColumns().addAll(columnSentence,columnPattern,columnSelected);

        comboBox.getItems().addAll("sentence","word");
        comboBox.valueProperty().addListener((ChangeListener<String>) (observableValue, oldValue, newValue) -> {
            if(newValue.matches("sentence")) loadSentenceView();
            if(newValue.matches("word")) loadWordView();
        });

        columnSentence.setCellValueFactory(
                new PropertyValueFactory<SentenceRow, String>("sentence"));

        columnPatternBeginLine.setCellValueFactory(
                new PropertyValueFactory<SentenceRow, String>("patternBeginLine"));
        columnPatternBeginIndex.setCellValueFactory(
                new PropertyValueFactory<SentenceRow, String>("patternBeginIndex"));
        columnSelectedBeginLine.setCellValueFactory(
                new PropertyValueFactory<SentenceRow, String>("selectedBeginLine"));
        columnSelectedBeginIndex.setCellValueFactory(
                new PropertyValueFactory<SentenceRow, String>("selectedBeginIndex"));

        columnWord.setCellValueFactory(
                new PropertyValueFactory<WordRow, String>("word"));
        columnWordCount.setCellValueFactory(
                new PropertyValueFactory<WordRow, String>("counter"));
    }
}
