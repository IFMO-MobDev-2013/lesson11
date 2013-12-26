package com.example.Esperanto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Categories extends Activity {
    ListView listview;
    int countOfCategories = 9;
    String[] categoryNames, arrayNames;
    int[] imageIcos;
    DBAdapter stat;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        listview = (ListView) findViewById(R.id.listView);
        arrayNames = new String[]{"animals", "products", "furniture", "plant", "weather", "tech", "clothes", "professions","always","all"};
        stat = new DBAdapter(this);
        Cursor cursor = stat.getAllData();
        if (cursor.getCount() <= 0) {
            fill();
        }

        getArrays();
        categoryNames = getResources().getStringArray(R.array.categoryNames);
        makeList();
    }

    private void makeList() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>(countOfCategories);
        HashMap<String, Object> map;
        for (int i = 0; i < countOfCategories; ++i) {
            map = new HashMap<String, Object>();
            map.put("names", categoryNames[i]);
            map.put("percent", stat.getPercent(arrayNames[i]) + "%");
            map.put("icon", imageIcos[i]);
            data.add(map);
        }
        map = new HashMap<String, Object>();
        map.put("names", getString(R.string.allcat));
        map.put("percent", "");
        map.put("icon", R.drawable.allico);
        data.add(map);

        String[] from = {"names", "percent", "icon"};
        int[] to = {R.id.categoryNames, R.id.percents, R.id.categoryIcon};

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.categorylist, from, to);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> l, View view,
                                    int position, long id) {
                Intent intent = new Intent(Categories.this, GameTypes.class);
                intent.putExtra("category", arrayNames[position]);
                startActivity(intent);
                Categories.this.finish();
            }
        });
    }
    private void fill() {
        int[] cats = new int[] {R.array.animals, R.array.products, R.array.furniture, R.array.plant, R.array.weather, R.array.tech, R.array.clothes, R.array.professions, R.array.always};
        int[] ecats = new int[] {R.array.eanimals, R.array.eproducts, R.array.efurniture, R.array.eplant, R.array.eweather, R.array.etech, R.array.eclothes, R.array.eprofessions, R.array.ealways};
        for (int i = 0; i < cats.length; i++) {
            String[] names = getResources().getStringArray(cats[i]);
            String[] enames = getResources().getStringArray(ecats[i]);
            String nowcat = arrayNames[i];
            for (int j = 1; j < names.length - 1; j++) {
                stat.insert(nowcat, nowcat + Integer.toString(j), 50, names[j], enames[j], 0);
            }
        }
    }
    private void getArrays() {
        imageIcos = new int[] {R.drawable.animalsico, R.drawable.productsico, R.drawable.furnitureico,R.drawable.plantico,R.drawable.weatherico,R.drawable.techico,R.drawable.clothesico,R.drawable.professionsico,R.drawable.alwaysico};
    }
}
