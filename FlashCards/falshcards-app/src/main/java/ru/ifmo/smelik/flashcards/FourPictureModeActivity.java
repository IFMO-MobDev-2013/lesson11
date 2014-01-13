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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ru.ifmo.smelik.flashcards.database.DatabaseTable;
import ru.ifmo.smelik.flashcards.database.dbOpenHelper;

public class FourPictureModeActivity extends Activity {

    int id, i = 0, count, mas[];
    dbOpenHelper helper;
    DatabaseTable table;
    String[] wordsFROM, wordsTO;
    TextView word;
    ImageView image;
    ImageView firstImage, secondImage, thirdImage, fouthImage;
    Context context;
    Category category;
    boolean tofrom = false;
    final Handler handler = new Handler();
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_picture_mode_layout);

        word = (TextView) findViewById(R.id.word);
        image = (ImageView) findViewById(R.id.image);
        firstImage = (ImageView) findViewById(R.id.firstImage);
        secondImage = (ImageView) findViewById(R.id.secondImage);
        thirdImage = (ImageView) findViewById(R.id.thirdImage);
        fouthImage = (ImageView) findViewById(R.id.fouthImage);
        context = this;

        helper = new dbOpenHelper(this);
        table = new DatabaseTable(helper.getWritableDatabase());

        id = getIntent().getExtras().getInt(Utils.SELECTION);
        category = table.getCategory(id + 1);
        count = category.getWords().size();
        setTitle(Utils.getLocalizedResources(this, Utils.localeFROM).getStringArray(R.array.categories)[id]);

        int resID = getResources().getIdentifier("category" + (id + 1), "array", this.getPackageName());
        wordsFROM = Utils.getLocalizedResources(this, Utils.localeFROM).getStringArray(resID);
        wordsTO = Utils.getLocalizedResources(this, Utils.localeTO).getStringArray(resID);
        setResourses(i);

        firstImage.invalidateDrawable(Utils.loadImageFromAsset(this, category.getWords().get(i)));

        firstImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mas[0] == i) {
                    image.setImageResource(R.drawable.ok);
                    image.setVisibility(View.VISIBLE);
                    word.setText(wordsFROM[i]);
                    table.changeRating(category.getWords().get(i), 1);
                } else {
                    image.setImageResource(R.drawable.wrong);
                    image.setVisibility(View.VISIBLE);
                    word.setText(wordsFROM[i]);
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
                                image.setVisibility(View.INVISIBLE);
                                if (check())
                                    setResourses(i);
                            }
                        });
                    }
                }, 1000L);
            }
        });

        secondImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mas[1] == i) {
                    image.setImageResource(R.drawable.ok);
                    image.setVisibility(View.VISIBLE);
                    word.setText(wordsFROM[i]);
                    table.changeRating(category.getWords().get(i), 1);
                } else {
                    image.setImageResource(R.drawable.wrong);
                    image.setVisibility(View.VISIBLE);
                    word.setText(wordsFROM[i]);
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
                                image.setVisibility(View.INVISIBLE);
                                if (check())
                                    setResourses(i);
                            }
                        });
                    }
                }, 1000L);
            }
        });

        thirdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mas[2] == i) {
                    image.setImageResource(R.drawable.ok);
                    image.setVisibility(View.VISIBLE);
                    word.setText(wordsFROM[i]);
                    table.changeRating(category.getWords().get(i), 1);
                } else {
                    image.setImageResource(R.drawable.wrong);
                    image.setVisibility(View.VISIBLE);
                    word.setText(wordsFROM[i]);
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
                                image.setVisibility(View.INVISIBLE);
                                if (check())
                                    setResourses(i);
                            }
                        });
                    }
                }, 1000L);
            }
        });

        fouthImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mas[3] == i) {
                    image.setImageResource(R.drawable.ok);
                    image.setVisibility(View.VISIBLE);
                    word.setText(wordsFROM[i]);
                    table.changeRating(category.getWords().get(i), 1);
                } else {
                    image.setImageResource(R.drawable.wrong);
                    image.setVisibility(View.VISIBLE);
                    word.setText(wordsFROM[i]);
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
                                image.setVisibility(View.INVISIBLE);
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
        setImage();
        image = (ImageView) findViewById(R.id.image);
        word.setText(wordsTO[resId]);
    }

    private boolean check() {
        if (i >= count) {
            setResult(RESULT_OK);
            finish();
            return false;
        }
        return true;
    }

    private void setImage() {
        random();
        firstImage.setImageDrawable(Utils.loadImageFromAsset(this, category.getWords().get(mas[0])));
        secondImage.setImageDrawable(Utils.loadImageFromAsset(this, category.getWords().get(mas[1])));
        thirdImage.setImageDrawable(Utils.loadImageFromAsset(this, category.getWords().get(mas[2])));
        fouthImage.setImageDrawable(Utils.loadImageFromAsset(this, category.getWords().get(mas[3])));
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
