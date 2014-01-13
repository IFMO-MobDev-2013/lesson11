package ru.ifmo.smelik.flashcards;

/**
 * Created by Nick Smelik on 12.01.14.
 */
public class Word {
    private long id;
    private int arrayID;
    private Category category;
    private int rating = 0;

    public Word(int arrayID, Category category) {
        this.arrayID = arrayID;
        this.category = category;
    }

    public Word(long id, int arrayID, Category category, int rating) {
        this.id = id;
        this.arrayID = arrayID;
        this.category = category;
        this.rating = rating;
    }

    public long getID() {
        return id;
    }

    public int getArrayID() {
        return arrayID;
    }

    public Category getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }
}
