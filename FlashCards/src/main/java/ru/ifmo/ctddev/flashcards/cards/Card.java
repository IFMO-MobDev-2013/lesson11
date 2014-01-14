package ru.ifmo.ctddev.flashcards.cards;

import java.util.EnumMap;

public class Card {

    private int id;
    private int categoryId;
    private EnumMap<Language, String> title;

    public Card(int id, int categoryId, EnumMap<Language, String> title) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
    }

    public Card(int categoryId, EnumMap<Language, String> title) {
        this.categoryId = categoryId;
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setTitle(EnumMap<Language, String> title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getTitle(Language language) {
        return title.get(language);
    }
}
