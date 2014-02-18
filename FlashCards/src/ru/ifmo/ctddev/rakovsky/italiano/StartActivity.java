package ru.ifmo.ctddev.rakovsky.italiano;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class StartActivity extends Activity {

    private SharedPreferences preferences;
    private static final String PREFERENCES = "preferences";
    private static final String IS_FIRST = "is_first";
    public static ProgressBar progressBar;
    public static TextView progressBarTextView;

    Database db;

    private static final int WORDS_COUNT = 100;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        preferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        db = new Database(this);
        String temp2[] = getResources().getStringArray(R.array.words_id);
        if (!preferences.contains(IS_FIRST)) {
            for (int i = 0; i < temp2.length; i++) {
                db.addWord(temp2[i], 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(IS_FIRST, false);
                editor.apply();
            }
        }

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final TextView textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBarTextView = (TextView) findViewById(R.id.textView1);

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.modes, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int count = 0;
                HashMap<String, Integer> hashMap = db.getAll();
                Collection<Integer> collection = hashMap.values();
                Iterator<Integer> iterator = collection.iterator();
                while (iterator.hasNext()) {
                    Integer element = iterator.next();
                    if (element == 1) {
                        count++;
                    }
                }
                progressBarTextView.setText(String.valueOf(count) + "%");
                progressBar.setProgress(count);
                textView.setText(R.string.progress);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button button = (Button) findViewById(R.id.button);

        Button clear = (Button) findViewById(R.id.clear_stats);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.clearStats();
                String[] temp = getResources().getStringArray(R.array.words_id);
                for (int i = 0; i < temp.length; i++) {
                    db.addWord(temp[i], 0);
                }
                progressBar.setProgress(0);
                progressBarTextView.setText("0%");
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = spinner.getSelectedItemPosition();
                switch (pos) {
                    case 0 : startActivity(new Intent(getApplicationContext(), CategoriesList.class));
                    case 1 : ;
                    case 2 : ;
                }

            }
        });
    }
}
