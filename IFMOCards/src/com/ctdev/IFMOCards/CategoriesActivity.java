package com.ctdev.IFMOCards;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CategoriesActivity extends Activity {
    ArrayList<Category> arrayList;
    ArrayAdapter<Category> arrayAdapter;
    ListView lvCategories;

    void loadCategoriesFromFile() throws IOException {
        Scanner sc = new Scanner(getAssets().open("categories.txt"));
        int count = 0;
        while (sc.hasNext()) {
            arrayList.add(new Category(sc.nextLine(), count, 0));
        }
        CategoriesDataBaseHelper categoriesDataBaseHelper = new CategoriesDataBaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = categoriesDataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < arrayList.size(); i++) {
            contentValues.put(CategoriesDataBaseHelper.NAME, arrayList.get(i).name);
            contentValues.put(CategoriesDataBaseHelper.WORDS, arrayList.get(i).words);
            //contentValues.put(CategoriesDataBaseHelper.ID, arrayList.get(i).id);
            sqLiteDatabase.insert(CategoriesDataBaseHelper.DATABASE_NAME, null, contentValues);
        }
        sqLiteDatabase.close();
        categoriesDataBaseHelper.close();
    }

    boolean isDataBaseCreate() {
        boolean t;
        CategoriesDataBaseHelper categoriesDataBaseHelper = new CategoriesDataBaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = categoriesDataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(CategoriesDataBaseHelper.DATABASE_NAME, null, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            t = false;
        } else t = true;
        cursor.close();
        sqLiteDatabase.close();
        categoriesDataBaseHelper.close();
        return t;
    }

    void loadCategoriesFromDataBase() {
        CategoriesDataBaseHelper categoriesDataBaseHelper = new CategoriesDataBaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = categoriesDataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(CategoriesDataBaseHelper.DATABASE_NAME, null, null, null, null, null, null);
        int idColumn = cursor.getColumnIndex(CategoriesDataBaseHelper.ID);
        int nameColumn = cursor.getColumnIndex(CategoriesDataBaseHelper.NAME);
        int wordsColumn = cursor.getColumnIndex(CategoriesDataBaseHelper.WORDS);
        arrayList.clear();
        while (cursor.moveToNext()) {
            arrayList.add(new Category(cursor.getString(nameColumn), cursor.getInt(idColumn), cursor.getInt(wordsColumn)));
        }
        cursor.close();
        sqLiteDatabase.close();
        categoriesDataBaseHelper.close();
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_activity);
        arrayList = new ArrayList<Category>();
        arrayAdapter = new ArrayAdapter<Category>(getApplicationContext(), R.layout.list_view_item, arrayList);
        lvCategories = (ListView) findViewById(R.id.lvCategories);
        lvCategories.setAdapter(arrayAdapter);
        if (!isDataBaseCreate()) {
            try {
                loadCategoriesFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadCategoriesFromDataBase();
        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(CategoriesDataBaseHelper.ID, arrayList.get(position).id);
                intent.putExtra(CategoriesDataBaseHelper.NAME, arrayList.get(position).name);
                intent.putExtra(CategoriesDataBaseHelper.WORDS,arrayList.get(position).words);
                intent.setClass(getApplicationContext(), ImageActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategoriesFromDataBase();
    }
}
