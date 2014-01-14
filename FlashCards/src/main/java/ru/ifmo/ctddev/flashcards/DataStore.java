package ru.ifmo.ctddev.flashcards;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import ru.ifmo.ctddev.flashcards.cards.Card;
import ru.ifmo.ctddev.flashcards.cards.Category;
import ru.ifmo.ctddev.flashcards.cards.Language;


public class DataStore {

    private DatabaseOpenHelper dbOpenHelper;

    public DataStore(Context context) {
        dbOpenHelper = new DatabaseOpenHelper(context);
    }

    public Cursor getAllCategories() {
        String query = "SELECT * FROM " + DatabaseOpenHelper.CATEGORIES_TABLE_NAME;
        return dbOpenHelper.getReadableDatabase().rawQuery(query, null);
    }

    public List<Card> getAllCardsAsList(int categoryId) {
        String query = "SELECT * FROM " + DatabaseOpenHelper.CARDS_TABLE_NAME
                + " WHERE " + DatabaseOpenHelper.CATEGORY_ID_COLUMN + " = " + categoryId;
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery(query, null);

        List<Card> cards = new ArrayList<>();
        while (cursor.moveToNext()) {
            cards.add(createCard(cursor));
        }

        return cards;
    }

    public Cursor getAllCards(int categoryId) {
        String query = "SELECT * FROM " + DatabaseOpenHelper.CARDS_TABLE_NAME
                + " WHERE " + DatabaseOpenHelper.CATEGORY_ID_COLUMN + " = " + categoryId;
        return dbOpenHelper.getReadableDatabase().rawQuery(query, null);
    }

    public void incRating(int cardId) {
        String sql = "UPDATE " + DatabaseOpenHelper.RATING_TABLE_NAME
                + " SET " + DatabaseOpenHelper.RATING_COLUMN + " = " + DatabaseOpenHelper.RATING_COLUMN + " +1"
                + " WHERE " + DatabaseOpenHelper.CARD_ID_COLUMN + " = " + cardId
                + " AND " + DatabaseOpenHelper.LANGUAGE_COLUMN + " = \"" + Constants.LEARNING_LANGUAGE.getName() + "\""
                + " AND " + DatabaseOpenHelper.RATING_COLUMN + " < " + Constants.MAX_RATING;
        dbOpenHelper.getWritableDatabase().execSQL(sql);
    }

    public void decRating(int cardId) {
        String sql = "UPDATE " + DatabaseOpenHelper.RATING_TABLE_NAME
                + " SET " + DatabaseOpenHelper.RATING_COLUMN + " = " + DatabaseOpenHelper.RATING_COLUMN + " -1"
                + " WHERE " + DatabaseOpenHelper.CARD_ID_COLUMN + " = " + cardId
                + " AND " + DatabaseOpenHelper.LANGUAGE_COLUMN + " = \"" + Constants.LEARNING_LANGUAGE.getName() + "\""
                + " AND " + DatabaseOpenHelper.RATING_COLUMN + " > " + 0;
        dbOpenHelper.getWritableDatabase().execSQL(sql);
    }

    public void clearRating(int categoryId) {
        dbOpenHelper.getWritableDatabase().execSQL("UPDATE " + DatabaseOpenHelper.RATING_TABLE_NAME
                + " SET " + DatabaseOpenHelper.RATING_COLUMN + " = " + Constants.START_RATING
                + " WHERE " + DatabaseOpenHelper.CATEGORY_ID_COLUMN + " = " + categoryId
                + " AND " + DatabaseOpenHelper.LANGUAGE_COLUMN + " = \"" + Constants.LEARNING_LANGUAGE.getName() + "\"");
    }

    public int getCardCount(int categoryId) {
        return dbOpenHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + DatabaseOpenHelper.RATING_TABLE_NAME
                        + " WHERE " + DatabaseOpenHelper.CATEGORY_ID_COLUMN + " = " + categoryId
                        + " AND " + DatabaseOpenHelper.LANGUAGE_COLUMN + " = \"" + Constants.LEARNING_LANGUAGE.getName() + "\"",
                null).getCount();
    }

    public int getLearnedCardCount(int categoryId) {
        return dbOpenHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + DatabaseOpenHelper.RATING_TABLE_NAME
                        + " WHERE " + DatabaseOpenHelper.CATEGORY_ID_COLUMN + " = " + categoryId
                        + " AND " + DatabaseOpenHelper.LANGUAGE_COLUMN + " = \"" + Constants.LEARNING_LANGUAGE.getName() + "\""
                        + " AND " + DatabaseOpenHelper.RATING_COLUMN + " = 0"
                , null).getCount();
    }

    public int getRating(int cardId) {
        String query = "SELECT * FROM " + DatabaseOpenHelper.RATING_TABLE_NAME
                + " WHERE " + DatabaseOpenHelper.CARD_ID_COLUMN + " = " + cardId
                + " AND " + DatabaseOpenHelper.LANGUAGE_COLUMN + " = \"" + Constants.LEARNING_LANGUAGE.getName() + "\"";
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.RATING_COLUMN));
    }

    public Category getCategory(int categoryId) {
        String query = "SELECT * FROM " + DatabaseOpenHelper.CATEGORIES_TABLE_NAME
                + " WHERE " + DatabaseOpenHelper.ID_COLUMN + " = " + categoryId;
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        return createCategory(cursor);
    }

    private Category createCategory(Cursor cursor) {
        String eng = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.ENG_COLUMN));
        String rus = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.RUS_COLUMN));
        String fra = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.FRA_COLUMN));
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.ID_COLUMN));

        EnumMap<Language, String> title = new EnumMap<>(Language.class);
        title.put(Language.ENG, eng);
        title.put(Language.RUS, rus);
        title.put(Language.FRA, fra);

        return new Category(id, title);
    }

    private Card createCard(Cursor cursor) {
        String eng = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.ENG_COLUMN));
        String rus = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.RUS_COLUMN));
        String fra = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.FRA_COLUMN));

        int id = cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.ID_COLUMN));
        int categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.CATEGORY_ID_COLUMN));

        EnumMap<Language, String> title = new EnumMap<>(Language.class);
        title.put(Language.ENG, eng);
        title.put(Language.RUS, rus);
        title.put(Language.FRA, fra);

        return new Card(id, categoryId, title);
    }
}
