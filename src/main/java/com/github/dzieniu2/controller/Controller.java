package com.github.dzieniu2.controller;

import com.github.dzieniu2.other.*;
import com.github.dzieniu2.vo.SentencePair;
import com.github.dzieniu2.vo.TableItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Controller {

    @FXML
    private Label labelSelectedFile, labelPattern, labelSelectedDirectory;
    @FXML
    private ListView listViewFilesList, listViewChosenFile, listViewPattern;

    private static File selectedFile, selectedDirectory, patternFile;
    private static int patternFileIndex;
    private static String[] filenameList;
    private static ArrayList<TextFile> filesList;
    private static ArrayList<CustomString> selectedFileLines, patternFileLines;


    @FXML
    private void chooseFile() throws Exception {

        CustomFileChooser chooser = new CustomFileChooser();
        if (chooser.selectFile() != null) {
            selectedFile = chooser.getSelectedFile();
            labelSelectedFile.setText("  Selected file : " + selectedFile.getName());
            listViewChosenFile.getItems().clear();
            listViewChosenFile.setPadding(new Insets(0));

            CustomFileReader customFileReader = new CustomFileReader();
            selectedFileLines = customFileReader.readCustomString(selectedFile);

            for (int i = 0; i < selectedFileLines.size(); i++) {
                listViewChosenFile.getItems().add((i+1)+".  "+selectedFileLines.get(i).getString());
            }

        }
    }

    @FXML
    private void chooseDirectory() throws Exception {

        CustomDirectoryChooser chooser = new CustomDirectoryChooser();
        if (chooser.selectDirectory() != null) {
            selectedDirectory = chooser.getSelectedDirectory();
            filenameList = chooser.getFilesList();

            filesList = new ArrayList<>();
            for (int i = 0; i < filenameList.length; i++) {
                filesList.add(new TextFile(filenameList[i], selectedDirectory.getAbsolutePath() + "\\" + filenameList[i]));
            }
            labelSelectedDirectory.setText("  Selected directory: " + selectedDirectory.getAbsolutePath());
            refreshFilesList();
        }
    }


    @FXML
    private void sortByFilenameMatch() throws IOException {

        if (areChosen()) {
            Collections.sort(filesList, new Comparator<TextFile>() {
                public int compare(TextFile o1, TextFile o2) {
                    if (o1.getNameMatch(selectedFile) == o2.getNameMatch(selectedFile))
                        return 0;
                    return o1.getNameMatch(selectedFile) > o2.getNameMatch(selectedFile) ? -1 : 1;
                }
            });
            refreshFilesList();
        }
    }

    @FXML
    private void sentenceComparison() throws IOException {

        ArrayList<SentencePair> sentencePairs = filesList.get(patternFileIndex).getSentenceMatch(selectedFile);
        ObservableList<TableItem> result = FXCollections.observableArrayList();
        if(sentencePairs.size()>0) {
            for (SentencePair pair : sentencePairs) {
                result.add(new TableItem("\""+pair.getSentence()+"\"",pair.getFirstSentence().getBeginLine().getLineNumber()+"",
                        pair.getFirstSentence().getBeginLine().getIndexNumber()+"",
                        pair.getSecondSentence().getBeginLine().getLineNumber()+"",
                        pair.getSecondSentence().getBeginLine().getIndexNumber()+""));
            }
        }

        CustomFileReader customFileReader = new CustomFileReader();
        CustomString selectedFileString = new CustomString(customFileReader.readContent(selectedFile));
        int sentencesCount = 0;
        while(selectedFileString.nextSentence()!=null) sentencesCount++;

        //Word
        HashMap<String,Integer> wordsContained = filesList.get(patternFileIndex).getWordMatch(selectedFile);

        int simWordsCount = 0;
        for(Map.Entry<String, Integer> entry : wordsContained.entrySet()) {

            simWordsCount = simWordsCount+entry.getValue();
        }

        int allWordsCount = 0;
        CustomString customString = new CustomString(new CustomFileReader().readContent(selectedFile)
                .replaceAll(System.lineSeparator(),""));
        while(customString.nextWord()!=null){
            allWordsCount++;
        }

        showResultWindowSentence(sentencePairs.size(),sentencesCount,result, simWordsCount, allWordsCount, wordsContained);
    }

    @FXML
    private void wordComparison() throws IOException {

    }

    @FXML
    private void lineComparison() {
        LineComparison lineComparison = new LineComparison();
        lineComparison.compare(patternFileLines, selectedFileLines);

        listViewChosenFile.getItems().clear();


        for (int i = 0; i < selectedFileLines.size(); i++) {
            Label label = new Label(selectedFileLines.get(i).getString());
            label.setMinWidth(listViewChosenFile.getWidth());
            label.setMinHeight(24);
            if (selectedFileLines.get(i).isEqual())
                label.setStyle("-fx-background-color: greenyellow;");
            else
                label.setStyle("-fx-background-color: orangered");
            listViewChosenFile.getItems().add(label);
        }

        int lines = selectedFileLines.size();
        int equalLines = 0;
        for (CustomString str : selectedFileLines)
            if (str.isEqual()) equalLines++;

        double result = (equalLines/(double)lines) * 100.0;
        System.out.println("Podobieństwo plików : " + result  + "%");
        DecimalFormat format = new DecimalFormat(".##");
        showResultWindow(format.format(result).toString()+"%");
    }

    private void showResultWindow(String result) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/ResultWindow.fxml"
                    )
            );
            Stage stage = new Stage();
            stage.setScene(new Scene((Pane) loader.load()));
            ResultController resultController = loader.<ResultController>getController();
            resultController.initData(result);
            stage.setTitle("Results");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void showResultWindowSentence(int numberFound, int sentencesCount, ObservableList result,
                                          int simWordsCount, int allWordsCount, HashMap<String,Integer> wordsContained) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/ResultWindowSentence.fxml"
                    )
            );
            Stage stage = new Stage();
            stage.setScene(new Scene((Pane) loader.load()));
            ResultController resultController = loader.<ResultController>getController();
            resultController.initDataSentence(numberFound, sentencesCount, result, simWordsCount, allWordsCount, wordsContained);
            stage.setTitle("Results");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean areChosen() {

        if (selectedFile == null) {
            return false;
        } else if (selectedDirectory == null) {
            return false;
        }
        return true;
    }

    private void refreshFilesList() {

        listViewFilesList.getItems().clear();
        if (!filesList.equals(null)) {
            for (int i = 0; i < filesList.size(); i++) {

                final int tmp = i;
                Label label = new Label((i + 1) + "." + filesList.get(i).getFilename());
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        listViewPattern.getItems().clear();
                        labelPattern.setText("  Pattern file: ".concat(filesList.get(tmp).getFilename()));

                        try {
                            CustomFileReader customFileReader = new CustomFileReader();
                            patternFileLines = customFileReader.readCustomString(filesList.get(tmp).getFile());
                            patternFile = filesList.get(tmp).getFile();
                            patternFileIndex = tmp;

                            for (int i = 0; i < patternFileLines.size(); i++) {
                                listViewPattern.getItems().add((i+1)+".  "+patternFileLines.get(i).getString());
                            }

                        } catch (IOException e) {
                        }
                    }
                });
                listViewFilesList.getItems().add(label);
            }
        }
    }
}