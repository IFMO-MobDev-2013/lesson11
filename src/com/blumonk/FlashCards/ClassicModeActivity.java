package com.blumonk.FlashCards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Button yesButton;
    private Button noButton;
    private int result;
    private int cardIndex;
    private String locale;
    private DbAdapter dbAdapter;
    private String from;
    private String to;
    private String picsId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classicmode);
        Intent intent = getIntent();
        picture = (ImageView) findViewById(R.id.picture);
        wordField = (TextView) findViewById(R.id.theword);
        yesButton = (Button) findViewById(R.id.yesbutton);
        noButton = (Button) findViewById(R.id.nobutton);
        dbAdapter = new DbAdapter(this);

        from = intent.getStringExtra(ChooseCategoryActivity.NATIVE_CATEGORY_NAME);
        to = intent.getStringExtra(ChooseCategoryActivity.STUDIED_CATEGORY_NAME);
        picsId = intent.getStringExtra(ChooseCategoryActivity.DEFAULT_CATEGORY_NAME);
        locale = intent.getStringExtra(MainActivity.NATIVE_LANG);

        String[] words = getStringArrayById(from);
        String[] translations = getStringArrayById(to);
        String[] ids = getStringArrayById(picsId);

        for (int i = 0; i < words.length; ++i) {
            cards.add(new FlashCard(ids[i], words[i], translations[i]));
        }

        Random rand = new Random();
        for (int i = 0; i < cards.size(); ++i) {
            int j = rand.nextInt(i + 1);
            FlashCard card = cards.get(j);
            cards.set(j, cards.get(i));
            cards.set(i, card);
        }

        result = 0;
        cardIndex = 0;
        setCard(cards.get(0));

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordField.setText(cards.get(cardIndex).translation);
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++result;
                nextCard();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCard();
            }
        });
    }

    private void nextCard() {
        ++cardIndex;
        if (cardIndex < cards.size()) {
            setCard(cards.get(cardIndex));
        } else {
            HashMap<String, Integer> stats = dbAdapter.getAllResults();
            if (stats.containsKey(to)) {
                if (result > stats.get(to)) {
                    dbAdapter.updateResult(to, result);
                }
            } else {
                dbAdapter.addResult(to, result);
            }

            new AlertDialog.Builder(this)
                    .setTitle(getStringById("congrats_" + locale))
                    .setMessage(getStringById("result_" + locale) + " " + result + " / 10")
                    .setPositiveButton(getStringById("continue_" + locale), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    public String[] getStringArrayById(String idName) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(idName, "array", packageName);
        return getResources().getStringArray(resId);
    }

    public String getStringById(String idName) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(idName, "string", packageName);
        return getResources().getString(resId);
    }

    public void setCard(FlashCard flashCard) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(flashCard.name, "drawable", packageName);
        picture.setImageResource(resId);
        wordField.setText(flashCard.word);
    }

}
