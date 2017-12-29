package com.github.dzieniu2.other;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FilenameFilter;

public class CustomDirectoryChooser {

    private File selectedDirectory;

    public CustomDirectoryChooser(){}

    public File selectDirectory(){

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose directory");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = new Stage();
        selectedDirectory = directoryChooser.showDialog(stage);
        return selectedDirectory;
    }

    public String[] getFilesList(){

        String[] filesList = selectedDirectory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if((getFileExtension(name).matches("txt")) || (getFileExtension(name).matches("doc"))
                        || (getFileExtension(name).matches("docx"))){
                    return true;
                }else{
                    return false;
                }
            }
        });
        return filesList;
    }

    private String getFileExtension(String string){

        String extension = "";
        int j = string.lastIndexOf('.');
        if (j > 0) {
            extension = string.substring(j+1);
        }
        return extension;
    }

    public File getSelectedDirectory(){
        return selectedDirectory;
    }
}
