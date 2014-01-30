package com.dronov.java.android.FlashCards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.dronov.java.android.FlashCards.databases.ChinaFlashCards;
import com.dronov.java.android.FlashCards.databases.EnglishFlashCards;
import com.dronov.java.android.FlashCards.databases.RussianFlashCards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 29.01.14
 * Time: 0:25
 * To change this template use File | Settings | File Templates.
 */
public class FourPicture extends Activity {
    private ArrayList<Word> result = new ArrayList<Word>();
    private GridView layout;
    private GridViewAdapter adapter;
    private String category;
    private int goodIndex;
    private List<Bitmap> array;
    private TextView textView;
    private int count = 0;
    private Word categoryWord;
    private int resultData = 0;
    private Word current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.four_picture);

        textView = (TextView) findViewById(R.id.fourWordsText);
        Intent intent = getIntent();
        category = intent.getStringExtra(getResources().getString(R.string.category));

        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);
        int width = dimension.widthPixels;
        int height = dimension.heightPixels;


        layout = (GridView) findViewById(R.id.grid_view);
        layout.setNumColumns(2);
        layout.setColumnWidth((int) (width * 0.5));

        array = new ArrayList<Bitmap>();
        result = new ParseCategory(this, category).parse();
        categoryWord = result.get(0);
        result.remove(0);

        count = -1;
        putImages();

        layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == goodIndex)
                    resultData++;
                putImages();
            }
        });
    }

    private void putImages() {
        count++;
        if (count == result.size()) {
            addResultToDatabase();
            finishSession();
            return;
        }
        array.clear();
        current = result.get(count);
        textView.setText(getToLanguage(current));

        ArrayList<Word> finalList = new ArrayList<Word>();
        finalList.add(current);

        ArrayList<Word> newArray = new ArrayList<Word>();
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


        for (int i = 0; i < finalList.size(); i++)
            array.add(imageCreateBitmap(finalList.get(i).getEnglish()));
        adapter = new GridViewAdapter(this, android.R.layout.simple_list_item_1, array);
        layout.setAdapter(adapter);
    }

    private void addResultToDatabase() {
        RussianFlashCards database = new RussianFlashCards(this);
        database.open();
        database.update(categoryWord.getRussian(), String.valueOf(resultData) + getString(R.string.zero));
        database.close();

        EnglishFlashCards database1 = new EnglishFlashCards(this);
        database1.open();
        database1.update(categoryWord.getEnglish(), String.valueOf(resultData) + getString(R.string.zero));
        database1.close();

        ChinaFlashCards database2 = new ChinaFlashCards(this);
        database2.open();
        database2.update(categoryWord.getChina(), String.valueOf(resultData) + getString(R.string.zero));
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
            return getResources().getString(R.string.result_russian) + " " + String.valueOf(count) + getString(R.string.zero);
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.china)))
            return getResources().getString(R.string.result_china) + " " + String.valueOf(count) + getString(R.string.zero);
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.english)))
            return getResources().getString(R.string.result_english) + " " + String.valueOf(count) + getString(R.string.zero);
        return null;
    }

    private Bitmap imageCreateBitmap(String link) {
        int id = getResources().getIdentifier(link.toLowerCase(), "drawable", getPackageName());
        Bitmap current = BitmapFactory.decodeResource(getResources(), id);
        return current;
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
