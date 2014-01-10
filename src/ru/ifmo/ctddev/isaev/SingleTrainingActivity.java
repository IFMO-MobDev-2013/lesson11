package ru.ifmo.ctddev.isaev;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import ru.ifmo.ctddev.isaev.FlashCards.R;
import ru.ifmo.ctddev.isaev.orm.Category;
import ru.ifmo.ctddev.isaev.orm.Word;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static ru.ifmo.ctddev.isaev.General.*;

/**
 * User: Xottab
 * Date: 04.01.14
 */
public class SingleTrainingActivity extends MyActivity {
    ImageView picture;
    TextView description;
    Button check;
    Button iKnow;
    Category category;
    Button iDontKnow;
    ProgressBar progressBar;
    final Handler handler = new Handler();
    int current = 0;
    List<Word> words;
    String[] origins;
    String[] translates;
    boolean ready = true;
    Timer timer = new Timer();


    public void updateScreen() {
        picture.setImageDrawable(getImageByCategory(category.getResID(), words.get(current).getArrayNumber() + 1));
        ++General.wordsCount;
        Log.i("word`s current status is ", String.valueOf(words.get(current).getStatus()));
        description.setText(origins[words.get(current).getArrayNumber()]);
        iKnow.setText(R.string.i_know);
        iDontKnow.setText(R.string.i_dont_know);
        ready = true;
    }

    public void backToCategories() {
        try {
            categoryDao.update(category);
        } catch (SQLException e) {
            Log.e("", "", e);
        }
        Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_card);
        picture = (ImageView) findViewById(R.id.singleCardImage);
        description = (TextView) findViewById(R.id.singleCardText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        check = (Button) findViewById(R.id.singleCardOkButton);
        iKnow = (Button) findViewById(R.id.button);
        iDontKnow = (Button) findViewById(R.id.button2);
        category = (Category) getIntent().getSerializableExtra("cat");
        words = (List<Word>) category.getWords();
        Collections.sort(words);
        Resources resources = getLocalizedResources(this, toLocale);
        Log.i("category number is ", String.valueOf(category));
        int resID = resources.getIdentifier("category" + category.getResID(), "array", this.getPackageName());
        translates = resources.getStringArray(resID);
        resources = getLocalizedResources(this, fromLocale);
        origins = resources.getStringArray(resID);
        updateScreen();
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ready) {
                    iKnow.setText(R.string.i_was_right);
                    iDontKnow.setText(R.string.i_was_wrong);
                    description.setText(translates[words.get(current).getArrayNumber()]);
                }
            }
        });
        iKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ready) {
                    ready = false;
                    processRightAnswer(category, words.get(current));
                    current++;
                    if (current == words.size()) {
                        backToCategories();
                    } else {
                        updateScreen();
                    }
                }
            }
        });
        iDontKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ready) {
                    description.setText(translates[words.get(current).getArrayNumber()]);
                    processWrongAnswer(category, words.get(current));
                    current++;
                    if (current == words.size()) {
                        backToCategories();
                    } else {
                        ready = false;
                        progressBar.setVisibility(View.VISIBLE);
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        updateScreen();
                                    }
                                });
                            }
                        }, 2000L);
                    }
                }
            }
        });
    }
}
