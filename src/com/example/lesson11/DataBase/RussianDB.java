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
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 */
public class RussianDB {

    private static final String DB_NAME = "images_russian";
    private static final int DB_VERSION = 3;
    private static final String DB_TABLE = "rates_russian"  ;

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

    public RussianDB(Context context) {
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
        addChannel(context.getString(R.string.clothes_rus), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.body_rus), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.animal_rus), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.math_rus), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.sport_rus), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.color_rus), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.weather_rus), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.country_rus), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.fruits_rus), context.getString(R.string.zeros));
        addChannel(context.getString(R.string.forhome_rus), context.getString(R.string.zeros));
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
