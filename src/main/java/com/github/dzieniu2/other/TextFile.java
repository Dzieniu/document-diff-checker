package com.github.dzieniu2.other;

import com.github.dzieniu2.algorythm.KnuthMorrisPratt;
import com.github.dzieniu2.vo.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TextFile {

    private String filename,filepath;

    private Integer fileLenght;

    private ArrayList<Integer> linesLength;

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

        TextFile patternTextFile = new TextFile(patternFile.getName(),patternFile.getAbsolutePath());

        String sentence = "";
        Sentence patternSentence,selectedSentence;
        while ((sentence = patternFileString.nextSentence()) != null){
            Integer index = KnuthMorrisPratt.KMP_alg(selectedFileString.getString().replaceAll(System.lineSeparator(),""),sentence);
            if(index!=null) {
                patternSentence = new Sentence(patternTextFile.getlineForIndex(patternFileString.getString()
                        .substring(0,patternFileString.getFlag()-sentence.length())
                        .replaceAll(System.lineSeparator(),"").length()),null);
                selectedSentence = new Sentence(this.getlineForIndex(index),null);
                sentencePairs.add(new SentencePair(sentence,patternSentence,selectedSentence));
            }
        }
        return sentencePairs;
    }

    public HashMap<String,Integer> getWordMatch(File patternFile) throws IOException{

        HashMap<String,Integer> wordsContained = new HashMap<>();
        HashMap<String,Integer> wordsAll = new HashMap<>();
        ArrayList<String> wordsString = new ArrayList<>();

        CustomFileReader customFileReader = new CustomFileReader();

        CustomString patternFileString = new CustomString(customFileReader.readContent(patternFile).replaceAll(System.lineSeparator(),""));
        CustomString selectedFileString = new CustomString(customFileReader.readContent(new File(filepath)).replaceAll(System.lineSeparator(),""));

        {
        String word = "";
        while((word = selectedFileString.nextWord()) != null){
            wordsString.add(word);
        }}

        for(String word : wordsString){
           if (!wordsAll.containsKey(word)) wordsAll.put(word,1);
           else wordsAll.put(word, wordsAll.get(word)+1);
        }

        String word = "";
        while((word = patternFileString.nextWord()) != null){
            if(!wordsAll.containsKey(word)) wordsContained.put(word,0);
            else wordsContained.put(word, wordsAll.get(word));
        }
        return wordsContained;
    }

    public int getFileLenght() throws IOException {
        if(fileLenght==null)
            fileLenght = new CustomFileReader().readContent(getFile()).replaceAll(System.lineSeparator(),"").length();
        return fileLenght;
    }

    public ArrayList<Integer> getLinesLength() throws IOException {
        if(linesLength==null){
            ArrayList<String> lines = new CustomFileReader().readLines(getFile());
            linesLength = new ArrayList<>();
            for(String line : lines){
                line = line.replaceAll(System.lineSeparator(),"");
                linesLength.add(line.length());
            }
        }
        return linesLength;
    }

    public Line getlineForIndex(int index) throws IOException {
        if(index<=getFileLenght()){
            int counter = 0;
            for(int i=0;i<getLinesLength().size();i++){
                for(int j=0;j<getLinesLength().get(i);j++){
                    counter++;
                    if(counter==index) return new Line(i+1,j+1);
                }
            }
        }
        return null;
    }

    public File getFile(){

        return new File(filepath);
    }

    public String getFilename() {
        return filename;
    }

    public String getFilepath() {
        return filepath;
    }
}
