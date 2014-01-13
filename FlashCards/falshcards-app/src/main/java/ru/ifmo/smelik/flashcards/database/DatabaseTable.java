package ru.ifmo.smelik.flashcards.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ru.ifmo.smelik.flashcards.Category;
import ru.ifmo.smelik.flashcards.Utils;
import ru.ifmo.smelik.flashcards.Word;

/**
 * Created by Nick Smelik on 12.01.14.
 */
public class DatabaseTable {
    private static final String CATEGORY_TABLE_NAME = "category";
    private static final String WORDS_TABLE_NAME = "words";
    private static final String CATEGORY_ID = "category_id";
    private static final String CATEGORY_STATUS = "status";
    private static final String ARRAY_ID = "array_id";
    private static final String RATING = "rating";

    private static final String CREATE_CATEGORY_TABLE = String.format(
            "create table %s (" +
                    "_id integer not null primary key autoincrement," +
                    "%s integer default null" +
                    ")",
            CATEGORY_TABLE_NAME,
            CATEGORY_STATUS
    );

    private static final String CREATE_WORDS_TABLE = String.format(
            "create table %s (" +
                    "_id integer not null primary key autoincrement," +
                    "%s integer not null," +
                    "%s integer not null," +
                    "%s integer default null" +
                    ")",
            WORDS_TABLE_NAME,
            CATEGORY_ID,
            ARRAY_ID,
            RATING
    );

    private static final String DROP_TABLE_QUERY = "drop table if exists " + CATEGORY_TABLE_NAME + " and " + WORDS_TABLE_NAME;
    private static final String GET_ALL_CATEGORIES_QUERY = "select * from " + CATEGORY_TABLE_NAME;
    private static final String GET_CATEGORY_QUERY = "select * from " + CATEGORY_TABLE_NAME + " todo where _id = ?";
    private static final String GET_CATEGORYS_WORDS_QUERY = "select * from " + WORDS_TABLE_NAME + " todo where " + CATEGORY_ID + " = ?";
    private static final String GET_WORD_QUERY =  "select * from " + WORDS_TABLE_NAME + " todo where _id = ?";

    public static void init(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_WORDS_TABLE);
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL(DROP_TABLE_QUERY);
    }

    private final SQLiteDatabase db;

    public DatabaseTable(SQLiteDatabase db) {
        this.db = db;
    }

    public void insert(Category newCategory) {
        ContentValues values = new ContentValues(1);
        values.put(CATEGORY_STATUS, newCategory.getStatus());
        db.insertOrThrow(CATEGORY_TABLE_NAME, null, values);
        for (Word word : newCategory.getWords()) {
            values = new ContentValues(2);
            values.put(CATEGORY_ID, newCategory.getId());
            values.put(ARRAY_ID, word.getArrayID());
            values.put(RATING, word.getRating());
            db.insertOrThrow(WORDS_TABLE_NAME, null, values);
        }
    }

    public ArrayList<Category> getAll() {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(GET_ALL_CATEGORIES_QUERY, null);
            ArrayList<Category> result = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                int status = cursor.getInt(1);
                Category category = new Category(id, status);
                ArrayList<Word> words = getWords(id, category);
                category.setWords(words);

                result.add(category);
            }
            return result;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Category getCategory(int id) {
        Cursor cursor = null;
        Category result = null;
        try {
            cursor = db.rawQuery(GET_CATEGORY_QUERY, new String[]{Long.toString(id)});
            if (cursor != null) {
                cursor.moveToFirst();
                int _id = cursor.getInt(0);
                int status = cursor.getInt(1);
                result = new Category(_id, status);
                ArrayList<Word> words = getWords(_id, result);
                result.setWords(words);
            }
            return result;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public ArrayList<Word> getWords(long id, Category category) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(GET_CATEGORYS_WORDS_QUERY, new String[] {Long.toString(id)});
            ArrayList<Word> result = new ArrayList<>();
            while (cursor.moveToNext()) {
                long _id = cursor.getLong(0);
                int array_id = cursor.getInt(2);
                int rating = cursor.getInt(3);

                result.add(new Word(
                        _id,
                        array_id,
                        category,
                        rating
                ));
            }
            return result;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void changeRating(Word word, int dif) {
        ContentValues values = new ContentValues(2);
        if ((word.getCategory().getStatus() > 0 && dif < 0) || (word.getCategory().getStatus() < Utils.MAX_CATEGORY_VALUE && dif > 0)) {
            values.put("_id", word.getCategory().getId());
            values.put(CATEGORY_STATUS, word.getCategory().getStatus() + dif);
            db.update(CATEGORY_TABLE_NAME, values, "_id = ?", new String[]{Integer.toString(word.getCategory().getId())});
        }
        if ((word.getRating() > 0 && dif < 0) || (word.getRating() < Utils.MAX_WORD_VALUE && dif > 0)) {
            values = new ContentValues(4);
            values.put("_id", word.getID());
            values.put(CATEGORY_ID, word.getCategory().getId());
            values.put(ARRAY_ID, word.getArrayID());
            values.put(RATING, word.getRating() + dif);
            db.update(WORDS_TABLE_NAME, values, "_id = ?", new String[]{Long.toString(word.getID())});
        }
    }
}
