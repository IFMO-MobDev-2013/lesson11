package com.dronov.java.android.FlashCards;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 30.01.14
 * Time: 6:36
 * To change this template use File | Settings | File Templates.
 */
public class ParseCategoryFromAllFiles {
    Context context;
    ArrayList<String> categories;
    public ParseCategoryFromAllFiles(Context context, ArrayList<String> categories) {
        this.context = context;
        this.categories = categories;
    }

    public ArrayList<Word> parse() {
        ArrayList<Word> result = new ArrayList<Word>();
        for (int i = 0; i < categories.size(); i++) {
            String current = categories.get(i);
            ArrayList<Word> arrayList = new ParseCategory(context, current).parse();
            result.add(arrayList.get(0));
        }
        return result;
    }
}
