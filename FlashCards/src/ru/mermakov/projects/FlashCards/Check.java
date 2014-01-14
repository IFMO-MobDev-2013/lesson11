package ru.mermakov.projects.FlashCards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Check extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check);
        ArrayAdapter<String> adapter;
        ListView listview;
        listview = (ListView) findViewById(R.id.listView1);
        final ArrayList choose = new ArrayList<String>();
        choose.add(getResources().getString(R.string.First));
        choose.add(getResources().getString(R.string.Second));
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, choose);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View p,
                                    int position, long id) {
                if (position == 0) {
                    final Intent intent = new Intent(Check.this, First.class);
                    startActivity(intent);
                } else {
                    final Intent intent = new Intent(Check.this, Second.class);
                    startActivity(intent);
                }

            }
        });
    }

}
