package ru.ifmo.ii2539.flashcards;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardActivity extends Activity {

    private int cardNumber;
    private int r, t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        showCard(getIntent().getExtras().getInt("category"));
    }

    void showCard(int category) {
        int number = getNumber(category);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Drawable image = getResources().getDrawable(getResources().getIdentifier("i" + Integer.toString(category) + Integer.toString(number), "drawable", getPackageName()));
        imageView.setImageDrawable(image);
        TextView textView = (TextView) findViewById(R.id.textView);
        cardNumber = 10 * category + number;
        textView.setText(getResources().getStringArray(R.array.ru)[cardNumber]);
    }

    private int getNumber(int category) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        List<WordStats> stats = databaseHandler.getAllStats();
        double min = 100;
        for (int i = 0; i < 10; i++) {
            if (stats.get(10 * category + i).getTotal() == 0) {
                min = 0;
            } else {
                min = Math.min(min, stats.get(10 * category + i).getRight() / stats.get(10 * category + i).getTotal());
            }
        }
        List<Integer> candidates = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            if (stats.get(10 * category + i).getTotal() == 0 || stats.get(10 * category + i).getRight() / stats.get(10 * category + i).getTotal() == min) {
                candidates.add(i);
            }
        }
        int num = candidates.get(new Random().nextInt(candidates.size()));
        r = stats.get(10 * category + num).getRight();
        t = stats.get(10 * category + num).getRight();
        return num;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Intent intent = new Intent(getApplicationContext(), BackSideActivity.class);
            intent.putExtra("number", cardNumber);
            intent.putExtra("r", r);
            intent.putExtra("t", t);
            startActivity(intent);
        }
        showCard(cardNumber / 10);
        return super.onTouchEvent(event);
    }
}
