package com.mobdev.cardz;

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
		Button btn_cats = (Button) findViewById(R.id.cats);
		final Button btn_clr = (Button) findViewById(R.id.clear);
		btn_cats.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						CategoriesActivity.class);
				startActivity(intent);
			}
		});
		btn_clr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btn_clr.setClickable(false);
				CategoriesDataBaseHelper categoriesDataBaseHelper = new CategoriesDataBaseHelper(
						getApplicationContext());
				SQLiteDatabase sqLiteDatabase = categoriesDataBaseHelper
						.getWritableDatabase();
				ContentValues contentValues = new ContentValues();
				contentValues.put(CategoriesDataBaseHelper.WORDS, 0);
				sqLiteDatabase.update(CategoriesDataBaseHelper.DATABASE_NAME,
						contentValues, null, null);
				sqLiteDatabase.close();
				categoriesDataBaseHelper.close();
				Toast.makeText(getApplicationContext(), R.string.done,
						Toast.LENGTH_LONG).show();
				btn_clr.setClickable(true);
			}
		});
	}
}