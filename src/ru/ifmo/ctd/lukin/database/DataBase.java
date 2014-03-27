package ru.ifmo.ctd.lukin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_NAME = "wordsDataBase";

    private static final String TABLE_NAME = "words";

    private static final String KEY_ID = "id";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_WORD = "word";
    private static final String KEY_NUM_OF_CORRECT = "correct";
    private static final String KEY_NUM_OF_ATTEMPTS = "attempts";

    private static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY
            + " TEXT," + KEY_WORD + " TEXT,"
            + KEY_NUM_OF_CORRECT + " INTEGER," + KEY_NUM_OF_ATTEMPTS + " INTEGER" + ")";


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long createWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        putValues(values, word);

        return db.insert(TABLE_NAME, null, values);
    }

    public Word getWord(String word) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_WORD + " = '" + word + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        Word w = new Word();
        w.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
        w.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        w.setWord(c.getString(c.getColumnIndex(KEY_WORD)));

        return w;
    }

    public int incCorrect(String word) {
        SQLiteDatabase db = this.getWritableDatabase();

        int countOfCorrect = getCountOfCorrect(word);
        int countOfAttemots = getCountOfAttempts(word);

        ContentValues values = new ContentValues();
        values.put(KEY_NUM_OF_ATTEMPTS, countOfAttemots + 1);
        values.put(KEY_NUM_OF_CORRECT, countOfCorrect + 1);

        return db.update(TABLE_NAME, values, KEY_WORD + " = '" + word + "'", null);
    }

    public int getCountOfCorrect(String word) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_WORD + " = '" + word + "'";

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        return c.getInt(c.getColumnIndex(KEY_NUM_OF_CORRECT));
    }

    public int getCountOfAttempts(String word) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_WORD + " = '" + word + "'";

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        return c.getInt(c.getColumnIndex(KEY_NUM_OF_ATTEMPTS));
    }

    public int incIncorrect(String word) {
        SQLiteDatabase db = this.getWritableDatabase();

        int countOfAttemots = getCountOfAttempts(word);

        ContentValues values = new ContentValues();
        values.put(KEY_NUM_OF_ATTEMPTS, countOfAttemots + 1);

        return db.update(TABLE_NAME, values, KEY_WORD + " = '" + word + "'", null);
    }

    private void putValues(ContentValues v, Word w) {
        v.put(KEY_CATEGORY, w.getCategory());
        v.put(KEY_WORD, w.getWord());
        v.put(KEY_NUM_OF_CORRECT, w.getNumOfCorrect());
        v.put(KEY_NUM_OF_ATTEMPTS, w.getNumOfAttempts());
    }


}
