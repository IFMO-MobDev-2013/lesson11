package ru.ifmo.ii2539.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userResults";
    private static final String DATABASE_TABLE = "results";
    private static final String KEY_ID = "id";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_RIGHT = "right";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TOTAL + " INTEGER," + KEY_RIGHT + " INTEGER" + ")";
        database.execSQL(CREATE_CONTACTS_TABLE);
        for (int i = 0; i < 100; i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_TOTAL, 0);
            values.put(KEY_RIGHT, 0);
            database.insert(DATABASE_TABLE, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    public void addWordStats(WordStats stats) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TOTAL, stats.getTotal());
        values.put(KEY_RIGHT, stats.getRight());
        database.insert(DATABASE_TABLE, null, values);
        database.close();
    }

    public WordStats getWordStats(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_TOTAL, KEY_RIGHT}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new WordStats(cursor.getInt(1), cursor.getInt(2));
    }

    public List<WordStats> getAllStats() {
        List<WordStats> statsList = new ArrayList<WordStats>();
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                WordStats stats = new WordStats();
                stats.setTotal(cursor.getInt(1));
                stats.setRight(cursor.getInt(2));
                statsList.add(stats);
            } while (cursor.moveToNext());
        }
        return statsList;
    }

    public int updateWordStats(int id, WordStats stats) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TOTAL, stats.getTotal());
        values.put(KEY_RIGHT, stats.getRight());
        return database.update(DATABASE_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
