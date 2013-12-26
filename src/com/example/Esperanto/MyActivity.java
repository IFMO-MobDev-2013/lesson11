package com.example.Esperanto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void customPlay(View v) {
        Intent intent = new Intent(this, Categories.class);
        startActivity(intent);
    }
    public void randomGame(View v) {
        Class game = GameThree.class;
        String category;
        String[] categories = new String[]{"animals", "products", "furniture", "plant", "weather", "tech", "clothes", "professions","always","all"};
        category = categories[new Random().nextInt(categories.length)];
        int x = new Random().nextInt(3);
        if (x == 0) game = GameOne.class;
        if (x == 1) game = GameTwo.class;
        if (x == 2) game = GameThree.class;
        Intent intent = new Intent(this, game);
        intent.putExtra("category", category);
        this.finish();
        startActivity(intent);
    }
}


