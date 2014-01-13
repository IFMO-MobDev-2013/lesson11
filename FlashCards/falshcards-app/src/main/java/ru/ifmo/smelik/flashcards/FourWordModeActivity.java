package ru.ifmo.smelik.flashcards;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import ru.ifmo.smelik.flashcards.database.DatabaseTable;
import ru.ifmo.smelik.flashcards.database.dbOpenHelper;

public class FourWordModeActivity extends Activity{

    int id, i = 0, count, mas[];
    dbOpenHelper helper;
    DatabaseTable table;
    String[] wordsFROM, wordsTO;
    TextView word;
    ImageView image;
    Button firstButton, secondButton, thirdButton, fouthButton;
    Context context;
    Category category;
    boolean tofrom = false;
    final Handler handler = new Handler();
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_word_mode_layout);

        word = (TextView) findViewById(R.id.word);
        image = (ImageView) findViewById(R.id.image);
        firstButton = (Button) findViewById(R.id.firstButton);
        secondButton = (Button) findViewById(R.id.secondButton);
        thirdButton = (Button) findViewById(R.id.thirdButton);
        fouthButton = (Button) findViewById(R.id.fouthButton);
        context = this;

        helper = new dbOpenHelper(this);
        table = new DatabaseTable(helper.getWritableDatabase());

        id = getIntent().getExtras().getInt(Utils.SELECTION);
        category = table.getCategory(id + 1);
        count = category.getWords().size();

        int resID = getResources().getIdentifier("category" + (id + 1), "array", this.getPackageName());
        wordsFROM = Utils.getLocalizedResources(this, Utils.localeFROM).getStringArray(resID);
        wordsTO = Utils.getLocalizedResources(this, Utils.localeTO).getStringArray(resID);
        setResourses(i);
        setTitle(Utils.getLocalizedResources(this, Utils.localeFROM).getStringArray(R.array.categories)[id]);

        firstButton.setText(wordsTO[i]);
        secondButton.setText(wordsTO[i + 1]);

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstButton.getText().equals(wordsTO[i])) {
                    image.setImageResource(R.drawable.ok);
                    table.changeRating(category.getWords().get(i), 1);
                } else {
                    image.setImageResource(R.drawable.wrong);
                    table.changeRating(category.getWords().get(i), -1);
                }
                category = table.getCategory(id + 1);
                i++;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (check())
                                    setResourses(i);
                            }
                        });
                    }
                }, 1000L);
            }
        });

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (secondButton.getText().equals(wordsTO[i])) {
                    image.setImageResource(R.drawable.ok);
                    table.changeRating(category.getWords().get(i), 1);
                } else {
                    image.setImageResource(R.drawable.wrong);
                    table.changeRating(category.getWords().get(i), -1);
                }
                category = table.getCategory(id + 1);
                i++;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (check())
                                    setResourses(i);
                            }
                        });
                    }
                }, 1000L);
            }
        });

        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thirdButton.getText().equals(wordsTO[i])) {
                    image.setImageResource(R.drawable.ok);
                    table.changeRating(category.getWords().get(i), 1);
                } else {
                    image.setImageResource(R.drawable.wrong);
                    image.setVisibility(View.VISIBLE);
                    table.changeRating(category.getWords().get(i), -1);
                }
                category = table.getCategory(id + 1);
                i++;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (check())
                                    setResourses(i);
                            }
                        });
                    }
                }, 1000L);
            }
        });

        fouthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fouthButton.getText().equals(wordsTO[i])) {
                    image.setImageResource(R.drawable.ok);
                    table.changeRating(category.getWords().get(i), 1);
                } else {
                    image.setImageResource(R.drawable.wrong);
                    table.changeRating(category.getWords().get(i), -1);
                }
                category = table.getCategory(id + 1);
                i++;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (check())
                                    setResourses(i);
                            }
                        });
                    }
                }, 1000L);
            }
        });
    }

    private void setResourses(int resId) {
        setButton();
        image.setImageDrawable(Utils.loadImageFromAsset(context, category.getWords().get(resId)));
        word.setText(wordsFROM[resId]);
    }

    private boolean check() {
        if (i >= count) {
            setResult(RESULT_OK);
            finish();
            return false;
        }
        return true;
    }

    private void setButton() {
        random();
        firstButton.setText(wordsTO[mas[0]]);
        secondButton.setText(wordsTO[mas[1]]);
        thirdButton.setText(wordsTO[mas[2]]);
        fouthButton.setText(wordsTO[mas[3]]);
    }

    private void random() {
        mas = new int[4];
        for (int j = 0; j < 4; j++) {
            mas[j] = -1;
        }
        Random r = new Random();
        int place = Math.abs(r.nextInt(757) % 4);
        mas[place] = i;
        for (int j = 0; j < 4; j++) {
            if (j != place) {
                int next = Math.abs(r.nextInt(757) % count);
                while (mas[0] == next || mas[1] == next || mas[2] == next || mas[3] == next) {
                    next = Math.abs(r.nextInt() % count);
                }
                mas[j] = next;
            }
        }
    }
}
