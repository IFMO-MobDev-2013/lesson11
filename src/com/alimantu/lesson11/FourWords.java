package com.alimantu.lesson11;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.alimantu.lesson11.LangBase.EnglishLang;
import com.alimantu.lesson11.LangBase.GermanLang;
import com.alimantu.lesson11.LangBase.RussianLang;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 29.03.14
 * Time: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class FourWords extends Activity {
    private ArrayList<DictElem> result = new ArrayList<DictElem>();
    private String category;
    private int goodIndex;
    private int count = 0;
    private DictElem categoryWord;
    private int resultData = 0;
    private DictElem current;
    private String name = "";
    private int id;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button0;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_words);

        Intent intent = getIntent();
        category = intent.getStringExtra(getResources().getString(R.string.cat));
        button0 = (Button) findViewById(R.id.four_words_button1);
        button1 = (Button) findViewById(R.id.four_words_button2);
        button2 = (Button) findViewById(R.id.four_words_button3);
        button3 = (Button) findViewById(R.id.four_words_button4);
        imageView = (ImageView) findViewById(R.id.four_words_picture);

        result = new CategoryParser(this, category).parse();
        categoryWord = result.get(0);
        result.remove(0);

        count = -1;
        putButtons();

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goodIndex == 0)
                    resultData++;
                putButtons();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goodIndex == 1)
                    resultData++;
                putButtons();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goodIndex == 2)
                    resultData++;
                putButtons();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goodIndex == 3)
                    resultData++;
                putButtons();
            }
        });
    }

    private void putButtons() {
        count++;
        if (count == result.size()) {
            addResultToDatabase();
            finishSession();
            return;
        }
        current = result.get(count);
        setPicture(current.getEng());

        ArrayList<DictElem> finalList = new ArrayList<DictElem>();
        finalList.add(current);

        ArrayList<DictElem> newArray = new ArrayList<DictElem>();
        for (int i = 0; i < result.size(); i++)
            newArray.add(result.get(i));
        newArray.remove(count);
        Collections.shuffle(newArray);
        for (int i = 0; i < 3; i++)
            finalList.add(newArray.get(i));
        Collections.shuffle(finalList);
        for (int i = 0; i < finalList.size(); i++)
            if (finalList.get(i).equals(current))
                goodIndex = i;

            if(MyActivity.targetLang.equals(getResources().getString(R.string.rus)))
            {
                button0.setText(finalList.get(0).getRus());
                button1.setText(finalList.get(1).getRus());
                button2.setText(finalList.get(2).getRus());
                button3.setText(finalList.get(3).getRus());
            }
            else{
                if(MyActivity.targetLang.equals(getResources().getString(R.string.eng)))
                {
                    button0.setText(finalList.get(0).getEng());
                    button1.setText(finalList.get(1).getEng());
                    button2.setText(finalList.get(2).getEng());
                    button3.setText(finalList.get(3).getEng());
                }
                else
                {
                    button0.setText(finalList.get(0).getDe());
                    button1.setText(finalList.get(1).getDe());
                    button2.setText(finalList.get(2).getDe());
                    button3.setText(finalList.get(3).getDe());
                }
            }
    }

    private void addResultToDatabase() {
        if (MyActivity.targetLang.equals(getResources().getString(R.string.rus))){
            RussianLang database = new RussianLang(this);
            database.open();
            database.update(categoryWord.getRus(), resultData);
            database.close();
        }
        if (MyActivity.targetLang.equals(getResources().getString(R.string.eng))){
            EnglishLang database1 = new EnglishLang(this);
            database1.open();
            database1.update(categoryWord.getEng(), resultData);
            database1.close();
        }
        if (MyActivity.targetLang.equals(getResources().getString(R.string.de))){
            GermanLang database2 = new GermanLang(this);
            database2.open();
            database2.update(categoryWord.getDe(), resultData);
            database2.close();
        }
    }

    private void finishSession() {
        final Activity activity = this;
        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity)
                .setMessage(getOkMessage(resultData))
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

        final AlertDialog alert = dialog.create();
        alert.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                startActivity(intent);
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                startActivity(intent);
            }
        });

        handler.postDelayed(runnable, 2000);
    }

    private String getOkMessage(int count) {
        if (MyActivity.usedLang.equals(getResources().getString(R.string.rus)))
            return getResources().getString(R.string.rus_score) + " " + String.valueOf(count) + getString(R.string.rate2);
        if (MyActivity.usedLang.equals(getResources().getString(R.string.de)))
            return getResources().getString(R.string.de_score) + " " + String.valueOf(count) + getString(R.string.rate2);
        if (MyActivity.usedLang.equals(getResources().getString(R.string.eng)))
            return getResources().getString(R.string.eng_score) + " " + String.valueOf(count) + getString(R.string.rate2);
        return null;
    }

    private void setPicture(String link) {

        try {
            name = (URLEncoder.encode(link, "UTF-8").replace("+", "_")).toLowerCase();
            id = getResources().getIdentifier(name, "drawable", getPackageName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Bitmap current = BitmapFactory.decodeResource(getResources(), id);
        imageView.setImageBitmap(current);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
        finish();
    }
}
