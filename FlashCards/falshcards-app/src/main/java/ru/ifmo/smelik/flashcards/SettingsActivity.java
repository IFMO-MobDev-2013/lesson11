package ru.ifmo.smelik.flashcards;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

public class SettingsActivity extends Activity {


    String[] data, mode;
    Locale[] lang;
    Class[] game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        data = new String[]{getString(R.string.english), getString(R.string.russian), getString(R.string.chinese)};
        lang = new Locale[]{Locale.US, Utils.RUSSIAN, Locale.CHINESE};
        mode = new String[]{getString(R.string.game_mode_one), getString(R.string.game_mode_two), getString(R.string.game_mode_three)};
        game = new Class[]{PracticeModeActivity.class, FourWordModeActivity.class, FourPictureModeActivity.class};

        final SharedPreferences lt = getSharedPreferences(Utils.LANGUAGE_TO,
                Context.MODE_PRIVATE);
        final SharedPreferences lf = getSharedPreferences(Utils.LANGUAGE_FROM,
                Context.MODE_PRIVATE);
        final SharedPreferences gm = getSharedPreferences(Utils.GAME_MODE,
                Context.MODE_PRIVATE);

        Spinner spinnerFrom = (Spinner) findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = (Spinner) findViewById(R.id.spinnerTo);
        Spinner spinnerType = (Spinner) findViewById(R.id.spinnerType);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerFrom.setAdapter(adapter);
        spinnerFrom.setSelection(lf.getInt(Utils.LANGUAGE_FROM, 0));
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Utils.localeFROM = lang[position];
                SharedPreferences.Editor e = lf.edit();
                e.putInt(Utils.LANGUAGE_FROM, position);
                e.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerTo.setAdapter(adapter);
        spinnerTo.setSelection(lt.getInt(Utils.LANGUAGE_TO, 0));
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Utils.localeTO = lang[position];
                SharedPreferences.Editor e = lt.edit();
                e.putInt(Utils.LANGUAGE_TO, position);
                e.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mode);
        spinnerType.setAdapter(adapter);
        spinnerType.setSelection(gm.getInt(Utils.GAME_MODE, 0));
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Utils.gameMode = game[position];
                SharedPreferences.Editor e = gm.edit();
                e.putInt(Utils.GAME_MODE, position);
                e.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

}
