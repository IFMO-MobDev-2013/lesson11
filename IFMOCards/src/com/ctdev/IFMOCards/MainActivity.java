package com.ctdev.IFMOCards;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Button button = (Button) findViewById(R.id.button);
        Button button1 = (Button) findViewById(R.id.button2);
        final Button button2 = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), CategoriesActivity.class);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.help_msg, 10000).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.setClickable(false);
                CategoriesDataBaseHelper categoriesDataBaseHelper = new CategoriesDataBaseHelper(getApplicationContext());
                SQLiteDatabase sqLiteDatabase = categoriesDataBaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(CategoriesDataBaseHelper.WORDS, 0);
                sqLiteDatabase.update(CategoriesDataBaseHelper.DATABASE_NAME, contentValues, null, null);
                sqLiteDatabase.close();
                categoriesDataBaseHelper.close();
                Toast.makeText(getApplicationContext(), R.string.done, 1000).show();
                button2.setClickable(true);
            }
        });
    }
}