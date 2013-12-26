package ru.ifmo.ii2539.flashcards;

public class WordStats {
    private int total;
    private int right;

    public WordStats() {
    }

    public WordStats(int total, int right) {
        this.total = total;
        this.right = right;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
