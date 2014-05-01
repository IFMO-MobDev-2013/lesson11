package com.alimantu.lesson11;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alimantu.lesson11.LangBase.GermanLang;
import com.alimantu.lesson11.LangBase.RussianLang;
import com.alimantu.lesson11.LangBase.EnglishLang;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import java.util.logging.Handler;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 29.03.14
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class FourPictures extends Activity{

    private ArrayList<DictElem> result = new ArrayList<DictElem>();
    private GridView layout;
    private GridViewAdapter adapter;
    private String category;
    private int goodIndex;
    private List<Bitmap> array;
    private TextView textView;
    private int count = 0;
    private DictElem categoryWord;
    private int resultData = 0;
    private DictElem current;
    private int width;
 //   private LinearLayout backGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_pictures);

        textView = (TextView) findViewById(R.id.fourWordsText);
   //     backGround = (LinearLayout) findViewById(R.id.background);
        Intent intent = getIntent();
        category = intent.getStringExtra(getResources().getString(R.string.cat));

        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);
        width = dimension.widthPixels;
//        int height = dimension.heightPixels;

        layout = (GridView) findViewById(R.id.grid_view);
        layout.setNumColumns(2);
        layout.setPadding((int) (width*0.03),0,(int) (width*0.03), 0);
        layout.setVerticalSpacing((int) (width * 0.03));
        layout.setHorizontalSpacing((int) (width * 0.03));
        layout.setColumnWidth((int) (width * 0.455));
        layout.setStretchMode(GridView.NO_STRETCH);

        array = new ArrayList<Bitmap>();
        result = new CategoryParser(this, category).parse();
        categoryWord = result.get(0);
        result.remove(0);

        count = -1;
        putImages();

        layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == goodIndex)
                {
                    resultData++;
                }
                else {
                }
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
        textView.setText(getTargetLang(current));

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


        for (int i = 0; i < finalList.size(); i++)
            array.add(imageCreateBitmap(finalList.get(i).getEng()));
        adapter = new GridViewAdapter(this, android.R.layout.simple_list_item_1, array);
        layout.setAdapter(adapter);
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

    private Bitmap imageCreateBitmap(String link) {
        int id = 0;
        String name = "error";

        try {
            name = (URLEncoder.encode(link, "UTF-8").replace("+", "_")).toLowerCase();
            id = getResources().getIdentifier(name, "drawable", getPackageName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Bitmap current = BitmapFactory.decodeResource(getResources(), id);
        int res = (int)( width * 0.455);
        Bitmap tmp = Bitmap.createScaledBitmap(current, res, res, true);
        return tmp;
    }

//    public String getUsedLang(DictElem elem) {
//        if (MyActivity.usedLang.equals(getResources().getString(R.string.rus)))
//            return elem.getRus();
//        if (MyActivity.usedLang.equals(getResources().getString(R.string.de)))
//            return elem.getDe();
//        if (MyActivity.usedLang.equals(getResources().getString(R.string.eng)))
//            return elem.getEng();
//        return null;
//    }

    public String getTargetLang(DictElem elem) {
        if (MyActivity.targetLang.equals(getResources().getString(R.string.rus)))
            return elem.getRus();
        if (MyActivity.targetLang.equals(getResources().getString(R.string.de)))
            return elem.getDe();
        if (MyActivity.targetLang.equals(getResources().getString(R.string.eng)))
            return elem.getEng();
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
        finish();
    }
}
