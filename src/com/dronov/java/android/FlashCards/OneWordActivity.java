package com.dronov.java.android.FlashCards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.dronov.java.android.FlashCards.databases.ChinaFlashCards;
import com.dronov.java.android.FlashCards.databases.EnglishFlashCards;
import com.dronov.java.android.FlashCards.databases.RussianFlashCards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 28.01.14
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */

public class OneWordActivity extends Activity {
    private ArrayList<Word> result = new ArrayList<Word>();
    private int count = 0;
    private ImageView imageView;
    private TextView textView;
    private CharSequence resultLanguage;
    private int resultData = 0;
    private String category;
    private Word categoryWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_word_activity);

        Intent intent = getIntent();
        category = intent.getStringExtra(getResources().getString(R.string.category));

        result = new ParseCategory(this, category).parse();
        categoryWord = result.get(count);
        result.remove(0);

        imageView = (ImageView) findViewById(R.id.picture);
        textView = (TextView) findViewById(R.id.theword);
        Collections.shuffle(result);
        setPicture(result.get(count));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(getToLanguage(result.get(count)));
            }
        });

        Button yes = (Button) findViewById(R.id.yes);
        Button no = (Button) findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultData++;
                count++;
                if (count >= result.size()) {
                    addResultToDatabase();
                    finishSession();
                    return;
                }

                setPicture(result.get(count));
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count >= result.size()) {
                    addResultToDatabase();
                    finishSession();
                    return;
                }
                setPicture(result.get(count));
            }
        });
    }

    private void addResultToDatabase() {
        RussianFlashCards database = new RussianFlashCards(this);
        database.open();
        database.update(categoryWord.getRussian(), String.valueOf(resultData) + "/10");
        database.close();

        EnglishFlashCards database1 = new EnglishFlashCards(this);
        database1.open();
        database1.update(categoryWord.getEnglish(), String.valueOf(resultData) + "/10");
        database1.close();

        ChinaFlashCards database2 = new ChinaFlashCards(this);
        database2.open();
        database2.update(categoryWord.getChina(), String.valueOf(resultData) + "/10");
        database2.close();
    }

    private void finishSession() {
        final Activity activity = this;
        new AlertDialog.Builder(activity)
                .setMessage(getOkMessage(resultData))
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).create().show();
    }

    private String getOkMessage(int count) {
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.russian)))
            return getResources().getString(R.string.result_russian) + " " + String.valueOf(count) + "/10";
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.china)))
            return getResources().getString(R.string.result_china) + " " + String.valueOf(count) + "/10";
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.english)))
            return getResources().getString(R.string.result_english) + " " + String.valueOf(count) + "/10";
        return null;
    }

    private void setPicture(Word word) {
        imageView.setImageResource(getResources().getIdentifier(word.getEnglish().toLowerCase(), "drawable", getPackageName()));
        textView.setText(getFromLanguage(word));
    }

    public String getFromLanguage(Word word) {
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.russian)))
            return word.getRussian();
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.china)))
            return word.getChina();
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.english)))
            return word.getEnglish();
        return null;
    }

    public String getToLanguage(Word word) {
        if (MainActivity.toLanguage.equals(getResources().getString(R.string.russian)))
            return word.getRussian();
        if (MainActivity.toLanguage.equals(getResources().getString(R.string.china)))
            return word.getChina();
        if (MainActivity.toLanguage.equals(getResources().getString(R.string.english)))
            return word.getEnglish();
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();    //To change body of overridden methods use File | Settings | File Templates.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
