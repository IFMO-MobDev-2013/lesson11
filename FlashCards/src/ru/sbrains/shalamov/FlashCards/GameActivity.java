package ru.sbrains.shalamov.FlashCards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity {


    String curCategory;
    //int gameMode;  // 1 - enToRu  // 2 - ruToEn
    //private static int categoriesCount = 10;

    SharedPreferences sPref;

    private static final String KEY_CATEG = "category";
    private static final String KEY_MODE = "gameMode";
    private static final String KEY_BASESTATE = "baseState";

    private static final int MODE_EN_RU = 1;
    private static final int MODE_RU_EN = 2;

    Button categories;
    TextView task;
    TextView answer;
    ImageView image;

    ImageButton ok;
    ImageButton no;
    Word word;

    WordsMaster wordsMaster;

    private static final int EN_to_RU_ID = Menu.FIRST;
    private static final int RU_to_EN_ID = Menu.FIRST + 1;
    private static final int DROP_STAT = Menu.FIRST + 2;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        wordsMaster = new WordsMaster(this, getCurCategory());
        curCategory = getCurCategory();

        if (getBaseState() == 0) {
            wordsMaster.dropAll();
            fillDB();
            wordsMaster.checkCategory(curCategory);
            setBaseState(MODE_EN_RU);
        }



        categories = (Button) findViewById(R.id.button);
        categories.setText(curCategory);
        task = (TextView) findViewById(R.id.textViewTask);
        answer = (TextView) findViewById(R.id.textViewAnswer);

        ok = (ImageButton) findViewById(R.id.imageButtonOK);
        no = (ImageButton) findViewById(R.id.imageButtonNO);
        ok.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);

        image = (ImageView)findViewById(R.id.imageViewTask);

        fillScreen();

        // Click Listeners:

        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getGameMode() == MODE_EN_RU)
                    answer.setText(word.ru);
                else
                    answer.setText(word.en);

                ok.setVisibility(View.VISIBLE);
                no.setVisibility(View.VISIBLE);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordsMaster.save(word.en, true);
                fillScreen();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordsMaster.save(word.en, false);
                fillScreen();
            }
        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, CategoriesActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void fillDB() {
        String[] categ = getResources().getStringArray(R.array.categories);
        int i = 0;

        String[] en = getResources().getStringArray(R.array.FRUIT);
        String[] ru = getResources().getStringArray(R.array._FRUIT);
        int[] img = Mapper.map(en);
        wordsMaster.addWords(en, ru, img, categ[i]);
        ++i;

        en = getResources().getStringArray(R.array.VEGETABLES);
        ru = getResources().getStringArray(R.array._VEGETABLES);
        img = Mapper.map(en);
        wordsMaster.addWords(en, ru, img, categ[i]);
        ++i;

        en = getResources().getStringArray(R.array.ALCOHOL);
        ru = getResources().getStringArray(R.array._ALCOHOL);
        img = Mapper.map(en);
        wordsMaster.addWords(en, ru, img, categ[i]);
        ++i;

        en = getResources().getStringArray(R.array.HOUSE);
        ru = getResources().getStringArray(R.array._HOUSE);
        img = Mapper.map(en);
        wordsMaster.addWords(en, ru, img, categ[i]);
        ++i;

        en = getResources().getStringArray(R.array.BATHROOM);
        ru = getResources().getStringArray(R.array._BATHROOM);
        img = Mapper.map(en);
        wordsMaster.addWords(en, ru, img, categ[i]);
        ++i;

        en = getResources().getStringArray(R.array.KITCHEN);
        ru = getResources().getStringArray(R.array._KITCHEN);
        img = Mapper.map(en);
        wordsMaster.addWords(en, ru, img, categ[i]);
        ++i;

        en = getResources().getStringArray(R.array.COLOURS);
        ru = getResources().getStringArray(R.array._COLOURS);
        img = Mapper.map(en);
        wordsMaster.addWords(en, ru, img, categ[i]);
        ++i;

        en = getResources().getStringArray(R.array.SPORT);
        ru = getResources().getStringArray(R.array._SPORT);
        img = Mapper.map(en);
        wordsMaster.addWords(en, ru, img, categ[i]);
        ++i;

        en = getResources().getStringArray(R.array.MUSIC);
        ru = getResources().getStringArray(R.array._MUSIC);
        img = Mapper.map(en);
        wordsMaster.addWords(en, ru, img, categ[i]);
        ++i;

        en = getResources().getStringArray(R.array.CITY);
        ru = getResources().getStringArray(R.array._CITY);
        img = Mapper.map(en);
        wordsMaster.addWords(en, ru, img, categ[i]);
        ++i;

        wordsMaster.makeQueue();
    }


    private void fillScreen() {
        word = wordsMaster.getOne(curCategory);
        if (getGameMode() == MODE_EN_RU)
        {
            task.setText(word.en);
            answer.setText("?");
            image.setImageResource(word.img);
            ok.setVisibility(View.INVISIBLE);
            no.setVisibility(View.INVISIBLE);
        }
        else
        {
            // alternative game mode!
            task.setText(word.ru);
            answer.setText("?");
            image.setImageResource(word.img);
            ok.setVisibility(View.INVISIBLE);
            no.setVisibility(View.INVISIBLE);
        }
    }


    private int getBaseState() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(KEY_BASESTATE, "0");
        return (int) Integer.parseInt(savedText);
    }

    private void setBaseState(int id) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(KEY_BASESTATE, new Integer(id).toString());
        ed.commit();
    }

    private String getCurCategory() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(KEY_CATEG, "FRUIT - ФРУКТЫ");
        return savedText;
    }

    private void setCurCategory(String c) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(KEY_CATEG, c);
        ed.commit();
        categories.setText(c);
    }

    private int getGameMode() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(KEY_MODE, "1");
        return (int) Integer.parseInt(savedText);
    }

    private void setGameMode(int id) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(KEY_MODE, new Integer(id).toString());
        ed.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0)
            return;
        else {
            String c = data.getStringExtra(CategoriesActivity.KEY_ANS);
            setCurCategory(c);
            curCategory = c;
            wordsMaster.checkCategory(c);
            fillScreen();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, EN_to_RU_ID, 0, R.string.en_to_ru);
        menu.add(0, RU_to_EN_ID, 0, R.string.ru_to_en);
        menu.add(0, DROP_STAT, 0, R.string.drop_stat);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case EN_to_RU_ID:
                setGameMode(MODE_EN_RU);
                fillScreen();
                return true;
            case RU_to_EN_ID:
                setGameMode(MODE_RU_EN);
                fillScreen();
                return true;
            case DROP_STAT:
                AlertDialog.Builder a = new AlertDialog.Builder(this);
                a.setTitle(R.string.sure);

                a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        wordsMaster.dropStat();
                    }
                });

                a.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                a.show();
        }

        return super.onMenuItemSelected(featureId, item);
    }
}
