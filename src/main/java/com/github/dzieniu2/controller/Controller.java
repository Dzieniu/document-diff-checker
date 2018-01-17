package com.github.dzieniu2.controller;

import com.github.dzieniu2.other.*;
import com.github.dzieniu2.vo.SentencePair;
import com.github.dzieniu2.vo.SentenceRow;
import com.github.dzieniu2.vo.WordRow;
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

// w tej klasie odbywaja sie wszystki funkcje sterujace programem jak sortowanie,
// wyswietlanie zawartosci katalogow i plikow oraz wyswietlania wynikow
public class Controller {

    @FXML
    private Label labelSelectedFile, labelPattern, labelSelectedDirectory, resultLabel;
    @FXML
    private ListView listViewFilesList, listViewChosenFile, listViewPattern;

    private static File selectedFile, selectedDirectory, patternFile;
    private static int patternFileIndex;
    private static String[] filenameList;
    private static ArrayList<TextFile> filesList;
    private static ArrayList<CustomString> selectedFileLines, patternFileLines;


    // przycisk menu dzieki ktoremu mozemy wybierac plik
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

            sortByFilenameMatch();
        }
    }

    // przycisk menu dzieki ktoremu mozemy wybierac folder
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
            if (isFileChoosen())
                sortByFilenameMatch();
            else
                refreshFilesList();
        }
    }


    // sortuje liste plikow wedlug wzorca pattern
    @FXML
    private void sortByFilenameMatch() throws IOException {

        if (isDirectoryChoosen() && isFileChoosen()) {
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

    // porownuje pliki tekstowe zdanie po zdaniu
    // i przekazuje wynik do nowego okna
    @FXML
    private void sentenceComparison() throws IOException {

        CustomString selectedFileString = new CustomString(new CustomFileReader().readContent(selectedFile));

        ArrayList<SentencePair> sentencePairs = filesList.get(patternFileIndex).getSentenceMatch(selectedFile);

        ObservableList<SentenceRow> sentenceRows = FXCollections.observableArrayList();
        if(sentencePairs.size()>0) {
            for (SentencePair pair : sentencePairs) {
                sentenceRows.add(new SentenceRow("\""+pair.getSentence()+"\"",pair.getFirstSentence().getBeginLine().getLineNumber()+"",
                        pair.getFirstSentence().getBeginLine().getIndexNumber()+"",
                        pair.getSecondSentence().getBeginLine().getLineNumber()+"",
                        pair.getSecondSentence().getBeginLine().getIndexNumber()+""));
            }
        }

        int similarSentences = sentencePairs.size();
        int allSentences = selectedFileString.countSentences();

        //Word
        HashMap<String,Integer> wordsContained = filesList.get(patternFileIndex).getWordMatch(selectedFile);

        ObservableList<WordRow> wordRows = FXCollections.observableArrayList();
        if(wordsContained.size()>0) {
            for(Map.Entry<String, Integer> entry : wordsContained.entrySet()) {
                wordRows.add(new WordRow(entry.getKey(),entry.getValue()));
            }
        }

        int similarWords = 0;
        for(Map.Entry<String, Integer> entry : wordsContained.entrySet()) {

            similarWords = similarWords+entry.getValue();
        }

        int allWords = selectedFileString.countWords();

        showResultWindow(similarSentences,allSentences,sentenceRows, similarWords, allWords, wordRows);
    }

    // poronoje pliki linia po linii
    // i wyswietla na ekrania wynik wraz z zaznaczonymi zgodnymi liniami
    @FXML
    private void lineComparison() {
        LineComparison lineComparison = new LineComparison();
        lineComparison.compare(patternFileLines, selectedFileLines);

        listViewChosenFile.getItems().clear();

        for (int i = 0; i < selectedFileLines.size(); i++) {
            if (selectedFileLines.get(i).isEqual())
                listViewChosenFile.getItems().add(createLabel((i+1) + ". " + selectedFileLines.get(i).getString(), "red"));
            else
                listViewChosenFile.getItems().add(createLabel((i+1) + ". " + selectedFileLines.get(i).getString(), "green"));
        }

        int linesSizeDiff = patternFileLines.size() - selectedFileLines.size();
        for (int i = 0; i < linesSizeDiff; i++) {
            listViewChosenFile.getItems().add(createLabel("", "grey"));
            selectedFileLines.add(new CustomString(""));
        }

        int lines = selectedFileLines.size();
        int equalLines = 0;
        for (CustomString str : selectedFileLines)
            if (str.isEqual()) equalLines++;

        double result = (equalLines/(double)lines) * 100.0;
        DecimalFormat format = new DecimalFormat("###.##");
        resultLabel.setText("Podobieństwo plików : " + format.format(result).toString() + "%");
    }

    // tworzy etykiete
    private Label createLabel(String text, String color) {
        Label label = new Label(text);
        label.setMinWidth(listViewChosenFile.getWidth());
        label.setMinHeight(24);
        label.setStyle(getColor(color));
        return label;
    }

    // ustala kolor podkreslenia zgodnosci tekstu
    private String getColor(String color) {
        switch(color) {
            case "red": return "-fx-background-color: rgba(0, 255, 0, 0.5);";
            case "green": return "-fx-background-color: rgba(255, 0, 0, 0.5);";
            case "grey": return "-fx-background-color: rgba(96, 96, 96, 0.5);";
            default: return "-fx-background-color: rgba(96, 96, 96, 0.5);";
        }
    }

    // otwiera nowe okno i wyswietla tam wyniki
    // dzisla tylko w przypadku porownywania zdan nie linii
    private void showResultWindow(int similarSentences, int allSentences, ObservableList sentenceRows,
                                  int similarWords, int allWords, ObservableList wordRows) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/SentenceComparison.fxml"
                    )
            );
            Stage stage = new Stage();
            stage.setScene(new Scene((Pane) loader.load()));
            ResultController resultController = loader.<ResultController>getController();
            resultController.sentenceComparison(similarSentences, allSentences, sentenceRows, similarWords, allWords, wordRows);
            stage.setTitle("Results");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isDirectoryChoosen() {
        return selectedDirectory == null ? false : true;
    }

    private boolean isFileChoosen() {
        return selectedFile == null ? false : true;
    }

    // odwieza liste plikow w wybranym folderze
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

                        if (isDirectoryChoosen() && isFileChoosen())
                            lineComparison();
                    }
                });
                listViewFilesList.getItems().add(label);
            }
        }
    }
}
