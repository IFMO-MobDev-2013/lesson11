package ru.ifmo.ctd.koshik;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ru.ifmo.ctd.koshik.database.DataBase;

import java.util.Random;


public class LearningActivity extends Activity {

    private ImageView imageView;
    private TextView textView;
    private ImageButton correctButton;
    private ImageButton incorrectButton;
    private DataBase db;

    private String word;
    private int curIndex;

    private String[] translations;
    private String[] words;
    private String[] imagesName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning);

        db = new DataBase(getApplicationContext());

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        correctButton = (ImageButton) findViewById(R.id.correct);
        Bitmap b = BitmapFactory.decodeResource(getApplicationContext()
                .getResources(), R.drawable.correct);
        correctButton.setImageBitmap(b);
        incorrectButton = (ImageButton) findViewById(R.id.incorrect);
        Bitmap b1 = BitmapFactory.decodeResource(getApplicationContext()
                .getResources(), R.drawable.incorrect);
        incorrectButton.setImageBitmap(b1);

        int numOfCategory = getIntent().getIntExtra("num", 0);
        String[] allTranslations = getResources().getStringArray(R.array.is_words);
        String[] allWords = getResources().getStringArray(R.array.words);
        String[] allImagesName = getResources().getStringArray(R.array.images_name);

        translations = new String[10];
        words = new String[10];
        imagesName = new String[10];
        for (int i = 0; i < 10; i++) {
            translations[i] = allTranslations[numOfCategory * 10 + i];
            words[i] = allWords[numOfCategory * 10 + i];
            imagesName[i] = allImagesName[numOfCategory * 10 + i];
        }
        changeWord();
    }

    public void showButtons(View v) {
        textView.setText(translations[curIndex]);
        correctButton.setVisibility(View.VISIBLE);
        incorrectButton.setVisibility(View.VISIBLE);
    }

    private void changeWord() {

        Random r = new Random();
        int[] indexes = new int[10 / 3];
        double[] priorities = new double[10 / 3];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = Math.abs(r.nextInt()) % 10;
            priorities[i] = (double)db.getCountOfCorrect(translations[indexes[i]]) /
                    (double)db.getCountOfAttempts(translations[indexes[i]]);
        }


        for (int i = 0; i < priorities.length; i++) {
            for (int j = 1 + i; j < priorities.length; j++) {
                if (priorities[i] > priorities[j]) {
                    double t = priorities[i];
                    priorities[i] = priorities[j];
                    priorities[j] = t;

                    int c = indexes[i];
                    indexes[i] = indexes[j];
                    indexes[j] = c;
                }
            }
        }

        for (int i = 0; i < priorities.length; i++) {
            Log.d(i + 1 +"", priorities[i] + "");
        }

        curIndex = indexes[0];

        String name = imagesName[curIndex];
        int resID = getResources().getIdentifier(name, "drawable", getPackageName());
        //imageView.setImageResource(resID);
        Bitmap b = BitmapFactory.decodeResource(getApplicationContext()
                .getResources(), resID);
        imageView.setImageBitmap(b);
        textView.setText(words[curIndex]);
        word = translations[curIndex];

        correctButton.setVisibility(View.INVISIBLE);
        incorrectButton.setVisibility(View.INVISIBLE);
    }

    public void incCorrect(View v) {
        db.incCorrect(word);
        changeWord();
    }

    public void decCorrect(View v) {
        db.incIncorrect(word);
        changeWord();
    }

}