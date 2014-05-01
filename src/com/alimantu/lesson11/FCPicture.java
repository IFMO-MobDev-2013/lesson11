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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alimantu.lesson11.LangBase.EnglishLang;
import com.alimantu.lesson11.LangBase.RussianLang;
import com.alimantu.lesson11.LangBase.GermanLang;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 29.03.14
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class FCPicture extends Activity {

    private ArrayList<DictElem> result = new ArrayList<DictElem>();
    private int count = 0;
    private ImageView imageView;
    private TextView fromText;
    private TextView toText;
    private int resultData = 0;
    private String category;
    private Button yes;
    private Button no;
    private DictElem categoryWord;
    private String name;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fc_picture);

        Intent intent = getIntent();
        category = intent.getStringExtra(getResources().getString(R.string.cat));

        result = new CategoryParser(this, category).parse();
        categoryWord = result.get(count);
        result.remove(0);

        imageView = (ImageView) findViewById(R.id.picture);
        fromText = (TextView) findViewById(R.id.theword);
        toText = (TextView) findViewById(R.id.answer);
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);

        Collections.shuffle(result);
        setPicture(result.get(count));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toText.setText(getToLanguage(result.get(count)));
                yes.setEnabled(true);
                yes.setVisibility(View.VISIBLE);
                no.setEnabled(true);
                no.setVisibility(View.VISIBLE);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultData++;
                count++;
                if (count >= result.size()) {
                    addResultToDatabase();
                    finishSession();
                    return;
                }

                setPicture(result.get(count));
                initFields();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count >= result.size()) {
                    addResultToDatabase();
                    finishSession();
                    return;
                }
                setPicture(result.get(count));
                initFields();
            }
        });
    }

    private void initFields() {

        yes.setEnabled(false);
        yes.setVisibility(View.INVISIBLE);
        no.setEnabled(false);
        no.setVisibility(View.INVISIBLE);
        toText.setText("");
    }

    private void addResultToDatabase() {
        RussianLang database = new RussianLang(this);
        database.open();
        database.update(categoryWord.getRus(), resultData);
        database.close();

        EnglishLang database1 = new EnglishLang(this);
        database1.open();
        database1.update(categoryWord.getEng(), resultData);
        database1.close();

        GermanLang database2 = new GermanLang(this);
        database2.open();
        database2.update(categoryWord.getDe(), resultData);
        database2.close();
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
        yes.setEnabled(false);
        no.setEnabled(false);
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

    private void setPicture(DictElem word) {

        String link = result.get(count).getEng().toLowerCase();

        try {
            name = (URLEncoder.encode(link, "UTF-8").replace("+", "_")).toLowerCase();
            id = getResources().getIdentifier(name, "drawable", getPackageName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Bitmap current = BitmapFactory.decodeResource(getResources(), id);
        imageView.setImageBitmap(current);

        fromText.setText(getFromLanguage(word));
    }

    public String getFromLanguage(DictElem word) {
        if (MyActivity.usedLang.equals(getResources().getString(R.string.rus)))
            return word.getRus();
        if (MyActivity.usedLang.equals(getResources().getString(R.string.de)))
            return word.getDe();
        if (MyActivity.usedLang.equals(getResources().getString(R.string.eng)))
            return word.getEng();
        return null;
    }

    public String getToLanguage(DictElem word) {
        if (MyActivity.targetLang.equals(getResources().getString(R.string.rus)))
            return word.getRus();
        if (MyActivity.targetLang.equals(getResources().getString(R.string.de)))
            return word.getDe();
        if (MyActivity.targetLang.equals(getResources().getString(R.string.eng)))
            return word.getEng();
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();    //To change body of overridden methods use File | Settings | File Templates.
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
        finish();
    }

}
