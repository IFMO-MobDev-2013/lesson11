package com.example.lesson11;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.lesson11.DataBase.RussianDB;
import com.example.lesson11.DataBase.UzbekDB;

/**
 * Created with IntelliJ IDEA.
 * User: javlon
 * Date: 02.02.14
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */


public class CategoryActivity extends Activity {


    Cursor cursor;
    SimpleCursorAdapter adapter;
    Integer rus = 1, uzb = 1;
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        ListView listView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        String language = intent.getStringExtra("nativelanguage");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lltopcat);
        int x = getResources().getColor(R.color.Bckgr);
        linearLayout.setBackgroundColor(x);

        TextView textView32 = (TextView) findViewById(R.id.textView32);


        if(language.equals("rus")){
            textView32.setText("Выберите категорию:");
            RussianDB db = new RussianDB(this);
            db.open();
            cursor = db.getAllData();
            String[] from = new String[] {
                    RussianDB.COLUMN_TITLE,
                    RussianDB.COLUMN_NUMBER
            };
            int[] to = new int[] {
                    R.id.category,
                    R.id.categoryrate
            };
            startManagingCursor(cursor);
            adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to);
            listView.setAdapter(adapter);

            registerForContextMenu(listView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = ((TextView)view.findViewById(R.id.category)).getText().toString();
                    Intent currintent = new Intent(CategoryActivity.this, FlashCardsActivity.class);
                    currintent.putExtra("category", name);
                    currintent.putExtra("nativelanguage", "rus");
                    startActivity(currintent);
                }
            });
        }
        else if(language.equals("uzb")){
            textView32.setText("Kategoriyani tanlang:");

            UzbekDB db = new UzbekDB(this);
            db.open();
            cursor = db.getAllData();
            String[] from = new String[] {
                    UzbekDB.COLUMN_TITLE,
                    UzbekDB.COLUMN_NUMBER
            };
            int[] to = new int[] {
                    R.id.category,
                    R.id.categoryrate
            };
            startManagingCursor(cursor);
            adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to);
            listView.setAdapter(adapter);

            registerForContextMenu(listView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = ((TextView)view.findViewById(R.id.category)).getText().toString();
                    Intent currintent = new Intent(CategoryActivity.this, FlashCardsActivity.class);
                    currintent.putExtra("category", name);
                    currintent.putExtra("nativelanguage", "uzb");
                    startActivity(currintent);
                }
            });
        }
    }
}
