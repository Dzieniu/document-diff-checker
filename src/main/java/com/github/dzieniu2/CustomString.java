package com.github.dzieniu2;

public class CustomString {

    private String string;
    private int flag;

    public CustomString(String string){

        this.string = string;
        flag = 0;
    }

    public int countWords(){

        int counter = 0;
        boolean j = false;
        for(int i=0;i<string.length();i++){
            if(string.charAt(i)!=' ' && (string.charAt(i)!='.') && (string.charAt(i)!=',')){
                j = true;
            }
            if(((string.charAt(i)==' ') || (string.charAt(i)=='.') || (string.charAt(i)==',')) && (j==true)){
                counter++;
                j = false;
            }else{
                if(i==string.length()-1 && j==true){
                    counter++;
                }
            }
        }
        return counter;
    }

    public String hasLessWords(String string){

        if((this.countWords()<new CustomString(string).countWords()) || (this.countWords()==new CustomString(string).countWords())){
            return this.string;
        }else return string;
    }

    public String hasMoreWords(String string){

        if((this.countWords()>new CustomString(string).countWords()) || (this.countWords()==new CustomString(string).countWords())){
            return this.string;
        }else return string;
    }

    public String nextWord(){

        String word = "";
        boolean j = false;
        for(int i=flag;i<string.length();i++){
            if(string.charAt(i)!=' ' && (string.charAt(i)!='.') && (string.charAt(i)!=',')){
                j = true;
                word = word + string.charAt(i);

            }
            if(((string.charAt(i)==' ') || (string.charAt(i)=='.') || (string.charAt(i)==',')) && (j==true)){
                return word;
            }else{
                if(i==string.length()-1 && j==true){
                    flag++;
                    return word;
                }
            }
            flag++;
        }
        return null;
    }

    public int countSentences(){

        int counter = 0;
        boolean j = false;
        for(int i=0;i<string.length();i++){
            if(string.charAt(i)!=' ' && (string.charAt(i)!='.') && (string.charAt(i)!=',')){
                j = true;
            }
            if((string.charAt(i)=='.') && (j==true)){
                counter++;
                j = false;
            }else{
                if(i==string.length()-1 && j==true){
                    counter++;
                }
            }
        }
        return counter;
    }

    public String nextSentence(){

        String word = "";
        boolean j = false;
        for(int i=flag;i<string.length();i++){
            if(string.charAt(i)!=' '){
                j=true;
            }
            if(string.charAt(i)!='.' && j==true){
                word = word + string.charAt(i);
                if(i==string.length()-1){
                    flag++;
                    return word;
                }
            }else if(string.charAt(i)=='.'){
                flag++;
                return word;
            }
            flag++;
        }
        return null;
    }

    public String getString(){
        return string;
    }

    public int getFlag() {
        return flag;
    }
}