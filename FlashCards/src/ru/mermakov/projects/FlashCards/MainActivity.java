package ru.mermakov.projects.FlashCards;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.yandex.metrica.Counter;

public class MainActivity extends Activity {
    ImageView targetImage;
    Bitmap srcBitmapLocal;
    ListView listview;
    TextView textView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin);

        Counter.initialize(getApplicationContext());
        Counter.sharedInstance().reportEvent("MainActivity");
        Counter.sharedInstance().sendEventsBuffer();
        String[] usmessage;
        usmessage = getResources().getStringArray(R.array.topic);
        listview = (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, usmessage);
        listview.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        textView = (TextView) findViewById(R.id.textView1);
        final String s = getResources().getString(R.string.Topics);
        textView.setText(s);

        final String number = "number";
        final Intent intent = new Intent(MainActivity.this, FlashCard.class);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View p,
                                    int position, long id) {

                intent.putExtra(number, 10 * position);
                startActivity(intent);

            }
        });

        // targetImage.setImageBitmap(srcBitmapLocal);

    }

}
