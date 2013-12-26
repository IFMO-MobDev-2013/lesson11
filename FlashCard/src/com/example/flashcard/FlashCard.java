package com.example.flashcard;

import java.util.ArrayList;

import com.yandex.metrica.Counter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import android.content.DialogInterface.OnClickListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FlashCard extends Activity {
	DataBaseHelper sqh;
	SQLiteDatabase sqdb;
	ImageView targetImage;
	Bitmap srcBitmapLocal;
	Bitmap srcBitmapLocal2;
	TextView word;
	Button next;
	Button prev;
	Button show;
	AlertDialog.Builder ad;
	Context context;
	int pos;
	int sum = 0;
	int[] picture = { R.drawable.father, R.drawable.mother, R.drawable.son,
			R.drawable.daughter, R.drawable.grandmother,
			R.drawable.grandfather, R.drawable.wedding, R.drawable.ring,
			R.drawable.birthday, R.drawable.family, R.drawable.leg,
			R.drawable.arm, R.drawable.head, R.drawable.eyes, R.drawable.ear,
			R.drawable.mouth, R.drawable.nose, R.drawable.teeth,
			R.drawable.back, R.drawable.stomach, R.drawable.river,
			R.drawable.mountains, R.drawable.forest, R.drawable.ocean,
			R.drawable.desert, R.drawable.steppe, R.drawable.lake,
			R.drawable.tundra, R.drawable.taiga, R.drawable.stars,
			R.drawable.pizza, R.drawable.soup, R.drawable.salad,
			R.drawable.tea, R.drawable.coffee, R.drawable.juice,
			R.drawable.lemonad, R.drawable.chocolate, R.drawable.cookie,
			R.drawable.cutlet, R.drawable.jeans, R.drawable.tshort,
			R.drawable.shorts, R.drawable.dress, R.drawable.shirt,
			R.drawable.blouse, R.drawable.jacket, R.drawable.shoes,
			R.drawable.trainers, R.drawable.boots, R.drawable.cat,
			R.drawable.dog, R.drawable.parrot, R.drawable.lion,
			R.drawable.tiger, R.drawable.bear, R.drawable.fish,
			R.drawable.bird, R.drawable.pinguin, R.drawable.ass,
			R.drawable.window, R.drawable.roof, R.drawable.door,
			R.drawable.kettle, R.drawable.stove, R.drawable.table,
			R.drawable.carpet, R.drawable.sofa, R.drawable.bed, R.drawable.tv,
			R.drawable.bus, R.drawable.tram, R.drawable.trolleybus,
			R.drawable.skycraper, R.drawable.lights, R.drawable.shop,
			R.drawable.school, R.drawable.hospital, R.drawable.house,
			R.drawable.museum, R.drawable.football, R.drawable.hockey,
			R.drawable.swim, R.drawable.equestrian_sport, R.drawable.ball,
			R.drawable.brasket, R.drawable.horizontal_bar, R.drawable.net,
			R.drawable.stick, R.drawable.chess, R.drawable.phone,
			R.drawable.car, R.drawable.ship, R.drawable.computer,
			R.drawable.motocycle, R.drawable.airplane, R.drawable.train,
			R.drawable.watch, R.drawable.snowmoblie, R.drawable.spaceship };

	String number = "number";
	String[] global_our_word;
	String[] global_to_word;
	int[] count = new int[105];
	ArrayList<Integer> queue = new ArrayList<Integer>();

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		targetImage = (ImageView) findViewById(R.id.imageContainer);

		targetImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View V) {
				String title = getResources().getString(R.string.answer);
				String button1String = "YES";
				String button2String = "NO";
				String message = global_to_word[pos];
				ad = new AlertDialog.Builder(context);
				ad.setTitle(title);
				ad.setMessage(message);

				ad.setPositiveButton(button1String,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int arg1) {
								count[pos]++;

								sqdb = sqh.getWritableDatabase();
								ContentValues contentValues = new ContentValues();
								contentValues.put(DataBaseHelper.UID, pos + 1);
								contentValues.put(DataBaseHelper.CATNAME,
										count[pos]);
								int t = pos + 1;
								sqdb.update(DataBaseHelper.TABLE_NAME,
										contentValues, DataBaseHelper.UID + "="
												+ t, null);
								sqdb.close();
								pos = getMin();

								showPicture();

							}
						});
				ad.setNegativeButton(button2String,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int arg1) {
								pos = getMin();

								showPicture();
							}
						});
				ad.show();
			}
		});
		word = (TextView) findViewById(R.id.textView1);
		String[] our_word = getResources().getStringArray(R.array.our_word);
		global_our_word = our_word;
		String[] to_word = getResources().getStringArray(R.array.word);
		global_to_word = to_word;
		pos = getIntent().getExtras().getInt(number);

		sqh = new DataBaseHelper(this);

		// База нам нужна для записи и чтения

		getCount();
		for (int i = pos; i < pos + 10; i++) {
			queue.add(count[i]);
		}
		word.setText(our_word[pos]);
		pos = getMin();
		showPicture();
		context = FlashCard.this;
		next = (Button) findViewById(R.id.button3);
		
	}

	public int getMin() {

		int number = pos;
		if (pos % 10 == 9) {
			number = pos - 9;
		} else {
			number++;
		}
		int min = count[number];
		for (int i = 10 * (pos / 10); i < 10 * (pos / 10) + 10; i++) {
			if (min > count[i] && i != pos) {
				min = count[i];
				number = i;
			}
		}
		// System.out.println(number);
		return number;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("NewApi")
	private static Point getDisplaySize(final Display display) {
		Point point = new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) { // API
																			// LEVEL
																			// 13
			display.getSize(point);
		} else {
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		return point;
	}

	public void showWord() {
		word.setText(global_to_word[pos]);

	}

	public void showPicture() {
		Display display = getWindowManager().getDefaultDisplay();
		sum++;
		Bitmap b = BitmapFactory.decodeResource(getApplicationContext()
				.getResources(), picture[pos]);

		Point size = getDisplaySize(display);
		targetImage.setAdjustViewBounds(true);
		int width = size.x;
		int height = size.y;
		width = Math.min((int) (width / 1.5), b.getWidth());
		Bitmap c = Bitmap.createScaledBitmap(b, width, (int) (height / 1.5),
				true);
		targetImage.setImageBitmap(c);
		word.setText(global_our_word[pos]);
	}

	public void getCount() {
		sqdb = sqh.getWritableDatabase();
		Cursor cursor = sqdb.query(DataBaseHelper.TABLE_NAME, null, null, // The
																			// columns
																			// for
																			// the
																			// WHERE
																			// clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);
		int counter = 0;
		while (cursor.moveToNext()) {
			// GET COLUMN INDICES + VALUES OF THOSE COLUMNS
			int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.UID));
			int name = cursor.getInt(cursor
					.getColumnIndex(DataBaseHelper.CATNAME));
			count[id - 1] = name;

			counter++;
		}

		cursor.close();
		if (counter == 0) {
			for (int i = 0; i < 100; i++) {
				ContentValues cv = new ContentValues();
				cv.put(DataBaseHelper.CATNAME, 0);
				// вызываем метод вставки
				sqdb.insert(DataBaseHelper.TABLE_NAME, DataBaseHelper.CATNAME,
						cv);
			}
			getCount();
		}

		sqdb.close();

	}

	@Override
	public void onDestroy() {
		Counter.sharedInstance().reportEvent(String.valueOf(sum));
		Counter.sharedInstance().sendEventsBuffer();
	}

}