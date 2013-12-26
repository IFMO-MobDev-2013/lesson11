package com.example.Esperanto;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class GameTypes extends Activity {

    String category;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gametypes);
        category = getIntent().getStringExtra("category");
    }

    public void gameOne(View v) {
        start(GameOne.class);
    }
    public void gameTwo(View v) {
        start(GameTwo.class);
    }
   public void gameThree(View v) {
        start(GameThree.class);
    }
    public void back(View view) {
        Intent intent = new Intent(this, Categories.class);
        this.finish();
        startActivity(intent);
    }
    private void start(Class classs) {
        Intent intent = new Intent(this, classs);
        intent.putExtra("category", category);
        this.finish();
        startActivity(intent);
    }


}

