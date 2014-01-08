package ru.ifmo.ctddev.isaev;

import android.content.Intent;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LanguageChoosingActivity.this, LanguageChoosingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            this.finish();
        }
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
                    Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                    General.fromLocale = new Locale(getResources().getStringArray(R.array.locales)[left.getSelectedItemPosition()]);
                    General.toLocale = new Locale(getResources().getStringArray(R.array.locales)[right.getSelectedItemPosition()]);
                    startActivity(intent);
                }
            }
        });
    }
}
