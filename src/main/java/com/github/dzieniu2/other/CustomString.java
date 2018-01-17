package com.github.dzieniu2.other;

// klasa rozszerzajaca string a dokladnie implementujaca dodatkowe metody przydatne przy tworzeniu porownywania
// klasa zawiera rowniez pola flag i isEqual w celu pozniejszego porownywania dwoch linii
public class CustomString {

    private String string;
    private int flag;
    private boolean isEqual;

    public CustomString(String string){

        this.string = string;
        flag = 0;
        isEqual = false;
    }

    // zlicza slowa w ciagu
    public int countWords(){

        int counter = 0;
        CustomString holder = new CustomString(getString());
        while(holder.nextWord()!=null) counter++;
        return counter;
    }


    // sprawdza ktory ciag ma miej znakow
    // zwraca ten ciag kotry ma mniej znakow
    public String hasLessWords(String string){

        if((this.countWords()<new CustomString(string).countWords()) || (this.countWords()==new CustomString(string).countWords())){
            return this.string;
        }else return string;
    }

    // sprawdza ktory ciag ma wiecej znakow
    // zwraca ten ktory ma wiecej znakow
    public String hasMoreWords(String string){

        if((this.countWords()>new CustomString(string).countWords()) || (this.countWords()==new CustomString(string).countWords())){
            return this.string;
        }else return string;
    }

    // przechodzi do nastepnego slowa w okreslonym ciagu i go zwraca
    public String nextWord(){

        String word = "";
        boolean j = false;
        for(int i=flag;i<string.length();i++){
            if(string.charAt(i)!=' ' && (string.charAt(i)!='.')&& (string.charAt(i)!='?')
                    && (string.charAt(i)!='!') && (string.charAt(i)!=',')){
                j = true;
                word = word + string.charAt(i);

            }
            if(((string.charAt(i)==' ') || (string.charAt(i)=='.') || (string.charAt(i)==',') || (string.charAt(i)=='?')
                    || (string.charAt(i)=='!')) && (j==true)){
                word = word.replaceAll(System.lineSeparator(),"");
                if(word.length()==0) return  null;
                return word;
            }else{
                if(i==string.length()-1 && j==true){
                    flag++;
                    word = word.replaceAll(System.lineSeparator(),"");
                    if(word.length()==0) return  null;
                    return word;
                }
            }
            flag++;
        }
        return null;
    }

    // zlicza zdania
    public int countSentences(){

        int counter = 0;
        CustomString holder = new CustomString(getString());
        while(holder.nextSentence()!=null) counter++;
        return counter;
    }

    // zwraca kolejne zdanie w ciagu jesli nie wystepuje, zwraca null
    public String nextSentence(){

        String word = "";
        boolean j = false;
        for(int i=flag;i<string.length();i++){
            if(string.charAt(i)!=' '){
                j=true;
            }
            if((string.charAt(i)!='.' && string.charAt(i)!='!' && string.charAt(i)!='?') && j==true){
                word = word + string.charAt(i);
                if(i==string.length()-1){
                    flag++;
                    word = word.replaceAll(System.lineSeparator(),"");
                    if(word.length()==0) return  null;
                    return word;
                }
            }else if(string.charAt(i)=='.' || string.charAt(i)=='!' || string.charAt(i)=='?'){
                flag++;
                word = word.replaceAll(System.lineSeparator(),"");
                if(word.length()==0) return  null;
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

    public boolean isEqual() {
        return isEqual;
    }

    public void setIsEqual(boolean tof) {
        isEqual = tof;
    }

    @Override
    public String toString() {
        return "CustomString{" +
                "string='" + string + '\'' +
                ", flag=" + flag +
                ", isEqual=" + isEqual +
                '}';
    }
}
