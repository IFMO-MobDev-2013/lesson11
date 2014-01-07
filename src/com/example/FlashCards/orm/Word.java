package com.example.FlashCards.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * User: Xottab
 * Date: 05.01.14
 */
@DatabaseTable
public class Word implements Serializable, Comparable<Word> {

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField(foreign = true)
    private Category category;

    @DatabaseField(canBeNull = false)
    private int status;

    @DatabaseField(canBeNull = false)
    private int arrayNumber;

    public Word(Category category, int arrayNumber) {
        this.category = category;
        this.status = 0;
        this.arrayNumber = arrayNumber;
    }

    public Word() {
    }

    public int getArrayNumber() {
        return arrayNumber;
    }

    public Category getCategory() {
        return category;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public int compareTo(Word word) {
        return status > word.status ? 1 : status == word.status ? 0 : -1;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
