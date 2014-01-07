package com.example.FlashCards;
/**
 * User: Xottab
 * Date: 06.12.13
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.FlashCards.orm.Category;
import com.example.FlashCards.orm.Word;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "flashCardsTable";
    private static final int DATABASE_VERSION = 1;
    private Dao categoryDao = null;
    private Dao wordDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Word.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Word.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao getCategoryDao() throws SQLException {
        if (categoryDao == null) {
            categoryDao = getDao(Category.class);
        }
        return categoryDao;
    }

    public Dao getWordDao() throws SQLException {
        if (wordDao == null) {
            wordDao = getDao(Word.class);
        }
        return wordDao;
    }

    @Override
    public void close() {
        super.close();
        categoryDao = null;
        wordDao = null;
    }
}
