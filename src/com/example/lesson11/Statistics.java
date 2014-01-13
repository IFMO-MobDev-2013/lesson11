package com.example.lesson11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 12/26/13
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Statistics extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "translations.db";
    private static final int DATABASE_VERSION = 1;
    private int current_minimum;

    private static final String TABLE_WORDS = "WORD_COUNT";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_JP = "jp";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_COUNT = "count";
    private SQLiteDatabase database;
    private Context activity;
    private int[][] counts = new int[10][10];
    private String[] allColumns = { this.COLUMN_ID,
            this.COLUMN_CATEGORY,
            this.COLUMN_JP,
            this.COLUMN_COUNT};

    public Statistics(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        activity = context;
        current_minimum = 0;

    }

    private static final String DATABASE_CREATE = "create table "
            + TABLE_WORDS + "(" + COLUMN_ID
            + " integer, " + COLUMN_CATEGORY
            + " integer, " + COLUMN_JP
            + " text not null, " + COLUMN_COUNT
            + " integer );";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Statistics.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);

        onCreate(db);

    }
    public void open() throws SQLException {

        database = this.getWritableDatabase();
        Cursor cur = database.query(TABLE_WORDS, allColumns, null, null, null, null, null);
        cur.moveToFirst();
        current_minimum = Integer.MAX_VALUE;
        while(!cur.isAfterLast()) {
            int _id = cur.getInt(0);
            int _cat = cur.getInt(1);
            int _cnt = cur.getInt(3);
            counts[_cat][_id] = _cnt;
            if (_cnt < current_minimum) {
                current_minimum = _cnt;
            }
            cur.moveToNext();
        }
    }



    private void increment(int category, int id) {
        counts[category][id]++;
        Cursor cursor = database.query(TABLE_WORDS, allColumns, COLUMN_ID + " = " + id + " AND " + COLUMN_CATEGORY + " = " + category, null, null, null, null);
        cursor.moveToFirst();
        ContentValues cv = new ContentValues();
        if (!cursor.isAfterLast()) {
            int tmp = cursor.getInt(3);
            Log.w("Statistics", "COUNT(" + id + ") = " + (tmp + 1));
            cv.put(COLUMN_COUNT, tmp + 1);
            database.update(TABLE_WORDS, cv, COLUMN_ID + " = " + id + " AND " + COLUMN_CATEGORY + " = " + category, null);

        }
        else {
            Log.w("Statistics", "Inserting new " + category + " ,"  + id);
            cv.put(COLUMN_ID, id);
            cv.put(COLUMN_CATEGORY, category);
            cv.put(COLUMN_JP, QuizActivity.getStringResource(activity, category, id, "jp"));


            cv.put(COLUMN_COUNT, 0);
            database.insert(TABLE_WORDS, null, cv);
        }
    }
    public int nextFlashcard(int category_id, int flashcard_id) {
        //temorary
        increment(category_id, flashcard_id);
        return leastViewed(category_id, flashcard_id);

    }

    private int leastViewed(int ctg, int flsh) {
        int leastViewed = 0;
        current_minimum = Integer.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            if (counts[ctg][i] <= current_minimum && i != flsh) {
                current_minimum = counts[ctg][i];
                leastViewed = i;
            }
        }
        return leastViewed;
    }

    public Cursor showStats() {
        Cursor cursor = database.query(TABLE_WORDS, allColumns, null, null, null, null, null);
        return cursor;
    }

}
