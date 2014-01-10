package ru.ifmo.ctddev.isaev;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.ifmo.ctddev.isaev.FlashCards.R;
import ru.ifmo.ctddev.isaev.orm.Category;
import ru.ifmo.ctddev.isaev.orm.Word;

import java.sql.SQLException;
import java.util.*;

import static ru.ifmo.ctddev.isaev.General.*;

/**
 * User: Xottab
 * Date: 06.01.14
 */
public class PictureToWordsModeActivity extends MyActivity {
    TextView leftTop;
    TextView leftBottom;
    TextView rightTop;
    TextView rightBottom;
    ImageView result;
    ImageView picture;
    TextView description;
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

    public void addOnTouchEvent(final TextView target, final int n) {
        target.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    processAnswer(n);
                }
                return false;
            }

        });
    }


    private void processAnswer(int i) {
        if (ready) {
            ready = false;
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
        setContentView(R.layout.picture_to_words);
        leftTop = (TextView) findViewById(R.id.textView);
        leftBottom = (TextView) findViewById(R.id.textView3);
        rightTop = (TextView) findViewById(R.id.textView2);
        rightBottom = (TextView) findViewById(R.id.textView4);
        result = (ImageView) findViewById(R.id.imageView2);
        picture = (ImageView) findViewById(R.id.imageView);
        description = (TextView) findViewById(R.id.textView5);
        category = (Category) getIntent().getSerializableExtra("cat");
        words = category.getWords();
        Collections.sort(words);
        Resources resources = getLocalizedResources(this, toLocale);
        Log.i("category number is ", String.valueOf(category));
        int resID = resources.getIdentifier("category" + category.getResID(), "array", this.getPackageName());
        translates = resources.getStringArray(resID);
        resources = getLocalizedResources(this, fromLocale);
        origins = resources.getStringArray(resID);
        updateScreen();

        addOnTouchEvent(leftTop, 0);
        addOnTouchEvent(leftBottom, 1);
        addOnTouchEvent(rightTop, 2);
        addOnTouchEvent(rightBottom, 3);
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
        leftTop.setText(translates[words.get(shuff.get(0)).getArrayNumber()]);
        leftBottom.setText(translates[words.get(shuff.get(1)).getArrayNumber()]);
        rightTop.setText(translates[words.get(shuff.get(2)).getArrayNumber()]);
        rightBottom.setText(translates[words.get(shuff.get(3)).getArrayNumber()]);
        Log.i("word`s current status is ", String.valueOf(words.get(current).getStatus()));
        description.setText(origins[words.get(current).getArrayNumber()]);
        picture.setImageDrawable(getImageByCategory(category.getResID(), words.get(current).getArrayNumber() + 1));
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
