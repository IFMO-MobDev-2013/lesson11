package ru.ifmo.ctd.koshik.database;

public class Word {

    private int id;
    private String category;
    private String word;
    private int numOfCorrect;
    private int numOfAttempts;

    public Word() {
        numOfAttempts = 1;
    };

    public void setId(int id1) {
        id = id1;
    }

    public void setCategory(String c) {
        category = c;
    }

    public void setWord(String w) {
        word = w;
    }

    public void setNumOfCorrect(int correct) {
        numOfCorrect = correct;
    }

    public void setNumOfAttempts(int n) {
        numOfAttempts = n;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getWord () {
        return word;
    }

    public int getNumOfCorrect() {
        return numOfCorrect;
    }

    public int getNumOfAttempts() {
        return numOfAttempts;
    }
}
