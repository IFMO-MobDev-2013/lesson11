package ru.ifmo.ctddev.isaev;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import ru.ifmo.ctddev.isaev.FlashCards.R;

import java.util.Locale;

/**
 * User: Xottab
 * Date: 03.01.14
 */
public class LanguageChoosingActivity extends MyActivity {
    Spinner left;
    Spinner right;
    Button okGo;
    String from;
    String to;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(General.PREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        if (General.isFirstLaunch) {
            setContentView(R.layout.lang_choose);
            left = (Spinner) findViewById(R.id.spinner);
            right = (Spinner) findViewById(R.id.spinner2);
            okGo = (Button) findViewById(R.id.button);
            okGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
                    if (left.getSelectedItemPosition() == right.getSelectedItemPosition()) {
                        toast.setText(getResources().getString(R.string.cantBeEqual));
                        toast.show();
                    } else {
                        from = getResources().getStringArray(R.array.locales)[left.getSelectedItemPosition()];
                        to = getResources().getStringArray(R.array.locales)[right.getSelectedItemPosition()];
                        editor.putString(General.LOCALE_FROM, from);
                        editor.putString(General.LOCALE_TO, to);
                        editor.commit();
                        General.fromLocale = new Locale(from);
                        General.toLocale = new Locale(to);
                        Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                        startActivity(intent);
                    }
                }
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
            General.fromLocale = new Locale(preferences.getString(General.LOCALE_FROM, null));
            General.toLocale = new Locale(preferences.getString(General.LOCALE_TO, null));
            startActivity(intent);
        }
    }
}
