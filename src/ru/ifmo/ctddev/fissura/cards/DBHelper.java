package ru.ifmo.ctddev.fissura.cards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "dictionary";
    private static final String STATISTICS = "statistics";
    private static final String KEY_ID = "_id";
    private static final String KEY_WORD = "word";
    private static final String KEY_IS_KNOWN = "isKnown";

    private static final String CREATE_TABLE = "CREATE TABLE " + STATISTICS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WORD + " TEXT," + KEY_IS_KNOWN + " INTEGER" + ")";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void addWord(String word, int isKnown) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_IS_KNOWN, isKnown);
        cv.put(KEY_WORD, word);
        db.insert(STATISTICS, null, cv);
        db.close();
    }

    public HashMap<String, Integer> getAll() {
        HashMap<String, Integer> res = new HashMap<String, Integer>();
        String query = "SELECT  * FROM " + STATISTICS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(1);
                int isKnown = Integer.parseInt(cursor.getString(2));
                res.put(word, isKnown);
            } while (cursor.moveToNext());
        }
        return res;
    }

    public void update(String word, int isKnown) {
        SQLiteDatabase db = this.getWritableDatabase();
        String request = String.format("UPDATE %s SET %s = '%s' WHERE %s LIKE '%s'", STATISTICS, KEY_IS_KNOWN, isKnown, KEY_WORD, word);
        db.execSQL(request);
        db.close();
    }

    public void erase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + STATISTICS);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STATISTICS);
        onCreate(sqLiteDatabase);
    }

}
