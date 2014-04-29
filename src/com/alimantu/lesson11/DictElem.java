package com.alimantu.lesson11;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 30.03.14
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */
public class DictElem {
    private String rus;
    private String eng;
    private String de;

    public DictElem() {}

    public DictElem(String russian, String english, String china) {
        this.rus = russian;
        this.eng = english;
        this.de = china;
    }

    public String getRus() {
        return rus;
    }

    public void setRus(String rus) {
        this.rus = rus;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    @Override
    public boolean equals(Object o) {
        DictElem q = (DictElem)o;
        return q.getEng().equals(this.getEng()) && q.getDe().equals(this.getDe());
    }
}
