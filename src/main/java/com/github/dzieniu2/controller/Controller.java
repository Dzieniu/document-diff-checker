package com.github.dzieniu2.controller;

import com.github.dzieniu2.other.*;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
                listViewChosenFile.getItems().add(selectedFileLines.get(i).getString());
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
        String result = "";
        if(sentencePairs.size()>0) {
            result = result + "Pattern:              Selected:"+System.lineSeparator();
            for (SentencePair pair : sentencePairs) {
                result = result + "Line: "+pair.getFirstSentence().getBeginLine().getLineNumber()+
                        ", Index: "+pair.getFirstSentence().getBeginLine().getIndexNumber()+
                        "     Line: "+pair.getSecondSentence().getBeginLine().getLineNumber()+
                        ", Index: "+pair.getSecondSentence().getBeginLine().getIndexNumber()+System.lineSeparator();
            }
        }
        System.out.println(result);
        showResultWindowSentence(sentencePairs.size(),result);
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

    private void showResultWindowSentence(int numberFound, String result) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/ResultWindowSentence.fxml"
                    )
            );
            Stage stage = new Stage();
            stage.setScene(new Scene((Pane) loader.load()));
            ResultController resultController = loader.<ResultController>getController();
            resultController.initDataSentence(numberFound, result);
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
                                listViewPattern.getItems().add(patternFileLines.get(i).getString());
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
