package com.github.dzieniu2.other;

import com.github.dzieniu2.algorythm.KnuthMorrisPratt;

import java.util.ArrayList;
import java.util.List;

// klasa wykorzystuje algorytm Knutha Morrisa Pratta aby stwierdzic czy podane ciagi znakow sa identyczne
public class LineComparison {

    // metoda przyjmuje jako parametr 2 listy z liniami, jedna jest wzorcem, druga jest plikiem ktory porownujemy
    // w petli sa porownywane wszystkie linie z 2 list, porownywanie jest wykonane na podstawie algorytmy KMP
    // jesli porownywanie alogrytmem KMP zwroci null oznacza to ze linie nie sa identyczne i oznaczamy je jako false
    public void compare(List<CustomString> patternLines, List<CustomString> selectedLines) {
        
        boolean isEqual;

        if (patternLines.size() > selectedLines.size()) {
            List<CustomString> newLines = new ArrayList<>(patternLines.size());
            for (int i = 0; i < patternLines.size(); i++) {
                if (i < selectedLines.size())
                    newLines.add(selectedLines.get(i));
                else
                    newLines.add(new CustomString(""));
            }
            selectedLines = newLines;

            for (int i = 0; i < newLines.size(); i++)
                selectedLines.set(i, newLines.get(i));
        }

        for (int i = 0; i < patternLines.size(); i++) {
            if (isLonger(selectedLines.get(i), patternLines.get(i))) {
                isEqual = KnuthMorrisPratt.KMP_alg(patternLines.get(i).getString(), selectedLines.get(i).getString()) == null ? false : true;
                selectedLines.get(i).setIsEqual(isEqual);
                patternLines.get(i).setIsEqual(isEqual);
            } else {
                isEqual = KnuthMorrisPratt.KMP_alg(selectedLines.get(i).getString(), patternLines.get(i).getString()) == null ? false : true;
                selectedLines.get(i).setIsEqual(isEqual);
                patternLines.get(i).setIsEqual(isEqual);
            }
        }
    }

    // sprawdza ktory ciag jest dluzszy
    private boolean isLonger(CustomString str1, CustomString str2) {
        return str1.getString().length() > str2.getString().length() ? true : false;
    }
}
