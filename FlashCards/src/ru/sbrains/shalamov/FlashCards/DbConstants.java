package ru.sbrains.shalamov.FlashCards;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 17.11.13
 * Time: 19:42
 * To change this template use File | Settings | File Templates.
 */
public interface DbConstants {

    public static final String DATABASE_NAME = "FlashCardsDatabase";

    public static final String TABLE_WORDS = "words";
    public static final String TABLE_STATS = "statistics";
    public static final String TABLE_CATEGORIES = "categories";


    // common
    public static final String KEY_ROWID = "_id";


    //words
    public static final String KEY_RUS = "rus";
    public static final String KEY_EN = "en";
    public static final String KEY_IMG = "img";
    //public static final String KEY_STATID = "stat_id";
    public static final String KEY_CATID = "cat_id";


    // stats
    public static final String KEY_SHOWN = "shown";
    public static final String KEY_CORRECT = "correct";
    public static final String KEY_LAST = "last";
    public static final String KEY_WORDID = "word_id";

    //categories
    public static final String KEY_CATEG = "categ";
    public static final String KEY_SIZE = "size";


//-----------------------------------------------------------------------------------------


    public static final String CREATE_TABLE_WORDS = "CREATE TABLE IF NOT EXISTS " + TABLE_WORDS + " ( " +
            KEY_ROWID + " integer primary key autoincrement, " +
            KEY_RUS + " text not null , " +
            KEY_EN + " text not null , " +
            KEY_IMG + " integer not null , " +
            KEY_CATID + " integer not null " +
            "); ";

    public static final String CREATE_TABLE_STATS = "CREATE TABLE IF NOT EXISTS " + TABLE_STATS + " ( " +
            KEY_ROWID + " integer primary key autoincrement, " +
            KEY_SHOWN + " integer, " +
            KEY_CORRECT + " integer, " +
            KEY_LAST + " text not null , " +
            KEY_WORDID + " integer not null " +
            ");";

    public static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES + " ( " +
            KEY_ROWID + " integer primary key autoincrement, " +
            KEY_CATEG + " text not null, " +
            KEY_SIZE + " integer not null " +
            ");";
}
