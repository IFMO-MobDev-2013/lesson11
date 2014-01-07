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

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application
    private static final String DATABASE_NAME = "flashCardsTable";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the Book table
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

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Word.class, true);
// after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our Book class. It will create it or just give the cached
     * value.
     */
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

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        categoryDao = null;
        wordDao = null;
    }
}
