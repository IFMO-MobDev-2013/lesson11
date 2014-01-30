package com.dronov.java.android.FlashCards.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 28.01.14
 * Time: 5:48
 * To change this template use File | Settings | File Templates.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String dbName, Object o, int dbVersion) {
        super(context, dbName, (SQLiteDatabase.CursorFactory) o, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EnglishFlashCards.DB_CREATE);
        sqLiteDatabase.execSQL(RussianFlashCards.DB_CREATE);
        sqLiteDatabase.execSQL(ChinaFlashCards.DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
