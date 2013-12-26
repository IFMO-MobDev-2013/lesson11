package com.example.flashcard;

import java.util.ArrayList;

import android.R.string;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
