package com.example.Esperanto;

public class Word {
    String word;
    String translate;
    int stat;
    int reg;
    String image;
    public Word(String k, String translate, int s, String i, int r) {
        this.word = k;
        this.stat = s;
        this.image = i;
        this.translate = translate;
        this.reg = r;
    }
}
