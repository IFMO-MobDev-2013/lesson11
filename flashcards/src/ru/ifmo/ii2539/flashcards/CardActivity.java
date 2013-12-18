package ru.ifmo.ii2539.flashcards;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CardActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        int category = getIntent().getExtras().getInt("category");
        int number = 0;
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Drawable image = getResources().getDrawable(getResources().getIdentifier("i" + Integer.toString(category) + Integer.toString(number), "drawable", getPackageName()));
        imageView.setImageDrawable(image);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(getResources().getStringArray(R.array.ru)[10 * category + number]);
    }
}
