package com.github.dzieniu2.sentenceComparison;

import com.github.dzieniu2.other.CustomFileReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Document {

    private File file;

    private Integer length;

    private ArrayList<Integer> linesLength;

    public Document(File file) throws IOException {
        this.file = file;

        CustomFileReader customFileReader = new CustomFileReader();
        //length init
        length = customFileReader.readContent(file).replaceAll(System.lineSeparator(),"").length();
        System.out.println(length);
        //linesLength init
        ArrayList<String> lines = customFileReader.readLines(file);
        linesLength = new ArrayList<>();
        for(String line : lines){
            line = line.replaceAll(System.lineSeparator(),"");
            linesLength.add(line.length());
        }

        for(Integer length : linesLength){
            System.out.println(length);
        }
    }

    public Integer getLength(){
        return length;
    }

    public ArrayList<Integer> getLinesLength(){
        return linesLength;
    }

    public Line getlineForIndex(int index){
        if(index<=length){
            int counter = 0;
            for(int i=0;i<linesLength.size();i++){
                for(int j=0;j<linesLength.get(i);j++){
                    counter++;
                    if(counter==index) return new Line(i+1,j+1);
                }
            }
        }
        return null;
    }
}
