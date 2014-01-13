package ru.ifmo.ctd.koshik;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ru.ifmo.ctd.koshik.database.DataBase;

import java.util.ArrayList;
import java.util.Random;

public class ImagesActivity extends Activity {

    private String[] translations;
    private String[] imageNames;

    private ImageView[] images;

    private TextView tv;
    private ImageView imageView;

    private DataBase db;

    private Random r;
    private int[] indexes;
    private double[] priorities;

    private int answerID;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images);

        translations = getResources().getStringArray(R.array.is_words);
        imageNames = getResources().getStringArray(R.array.images_name);

        images = new ImageView[4];
        images[0] = (ImageView) findViewById(R.id.image1);
        images[1] = (ImageView) findViewById(R.id.image2);
        images[2] = (ImageView) findViewById(R.id.image3);
        images[3] = (ImageView) findViewById(R.id.image4);

        tv = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.imageC);

        db = new DataBase(getApplicationContext());

        r = new Random();
        indexes = new int[translations.length / 3];
        priorities = new double[translations.length / 3];

        changeView();
    }

    public void check(View v) {
        ImageView b = (ImageView) v;
        if (b.getId() == answerID) {
            db.incCorrect(tv.getText().toString());
            Bitmap bm = BitmapFactory.decodeResource(getApplicationContext()
                    .getResources(), R.drawable.correct);
            imageView.setImageBitmap(bm);

        } else {
            db.incIncorrect(tv.getText().toString());
            Bitmap bm = BitmapFactory.decodeResource(getApplicationContext()
                    .getResources(), R.drawable.incorrect);
            imageView.setImageBitmap(bm);
        }
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

        tv.setText(translations[indexes[0]]);

        ArrayList<Integer> temp = new ArrayList<>();

        int b = Math.abs(r.nextInt()) % 4;
        answerID = images[b].getId();
        for (int i = 0; i < 4; i++) {
            String name;
            if (i == b) {
                name = imageNames[indexes[0]];
            } else {
                boolean flag = true;
                int c = 0;
                while (flag) {
                    c = Math.abs(r.nextInt()) % 100;
                    flag = false;
                    for (int j = 0; j < temp.size(); j++) {
                        if ((temp.get(j) == c))
                            flag = true;
                    }
                    if (c == indexes[0]) flag = true;
                }
                temp.add(c);
                name = imageNames[c];
            }
            int resID = getResources().getIdentifier(name, "drawable", getPackageName());
            Bitmap bm = BitmapFactory.decodeResource(getApplicationContext()
                    .getResources(), resID);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = getDisplaySize(display);
            images[i].setAdjustViewBounds(true);

            int width = size.x;
            int height = size.y;
            width = Math.min((int) (width / 1.4), bm.getWidth());
            Bitmap c = Bitmap
                    .createScaledBitmap(bm, width, (int) (height / 2), true);
            images[i].setImageBitmap(c);
        }



    }
    @SuppressLint("NewApi")
    private static Point getDisplaySize(final Display display) {
        Point point = new Point();
            display.getSize(point);
        return point;
    }
}