package com.mobdev.cardz;

public class Category {
    String name;
    int id;
    int words;

    Category(String name, int id, int words) {
        this.name = name;
        this.id = id;
        this.words = words;
    }

    @Override
    public String toString() {
        return name + "  -  " + (words*10) + "%";
    }
}
