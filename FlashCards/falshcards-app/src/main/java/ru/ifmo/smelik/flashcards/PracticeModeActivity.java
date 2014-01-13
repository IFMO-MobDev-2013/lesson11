package ru.ifmo.smelik.flashcards;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Iterator;

import ru.ifmo.smelik.flashcards.database.DatabaseTable;
import ru.ifmo.smelik.flashcards.database.dbOpenHelper;

public class PracticeModeActivity extends Activity {

    int id, i = 0, count;
    dbOpenHelper helper;
    DatabaseTable table;
    String[] wordsFROM, wordsTO;
    TextView word;
    ImageView image;
    Button yesButton, noButton;
    Context context;
    Category category;
    boolean tofrom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_mode_layout);

        word = (TextView) findViewById(R.id.word);
        image = (ImageView) findViewById(R.id.image);
        yesButton = (Button) findViewById(R.id.yes);
        noButton = (Button) findViewById(R.id.no);
        context = this;

        helper = new dbOpenHelper(this);
        table = new DatabaseTable(helper.getWritableDatabase());

        id = getIntent().getExtras().getInt(Utils.SELECTION);
        category = table.getCategory(id + 1);
        count = category.getWords().size();

        int resID = getResources().getIdentifier("category" + (id + 1), "array", this.getPackageName());
        wordsFROM = Utils.getLocalizedResources(this, Utils.localeFROM).getStringArray(resID);
        wordsTO = Utils.getLocalizedResources(this, Utils.localeTO).getStringArray(resID);
        setResourses(i);
        setTitle(Utils.getLocalizedResources(this, Utils.localeFROM).getStringArray(R.array.categories)[id]);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table.changeRating(category.getWords().get(i), 1);
                category = table.getCategory(id + 1);
                i++;
                if (check())
                    setResourses(i);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table.changeRating(category.getWords().get(i), -1);
                category = table.getCategory(id + 1);
                i++;
                if (check())
                    setResourses(i);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    if (tofrom) {
                        word.setText(wordsFROM[i]);
                        tofrom = !tofrom;
                    } else {
                        word.setText(wordsTO[i]);
                        tofrom = !tofrom;
                    }
                }
            }
        });
    }

    private void setResourses(int resId) {
        image.setImageDrawable(Utils.loadImageFromAsset(context, category.getWords().get(resId)));
        word.setText(wordsFROM[resId]);
    }

    private boolean check() {
        if (i >= count) {
            setResult(RESULT_OK);
            finish();
            return false;
        }
        return true;
    }

}
