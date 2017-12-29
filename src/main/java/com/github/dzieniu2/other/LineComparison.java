package com.github.dzieniu2.other;

import com.github.dzieniu2.algorythm.KnuthMorrisPratt;

import java.util.ArrayList;
import java.util.List;

public class LineComparison {

    public void compare(List<CustomString> patternLines, List<CustomString> selectedLines) {
        
        boolean isEqual;

        if (patternLines.size() > selectedLines.size()) {
            List<CustomString> newLines = new ArrayList<>(patternLines.size());
            for (int i = 0; i < patternLines.size(); i++) {
                if (i < selectedLines.size())
                    newLines.add(selectedLines.get(i));
                else
                    newLines.add(new CustomString("hyhyhy"));
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
    
    private boolean isLonger(CustomString str1, CustomString str2) {
        return str1.getString().length() > str2.getString().length() ? true : false;
    }
}
