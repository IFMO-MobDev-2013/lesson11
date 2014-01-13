package com.example.lesson11;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 12/25/13
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Category {
    public final int id;
    public final String text;
    public Category(int id, String text) {
        this.id = id;
        this.text = text;
    }
    @Override
    public String toString() {
        return this.text;
    }
}
