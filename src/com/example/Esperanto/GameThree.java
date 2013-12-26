package com.example.Esperanto;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class GameThree extends Activity {

    ImageView image1, image2, image3, image4;
    Button text;
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
        setContentView(R.layout.three);
        text = (Button) findViewById(R.id.button);
        image1 = (ImageView) findViewById(R.id.imageView);
        image2 = (ImageView) findViewById(R.id.imageView1);
        image3 = (ImageView) findViewById(R.id.imageView2);
        image4 = (ImageView) findViewById(R.id.imageView3);
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
        text.setText(random[choose].translate);
        image1.setImageResource(getResources().getIdentifier(random[0].image, "drawable", getPackageName()));
        image2.setImageResource(getResources().getIdentifier(random[1].image, "drawable", getPackageName()));
        image3.setImageResource(getResources().getIdentifier(random[2].image, "drawable", getPackageName()));
        image4.setImageResource(getResources().getIdentifier(random[3].image, "drawable", getPackageName()));
        size = -100;
    }

    public void imone(View v) {
        if (size == 0) return;
        size = 0;
        if (choose == 0) trueAnswer(1);
        else falseAnswer(1);
    }
    public void imtwo(View v) {
        if (size == 0) return;
        size = 0;
        if (choose == 1) trueAnswer(2);
        else falseAnswer(2);
    }
    public void imthree(View v) {
        if (size == 0) return;
        size = 0;
        if (choose == 2) trueAnswer(3);
        else falseAnswer(3);
    }
    public void imfour(View v) {
        if (size == 0) return;
        size = 0;
        if (choose == 3) trueAnswer(4);
        else falseAnswer(4);
    }
    public void back(View view) {
        Intent intent = new Intent(this, Categories.class);

        startActivity(intent);
        this.finish();
    }
    private void trueAnswer(int x)  {
        Drawable[] layers = new Drawable[2];
        layers[1] = getResources().getDrawable(R.drawable.rightq);
        layers[1].setAlpha(90);
        switch (x) {
            case 1: {
                layers[0] = image1.getDrawable();
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                image1.setImageDrawable(layerDrawable);
                break;
            }
            case 2: {
                layers[0] = image2.getDrawable();
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                image2.setImageDrawable(layerDrawable);
                break;
            }
            case 3: {
                layers[0] = image3.getDrawable();
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                image3.setImageDrawable(layerDrawable);
                break;
            }
            case 4: {
                layers[0] = image4.getDrawable();
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                image4.setImageDrawable(layerDrawable);
                break;
            }
        }
        text.setText(text.getText() + "(" + getString(R.string.tapki)+")");
        random[choose].reg = Math.min(10, random[choose].reg + 2);
        update();
    }
    private void falseAnswer(int x) {
        Drawable[] layers = new Drawable[2];
        layers[1] = getResources().getDrawable(R.drawable.wrong);
        layers[1].setAlpha(90);
        switch (x) {
            case 1: {
                layers[0] = image1.getDrawable();
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                image1.setImageDrawable(layerDrawable);
                break;
            }
            case 2: {
                layers[0] = image2.getDrawable();
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                image2.setImageDrawable(layerDrawable);
                break;
            }
            case 3: {
                layers[0] = image3.getDrawable();
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                image3.setImageDrawable(layerDrawable);
                break;
            }
            case 4: {
                layers[0] = image4.getDrawable();
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                image4.setImageDrawable(layerDrawable);
                break;
            }
        }

        if (choose == 0) {
            layers[1] = getResources().getDrawable(R.drawable.rightq);
            layers[1].setAlpha(90);
            layers[0] = image1.getDrawable();
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            image1.setImageDrawable(layerDrawable);
        }
        if (choose == 1) {
            layers[1] = getResources().getDrawable(R.drawable.rightq);
            layers[1].setAlpha(90);
            layers[0] = image2.getDrawable();
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            image2.setImageDrawable(layerDrawable);
        }
        if (choose == 2) {
            layers[1] = getResources().getDrawable(R.drawable.rightq);
            layers[1].setAlpha(90);
            layers[0] = image3.getDrawable();
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            image3.setImageDrawable(layerDrawable);
        }
        if (choose == 3) {
            layers[1] = getResources().getDrawable(R.drawable.rightq);
            layers[1].setAlpha(90);
            layers[0] = image4.getDrawable();
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            image4.setImageDrawable(layerDrawable);
        }

        random[choose].reg = Math.max(0, random[choose].reg - 1);
        text.setText(text.getText() + "(" + getString(R.string.tapki)+")");
        update();
    }
    public void taptaptap(View v) {
        if (size == -100) return;
        next();
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

