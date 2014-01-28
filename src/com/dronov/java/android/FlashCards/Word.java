package com.dronov.java.android.FlashCards;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 28.01.14
 * Time: 21:58
 * To change this template use File | Settings | File Templates.
 */
public class Word {
    private String russian;
    private String english;
    private String china;

    public Word() {}
    public Word(String russian, String english, String china) {
        this.russian = russian;
        this.english = english;
        this.china = china;
    }

    public String getRussian() {
        return russian;
    }

    public void setRussian(String russian) {
        this.russian = russian;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChina() {
        return china;
    }

    public void setChina(String china) {
        this.china = china;
    }

    @Override
    public boolean equals(Object o) {
        Word q = (Word)o;
        return q.getEnglish().equals(this.getEnglish()) && q.getChina().equals(this.getChina());
    }
}
