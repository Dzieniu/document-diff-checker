package com.github.dzieniu2;

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

    public String readDoc(File selectedFile) throws IOException{

        FileInputStream fis = new FileInputStream(selectedFile);
        HWPFDocument document = new HWPFDocument(fis);
        fis.close();
        WordExtractor extractor = new WordExtractor(document);

        return extractor.getText();
    }

    public ArrayList<String> readDocLines(File selectedFile) throws IOException{

        FileInputStream fis = new FileInputStream(selectedFile);
        HWPFDocument document = new HWPFDocument(fis);
        fis.close();
        WordExtractor extractor = new WordExtractor(document);

        ArrayList<String> arrayList = new ArrayList<>();
        for(String para : extractor.getParagraphText()){
            arrayList.add(para);
        }
        return arrayList;
    }

    public String readDocx(File selectedFile) throws IOException{

        FileInputStream fis = new FileInputStream(selectedFile);
        XWPFDocument document = new XWPFDocument(fis);
        fis.close();
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        return extractor.getText();
    }

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

    public String readTxt(File selectedFile) throws IOException{

        BufferedReader reader  = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile),"UTF-8"));

        String line = "";
        String content = "";
        while((line = reader.readLine())!=null){
            content = content.concat(line).concat(System.lineSeparator());
        }
        reader.close();

        return content;
    }

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
}
