package com.github.dzieniu2;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Controller {

    @FXML private Label labelSelectedFile,labelPattern,labelSelectedDirectory;
    @FXML private ListView listViewFilesList,listViewChosenFile,listViewPattern;

    private static File selectedFile,selectedDirectory;
    private static String[]filenameList;
    private static ArrayList<TextFile> filesList;


    @FXML private void chooseFile() throws Exception{

        CustomFileChooser chooser = new CustomFileChooser();
        if(chooser.selectFile()!=null) {
            selectedFile = chooser.getSelectedFile();
            labelPattern.setText("  Pattern: "+selectedFile.getName());
            listViewPattern.getItems().clear();

            ArrayList<String> arrayList;
            CustomFileReader customFileReader = new CustomFileReader();
            arrayList = customFileReader.readLines(selectedFile);

            for (int i = 0; i < arrayList.size(); i++) {
                listViewPattern.getItems().add(arrayList.get(i));
            }
        }
    }

    @FXML private void chooseDirectory() throws Exception{

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
                            ArrayList<String> arrayList;
                            CustomFileReader customFileReader = new CustomFileReader();
                            arrayList = customFileReader.readLines(filesList.get(tmp).getFile());

                            for(int i=0;i<arrayList.size();i++){
                                listViewChosenFile.getItems().add(arrayList.get(i));
                            }

                        }catch (IOException e){}
                    }
                });
                listViewFilesList.getItems().add(label);
            }
        }
    }
}
