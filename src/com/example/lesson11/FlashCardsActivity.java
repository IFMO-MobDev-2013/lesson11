package com.example.lesson11;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.example.lesson11.DataBase.RussianDB;
import com.example.lesson11.DataBase.UzbekDB;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 02.02.14
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class FlashCardsActivity extends Activity {
    private ArrayList<Word> words = new ArrayList<Word>();
    private String category, language;
    private Integer count = 0;
    private int resultData = 0;
    TextView textView, textViewans;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcards);

        textView = (TextView) findViewById(R.id.textViewfl);
        imageView = (ImageView) findViewById(R.id.imageView);
        textViewans = (TextView) findViewById(R.id.textViewflans);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lltop);

        int x = getResources().getColor(R.color.Bckgr);
        linearLayout.setBackgroundColor(x);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        language = intent.getStringExtra("nativelanguage");
        words = new WordsCollection(this, category).searching();
        if(count > 0){
            words.remove(0);
        }

        Collections.shuffle(words);

        setPicture(words.get(count));

        final Button yes = (Button) findViewById(R.id.yes);
        final Button no = (Button) findViewById(R.id.no);
        yes.setEnabled(false);
        no.setEnabled(false);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(language.equals("rus")){
                    textViewans.setText(words.get(count).getUzbek());
                    Toast.makeText(getApplicationContext(),"Вы знали?", Toast.LENGTH_SHORT).show();
                }
                else if(language.equals("uzb")){
                    textViewans.setText(words.get(count).getRussian());
                    Toast.makeText(getApplicationContext(),"Bilar edizmi?", Toast.LENGTH_SHORT).show();
                }

                yes.setEnabled(true);
                no.setEnabled(true);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultData++;
                count++;
                if (count >= words.size()) {
                    addResultToDatabase();
                    finishSession();
                    return;
                }
                yes.setEnabled(false);
                no.setEnabled(false);
                setPicture(words.get(count));
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count >= words.size()) {
                    addResultToDatabase();
                    finishSession();
                    return;
                }
                yes.setEnabled(false);
                no.setEnabled(false);
                setPicture(words.get(count));
            }
        });
    }

    private void setPicture(Word word) {
        imageView.setImageResource(getResources().getIdentifier(word.getName().toLowerCase(), "drawable", getPackageName()));
        if(language.equals("rus")){
            textView.setText(word.getRussian());
        }
        else if(language.equals("uzb")){
            textView.setText(word.getUzbek());
        }
        textViewans.setText("*****");
    }
    private String getOkMessage(int count) {
        if (language.equals(getResources().getString(R.string.russian)))
            return getResources().getString(R.string.result_russian) + " " + String.valueOf(count) + getString(R.string.zero);
        if (language.equals(getResources().getString(R.string.uzbek)))
            return getResources().getString(R.string.result_uzbek) + " " + String.valueOf(count) + getString(R.string.zero);
        return null;
    }
    private void finishSession() {
        final Activity activity = this;
        new AlertDialog.Builder(activity)
                .setMessage(getOkMessage(resultData))
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).create().show();
    }
    private void addResultToDatabase() {
        RussianDB database = new RussianDB(this);
        database.open();
        database.update(category,  String.valueOf(resultData) + getString(R.string.zero));
        database.close();

        UzbekDB database1 = new UzbekDB(this);
        database1.open();
        database1.update(category,  String.valueOf(resultData) + getString(R.string.zero));
        database1.close();
    }
}


