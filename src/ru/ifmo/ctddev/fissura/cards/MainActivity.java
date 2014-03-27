package ru.ifmo.ctddev.fissura.cards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends Activity {

    private SharedPreferences preferences;
    private static final String PREFERENCES = "preferences";
    private static final String IS_FIRST = "is_first";
    public static TextView myProgress;

    private static final int WORDS_COUNT = 100;
    
    DBHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        preferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        db = new DBHelper(this);
        String temp2[] = getResources().getStringArray(R.array.words_id);
        if (!preferences.contains(IS_FIRST)) {
            for (int i = 0; i < temp2.length; i++) {
                db.addWord(temp2[i], 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(IS_FIRST, false);
                editor.apply();
            }
        }
        myProgress = (TextView) findViewById(R.id.textView1);

        Button startButton = (Button) findViewById(R.id.start_button);

        Button clear = (Button) findViewById(R.id.clear_stats);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Categories.class));
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] temp = getResources().getStringArray(R.array.words_id);
                for (int i = 0; i < temp.length; i++) {
                    db.addWord(temp[i], 0);
                }
                myProgress.setText("0%");
            }
        });
    }
}
