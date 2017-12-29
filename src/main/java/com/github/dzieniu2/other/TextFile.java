package com.github.dzieniu2.other;

import com.github.dzieniu2.algorythm.KnuthMorrisPratt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextFile {

    private String filename,filepath;

    public TextFile(String filename, String filepath) throws IOException{
        this.filename = filename;
        this.filepath = filepath;
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

    public ArrayList<SentencePair> getSentenceMatch(File patternFile) throws IOException {

        ArrayList<SentencePair> sentencePairs = new ArrayList<>();

        CustomFileReader customFileReader = new CustomFileReader();

        CustomString patternFileString = new CustomString(customFileReader.readContent(patternFile));
        CustomString selectedFileString = new CustomString(customFileReader.readContent(new File(filepath)));

        Document patternDocument = new Document(patternFile);
        Document selectedDocument = new Document(new File(filepath));

        String sentence = "";
        Sentence patternSentence,selectedSentence;
        while ((sentence = patternFileString.nextSentence()) != null){
            Integer index = KnuthMorrisPratt.KMP_alg(selectedFileString.getString().replaceAll(System.lineSeparator(),""),sentence);
            System.out.println(index);
            if(index!=null) {
                patternSentence = new Sentence(patternDocument.getlineForIndex(patternFileString.getString()
                        .substring(0,patternFileString.getFlag()-sentence.length())
                        .replaceAll(System.lineSeparator(),"").length()),null);
                selectedSentence = new Sentence(selectedDocument.getlineForIndex(index),null);
                sentencePairs.add(new SentencePair(patternSentence,selectedSentence));
            }
        }
        return sentencePairs;
    }

    public File getFile(){

        return new File(filepath);
    }

    public String getFilename() {
        return filename;
    }
}
