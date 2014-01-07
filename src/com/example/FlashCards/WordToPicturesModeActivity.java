package com.example.FlashCards;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.FlashCards.orm.Category;
import com.example.FlashCards.orm.Word;

import java.sql.SQLException;
import java.util.*;

import static com.example.FlashCards.General.*;

/**
 * User: Xottab
 * Date: 06.01.14
 */
public class WordToPicturesModeActivity extends MyActivity {
    ImageView leftTop;
    ImageView leftBottom;
    ImageView rightTop;
    ImageView rightBottom;
    ImageView result;
    TextView description;
    RelativeLayout rel;
    private Category category;
    private List<Word> words;
    private String[] translates;
    private String[] origins;
    private int current = 0;
    private boolean ready;
    Random rand = new Random();
    List<Integer> shuff = new ArrayList<Integer>(4);
    Timer timer = new Timer();
    final Handler handler = new Handler();

    public void addOnTouchEvent(final ImageView target) {
        target.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    target.setColorFilter(Color.argb(150, 155, 155, 155));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    target.setColorFilter(null);
                }
                return false;
            }

        });
    }

    private void processAnswer(int i) {
        if (ready) {
            ready=false;
            description.setText(translates[words.get(current).getArrayNumber()]);
            if (shuff.get(i) == current) {
                result.setImageDrawable(getResources().getDrawable(R.drawable.correct));
                result.setVisibility(View.VISIBLE);
                processRightAnswer(category, words.get(current));
            } else {
                result.setImageDrawable(getResources().getDrawable(R.drawable.wrong));
                result.setVisibility(View.VISIBLE);
                processWrongAnswer(category, words.get(current));
            }
            current++;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            result.setVisibility(View.INVISIBLE);
                            if (current < words.size()) {
                                updateScreen();
                            } else {
                                backToCategories();
                            }
                        }
                    });
                }
            }, 1000L);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_to_pictures);
        leftTop = (ImageView) findViewById(R.id.imageView);
        leftBottom = (ImageView) findViewById(R.id.imageView3);
        rightTop = (ImageView) findViewById(R.id.imageView2);
        rightBottom = (ImageView) findViewById(R.id.imageView5);
        result = (ImageView) findViewById(R.id.result);
        addOnTouchEvent(leftTop);
        addOnTouchEvent(leftBottom);
        addOnTouchEvent(rightTop);
        addOnTouchEvent(rightBottom);
        description = (TextView) findViewById(R.id.wordtopicsText);
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

        leftTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAnswer(0);
            }
        });
        leftBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAnswer(1);
            }
        });
        rightTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAnswer(2);
            }
        });
        rightBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAnswer(3);
            }
        });
    }

    public void updateScreen() {
        ++General.wordsCount;
        shuff.clear();
        shuff.add(current);
        int k = 1;
        while (k < 4) {
            int n = Math.abs(rand.nextInt(395)) % words.size();
            if (!(shuff.contains(n))) {
                shuff.add(n);
                Log.i("number selected: ", String.valueOf(n));
                k++;
            }
        }
        Collections.shuffle(shuff);
        leftTop.setImageDrawable(getImageByCategory(category.getResID(), words.get(shuff.get(0)).getArrayNumber() + 1));
        leftBottom.setImageDrawable(getImageByCategory(category.getResID(), words.get(shuff.get(1)).getArrayNumber() + 1));
        rightTop.setImageDrawable(getImageByCategory(category.getResID(), words.get(shuff.get(2)).getArrayNumber() + 1));
        rightBottom.setImageDrawable(getImageByCategory(category.getResID(), words.get(shuff.get(3)).getArrayNumber() + 1));
        Log.i("word`s current status is ", String.valueOf(words.get(current).getStatus()));
        description.setText(origins[words.get(current).getArrayNumber()]);
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

}
