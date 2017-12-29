package com.github.dzieniu2.other;

public class Sentence {

    private Line beginLine,endLine;

    public Sentence(Line beginLine, Line endLine) {
        this.beginLine = beginLine;
        this.endLine = endLine;
    }

    public Line getBeginLine() {
        return beginLine;
    }

    public Line getEndLine() {
        return endLine;
    }
}
