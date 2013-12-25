package com.ctdev.IFMOCards;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CategoriesDataBaseHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "categories";
    public static String ID = "_id";
    public static String NAME = "name";
    public static String WORDS = "words";


    public CategoriesDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    String createDatabase() {
        return "CREATE TABLE " + DATABASE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT," + WORDS + " INTEGER);";
    }

    String dropDataBase() {
        return "DROP TABLE IF EXISTS " + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDatabase());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.execSQL(dropDataBase());
            onCreate(db);
        }
    }
}
