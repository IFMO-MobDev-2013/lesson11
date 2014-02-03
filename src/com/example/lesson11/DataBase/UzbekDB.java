package com.example.lesson11.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.lesson11.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 02.02.14
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public class UzbekDB {


    private static final String DB_NAME = "images_uzbek";
    private static final int DB_VERSION = 3;
    private static final String DB_TABLE = "rates_uzbek"  ;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "category";
    public static final String COLUMN_NUMBER = "result";

    public static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_TITLE + " text, " +
                    COLUMN_NUMBER + " text" +
                    ");";

    private final Context context;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public UzbekDB(Context context) {
        this.context = context;
    }

    public void open() {
        mDBHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }


    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public void addChannel(String name, String number) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, name);
        cv.put(COLUMN_NUMBER, number);
        mDB.insert(DB_TABLE, null, cv);
    }

    public void deleteChannel(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public boolean isEmpty() {
        Cursor cursor = getAllData();
        boolean result = true;
        while (cursor.moveToNext()) {
            result = false;
        }
        return result;
    }

    public void addDefault() {
        addChannel(context.getString(R.string.clothes_uzb), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.body_uzb), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.animal_uzb), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.math_uzb), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.sport_uzb), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.color_uzb), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.weather_uzb), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.country_uzb), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.fruits_uzb), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.forhome_uzb), context.getString(R.string.zeros));
    }

    public ArrayList<String> getAllCategories() {
        ArrayList<String> result = new ArrayList<String>();
        Cursor cursor = getAllData();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        }
        return result;
    }

    public void update(String category, String s) {
        Cursor cursor = getAllData();
        long id = 0;
        while (cursor.moveToNext()) {
            String p = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            if (cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)).equals(category)) {
                id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                break;
            }
        }
        cursor.close();
        updateCategory(id, category, s);
    }

    private void updateCategory(long id, String category, String s) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, category);
        contentValues.put(COLUMN_NUMBER, s);
        mDB.update(DB_TABLE, contentValues, COLUMN_ID + "=" + id, null);
    }
}
