package ru.ifmo.ctddev.rakovsky.italiano;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Learning extends Activity {

    public static final int CATEGORY_NUMBER = 10;
    public static final int WORDS_COUNT = 10;
    public static final String DRAWABLE = "drawable";

    int i = 0;
    Database db;
    HashMap<String, Integer> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning);

        db = new Database(this);
        int category_number = getIntent().getIntExtra(CategoriesList.CATEGORY_KEY, 0);
        final Button yes = (Button) findViewById(R.id.button);
        final Button no = (Button) findViewById(R.id.button1);
        final TextView translation = (TextView) findViewById(R.id.answer_text_view);
        final TextView question = (TextView) findViewById(R.id.do_you_know_the_word_id);
        question.setVisibility(View.INVISIBLE);
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);
        translation.setVisibility(View.INVISIBLE);
        int begin_index = CATEGORY_NUMBER * category_number;
        i = begin_index;
        final String[] translations = new String[WORDS_COUNT];
        final String[] words = new String[WORDS_COUNT];
        final String[] words_id = new String[WORDS_COUNT];


        String[] temp = getResources().getStringArray(R.array.words);
        String[] temp2 = getResources().getStringArray(R.array.words_id);
        String[] temp3 = getResources().getStringArray(R.array.traslations);

        for (int i = begin_index; i < begin_index + WORDS_COUNT; i++) {
            words[i % WORDS_COUNT] = temp[i];
            words_id[i % WORDS_COUNT] = temp2[i];
            translations[i % WORDS_COUNT] = temp3[i];
        }

        int[] id = new int[WORDS_COUNT];

        for (int i = 0; i < words.length; i++) {
            id[i] = getResources().getIdentifier(words_id[i], DRAWABLE, getPackageName());
        }


        final TextView textView = (TextView) findViewById(R.id.current_word);

        final Bitmap[] bitmaps = new Bitmap[WORDS_COUNT];
        for (int i = 0; i < id.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), id[i]);
        }

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        int tmp = 0;
        hashMap = db.getAll();
        for (i = 0; i < WORDS_COUNT; i++) {
            if (hashMap.get(words_id[i]) == 0) {
                imageView.setImageBitmap(bitmaps[i]);
                textView.setText(words[i]);
                break;
            } else tmp++;
        }
        if (tmp == WORDS_COUNT) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
            finish();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translation.setText(translations[i]);
                translation.setVisibility(View.VISIBLE);
                yes.setVisibility(View.VISIBLE);
                no.setVisibility(View.VISIBLE);
                question.setVisibility(View.VISIBLE);

            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.update(words_id[i], 1);
                hashMap = db.getAll();
                int learnt = 0;
                i = (i + 1) % WORDS_COUNT;
                while (hashMap.get(words_id[i]) == 1) {
                    i = (i + 1) % WORDS_COUNT;
                    learnt++;
                    if (learnt == WORDS_COUNT) {

                        Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    }
                }
                imageView.setImageBitmap(bitmaps[i]);
                textView.setText(words[i]);
                translation.setVisibility(View.INVISIBLE);
                yes.setVisibility(View.INVISIBLE);
                no.setVisibility(View.INVISIBLE);
                question.setVisibility(View.INVISIBLE);
                int progress = StartActivity.progressBar.getProgress();
                StartActivity.progressBar.setProgress(progress + 1);
                StartActivity.progressBarTextView.setText(String.valueOf(progress + 1) + "%");
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hashMap = db.getAll();
                int learnt = 0;
                i = (i + 1) % WORDS_COUNT;
                while (hashMap.get(words_id[i]) == 1) {
                        i = (i + 1) % WORDS_COUNT;
                        learnt++;
                    if (learnt == WORDS_COUNT) {

                        Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
                imageView.setImageBitmap(bitmaps[i]);
                textView.setText(words[i]);
                translation.setVisibility(View.INVISIBLE);
                yes.setVisibility(View.INVISIBLE);
                no.setVisibility(View.INVISIBLE);
                question.setVisibility(View.INVISIBLE);
            }
        });
    }


}
