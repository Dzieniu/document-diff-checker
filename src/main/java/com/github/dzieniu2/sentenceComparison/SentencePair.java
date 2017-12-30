package com.github.dzieniu2.sentenceComparison;

import com.github.dzieniu2.sentenceComparison.Sentence;

public class SentencePair {

    private Sentence firstSentence,secondSentence;

    public SentencePair(Sentence firstSentence, Sentence secondSentence) {
        this.firstSentence = firstSentence;
        this.secondSentence = secondSentence;
    }

    public Sentence getFirstSentence() {
        return firstSentence;
    }

    public void setFirstSentence(Sentence firstSentence) {
        this.firstSentence = firstSentence;
    }

    public Sentence getSecondSentence() {
        return secondSentence;
    }

    public void setSecondSentence(Sentence secondSentence) {
        this.secondSentence = secondSentence;
    }
}
