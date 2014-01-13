package com.example.lesson11;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 12/28/13
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatisticsActivity extends ListActivity {
    private Statistics stats;
    private ArrayAdapter<StatView> statlist;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stats = new Statistics(this);
        stats.open();
        Cursor cursor = stats.showStats();
        statlist = new ArrayAdapter<StatView>(this, R.layout.category);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            int category = cursor.getInt(1);
            String word = cursor.getString(2);
            int count = cursor.getInt(3);
            //String tword = QuizActivity.getResName(category, id, )
            StatView sv = new StatView(word, count);
            statlist.add(sv);
            cursor.moveToNext();
        }
        setListAdapter(statlist);

    }
}