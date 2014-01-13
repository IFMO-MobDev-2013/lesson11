package com.example.lesson11;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 12/25/13
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuizActivity extends Activity {
    private int category_id;
    private int current_flashcard;
    private ImageView imageView;
    private TextView word;
    private Statistics stat;
    private TextView translation;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
        Intent intent = getIntent();
        category_id = intent.getIntExtra("id", 0);
        stat  = new Statistics(this);
        stat.open();
        current_flashcard = stat.nextFlashcard(category_id, current_flashcard);
        imageView = (ImageView)findViewById(R.id.image);
        word = (TextView)findViewById(R.id.word);
        translation = (TextView)findViewById(R.id.translation);
        imageView.setImageResource(getResource(category_id, current_flashcard));
        word.setText(getStringResource(this, category_id, current_flashcard, "jp"));
    }

    private int getResource(int cat_id, int cur_flashcard) {
        return getResources().getIdentifier("img_" + cat_id + "_" + cur_flashcard, "drawable", getPackageName());
    }

    public void showTranslation(View v) {
    /*    Locale current = getResources().getConfiguration().locale;
        String lang = current.getLanguage();
        String trans;
        switch (lang) {

            case "ru": {
                trans = getStringResource(category_id, current_flashcard, "ru");
                break;
            }
            case "zh": {
                trans = getStringResource(category_id, current_flashcard, "zh");
                break;
            }
            default: {
                trans = getStringResource(category_id, current_flashcard, "en");
                break;
            }


        }
        */
        String trans = getStringResource(this, category_id, current_flashcard, "tr");
        translation.setText(trans);

    }



    public void nextFlashcard(View v) {
        current_flashcard = stat.nextFlashcard(category_id, current_flashcard);
        imageView.setImageResource(getResource(category_id, current_flashcard));
        translation.setText("");

        word.setText(getStringResource(this, category_id, current_flashcard, "jp"));


    }

    public static String getStringResource(Context c, int category, int flashcard, String language) {
        return c.getResources().getString(c.getResources().getIdentifier(getResName(category, flashcard, language), "string", c.getPackageName()));
    }

    public static String getResName(int category, int flashcard, String language) {
        return ("w_" + category + "_" + flashcard + "_" + language);


    }
}