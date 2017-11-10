package com.github.dzieniu2;

import java.io.File;
import java.io.IOException;

public class TextFile {

    private String filename;

    public TextFile(String filename) throws IOException{
        this.filename = filename;
    }

    public double getNameMatch(File patternFile){

        CustomString patternName = new CustomString(patternFile.getName().substring(0, patternFile.getName().lastIndexOf('.')).toLowerCase());
        CustomString targetName = new CustomString(filename.substring(0, filename.lastIndexOf('.')).toLowerCase());
        if (patternName.getString().matches(targetName.getString())) {
            return 1.1;
        } else {
            CustomString lessWords = new CustomString(patternName.hasLessWords(targetName.getString()));
            CustomString moreWords = new CustomString(targetName.hasMoreWords(patternName.getString()));
            double counter = 0.0;
            for (int j = 0; j < lessWords.countWords(); j++) {
                if (KnuthMorrisPratt.KMP_alg(moreWords.getString(), lessWords.nextWord()) != null) {
                    counter = counter + 1.0;
                }
            }
            return counter / moreWords.countWords();
        }
    }

    public File getFile(String directoryPath){

        File file = new File(directoryPath+"\\"+filename);
        return file;
    }

    public String getFilename() {
        return filename;
    }
}
