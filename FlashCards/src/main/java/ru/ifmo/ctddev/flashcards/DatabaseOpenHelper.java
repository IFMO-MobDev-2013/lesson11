package ru.ifmo.ctddev.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.ifmo.ctddev.flashcards.cards.Language;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String ID_COLUMN = "_id";
    public static final String CATEGORY_ID_COLUMN = "category_id";
    public static final String CARD_ID_COLUMN = "card_id";
    public static final String ENG_COLUMN = Language.ENG.name();
    public static final String RUS_COLUMN = Language.RUS.name();
    public static final String FRA_COLUMN = Language.FRA.name();
    public static final String RATING_COLUMN = "rating";
    public static final String LANGUAGE_COLUMN = "language";
    //
    public static final String CATEGORIES_TABLE_NAME = "categories";
    public static final String CARDS_TABLE_NAME = "cards";
    public static final String RATING_TABLE_NAME = "rating";
    public static final String DB_NAME = "cards";
    public static final int VERSION = 1;
    //
    public static final String CATEGORIES_CREATE_SCRIPT = "CREATE TABLE " + CATEGORIES_TABLE_NAME
            + " ( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ENG_COLUMN + " TEXT, "
            + RUS_COLUMN + " TEXT, "
            + FRA_COLUMN + " TEXT" + ")";
    public static final String CARDS_CREATE_SCRIPT = "CREATE TABLE " + CARDS_TABLE_NAME
            + " ( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CATEGORY_ID_COLUMN + " INT, "
            + ENG_COLUMN + " TEXT, "
            + RUS_COLUMN + " TEXT, "
            + FRA_COLUMN + " TEXT )";
    public static final String RATING_CREATE_SCRIPT = "CREATE TABLE " + RATING_TABLE_NAME
            + " ( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CATEGORY_ID_COLUMN + " INT, "
            + CARD_ID_COLUMN + " INT, "
            + LANGUAGE_COLUMN + " TEXT, "
            + RATING_COLUMN + " INT )";

    private Context context;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CATEGORIES_CREATE_SCRIPT);
        sqLiteDatabase.execSQL(CARDS_CREATE_SCRIPT);
        sqLiteDatabase.execSQL(RATING_CREATE_SCRIPT);

        AssetManager am = context.getAssets();
        try {
            String[] fileList = am.list(Constants.CATEGORY_ASSETS);
            for (String filename : fileList) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(
                            am.open(Constants.CATEGORY_ASSETS + "/" + filename)
                    ));

                    String eng = br.readLine();
                    String rus = br.readLine();
                    String fra = br.readLine();

                    ContentValues values = new ContentValues();
                    values.put(ENG_COLUMN, eng);
                    values.put(RUS_COLUMN, rus);
                    values.put(FRA_COLUMN, fra);
                    long categoryId = sqLiteDatabase.insert(CATEGORIES_TABLE_NAME, null, values);

                    while (br.ready()) {
                        eng = br.readLine();
                        rus = br.readLine();
                        fra = br.readLine();

                        values = new ContentValues();
                        values.put(ENG_COLUMN, eng);
                        values.put(RUS_COLUMN, rus);
                        values.put(FRA_COLUMN, fra);
                        values.put(CATEGORY_ID_COLUMN, categoryId);
                        long cardId = sqLiteDatabase.insert(CARDS_TABLE_NAME, null, values);

                        for (Language l : Language.values()) {
                            values = new ContentValues();
                            values.put(CATEGORY_ID_COLUMN, categoryId);
                            values.put(CARD_ID_COLUMN, cardId);
                            values.put(LANGUAGE_COLUMN, l.getName());
                            values.put(RATING_COLUMN, Constants.START_RATING);
                            sqLiteDatabase.insert(RATING_TABLE_NAME, null, values);
                        }
                    }
                } finally {
                    if (br != null) {
                        br.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CARDS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RATING_TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
