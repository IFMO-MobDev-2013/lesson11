package com.alimantu.lesson11.LangBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.alimantu.lesson11.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 28.03.14
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class RussianLang {

    private static final String DB_NAME = "RussianLang";
    private static final int DB_VERSION = 16;
    private static final String DB_TABLE = "rus_level";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "cat";
    public static final String COLUMN_RES = "res";

    public static final String DB_CREATE = "create table "
            + DB_TABLE + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text, " +
            COLUMN_RES + " text" + ");";

    private final Context context;
    private DBHelper DBHelp;
    private SQLiteDatabase db;

    public static final int startRate = 11;

    public RussianLang(Context context)
    {
        this.context = context;
    }

    public void open()
    {
        DBHelp = new DBHelper(context, DB_NAME, null, DB_VERSION);
        db = DBHelp.getWritableDatabase();
    }

    public void close()
    {
        if(DBHelp != null)
            DBHelp.close();
    }

    public Cursor getAllData() {
        return db.query(DB_TABLE, null, null, null, null, null, null);
    }

    public void addChannel(String name, int number) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, name);
        cv.put(COLUMN_RES, number);
        db.insert(DB_TABLE, null, cv);
    }

    public void deleteChannel(long id) {
        db.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
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
        addChannel(context.getString(R.string.rus_animals), startRate);
        addChannel(context.getString(R.string.rus_body), startRate);
        addChannel(context.getString(R.string.rus_sport), startRate);
        addChannel(context.getString(R.string.rus_colour), startRate);
        addChannel(context.getString(R.string.rus_fru_veg), startRate);
        addChannel(context.getString(R.string.rus_countr), startRate);
        addChannel(context.getString(R.string.rus_prof), startRate);
        addChannel(context.getString(R.string.rus_weather), startRate);
        addChannel(context.getString(R.string.rus_nature), startRate);
        addChannel(context.getString(R.string.rus_music), startRate);
    }

    public ArrayList<String> getAllCategories() {
        ArrayList<String> result = new ArrayList<String>();
        Cursor cursor = getAllData();
        while (cursor.moveToNext()) {
            result.add(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        }
        return result;
    }

    public void update(String category, int s) {
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

    private void updateCategory(long id, String category, int s) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, category);
        contentValues.put(COLUMN_RES, s);
        db.update(DB_TABLE, contentValues, COLUMN_ID + "=" + id, null);
    }
}
