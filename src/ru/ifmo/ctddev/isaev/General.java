package ru.ifmo.ctddev.isaev;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import ru.ifmo.ctddev.isaev.orm.Category;
import ru.ifmo.ctddev.isaev.orm.Word;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Locale;

/**
 * User: Xottab
 * Date: 03.01.14
 */
public class General {


    public static final int MAX_CATEGORY_STATUS = 50;
    public static final int MAX_WORD_STATUS = 5;
    public static final String PREFERENCES = "mySettings";
    public static final String FIRST_LAUNCH = "isFirstLaunch";
    public static boolean isFirstLaunch = false;
    public static String LOCALE_FROM = "localeFrom";
    public static String LOCALE_TO = "localeTo";


    public static void processWrongAnswer(Category c, Word w) {
        if (c.getStatus() > 0) {
            c.setStatus(c.getStatus() - 1);
            if (w.getStatus() > 0) {
                w.setStatus(w.getStatus() - 1);
            }
            try {
                wordDao.update(w);
            } catch (SQLException e) {
                Log.e("ну что тут поделаешь", "что-то не так с базой");
            }
        }
    }

    public static void processRightAnswer(Category c, Word w) {

        if (w.getStatus() < MAX_WORD_STATUS) {
            w.setStatus(w.getStatus() + 1);
            if (c.getStatus() < MAX_CATEGORY_STATUS) {
                c.setStatus(c.getStatus() + 1);
            }
            try {
                wordDao.update(w);
            } catch (SQLException e) {
                Log.e("ну что тут поделаешь", "что-то не так с базой");
            }
        }
    }


    public static Locale fromLocale;
    public static Locale toLocale;
    public static Dao categoryDao;
    public static Dao wordDao;
    public static int wordsCount = 0;

    public static enum CategoryEnum {
        ANIMALS(1, "animals"),
        BODYPARTS(2, "bodyparts"),
        CLOTHES(3, "clothesandshoes"),
        DISEASES(4, "disease"),
        FEELINGS(5, "feelings"),
        FOOD(6, "foodanddrink"),
        FURNITURE(7, "furniture"),
        HOBBY(8, "hobby"),
        PLANTS(9, "trees"),
        WEATHER(10, "weather");

        public int id;
        public String folderName;

        private CategoryEnum(int id, String s) {
            this.id = id;
            this.folderName = s;
        }
    }
}
