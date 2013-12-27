package ru.sbrains.shalamov.FlashCards;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 17.11.13
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
public class DbAdapter implements DbConstants {

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mCtx;
    public static final int DATABASE_VERSION = 2;

    public static final String TAG = "FlashCards::DATABASE";


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_WORDS);
            db.execSQL(CREATE_TABLE_STATS);
            db.execSQL(CREATE_TABLE_CATEGORIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
            onCreate(db);
        }
    }

    public DbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        try {
            mDb = mDbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            try {
                mDb = mDbHelper.getReadableDatabase();
            } catch (Exception e) {
                e.printStackTrace();
                return this;
            }
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    //-------------------------------------------------------------------------------------------
    public void dropWORDS() {
        try {
            mDb.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
            mDb.execSQL(CREATE_TABLE_WORDS);
        } catch (Exception e) {
            Log.w(TAG, "can't drop WORDS - " + e.getMessage());
        }
    }

    public void dropStats() {
        try {
            mDb.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
            mDb.execSQL(CREATE_TABLE_STATS);
        } catch (Exception e) {
            Log.w(TAG, "can't drop STATS - " + e.getMessage());
        }
    }

    public void dropCategiries() {
        try {
            mDb.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
            mDb.execSQL(CREATE_TABLE_CATEGORIES);
        } catch (Exception e) {
            Log.w(TAG, "can't drop CATEGORIES - " + e.getMessage());
        }
    }

    //----------------------------------------------------------------------------------------------
    public Cursor fetchCategories() {
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_CATEGORIES, null, null, null, null, null, null);
        } catch (Exception e) {
            Log.w(TAG, "fetchCategories() error");
        } finally {
            return cursor;
        }
    }

    public Cursor fetchWords(int category_id) {
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_WORDS, null, KEY_CATID + " = " + category_id, null, null, null, null);
        } catch (Exception e) {
            Log.w(TAG, "fetchCategories() error");
        } finally {
            return cursor;
        }
    }

    int getWordId(String en) {
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_WORDS, new String[]{KEY_ROWID}, KEY_EN + " =? ", new String[]{en}, null, null, null, null);
        } catch (Exception e) {
            Log.w(TAG, "getWordId() error");
        } finally {
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex(KEY_ROWID));
        }
    }
    int getCategId(String categ) {
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_CATEGORIES, new String[]{KEY_ROWID}, KEY_CATEG + " =? ", new String[]{categ}, null, null, null, null);
        } catch (Exception e) {
            Log.w(TAG, "getCategId() error");
        } finally {
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex(KEY_ROWID));
        }
    }


    Cursor fetchStat(int word_id) {
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_STATS, null, KEY_WORDID + " = " + word_id, null, null, null, null);
        } catch (Exception e) {
            Log.w(TAG, "fetchStat() error");
        } finally {
            return cursor;
        }
    }

    Cursor fetchStat()
    {
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_STATS, null, null , null, null, null, null);
        } catch (Exception e) {
            Log.w(TAG, "fetchStat() error");
        } finally {
            return cursor;
        }
    }

    boolean updateStat(ContentValues cv) {
        return mDb.update(TABLE_STATS, cv, KEY_ROWID + " = " + cv.get(KEY_ROWID).toString(), null) > 0;
    }

    long addCategory(ContentValues cvC) {
        try {
            return mDb.insertOrThrow(TABLE_CATEGORIES, null, cvC);
        } catch (Exception e) {
            Log.w(TAG, " createCateg problem " + e.getMessage());
            return -100;
        }
        //return mDb.insert(TABLE_STATS, null,cvC);
    }

    long addWord(ContentValues cvW) {
        try {
            return mDb.insertOrThrow(TABLE_WORDS, null, cvW);
        } catch (Exception e) {
            Log.w(TAG, " createWord problem " + e.getMessage());
            return -100;
        }
        //return mDb.insert(TABLE_STATS, null,cvW);
    }

    long addStat(ContentValues cvS) {
        try {
            return mDb.insertOrThrow(TABLE_STATS, null, cvS);
        } catch (Exception e) {
            Log.w(TAG, " createStat problem " + e.getMessage());
            return -100;
        }
        //return mDb.insert(TABLE_STATS, null,cvS);
    }



    /*

    public boolean createCity(HashMap<String, String> h) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, h.get(KEY_NAME));
        initialValues.put(KEY_LAT, h.get(KEY_LAT));
        initialValues.put(KEY_LONG, h.get(KEY_LONG));
        try {
            return mDb.insertOrThrow(TABLE_CITIES, null, initialValues) > 0;
        } catch (Exception e) {
            Log.w(TAG, getClass().getName());
            return false;
        }
    }

    public boolean deleteCity(long rowId) {
        return mDb.delete(TABLE_CITIES, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean deleteCity(String name) {
        return deleteCity(getCityId(name));
    }


    public long getCityId(String name) {
        try {
            Cursor cursor = mDb.query(TABLE_CITIES, new String[]{KEY_ROWID}, KEY_NAME + " =? ", new String[]{name}, null, null, null, "1");
            if (cursor == null)
                return -1;
            cursor.moveToFirst();
            return cursor.getLong(cursor.getColumnIndex(KEY_ROWID));
        } catch (Exception e) {
            Log.w(TAG, "getCityId Failed!! " + e.getMessage().toString());
            return -1;
        }
    }
     //  /CITY. works correct
    //---------------------------------------------------------------------------------------------------
    // CURRENT
     public Cursor fetchCurrent() {
         Cursor cursor = null;
         try {
             cursor = mDb.query(TABLE_CURRENT, null, null, null, null, null, null);
         } catch (Exception e) {
             Log.w(TAG, getClass().getName());
         } finally {
             return cursor;
         }
     }

    public boolean createCurrent(HashMap<String, String> h) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TEMPC, h.get(KEY_TEMPC));
        initialValues.put(KEY_OBSTIME, h.get(KEY_OBSTIME));
        initialValues.put(KEY_WTHDESC, h.get(KEY_WTHDESC));
        initialValues.put(KEY_WSK, h.get(KEY_WSK));
        initialValues.put(KEY_WTHCODE, h.get(KEY_WTHCODE));
        initialValues.put(KEY_WTHICON, h.get(KEY_WTHICON));
        //initialValues.put(KEY_DATE, h.get(KEY_DATE));
        initialValues.put(KEY_CITYID, getCityId(h.get(KEY_NAME)));

        try {
            return mDb.insertOrThrow(TABLE_CURRENT, null, initialValues) > 0;
        } catch (Exception e) {
            Log.w(TAG, " createCurrent problem "+ e.getMessage());
            return false;
        }
    }

    public boolean deleteCurrent(long cityId) {
        return mDb.delete(TABLE_CURRENT, KEY_CITYID + "=" + cityId, null) > 0;
    }

    public boolean updateCurrent(HashMap<String, String> h) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TEMPC, h.get(KEY_TEMPC));
        initialValues.put(KEY_OBSTIME, h.get(KEY_OBSTIME));
        initialValues.put(KEY_WTHDESC, h.get(KEY_WTHDESC));
        initialValues.put(KEY_WSK, h.get(KEY_WSK));
        initialValues.put(KEY_WTHCODE, h.get(KEY_WTHCODE));
        initialValues.put(KEY_WTHICON, h.get(KEY_WTHICON));
        //initialValues.put(KEY_DATE, h.get(KEY_DATE));
        long city_id = getCityId(h.get(KEY_NAME));
        return mDb.update(TABLE_CURRENT, initialValues, KEY_CITYID + " = " + city_id, null) > 0;
    }

    public boolean deleteCurrent(String city_name) {
        return deleteCurrent(getCityId(city_name));
    }

    // /CURRENT     correct
    //----------------------------------------------------------------------------------------
    // FORECAST
    private Cursor fetchForecast() {
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_FORECAST, null, null, null, null, null, null);
        } catch (Exception e) {
            Log.w(TAG, getClass().getName());
        } finally {
            return cursor;
        }
    }

    public Cursor fetchForecast(String name) {
        if(name == null)
            return fetchForecast();
        Cursor cursor = null;
        try {
            cursor = mDb.query(TABLE_FORECAST, null, KEY_CITYID + " = " + getCityId(name), null, null, null, null);
        } catch (Exception e) {
            Log.w(TAG, getClass().getName());
        } finally {
            return cursor;
        }
    }


    public boolean createForecast(HashMap<String, String> h) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TEMPMAXC, h.get(KEY_TEMPMAXC));
        initialValues.put(KEY_TEMPMINC, h.get(KEY_TEMPMINC));

        initialValues.put(KEY_WTHDESC, h.get(KEY_WTHDESC));
        initialValues.put(KEY_WSK, h.get(KEY_WSK));
        //initialValues.put(KEY_HUMID, h.get(KEY_HUMID));
        initialValues.put(KEY_WTHCODE, h.get(KEY_WTHCODE));
        initialValues.put(KEY_WTHICON, h.get(KEY_WTHICON));
        initialValues.put(KEY_DATE, h.get(KEY_DATE));
        initialValues.put(KEY_CITYID, getCityId(h.get(KEY_NAME)));

        try {
            return mDb.insertOrThrow(TABLE_FORECAST, null, initialValues) > 0;
        } catch (Exception e) {
            Log.w(TAG, " createCurrent problem "+ e.getMessage());
            return false;
        }
    }

    public boolean deleteForecast(long cityId) {
        return mDb.delete(TABLE_CURRENT, KEY_CITYID + "=" + cityId, null) > 0;
    }

    public boolean updateForecast(HashMap<String, String> h) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TEMPMAXC, h.get(KEY_TEMPMAXC));
        initialValues.put(KEY_TEMPMINC, h.get(KEY_TEMPMINC));

        initialValues.put(KEY_WTHDESC, h.get(KEY_WTHDESC));
        initialValues.put(KEY_WSK, h.get(KEY_WSK));
        initialValues.put(KEY_WTHCODE, h.get(KEY_WTHCODE));
        initialValues.put(KEY_WTHICON, h.get(KEY_WTHICON));
        initialValues.put(KEY_DATE, h.get(KEY_DATE));

        long city_id = getCityId(h.get(KEY_NAME));
        return mDb.update(TABLE_FORECAST, initialValues, KEY_CITYID + " = " + city_id, null) > 0;
    }

    public boolean deleteForecast(String city_name) {
        return deleteForecast(getCityId(city_name));
    }



    // /FORECAST   not tested!!!    I suppose it's ok

    //-------------------------------------------------------------------------------------------------------
    */
}