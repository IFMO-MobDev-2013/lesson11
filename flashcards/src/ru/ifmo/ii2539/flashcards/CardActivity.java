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
        //textView.setText(getResources().getStringArray(R.array.ru)[10 * category + number]);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.updateWordStats(1, new WordStats(7, 4));
        try {
            //textView.setText(Integer.toString(databaseHandler.getWordStats(2).getRight()) + "/" + Integer.toString(databaseHandler.getWordStats(2).getTotal()));
            textView.setText(Integer.toString(databaseHandler.getAllStats().size()));
        } catch (Exception e) {
            textView.setText(e.getMessage());
        }
        //databaseHandler.updateWordStats(0, new WordStats(1, 1));
    }
}
