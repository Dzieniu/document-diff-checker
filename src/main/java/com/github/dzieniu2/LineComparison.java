package com.github.dzieniu2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineComparison {

    public List<CustomString> compare(File pattern, File file) {
        CustomFileReader fileReader = new CustomFileReader();
        List<CustomString> linesPattern = new ArrayList<>();
        List<CustomString> linesFile = new ArrayList<>();

        try {
            linesPattern = fileReader.readCustomString(pattern);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            linesFile = fileReader.readCustomString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < linesPattern.size(); i++) {
            if (linesFile.get(i).getString().length() > linesPattern.get(i).getString().length()) {
                System.out.println(KnuthMorrisPratt.KMP_alg(linesPattern.get(i).getString(), linesFile.get(i).getString()));
                System.out.println(KnuthMorrisPratt.KMP_alg(linesPattern.get(i).getString(), linesFile.get(i).getString()) == null ? "false" : true);
                linesFile.get(i).setIsEqual(KnuthMorrisPratt.KMP_alg(linesPattern.get(i).getString(), linesFile.get(i).getString()) == null ? false : true);
                linesPattern.get(i).setIsEqual(KnuthMorrisPratt.KMP_alg(linesPattern.get(i).getString(), linesFile.get(i).getString()) == null ? false : true);
            }

            else {
                System.out.println(KnuthMorrisPratt.KMP_alg(linesFile.get(i).getString(), linesPattern.get(i).getString()));
                System.out.println(KnuthMorrisPratt.KMP_alg(linesPattern.get(i).getString(), linesFile.get(i).getString()) == null ? "false" : true);
                linesFile.get(i).setIsEqual(KnuthMorrisPratt.KMP_alg(linesPattern.get(i).getString(), linesFile.get(i).getString()) == null ? false : true);
                linesPattern.get(i).setIsEqual(KnuthMorrisPratt.KMP_alg(linesPattern.get(i).getString(), linesFile.get(i).getString()) == null ? false : true);
            }

        }
        return linesPattern;
    }

}
