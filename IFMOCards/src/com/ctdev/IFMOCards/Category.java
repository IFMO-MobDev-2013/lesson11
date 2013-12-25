package com.ctdev.IFMOCards;

/**
 * Created with IntelliJ IDEA.
 * User: Alexei
 * Date: 19.12.13
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
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
        return name + " (" + words + "/10)";
    }
}
