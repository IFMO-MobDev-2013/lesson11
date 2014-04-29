package com.alimantu.lesson11;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 29.04.14
 * Time: 4:14
 * To change this template use File | Settings | File Templates.
 */
public class AllCategoriesParser {
    Context context;
    ArrayList<String> categories;
    public AllCategoriesParser(Context context, ArrayList<String> categories) {
        this.context = context;
        this.categories = categories;
    }

    public ArrayList<DictElem> parse() {
        ArrayList<DictElem> result = new ArrayList<DictElem>();
        for (int i = 0; i < categories.size(); i++) {
            String current = categories.get(i);
            ArrayList<DictElem> arrayList = new CategoryParser(context, current).parse();
            result.add(arrayList.get(0));
        }
        return result;
    }
}
