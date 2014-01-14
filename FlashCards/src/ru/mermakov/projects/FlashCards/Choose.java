package ru.mermakov.projects.FlashCards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.yandex.metrica.Counter;

import java.util.ArrayList;

public class Choose extends Activity {
    ArrayAdapter<String> adapter;
    ListView listview;
    ArrayList<String> choose = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Counter.initialize(getApplicationContext());
        Counter.sharedInstance().reportEvent("StartActivity");
        Counter.sharedInstance().sendEventsBuffer();

        setContentView(R.layout.choose);
        listview = (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, choose);

        listview.setAdapter(adapter);

        choose.add(getResources().getString(R.string.Trainer));
        choose.add(getResources().getString(R.string.Check));
        adapter.notifyDataSetChanged();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View p,
                                    int position, long id) {
                if (position == 0) {
                    final Intent intent = new Intent(Choose.this,
                            MainActivity.class);
                    startActivity(intent);
                } else {
                    final Intent intent = new Intent(Choose.this, Check.class);
                    startActivity(intent);
                }

            }
        });

    }

}
