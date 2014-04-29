package com.alimantu.lesson11;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 30.03.14
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
public class CategoryParser {

    private String query;
    private Context context;

    public CategoryParser(Context context, String query) {
        this.query = query;
        this.context = context;
    }

    public ArrayList<DictElem> parse() {
        ArrayList<DictElem> result = new ArrayList<DictElem>();

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(query.toLowerCase() + ".txt")));
            String line = reader.readLine();
            DictElem elem = new DictElem();
            int count = 0;
            while (line != null) {
                if (count == 0) {
                    elem.setRus(line);
                    count++;
                    line = reader.readLine();
                    continue;
                }
                if (count == 1) {
                    elem.setEng(line);
                    count++;
                    line = reader.readLine();
                    continue;
                }
                if (count == 2) {
                    elem.setDe(line);
                    result.add(elem);
                    count = 0;
                    elem = new DictElem();
                    line = reader.readLine();
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
