package ru.ifmo.ii2539.flashcards;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class CardActivity extends Activity {

    private int cardNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        showCard(getIntent().getExtras().getInt("category"));
    }

    void showCard(int category) {
        int number = 0;
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Drawable image = getResources().getDrawable(getResources().getIdentifier("i" + Integer.toString(category) + Integer.toString(number), "drawable", getPackageName()));
        imageView.setImageDrawable(image);
        TextView textView = (TextView) findViewById(R.id.textView);
        cardNumber = 10 * category + number;
        textView.setText(getResources().getStringArray(R.array.ru)[cardNumber]);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Intent intent = new Intent(getApplicationContext(), BackSideActivity.class);
            intent.putExtra("number", cardNumber);
            startActivity(intent);
        }
        return super.onTouchEvent(event);
    }
}
