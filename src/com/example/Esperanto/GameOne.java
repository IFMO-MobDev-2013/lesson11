package com.example.Esperanto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameOne extends Activity {

    ImageView image;
    TextView questionWord, koreanWord;
    Button knoww, dontknoww;
    String category;
    Word[] words;
    Boolean[] flag;
    int sizeOfFlag, position = 0;
    DBAdapter stat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one);
        questionWord = (TextView) findViewById(R.id.textView);
        koreanWord = (TextView) findViewById(R.id.textView1);
        image = (ImageView) findViewById(R.id.imageView);
        knoww = (Button) findViewById(R.id.button);
        dontknoww = (Button) findViewById(R.id.button1);
        stat = new DBAdapter(this);
        category = getIntent().getStringExtra("category");
        words = chooseWords();
        flag = new Boolean[words.length];
        nextWord();
    }

    private void nextWord() {
        clearFlag();
        int pos1 = position;
        sizeOfFlag = -1;
        int x = 0;
        while ((sizeOfFlag == -1 || sizeOfFlag > 1) && x < 100) {
            sizeOfFlag = words.length;
            if (x == 0) {
                for (int i = 0; i < words.length; ++i) {
                    int rand = new Random().nextInt(101);
                    if (rand <= words[i].stat) {
                        flag[i] = true;
                        position = i;
                    } else sizeOfFlag--;
                }
                x = 1;
                flag[pos1] = false;
            }
            for (int i = 0; i < words.length; ++i) {
                int rand = new Random().nextInt(101);
                if (rand <= words[i].stat && (flag[i])) {
                    position = i;
                } else {
                    flag[i] = false;
                    sizeOfFlag--;
                }
            }
            x++;
        }

        for (int i = 0; i < words.length; i++) {
            if (flag[i]) position = i;
            else words[i].stat = Math.min(80, words[i].stat + 1);
        }
        questionWord.setText(words[position].word);
        image.setImageResource(getResources().getIdentifier(words[position].image, "drawable", getPackageName()));
        koreanWord.setText(getString(R.string.click));
        knoww.setAlpha(0);
        dontknoww.setAlpha(0);
    }

    public void show(View v) {
        koreanWord.setText(words[position].translate);
        knoww.setAlpha(100);
        dontknoww.setAlpha(100);
        knoww.setText(getString(R.string.know));
        dontknoww.setText(getString(R.string.dontknow));
    }

    public void know(View v) {
        if (knoww.getAlpha() == 0) return;
        words[position].stat = Math.max(20, words[position].stat - 4);
        words[position].reg = Math.min(10, words[position].reg + 1);
        update();
        nextWord();
    }

    public void dontknow(View v) {
        if (knoww.getAlpha() == 0) return;
        words[position].stat = Math.min(80, words[position].stat + 4);
        words[position].reg = Math.max(0, words[position].reg - 1);
        update();
        nextWord();
    }
    public void back(View view) {
        Intent intent = new Intent(this, Categories.class);

        startActivity(intent);
        this.finish();
    }
    private void update() {
        if (category.equals("all")) return;
        for (int i = 0; i < words.length; i++) {
            stat.update(category, words[i].image, words[i].stat, words[i].word, words[i].translate, words[i].reg);
        }
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
