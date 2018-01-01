package com.github.dzieniu2.vo;

public class SentenceRow {

    private String sentence, patternBeginLine, patternBeginIndex, selectedBeginLine, selectedBeginIndex;

    public SentenceRow(String sentence, String patternBeginLine,
                       String patternBeginIndex, String selectedBeginLine, String selectedBeginIndex)
    {
        this.sentence = sentence;
        this.patternBeginLine = patternBeginLine;
        this.patternBeginIndex = patternBeginIndex;
        this.selectedBeginLine = selectedBeginLine;
        this.selectedBeginIndex = selectedBeginIndex;
    }

    public String getSentence() {
        return sentence;
    }

    public String getPatternBeginLine() {
        return patternBeginLine;
    }

    public String getPatternBeginIndex() {
        return patternBeginIndex;
    }

    public String getSelectedBeginLine() {
        return selectedBeginLine;
    }

    public String getSelectedBeginIndex() {
        return selectedBeginIndex;
    }
}
