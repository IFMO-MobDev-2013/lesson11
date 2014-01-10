package ru.ifmo.ctddev.isaev;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import ru.ifmo.ctddev.isaev.FlashCards.R;
import ru.ifmo.ctddev.isaev.orm.Category;
import ru.ifmo.ctddev.isaev.orm.Word;
import com.yandex.metrica.Counter;

import java.sql.SQLException;
import java.util.*;

import static ru.ifmo.ctddev.isaev.General.wordDao;
import static ru.ifmo.ctddev.isaev.MyActivity.getLocalizedResources;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        try {
            super.onCreate();
            Counter.initialize(getApplicationContext());
            Timer myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Counter.sharedInstance().reportEvent("Слов просмотрено: " + General.wordsCount);
                    General.wordsCount = 0;
                }
            }, 600L * 1000, 600L * 1000);
            DatabaseManager.getInstance().init(getApplicationContext());

            General.categoryDao = DatabaseManager.getInstance().getHelper().getCategoryDao();
            General.wordDao = DatabaseManager.getInstance().getHelper().getWordDao();

            SharedPreferences preferences = getSharedPreferences(General.PREFERENCES, Context.MODE_PRIVATE);
            if (preferences.getBoolean(General.FIRST_LAUNCH, true)) {
                Log.i("", "first launch of application");
                General.isFirstLaunch=true;
                String[] categories = getResources().getStringArray(R.array.category);
                for (int i = 0; i < categories.length; i++) {
                    int resID = getResources().getIdentifier("category" + (i + 1), "array", this.getPackageName());
                    String[] words = getResources().getStringArray(resID);
                    List<Word> wordList = new LinkedList<Word>();
                    Category category = new Category(General.CategoryEnum.values()[i].id);
                    General.categoryDao.create(category);
                    int k = 0;
                    for (String w : words) {
                        Word word = new Word(category, k++);
                        wordDao.create(word);
                        wordList.add(word);
                    }
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(General.FIRST_LAUNCH, false);
                    editor.commit();
                }
            } else {
                Log.i("", "not first launch of application");
            }

        } catch (SQLException e) {
            Log.e("error", "error", e);
        }

    }
}