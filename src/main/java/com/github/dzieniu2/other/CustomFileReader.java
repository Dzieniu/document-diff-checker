package com.github.dzieniu2.other;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomFileReader {

    public CustomFileReader(){}

    // zwraca zawartosc pliku .doc
    public String readDocContent(File selectedFile) throws IOException{

        FileInputStream fis = new FileInputStream(selectedFile);
        HWPFDocument document = new HWPFDocument(fis);
        fis.close();
        WordExtractor extractor = new WordExtractor(document);

        return extractor.getText();
    }
    // zwraca zawartosc pliku doc jako liste linii
    public ArrayList<String> readDocLines(File selectedFile) throws IOException{

        FileInputStream fis = new FileInputStream(selectedFile);
        HWPFDocument document = new HWPFDocument(fis);
        fis.close();
        WordExtractor extractor = new WordExtractor(document);

        ArrayList<String> arrayList = new ArrayList<>();
        for(String para : extractor.getParagraphText()){
            para = para.replaceAll(System.lineSeparator(),"");
            arrayList.add(para);
        }
        return arrayList;
    }

    // zwraca zawartosc pliku .docx
    public String readDocxContent(File selectedFile) throws IOException{

        FileInputStream fis = new FileInputStream(selectedFile);
        XWPFDocument document = new XWPFDocument(fis);
        fis.close();
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        return extractor.getText();
    }

    // zwraca zawartosc pliku docx jako liste linii
    public ArrayList<String> readDocxLines(File selectedFile) throws IOException{

        FileInputStream fis = new FileInputStream(selectedFile);
        XWPFDocument document = new XWPFDocument(fis);
        fis.close();
        List<XWPFParagraph> list = document.getParagraphs();

        ArrayList<String> arrayList= new ArrayList<>();
        for (XWPFParagraph para : list) {
            arrayList.add(para.getText());
        }
        return arrayList;
    }

    // zwraca zawartosc pliku .txt
    public String readTxtContent(File selectedFile) throws IOException{

        BufferedReader reader  = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile),"UTF-8"));

        String line = "";
        String content = "";
        while((line = reader.readLine())!=null){
            content = content.concat(line).concat(System.lineSeparator());
        }
        reader.close();

        return content;
    }

    // zwraca zawartosc pliku txt jako liste linii
    public ArrayList<String> readTxtLines(File selectedFile) throws IOException{

        BufferedReader reader  = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile),"UTF-8"));

        String line = "";
        ArrayList<String> arrayList= new ArrayList<>();
        while((line = reader.readLine())!=null){
            arrayList.add(line);
        }
        reader.close();

        return arrayList;
    }

    public ArrayList<CustomString> readCustomString(File selectedFile) throws IOException {

        ArrayList<CustomString> arrayList= new ArrayList<>();
        for(String line : readLines(selectedFile)){
            arrayList.add(new CustomString(line));
        }

        return arrayList;
    }

    public String readContent(File selectedFile) throws IOException{

        String content = "";
        switch (getFileExtension(selectedFile.getName())) {
            case "txt":
                content = readTxtContent(selectedFile);
                break;
            case "doc":
                content = readDocContent(selectedFile);
                break;
            case "docx":
                content = readDocxContent(selectedFile);
                break;
        }

        return content;
    }

    public ArrayList<String> readLines(File selectedFile) throws IOException{

        ArrayList<String> arrayList = new ArrayList<>();
        switch (getFileExtension(selectedFile.getName())) {
            case "txt":
                arrayList = readTxtLines(selectedFile);
                break;
            case "doc":
                arrayList = readDocLines(selectedFile);
                break;
            case "docx":
                arrayList = readDocxLines(selectedFile);
                break;
        }
        return arrayList;
    }

    private String getFileExtension(String string){

        String extension = "";
        int j = string.lastIndexOf('.');
        if (j > 0) {
            extension = string.substring(j+1);
        }
        return extension;
    }
}
