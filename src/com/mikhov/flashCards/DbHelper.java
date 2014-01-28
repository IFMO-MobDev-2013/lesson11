package com.mikhov.flashCards;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flash";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_CARDS = "create table cards (_id integer primary key autoincrement, category text, last_false integer, last_true integer);";
    private static final String DATABASE_CREATE_QUESTIONS = "create table questions (_id integer primary key autoincrement, category text, question text, answer text, image blob, mini_image blob, changed integer);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_CARDS);
        sqLiteDatabase.execSQL(DATABASE_CREATE_QUESTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cards");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS questions");
        onCreate(sqLiteDatabase);
    }
}
