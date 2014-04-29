package com.alimantu.lesson11.LangBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 28.03.14
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RussianLang.DB_CREATE);
        db.execSQL(EnglishLang.DB_CREATE);
        db.execSQL(GermanLang.DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
