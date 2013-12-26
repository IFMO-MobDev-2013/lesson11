package com.example.Esperanto;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameTwo extends Activity {

    Button text1, text2, text3, text4;
    TextView TAP;
    ImageView image;
    String category, answer;
    DBAdapter stat;
    Word[] words;
    boolean[] flag;
    Word[] random;
    int size,choose;
    long count;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
        image = (ImageView) findViewById(R.id.imageView);
        TAP = (TextView) findViewById(R.id.textView);
        text1 = (Button) findViewById(R.id.button);
        text2 = (Button) findViewById(R.id.button1);
        text3 = (Button) findViewById(R.id.button3);
        text4 = (Button) findViewById(R.id.button4);
        category = getIntent().getStringExtra("category");
        stat = new DBAdapter(this);
        words = chooseWords();
        flag = new boolean[words.length];
        random = new Word[4];
        answer = "";
        count = 0;
        next();
    }

    private void next() {
        TAP.setText("");
        text1.setBackgroundResource(R.drawable.darkitem);
        text2.setBackgroundResource(R.drawable.darkitem);
        text3.setBackgroundResource(R.drawable.darkitem);
        text4.setBackgroundResource(R.drawable.darkitem);
        count++;
        clearFlag();
        choose = 0;
        int temp;
        size = -1;
        while (choose != 4) {
            temp = new Random().nextInt(words.length);
            if (!flag[temp]) {
                flag[temp] = true;
                size++;
                random[size] = words[temp];
                choose++;
            }
        }
        choose = new Random().nextInt(4);
        while (answer.equals(random[choose].translate)) choose = new Random().nextInt(4);
        answer = random[choose].translate;

        image.setImageResource(getResources().getIdentifier(random[choose].image, "drawable", getPackageName()));
        text1.setText(random[0].translate);
        text2.setText(random[1].translate);
        text3.setText(random[2].translate);
        text4.setText(random[3].translate);
        choose = -100;
    }

    public void one(View v) {
        if (choose == 0) return;
        choose = 0;
        if (answer.equals(text1.getText())) trueAnswer(1);
        else falseAnswer(1);
    }
    public void two(View v) {
        if (choose == 0) return;
        choose = 0;
        if (answer.equals(text2.getText())) trueAnswer(2);
        else falseAnswer(2);
    }
    public void three(View v) {
        if (choose == 0) return;
        choose = 0;
        if (answer.equals(text3.getText())) trueAnswer(3);
        else falseAnswer(3);
    }
    public void four(View v) {
        if (choose == 0) return;
        choose = 0;
        if (answer.equals(text4.getText())) trueAnswer(4);
        else falseAnswer(4);
    }

    private void trueAnswer(int x)  {
        switch (x) {
            case 1: {
                text1.setBackgroundResource(R.drawable.greenitem);
                break;
            }
            case 2: {
                text2.setBackgroundResource(R.drawable.greenitem);
                break;
            }
            case 3: {
                text3.setBackgroundResource(R.drawable.greenitem);
                break;
            }
            case 4: {
                text4.setBackgroundResource(R.drawable.greenitem);
                break;
            }
        }
        random[choose].reg = Math.min(10, random[choose].reg + 2);
        update();
        TAP.setText(R.string.tapon);
    }
    private void falseAnswer(int x) {
        switch (x) {
            case 1: {
                text1.setBackgroundResource(R.drawable.reditem);
                break;
            }
            case 2: {
                text2.setBackgroundResource(R.drawable.reditem);
                break;
            }
            case 3: {
                text3.setBackgroundResource(R.drawable.reditem);
                break;
            }
            case 4: {
                text4.setBackgroundResource(R.drawable.reditem);
                break;
            }
        }

        if (answer.equals(text1.getText())) text1.setBackgroundResource(R.drawable.greenitem);
        if (answer.equals(text2.getText())) text2.setBackgroundResource(R.drawable.greenitem);
        if (answer.equals(text3.getText())) text3.setBackgroundResource(R.drawable.greenitem);
        if (answer.equals(text4.getText())) text4.setBackgroundResource(R.drawable.greenitem);

        random[choose].reg = Math.max(0, random[choose].reg - 1);
        update();
        TAP.setText(R.string.tapon);
    }

    private void update() {
        if (category.equals("all")) return;
            stat.update(category, random[choose].image, random[choose].stat, random[choose].word, random[choose].translate, random[choose].reg);
    }
    private void clearFlag() {
        for (int i = 0; i < words.length; i++) {
            flag[i] = false;
        }
    }
    public void taptap(View v) {
        if (choose == -100) return;
        next();

    }
    public void back(View view) {
        Intent intent = new Intent(this, Categories.class);

        startActivity(intent);
        this.finish();
    }
    private Word[] chooseWords() {
        Cursor cursor;
        if (!category.equals("all")) cursor = stat.getCategoryData(category);
        else cursor = stat.getAllData();
        Word[] temp = new Word[cursor.getCount()];
        int iter = -1;
        String word, trans, image;
        int st,reg;
        while (cursor.moveToNext()) {
            word = cursor.getString(cursor.getColumnIndex("word"));
            image = cursor.getString(cursor.getColumnIndex("image"));
            st = cursor.getInt(cursor.getColumnIndex("statistic"));
            trans = cursor.getString(cursor.getColumnIndex("trans"));
            reg = cursor.getInt(cursor.getColumnIndex("reg"));
            iter++;
            temp[iter] = new Word(word, trans, st, image, reg);
        }
        return temp;
    }
}

