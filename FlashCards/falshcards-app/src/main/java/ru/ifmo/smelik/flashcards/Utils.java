package ru.ifmo.smelik.flashcards;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import ru.ifmo.smelik.flashcards.database.DatabaseTable;
import ru.ifmo.smelik.flashcards.database.dbOpenHelper;

/**
 * Created by Nick Smelik on 12.01.14.
 */
public class Utils {

    public static final int MAX_CATEGORY_VALUE = 50;
    public static final int MAX_WORD_VALUE = 5;
    public static final String SETTINGS = "settings";
    public static final String FIRST_LAUNCH = "first_launch";
    public static final String LANGUAGE_FROM = "language_from";
    public static final String LANGUAGE_TO = "language_to";
    public static final String SELECTION = "selectionCategory";
    public static final String GAME_MODE = "mode";
    public static final Locale RUSSIAN = new Locale("ru");

    public static Drawable loadImageFromAsset(Context context, Word word) {
        try {
            String path = Category.values()[word.getCategory().getId() - 1].getName() + "/" + Integer.toString(word.getArrayID()) + ".jpg";
            InputStream ims = context.getAssets().open(path);
            Drawable d = Drawable.createFromStream(ims, null);
            return d;
        }
        catch(IOException ex) {
            return null;
        }
    }

    public static Resources getLocalizedResources(Context context, Locale locale) {
        DisplayMetrics metrics = new DisplayMetrics();
        Activity act = (Activity) context;
        act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Configuration conf = context.getResources().getConfiguration();
        conf.locale = locale;
        return new Resources(context.getAssets(), metrics, conf);
    }

    public static void correctAnswer(DatabaseTable table, Word word) {
        if (word.getCategory().getStatus() < MAX_CATEGORY_VALUE) {

        }
    }

    public static Locale localeTO;
    public static Locale localeFROM;
    public static Class gameMode;

    public static enum Category{
        ANIMALS(1, "Animals"),
        CLOTHING(2, "Clothing"),
        FOOD(3, "Food and Drinks"),
        HOTEL(4, "Hotel"),
        HOUSE(5, "House"),
        ILLNESS(6, "Illness"),
        LEISURE(7, "Leisure"),
        PLANTS(8, "Plants"),
        STREET(9, "Street"),
        WEATHER(10, "Weather");


        private int id;
        private String name;


        private Category(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
