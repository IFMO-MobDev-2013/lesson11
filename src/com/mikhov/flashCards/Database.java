package com.mikhov.flashCards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class Database {
    private static final String DATABASE_TABLE_CARDS = "cards";
    public static final String CARDS_COL_ID = "_id";
    public static final String CARDS_COL_CATEGORY = "category";
    public static final String CARDS_COL_FALSE = "last_false";
    public static final String CARDS_COL_TRUE = "last_true";

    private static final String DATABASE_TABLE_QUESTIONS = "questions";
    public static final String QUESTIONS_COL_ID = "_id";
    public static final String QUESTIONS_COL_CATEGORY = "category";
    public static final String QUESTIONS_COL_QUESTION = "question";
    public static final String QUESTIONS_COL_ANSWER = "answer";
    public static final String QUESTIONS_COL_IMAGE = "image";
    public static final String QUESTIONS_COL_MINI_IMAGE = "mini_image";
    public static final String QUESTIONS_COL_CHANGED = "changed";

    private static final String[] STR_CARDS = { CARDS_COL_ID, CARDS_COL_CATEGORY, CARDS_COL_FALSE, CARDS_COL_TRUE };
    private static final String[] STR_QUESTIONS = {QUESTIONS_COL_ID, QUESTIONS_COL_CATEGORY, QUESTIONS_COL_QUESTION,
            QUESTIONS_COL_ANSWER, QUESTIONS_COL_IMAGE, QUESTIONS_COL_MINI_IMAGE, QUESTIONS_COL_CHANGED };


    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;


    public Database(Context ctx) {
        this.context = ctx;
    }

    public Database open() throws SQLException {
        dbHelper = new DbHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean cardsIsNotEmpty() {
        Cursor cursor = getAllCardsData();
        return cursor.moveToNext();
    }
    public boolean questionsIsNotEmpty(String category) {
        Cursor cursor = getQuestionsData(category);
        return cursor.moveToNext();
    }

    public void dropCards() {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cards");
        sqLiteDatabase.execSQL("create table cards (_id integer primary key autoincrement, category text, last_false integer, last_true integer);");
    }
    public void dropQuestions() {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS questions");
        sqLiteDatabase.execSQL("create table questions (_id integer primary key autoincrement, category text, question text, answer text, image blob, mini_image blob, changed integer);");
    }
    public void dropAll() {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cards");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS questions");
    }

    public Cursor getAllCardsData() {
        return sqLiteDatabase.query(DATABASE_TABLE_CARDS, STR_CARDS, null, null, null, null, CARDS_COL_CATEGORY);
    }

    public Cursor getAllQuestionsData() {
        return sqLiteDatabase.query(DATABASE_TABLE_QUESTIONS, STR_QUESTIONS, null, null, null, null, null);
    }
    public Cursor getQuestionsData(String category) {
        return sqLiteDatabase.query(DATABASE_TABLE_QUESTIONS, STR_QUESTIONS, "category = " + "'" + category + "'", null, null, null, QUESTIONS_COL_QUESTION);
    }
    public Cursor getUndeletedQuestionsData(String category) {
        return sqLiteDatabase.query(DATABASE_TABLE_QUESTIONS, STR_QUESTIONS, QUESTIONS_COL_CATEGORY + "='" + category + "' AND (" + QUESTIONS_COL_CHANGED + "=0 OR " + QUESTIONS_COL_CHANGED + "=1)", null, null, null, QUESTIONS_COL_QUESTION);
    }

    public String getCategory(long id) throws SQLException {
        Cursor cursor = sqLiteDatabase.query(true, DATABASE_TABLE_CARDS, STR_CARDS, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(CARDS_COL_ID)) == id) {
                break;
            }
        }
        return cursor.getString(cursor.getColumnIndex(CARDS_COL_CATEGORY));
    }
    public String getQuestion(long id, String category) throws SQLException {
        Cursor cursor = sqLiteDatabase.query(true, DATABASE_TABLE_QUESTIONS, STR_QUESTIONS, "category = " + "'" + category + "'", null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(QUESTIONS_COL_ID)) == id) {
                break;
            }
        }
        return cursor.getString(cursor.getColumnIndex(QUESTIONS_COL_QUESTION));
    }
    public int getQuestionState(String question, String category) throws SQLException {
        Cursor cursor = sqLiteDatabase.query(true, DATABASE_TABLE_QUESTIONS, STR_QUESTIONS, "category = '" + category + "' AND question = '" + question + "'", null, null, null, null, null);
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex(QUESTIONS_COL_CHANGED));
        } else {
            return 0;
        }
    }

    public void deleteCategory(long id) {
        sqLiteDatabase.delete(DATABASE_TABLE_CARDS, CARDS_COL_ID + "=" + id, null);
    }
    public void deleteQuestion(String question, String category) {
        sqLiteDatabase.delete(DATABASE_TABLE_QUESTIONS, QUESTIONS_COL_QUESTION + "='" + question + "' AND category = '" + category + "'", null);
    }
    public void deleteQuestionsWithCategory(String category) {
        sqLiteDatabase.delete(DATABASE_TABLE_QUESTIONS, QUESTIONS_COL_CATEGORY + "='" + category + "'", null);
    }

    public void addCategory(String category, int lastfalse, int lasttrue) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CARDS_COL_CATEGORY, category);
        contentValues.put(CARDS_COL_FALSE, lastfalse);
        contentValues.put(CARDS_COL_TRUE, lasttrue);
        sqLiteDatabase.insert(DATABASE_TABLE_CARDS, null, contentValues);
    }
    public void addQuestion(String category, String question, String answer, Bitmap image, Bitmap mini_image, int changed) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTIONS_COL_CATEGORY, category);
        contentValues.put(QUESTIONS_COL_QUESTION, question);
        contentValues.put(QUESTIONS_COL_ANSWER, answer);
        if (image != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] image_byte = byteArrayOutputStream.toByteArray();
            contentValues.put(QUESTIONS_COL_IMAGE, image_byte);
        }
        if (mini_image != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mini_image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] image_byte = byteArrayOutputStream.toByteArray();
            contentValues.put(QUESTIONS_COL_MINI_IMAGE, image_byte);
        }
        contentValues.put(QUESTIONS_COL_CHANGED, changed);
        sqLiteDatabase.insert(DATABASE_TABLE_QUESTIONS, null, contentValues);
    }

    public boolean uniqCategory(String category) {
        boolean unique = true;
        Cursor cursor = getAllCardsData();
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(CARDS_COL_CATEGORY)).equals(category)) {
                unique = false;
                break;
            }
        }
        return unique;
    }
    public boolean uniqQuestion(String category, String question) {
        boolean unique = true;
        Cursor cursor = getQuestionsData(category);
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(QUESTIONS_COL_QUESTION)).equals(question)) {
                unique = false;
                break;
            }
        }
        return unique;
    }

    public void updateCategory(String oldCategory, String category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CARDS_COL_CATEGORY, category);
        sqLiteDatabase.update(DATABASE_TABLE_CARDS, contentValues, CARDS_COL_CATEGORY + "='" + oldCategory + "'", null);
    }

    public void updateStats(String category, int lastfalse, int lasttrue) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CARDS_COL_FALSE, lastfalse);
        contentValues.put(CARDS_COL_TRUE, lasttrue);
        sqLiteDatabase.update(DATABASE_TABLE_CARDS, contentValues, CARDS_COL_CATEGORY + "='" + category + "'", null);
    }

    public void updateQuestions(String oldCategory, String category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTIONS_COL_CATEGORY, category);
        sqLiteDatabase.update(DATABASE_TABLE_QUESTIONS, contentValues, QUESTIONS_COL_CATEGORY + "='" + oldCategory + "'", null);
    }
    public void updateQuestion(String question, String category, int changed) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTIONS_COL_CHANGED, changed);
        sqLiteDatabase.update(DATABASE_TABLE_QUESTIONS, contentValues, QUESTIONS_COL_QUESTION + "='" + question + "' AND category = '" + category + "'", null);
    }
    public void cancelAllChanges() {
        sqLiteDatabase.delete(DATABASE_TABLE_QUESTIONS, QUESTIONS_COL_CHANGED + " = 1", null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTIONS_COL_CHANGED, 0);
        sqLiteDatabase.update(DATABASE_TABLE_QUESTIONS, contentValues, QUESTIONS_COL_CHANGED + " = 3", null);
    }
    public void applyAllChanges() {
        sqLiteDatabase.delete(DATABASE_TABLE_QUESTIONS, QUESTIONS_COL_CHANGED + " = 3", null);
        sqLiteDatabase.delete(DATABASE_TABLE_QUESTIONS, QUESTIONS_COL_CHANGED + " = 3", null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTIONS_COL_CHANGED, 0);
        sqLiteDatabase.update(DATABASE_TABLE_QUESTIONS, contentValues, QUESTIONS_COL_CHANGED + " = 1", null);
    }
}
