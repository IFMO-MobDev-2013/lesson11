package ru.ifmo.ctddev.rakovsky.italiano;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoriesList extends Activity {
    public static final String CATEGORY_KEY = "category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_list);

        final ListView listView = (ListView) findViewById(R.id.listView);

        String[] categories = getResources().getStringArray(R.array.categories);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Learning.class);
                intent.putExtra(CATEGORY_KEY, i);
                startActivity(intent);
            }
        });
    }
}
