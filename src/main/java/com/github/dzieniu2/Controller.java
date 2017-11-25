package com.github.dzieniu2;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.apache.poi.util.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Controller {

    @FXML private Label labelSelectedFile,labelPattern,labelSelectedDirectory;
    @FXML private ListView listViewFilesList,listViewChosenFile,listViewPattern;

    private static File selectedFile,selectedDirectory,patternFile;
    private static String[]filenameList;
    private static ArrayList<TextFile> filesList;


    @FXML private void chooseFile() throws Exception{

        CustomFileChooser chooser = new CustomFileChooser();
        if(chooser.selectFile()!=null) {
            selectedFile = chooser.getSelectedFile();
            labelPattern.setText("  Pattern: "+selectedFile.getName());
            listViewPattern.getItems().clear();

            ArrayList<CustomString> arrayList;
            CustomFileReader customFileReader = new CustomFileReader();
            arrayList = customFileReader.readCustomString(selectedFile);

            for (int i = 0; i < arrayList.size(); i++) {
                listViewPattern.getItems().add(arrayList.get(i).getString());
            }
        }
    }

    @FXML private void chooseDirectory() throws Exception {

        CustomDirectoryChooser chooser = new CustomDirectoryChooser();
        if(chooser.selectDirectory()!=null) {
            selectedDirectory = chooser.getSelectedDirectory();
            filenameList = chooser.getFilesList();

            filesList = new ArrayList<>();
            for (int i = 0; i < filenameList.length; i++) {
                filesList.add(new TextFile(filenameList[i],selectedDirectory.getAbsolutePath()+"\\"+filenameList[i]));
            }
            labelSelectedDirectory.setText("  Selected directory: " + selectedDirectory.getAbsolutePath());
            refreshFilesList();
        }
    }


    @FXML private void sortByFilenameMatch() throws IOException{

        if(areChosen()) {
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

    @FXML private void lineComparison() {
        LineComparison lineComparison = new LineComparison();
        List<CustomString> csList = lineComparison.compare(patternFile, selectedFile);
        listViewChosenFile.getCellFactory();
        System.out.println(listViewChosenFile.getItems() + "@@s");
        ObservableList<CustomString> eL = listViewChosenFile.getItems();
        for (int i = 0; i < eL.size(); i++) {
            System.out.println(eL.get(i).getString());
        }


        listViewPattern.setCellFactory(new Callback<ListView<CustomString>, ListCell<CustomString>>(){
           @Override
           public ListCell<CustomString> call(ListView<CustomString> param) {
               return new ListCell<CustomString>() {
                    @Override
                   protected void updateItem(CustomString item, boolean empty) {
                       super.updateItem(item, empty);

                        if (!empty) {
                            setText(item.getString());
                            if (item.isEqual()) {
                                setStyle("-fx-control-inner-background: green;");
                            } else {
                                setStyle("-fx-control-inner-background: red;");
                            }
                        }
                   }
               };
           }
        });
    }

    private boolean areChosen(){

        if(selectedFile==null){
            return false;
        }else if(selectedDirectory==null){
            return false;
        }
        return true;
    }

    private void refreshFilesList() {

        listViewFilesList.getItems().clear();
        if(!filesList.equals(null)){
            for(int i=0;i<filesList.size();i++){

                final int tmp = i;
                Label label = new Label((i+1)+"."+filesList.get(i).getFilename());
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        listViewChosenFile.getItems().clear();
                        labelSelectedFile.setText("  Selected file: ".concat(filesList.get(tmp).getFilename()));

                        try{
                            ArrayList<CustomString> arrayList;
                            CustomFileReader customFileReader = new CustomFileReader();
                            arrayList = customFileReader.readCustomString(filesList.get(tmp).getFile());
                            patternFile = filesList.get(tmp).getFile();

                            for(int i=0;i<arrayList.size();i++){
                                listViewChosenFile.getItems().add(arrayList.get(i).getString());
                            }

                        }catch (IOException e){}
                    }
                });
                listViewFilesList.getItems().add(label);
            }
        }
    }
}
