package ru.ifmo.ctd.lukin;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import ru.ifmo.ctd.lukin.database.DataBase;

import java.util.Random;

public class WordsActivity extends Activity {

    private String[] translations;
    private String[] imageNames;

    private Button[] buttons;

    private ImageView iv;
    private ImageView imageView;

    private DataBase db;

    private String answer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words);

        translations = getResources().getStringArray(R.array.is_words);
        imageNames = getResources().getStringArray(R.array.images_name);

        buttons = new Button[4];
        buttons[0] = (Button)findViewById(R.id.but1);
        buttons[1] = (Button)findViewById(R.id.but2);
        buttons[2] = (Button)findViewById(R.id.but3);
        buttons[3] = (Button)findViewById(R.id.but4);

        iv = (ImageView) findViewById(R.id.image);
        imageView = (ImageView) findViewById(R.id.imageV);

        db = new DataBase(getApplicationContext());

        changeView();
    }

    public void checkAnswer(View v) {
        Button b = (Button) v;
        Bitmap bm;
        if (b.getText().equals(answer)) {
            db.incCorrect(answer);
            bm = BitmapFactory.decodeResource(getApplicationContext()
                    .getResources(), R.drawable.correct);
        } else {
            db.incIncorrect(answer);
            bm = BitmapFactory.decodeResource(getApplicationContext()
                    .getResources(), R.drawable.incorrect);
        }
        imageView.setImageBitmap(bm);
        imageView.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeView();
                imageView.setVisibility(View.INVISIBLE);
            }
        }, 1500);
    }

    private void changeView() {
        Random r = new Random();
        int[] indexes = new int[translations.length / 3];
        double[] priorities = new double[translations.length / 3];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = Math.abs(r.nextInt()) % 100;
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

        answer = translations[indexes[0]];

        String name = imageNames[indexes[0]];
        int resID = getResources().getIdentifier(name, "drawable", getPackageName());
        Bitmap bm = BitmapFactory.decodeResource(getApplicationContext()
                .getResources(), resID);
        iv.setImageBitmap(bm);

        int b = Math.abs(r.nextInt()) % 4;
        for (int i =0 ; i < 4; i++) {
            if (i == b) {
                buttons[i].setText(translations[indexes[0]]);
            } else {
                int c = Math.abs(r.nextInt()) % 100;
                buttons[i].setText(translations[c]);
            }
        }
    }
}