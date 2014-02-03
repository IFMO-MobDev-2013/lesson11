package com.example.lesson11;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 02.02.14
 * Time: 20:12
 * To change this template use File | Settings | File Templates.
 */
public class Word {
    private String russian;
    private String uzbek;
    private String name;
    public Word(){ };
    public String getRussian(){
        return russian;
    }
    public String getUzbek(){
        return uzbek;
    }
    public String getName(){
        return name;
    }
    public void setRussian(String russian){
        this.russian = russian;
    }
    public void setUzbek(String uzbek){
        this.uzbek =uzbek;
    }
    public void setName(String name){
        this.name = name;
    }
}
