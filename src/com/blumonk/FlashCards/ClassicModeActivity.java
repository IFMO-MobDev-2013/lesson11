package com.blumonk.FlashCards;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: blumonk
 * Date: 12/26/13
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassicModeActivity extends Activity {

    private ImageView picture;
    private TextView wordField;
    private ArrayList<FlashCard> cards = new ArrayList<FlashCard>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classicmode);
        Intent intent = getIntent();
        picture = (ImageView) findViewById(R.id.picture);
        wordField = (TextView) findViewById(R.id.theword);

        String from = intent.getStringExtra(ChooseCategoryActivity.NATIVE_CATEGORY_NAME);
        String to = intent.getStringExtra(ChooseCategoryActivity.STUDIED_CATEGORY_NAME);
        String picsId = intent.getStringExtra(ChooseCategoryActivity.DEFAULT_CATEGORY_NAME);

        String[] words = getStringArrayById(from);
        String[] translations = getStringArrayById(to);
        String[] ids = getStringArrayById(picsId);

        for (int i = 0; i < words.length; ++i) {
            try {
                cards.add(new FlashCard(ids[i], words[i], translations[i]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //WRITE SHUFFLE
        /*

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int n = rand.nextInt(10);
                FlashCard x = cards.get(n);
                setImage(x.name);
            }
        });
        */
    }

    public String[] getStringArrayById(String idName) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(idName, "array", packageName);
        return getResources().getStringArray(resId);
    }

    public void setCard(FlashCard flashCard) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(flashCard.name, "drawable", packageName);
        picture.setImageResource(resId);
        wordField.setText(flashCard.word);
    }

}
