package com.dronov.java.android.FlashCards;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 28.01.14
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public class ParseCategory {

    private String query;
    private Context context;
    public ParseCategory(Context context, String query) {
        this.query = query;
        this.context = context;
    }

    public ArrayList<Word> parse() {
        ArrayList<Word> result = new ArrayList<Word>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(query.toLowerCase() + ".txt")));
            String line = reader.readLine();
            Word word = new Word();
            int count = 0;
            while (line != null) {
                if (count == 0) {
                    word.setRussian(line);
                    count++;
                    line = reader.readLine();
                    continue;
                }
                if (count == 1) {
                    word.setEnglish(line);
                    count++;
                    line = reader.readLine();
                    continue;
                }
                if (count == 2) {
                    word.setChina(line);
                    result.add(word);
                    count = 0;
                    word = new Word();
                    line = reader.readLine();
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }
}
