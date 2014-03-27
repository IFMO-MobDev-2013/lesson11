package com.mobdev.cardz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class ImageActivity extends Activity {
    ImageView imageView;
    LinearLayout linearLayout;
    TextView textView;
    Button btnYes, btnNo;
    ArrayList<Picture> arrayList;
    int currentPicture;
    boolean show;
    int count;

    void swap(int i, int j) {
        Picture c = arrayList.get(i);
        arrayList.set(i, arrayList.get(j));
        arrayList.set(j, c);
    }

    void randomShuffle() {
        Random r = new Random();

        for (int i = 0; i < arrayList.size(); i++) {
            int x = r.nextInt(arrayList.size());
            int y = r.nextInt(arrayList.size());
            swap(x, y);
        }
    }

    void loadFromFile() throws IOException {
        arrayList.clear();
        int id = getIntent().getIntExtra(CategoriesDataBaseHelper.ID, 0);
        Scanner in = new Scanner(getAssets().open(id + ".txt"), "utf-8");
        int n = in.nextInt();
        in.nextLine();

        for (int i = 0; i < n; i++) {
            String path = in.nextLine();
            String wordRU = in.nextLine();
            String wordEN = in.nextLine();
            arrayList.add(new Picture(path, wordEN, wordRU));
        }
        randomShuffle();
        currentPicture = 0;
        setCurrentPicture();
    }

    void setCurrentPicture() {
        imageView.setImageResource(getApplicationContext().getResources().getIdentifier(arrayList.get(currentPicture).name, "drawable", getApplicationContext().getPackageName()));
        textView.setText(arrayList.get(currentPicture).wordRu);
        linearLayout.setVisibility(View.INVISIBLE);
        show = false;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
        btnNo = (Button) findViewById(R.id.btnNo);
        btnYes = (Button) findViewById(R.id.btnYes);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        arrayList = new ArrayList<Picture>();
        count = 0;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show) {
                    linearLayout.setVisibility(View.INVISIBLE);
                    textView.setText(arrayList.get(currentPicture).wordRu);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    textView.setText(arrayList.get(currentPicture).wordEn);
                }
                show = !show;
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                nextPicture();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPicture();
            }
        });

        try {
            loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void nextPicture() {
        if (currentPicture == arrayList.size() - 1) {
            updateResult();
            Resources resources = getResources();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getIntent().getStringExtra(CategoriesDataBaseHelper.NAME));
            builder.setCancelable(false);
            builder.setMessage(resources.getString(R.string.result) + " - " + (count*10) + "%\n" + resources.getString(R.string.again));
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    count = 0;
                    randomShuffle();
                    currentPicture = 0;
                    setCurrentPicture();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        } else {
            currentPicture++;
            setCurrentPicture();
        }
    }

    void updateResult() {
        Intent intent = getIntent();
        if (intent.getIntExtra(CategoriesDataBaseHelper.WORDS, 0) < count) {
            CategoriesDataBaseHelper categoriesDataBaseHelper = new CategoriesDataBaseHelper(getApplicationContext());
            SQLiteDatabase sqLiteDatabase = categoriesDataBaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CategoriesDataBaseHelper.NAME, intent.getStringExtra(CategoriesDataBaseHelper.NAME));
            contentValues.put(CategoriesDataBaseHelper.WORDS, count);
            sqLiteDatabase.update(CategoriesDataBaseHelper.DATABASE_NAME, contentValues, CategoriesDataBaseHelper.ID + "=" + intent.getIntExtra(CategoriesDataBaseHelper.ID, 0), null);
            sqLiteDatabase.close();
            categoriesDataBaseHelper.close();
        }
    }

}