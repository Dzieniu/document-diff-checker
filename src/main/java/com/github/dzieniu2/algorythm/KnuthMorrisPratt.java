package com.github.dzieniu2;

import java.util.ArrayList;

public class KnuthMorrisPratt {

    private static int m,n,i,j,t;
    private static ArrayList<Integer>P = new ArrayList<>();

    public KnuthMorrisPratt(){}

    public static Integer KMP_alg(String text, String pattern) {

        n = text.length();
        m = pattern.length();

        P.add(0);
        P.add(0);
        t=0;
        for (j=2; j<=m; j++)
        {
            while ((t>0)&&(pattern.charAt(t)!=pattern.charAt(j-1))) t=P.get(t);
            if (pattern.charAt(t)==pattern.charAt(j-1)) t++;
            P.add(j,t);
        }

        i=1; j=0;
        while (i<=n-m+1)
        {
            j=P.get(j);
            while((j<m)&&(pattern.charAt(j)==text.charAt(i+j-1))) j++;
            if (j==m) return i;
            i=i+Math.max(1,j-P.get(j));
        }
        return null;
    }
}

