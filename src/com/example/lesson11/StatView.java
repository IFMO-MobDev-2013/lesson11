package com.example.lesson11;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 1/13/14
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatView {
    public String name;
    public int score;
    public StatView(String name, int score) {
        this.name = name;
        this.score = score;
    }
    @Override
    public String toString() {
        return name + " " + score;
    }

}
