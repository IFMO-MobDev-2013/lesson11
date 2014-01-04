package com.blumonk.FlashCards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    public final static String NATIVE_LANG = "com.blumonk.FlashCards.native";
    public final static String STUDIED_LANG = "com.blumonk.FlashCards.studied";

    private Spinner nativeLang;
    private Spinner studiedLang;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        nativeLang = (Spinner) findViewById(R.id.nativelang);
        ArrayAdapter<CharSequence> nativeAdapter = ArrayAdapter.createFromResource(this, R.array.languages_know, android.R.layout.simple_spinner_item);
        nativeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nativeLang.setAdapter(nativeAdapter);

        studiedLang = (Spinner) findViewById(R.id.studiedlang);
        ArrayAdapter<CharSequence> studiedAdapter = ArrayAdapter.createFromResource(this, R.array.languages_study, android.R.layout.simple_spinner_item);
        studiedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studiedLang.setAdapter(studiedAdapter);
    }

    public void chooseCategory(View view) {
        Intent intent = new Intent(this, ChooseCategoryActivity.class);
        if (studiedLang.getSelectedItem().equals(nativeLang.getSelectedItem())) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please, select different languages", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            intent.putExtra(NATIVE_LANG, getLocale(nativeLang.getSelectedItem().toString()));
            intent.putExtra(STUDIED_LANG, getLocale(studiedLang.getSelectedItem().toString()));
            startActivity(intent);
        }
    }

    private String getLocale(String lang) {
        if ("English".equals(lang)) {
            return "en";
        } else if ("Русский".equals(lang)) {
            return "ru";
        } else {
            return "cn";
        }
    }
}
