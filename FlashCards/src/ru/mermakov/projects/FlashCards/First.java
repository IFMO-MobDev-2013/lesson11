package ru.mermakov.projects.FlashCards;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.yandex.metrica.Counter;

import java.util.ArrayList;
import java.util.Random;

public class First extends Activity {
    int[] d;
    ImageView picture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        Counter.initialize(getApplicationContext());
        Counter.sharedInstance().reportEvent("SecondModeActivity");
        Counter.sharedInstance().sendEventsBuffer();
        picture = (ImageView) findViewById(R.id.imageView1);
        Check();

    }

    public void Check() {
        Random t = new Random();
        int[] answer = new int[4];
        for (int i = 0; i < 4; i++) {
            answer[i] = t.nextInt(25) + i * 25;
        }
        final int good = t.nextInt(4);
        showPicture(answer[good]);
        ArrayAdapter<String> adapter;
        ListView listview;
        listview = (ListView) findViewById(R.id.listView1);
        final ArrayList choose = new ArrayList<String>();
        String[] to_word = getResources().getStringArray(R.array.word);
        for (int i = 0; i < 4; i++) {
            choose.add(to_word[answer[i]]);
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, choose);

        listview.setAdapter(adapter);
        final String ok = getResources().getString(R.string.Yes);
        final String bad = getResources().getString(R.string.No);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View p,
                                    int position, long id) {
                if (position == good) {
                    Toast toast = Toast.makeText(getApplicationContext(), ok,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Check();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), bad,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Check();
                }

            }
        });
    }

    public void showPicture(int x) {
        Display display = getWindowManager().getDefaultDisplay();

        Bitmap b = BitmapFactory.decodeResource(getApplicationContext()
                .getResources(), all_picture[x]);

        Point size = getDisplaySize(display);
        picture.setAdjustViewBounds(true);
        int width = size.x;
        int height = size.y;
        width = Math.min((int) (width / 1.7), b.getWidth());
        Bitmap c = Bitmap
                .createScaledBitmap(b, width, (int) (height / 2), true);
        picture.setImageBitmap(c);

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

    int[] all_picture = {R.drawable.father, R.drawable.mother, R.drawable.son,
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
            R.drawable.watch, R.drawable.snowmoblie, R.drawable.spaceship};
}
