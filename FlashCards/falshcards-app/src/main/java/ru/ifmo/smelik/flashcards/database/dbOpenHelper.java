package ru.ifmo.smelik.flashcards.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nick Smelik on 12.01.14.
 */
public class dbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "weather.db";
    private static final int VERSION = 7;

    public dbOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DatabaseTable.init(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DatabaseTable.drop(db);
        onCreate(db);
    }
}
