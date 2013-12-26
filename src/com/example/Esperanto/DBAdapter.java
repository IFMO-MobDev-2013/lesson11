package com.example.Esperanto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    public static final String TABLE_ID = "_id";
    private static final String DATABASE_NAME = "statistic";
    private static final int DATABASE_VERSION = 10;
    public static final String TABLE_NAME = "stat_table";
    public static final String CATEGORY = "category";
    public static final String IMAGE = "image";
    public static final String STATISTIC = "statistic";
    public static final String WORD = "word";
    public static final String TRANSLATE = "trans";
    public static final String REGISTER = "reg";



    private static final String SQL_CREATE_ENTRIES = "create table "
            + TABLE_NAME + " ("
            + TABLE_ID + " integer primary key autoincrement, "
            + IMAGE + " text not null, "
            + STATISTIC + " integer not null, "
            + WORD + " text not null, "
            + REGISTER + " integer not null, "
            + TRANSLATE + " text not null, "
            + CATEGORY + " text not null ); ";


    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    private final Context mcontext;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public DBAdapter(Context context) {
        this.mcontext = context;
        DBHelper = new DatabaseHelper(mcontext);
        db = DBHelper.getWritableDatabase();
    }


    public void insert(String category, String img, int stat, String word, String trans, int reg) {
        ContentValues cv =  new ContentValues();
        cv.put(CATEGORY, category);
        cv.put(IMAGE, img);
        cv.put(STATISTIC, stat);
        cv.put(WORD, word);
        cv.put(TRANSLATE, trans);
        cv.put(REGISTER, reg);
        db.insert(TABLE_NAME, null, cv);
    }

    public void update(String category, String img, int stat, String word, String trans, int reg) {
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY, category); cv.put(IMAGE, img); cv.put(STATISTIC, stat); cv.put(WORD, word); cv.put(TRANSLATE, trans); cv.put(REGISTER, reg);
        db.update(TABLE_NAME, cv, "category = ? AND word = ?", new String[]{category, word});
    }

    public Cursor getAllData() {
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getCategoryData(String category) {
        return db.query(TABLE_NAME, null, "category = ?", new String[] {category}, null, null, "category");
    }


    public int getPercent(String category) {
        Cursor cursor = db.query(TABLE_NAME, null, "category = ?", new String[] {category}, null, null, "category");
        int maxcount = cursor.getCount();
        int count = 0;
        while (cursor.moveToNext()) {
            count += cursor.getInt(cursor.getColumnIndex("reg"));
        }
        return (count*10) / maxcount;

    }


    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
