package ru.ifmo.ctddev.rakovsky.italiano;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "words";

    private static final String TABLE_STATS = "words_stats";

    private static final String KEY_ID = "_id";
    private static final String KEY_WORD = "word";
    private static final String KEY_LEARNT = "learnt";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_STATS + "("
                            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WORD + " TEXT,"
                            + KEY_LEARNT + " INTEGER" + ")";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        onCreate(sqLiteDatabase);
    }

    public void addWord(String word, int learnt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_LEARNT, learnt);
        cv.put(KEY_WORD, word);
        db.insert(TABLE_STATS, null, cv);
        db.close();
    }

    public HashMap<String, Integer> getAll() {
        HashMap<String, Integer> res = new HashMap<String, Integer>();
        String query = "SELECT  * FROM " + TABLE_STATS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(1);
                int learnt = Integer.parseInt(cursor.getString(2));
                res.put(word, learnt);
            } while (cursor.moveToNext());
        }

        return res;
    }

    public void update(String word, int learnt) {
        SQLiteDatabase db = this.getWritableDatabase();
        String request = String.format("UPDATE %s SET %s = '%s' WHERE %s LIKE '%s'",
                                TABLE_STATS, KEY_LEARNT, learnt, KEY_WORD, word);
        db.execSQL(request);
        db.close();
    }

    public void clearStats() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_STATS);
        db.close();
    }

}
