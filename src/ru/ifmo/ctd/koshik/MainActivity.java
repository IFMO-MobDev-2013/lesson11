package ru.ifmo.ctd.koshik;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.yandex.metrica.Counter;
import ru.ifmo.ctd.koshik.database.DataBase;
import ru.ifmo.ctd.koshik.database.Word;

public class MainActivity extends Activity {


    private DataBase db;

    private final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Counter.initialize(getApplicationContext());
        Counter.sharedInstance().reportEvent("start");
        Counter.sharedInstance().sendEventsBuffer();

        db = new DataBase(getApplicationContext());
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("firstTime", true)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
            fillDataBase();
        }
    }

    public void startLearning(View v) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    public void startImagesActivity(View v) {
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }

    public void startWordsActivity(View v) {
        Intent intent = new Intent(this, WordsActivity.class);
        startActivity(intent);
    }


    private void fillDataBase() {
        String[] words = getResources().getStringArray(R.array.is_words);
        String[] categories = getResources().getStringArray(R.array.categories);
        for (int i = 0; i < words.length; i++) {
            Word w = new Word();
            w.setWord(words[i]);
            w.setCategory(categories[i / 10]);
            w.setNumOfAttempts(1);
            w.setNumOfCorrect(0);
            db.createWord(w);
        }
    }
}
