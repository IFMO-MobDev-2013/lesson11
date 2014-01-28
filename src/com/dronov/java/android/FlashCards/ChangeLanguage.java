package com.dronov.java.android.FlashCards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 28.01.14
 * Time: 19:47
 * To change this template use File | Settings | File Templates.
 */
public class ChangeLanguage extends Activity {
    private Spinner first, second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.language);

        first = (Spinner) findViewById(R.id.nativeLanguage);
        second = (Spinner) findViewById(R.id.studiedLanguage);

        ArrayList<String> firstLang = new ArrayList<String>();
        firstLang.add(this.getString(R.string.russian));
        firstLang.add(this.getString(R.string.english));
        firstLang.add(this.getString(R.string.china));

        ArrayList<String> secondLang = new ArrayList<String>();
        secondLang.add(this.getString(R.string.russian));
        secondLang.add(this.getString(R.string.english));
        secondLang.add(this.getString(R.string.china));

        ArrayAdapter<String> firstAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, firstLang);
        ArrayAdapter<String> secondAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, secondLang);
        first.setAdapter(firstAdapter);
        second.setAdapter(secondAdapter);

        Button button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.fromLanguage = first.getSelectedItem().toString();
                MainActivity.toLanguage = second.getSelectedItem().toString();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();    //To change body of overridden methods use File | Settings | File Templates.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
