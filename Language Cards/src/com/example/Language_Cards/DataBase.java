package com.example.Language_Cards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataBase {
    public static SQLiteDatabase sqLiteDatabase;
    private Context context;
    private DatabaseHelper databaseHelper;
    private Cursor cursor;

    public static final String DATABASE_NAME = "flash_cards.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_WORDS_NAME = "my_words";
    public static final String KEY_ENGLISH = "english";
    public static final String KEY_RUSSIAN = "russian";
    public static final String KEY_CHINESE = "chinese";
    public static final String IS_SELECT = "is_select";
    public static final String KEY_ITEM = "my_item";

    public static final String KEY_ID_WORDS = "_id";
    public static final String TABLE_ITEMS_NAME = "my_items";
    public static final String KEY_ID_ITEMS = "_id";


    private static final String DATABASE_WORDS_CREATE = "CREATE TABLE " + TABLE_WORDS_NAME + " (" + KEY_ID_WORDS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             + KEY_ITEM + " TEXT NOT NULL, " + KEY_RUSSIAN + " TEXT NOT NULL, " + KEY_ENGLISH + " TEXT NOT NULL, " + KEY_CHINESE + " TEXT NOT NULL);";

    private static final String DATABASE_ITEMS_CREATE = "CREATE TABLE " + TABLE_ITEMS_NAME + " (" + KEY_ID_ITEMS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_RUSSIAN + " TEXT NOT NULL, " + KEY_ENGLISH + " TEXT NOT NULL, " + KEY_CHINESE + " TEXT NOT NULL, " + IS_SELECT + " TEXT NOT NULL);";

    public DataBase(Context context) {
        this.context = context;
    }

    public DataBase open() throws SQLiteException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }


    public boolean isEmptyWordsTable() {
        newWordsCursor();
        if (cursor.moveToNext())
            return false;
        else
            return true;
    }

    public boolean isEmptyItemsTable() {
        newItemsCursor();
        if (cursor.moveToNext())
            return false;
        else
            return true;
    }

    public long insertItem(String russia, String england, String china) {
        ContentValues values = new ContentValues();
        values.put(KEY_RUSSIAN, russia);
        values.put(KEY_ENGLISH, england);
        values.put(KEY_CHINESE, china);
        values.put(IS_SELECT, "--");
        return sqLiteDatabase.insert(TABLE_ITEMS_NAME, null, values);
    }

    public long insertWord(String item, String russia, String england, String china) {
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item);
        values.put(KEY_RUSSIAN, russia);
        values.put(KEY_ENGLISH, england);
        values.put(KEY_CHINESE, china);
        return sqLiteDatabase.insert(TABLE_WORDS_NAME, null, values);
    }

    public Cursor getCursor(String item, int n) {
        newWordsCursor();
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex(KEY_ITEM));
            if (str.equals(item)) {
                n--;
                if (n == 0)
                    break;
            }
        }
        return cursor;
    }

    public int getSystemLanguage() {
        newWordsCursor();
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(KEY_ITEM)).equals("NULL")) {
                String russia = cursor.getString(cursor.getColumnIndex(KEY_RUSSIAN));
                String england = cursor.getString(cursor.getColumnIndex(KEY_ENGLISH));
                String china = cursor.getString(cursor.getColumnIndex(KEY_CHINESE));
                if (russia.equals("+")) return 1;
                if (england.equals("+")) return 2;
                if (china.equals("+")) return 3;
            }
        }
        return 1;
    }

    public String getAnswer(String mainItem, int n) {
        newWordsCursor();
        while (cursor.moveToNext()) {
            String item = cursor.getString(cursor.getColumnIndex(KEY_ITEM));
            if (item.equals(mainItem)) {
                --n;
                if (n == 0) {
                    break;
                }
            }
        }
        return cursor.getString(cursor.getColumnIndex(KEY_RUSSIAN));
    }

    public String getAnswer(String mainItem, int n, int language) {
        newWordsCursor();
        while (cursor.moveToNext()) {
            String item = cursor.getString(cursor.getColumnIndex(KEY_ITEM));
            if (item.equals(mainItem)) {
                --n;
                if (n == 0) {
                    break;
                }
            }
        }
        if (language == 1)
            return cursor.getString(cursor.getColumnIndex(KEY_RUSSIAN));
        if (language == 2)
            return cursor.getString(cursor.getColumnIndex(KEY_ENGLISH));

        return cursor.getString(cursor.getColumnIndex(KEY_CHINESE));
    }

    public void updateLanguage(int n) {
        newWordsCursor();
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(KEY_ITEM)).equals("NULL")) {
                ContentValues values = new ContentValues();
                if (n == 1) {
                    values.put(KEY_RUSSIAN, "+");
                    values.put(KEY_ENGLISH, "-");
                    values.put(KEY_CHINESE, "-");
                }
                if (n == 2) {
                    values.put(KEY_RUSSIAN, "-");
                    values.put(KEY_ENGLISH, "+");
                    values.put(KEY_CHINESE, "-");
                }
                if (n == 3) {
                    values.put(KEY_RUSSIAN, "-");
                    values.put(KEY_ENGLISH, "-");
                    values.put(KEY_CHINESE, "+");
                }
                sqLiteDatabase.update(TABLE_WORDS_NAME, values, KEY_ID_WORDS + "=" + cursor.getString(cursor.getColumnIndex(KEY_ID_WORDS)), null);
                return;
            }
        }
    }

    public ArrayList<Integer> getArrayListOK() {
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        newItemsCursor();
        while (cursor.moveToNext()) {
            String isSelect = cursor.getString(cursor.getColumnIndex(IS_SELECT));
            if (isSelect.equals("OK")) {
                String russianItem = cursor.getString(cursor.getColumnIndex(KEY_RUSSIAN));
                if (russianItem.equals("Животные")) tmp.add(1);
                else if (russianItem.equals("В доме")) tmp.add(2);
                else if (russianItem.equals("Покупки")) tmp.add(3);
                else if (russianItem.equals("Спорт и здоровье")) tmp.add(4);
                else if (russianItem.equals("Продукты")) tmp.add(5);
                else if (russianItem.equals("Любовь и отношения")) tmp.add(6);
                else if (russianItem.equals("В городе")) tmp.add(7);
                else if (russianItem.equals("Путешествия")) tmp.add(8);
                else if (russianItem.equals("В ресторане")) tmp.add(9);
                else if (russianItem.equals("Работа")) tmp.add(10);
            }
        }
        return tmp;
    }

    public String getOK(String russia) {
        newItemsCursor();
        while (cursor.moveToNext()) {
            String russiaTable = cursor.getString(cursor.getColumnIndex(KEY_RUSSIAN));
            if (russia.equals(russiaTable)) {
                return cursor.getString(cursor.getColumnIndex(IS_SELECT));
            }
        }
        return null;
    }

    public void changeOK(String russia) {
        newItemsCursor();
        while (cursor.moveToNext()) {
            String russiaTable = cursor.getString(cursor.getColumnIndex(KEY_RUSSIAN));
            if (russia.equals(russiaTable)) {
                String isSelect = cursor.getString(cursor.getColumnIndex(IS_SELECT));
                ContentValues values = new ContentValues();
                if (isSelect.equals("OK")) {
                    values.put(IS_SELECT, "--");
                } else {
                    values.put(IS_SELECT, "OK");
                }
                sqLiteDatabase.update(TABLE_ITEMS_NAME, values, KEY_ID_ITEMS + "=" + cursor.getString(cursor.getColumnIndex(KEY_ID_ITEMS)), null);
                return;
            }
        }
    }

    private void newWordsCursor() {
        cursor = sqLiteDatabase.query(TABLE_WORDS_NAME, new String[] {
            KEY_ID_WORDS, KEY_ITEM, KEY_RUSSIAN, KEY_ENGLISH, KEY_CHINESE},
            null, // The columns for the WHERE clause
            null, // The values for the WHERE clause
            null, // don't group the rows
            null, // don't filter by row groups
            null // The sort order
        );
    }

    private void newItemsCursor() {
        cursor = sqLiteDatabase.query(TABLE_ITEMS_NAME, new String[] {
                KEY_ID_ITEMS, KEY_RUSSIAN, KEY_ENGLISH, KEY_CHINESE, IS_SELECT},
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("TABLE WORDS CREATE", DATABASE_WORDS_CREATE);
            Log.d("TABLE ITEMS CREATE", DATABASE_ITEMS_CREATE);
            db.execSQL(DATABASE_WORDS_CREATE);
            db.execSQL(DATABASE_ITEMS_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_WORDS_NAME);
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_ITEMS_NAME);
            onCreate(db);
        }
    }
}
