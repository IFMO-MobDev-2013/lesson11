package com.blumonk.FlashCards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by blumonk on 1/5/14.
 */
public class DbAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wordsStats";

    private static final String TABLE_STATS = "stats";

    private static final String KEY_ID = "id";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_RESULT = "result";

    public DbAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_STATS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY + " TEXT,"
                + KEY_RESULT + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        onCreate(db);
    }

    public void addResult(String category, int result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, category);
        values.put(KEY_RESULT, result);
        db.insert(TABLE_STATS, null, values);
        db.close();
    }

    public HashMap<String, Integer> getAllResults() {
        HashMap<String, Integer> resultsMap = new HashMap<String, Integer>();
        String selectQuery = "SELECT  * FROM " + TABLE_STATS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                resultsMap.put(cursor.getString(1), Integer.parseInt(cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return resultsMap;
    }

    public void updateResult(String category, int newResult) {
        SQLiteDatabase db = this.getWritableDatabase();
        String request = String.format("UPDATE %s SET %s = '%s' WHERE %s LIKE '%s'",
                TABLE_STATS, KEY_RESULT, newResult, KEY_CATEGORY, category);
        db.execSQL(request);
        db.close();
    }

    public void resetStats() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_STATS);
        db.close();
    }

}
