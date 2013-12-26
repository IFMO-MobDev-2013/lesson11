package ru.ifmo.ii2539.flashcards;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BackSideActivity extends Activity {

    private int number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backside);
        number = getIntent().getExtras().getInt("number");
        int r = getIntent().getExtras().getInt("r");
        int t = getIntent().getExtras().getInt("t");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Answer:\n" + getResources().getStringArray(R.array.en)[number++] + "\nWere you right?\n(Current stats: " + r + "/" + t + ")");
    }

    public void onYesClick(View view) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        WordStats stats = databaseHandler.getWordStats(number);
        databaseHandler.updateWordStats(number, new WordStats(stats.getTotal() + 1, stats.getRight() + 1));
        finish();
    }

    public void onNoClick(View view) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        WordStats stats = databaseHandler.getWordStats(number);
        databaseHandler.updateWordStats(number, new WordStats(stats.getTotal() + 1, stats.getRight()));
        finish();
    }
}
