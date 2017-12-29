package com.github.dzieniu2.other;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class CustomFileChooser {

    private File selectedFile;

    public CustomFileChooser(){}

    public File selectFile() throws IOException{

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose document");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text files", "*.doc","*.docx","*.txt"));
        Stage stage = new Stage();
        selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile;
    }

    public File getSelectedFile(){
        return selectedFile;
    }
}
