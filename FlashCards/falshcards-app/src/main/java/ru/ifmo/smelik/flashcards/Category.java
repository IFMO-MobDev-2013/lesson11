package ru.ifmo.smelik.flashcards;

import java.util.ArrayList;

/**
 * Created by Nick Smelik on 12.01.14.
 */
public class Category {
    private int id;
    private ArrayList<Word> words;
    private int status;

    public Category(int id) {
        this.id = id;
    }

    public Category(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

}
