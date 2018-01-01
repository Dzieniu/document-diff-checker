package com.github.dzieniu2.vo;

public class SentencePair {

    private Sentence firstSentence,secondSentence;
    private String sentence;

    public SentencePair(String sentence, Sentence firstSentence, Sentence secondSentence) {
        this.sentence = sentence;
        this.firstSentence = firstSentence;
        this.secondSentence = secondSentence;
    }

    public Sentence getFirstSentence() {
        return firstSentence;
    }

    public Sentence getSecondSentence() {
        return secondSentence;
    }

    public String getSentence() {
        return sentence;
    }
}
