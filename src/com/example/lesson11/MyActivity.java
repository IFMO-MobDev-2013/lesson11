package com.example.lesson11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.lesson11.DataBase.RussianDB;
import com.example.lesson11.DataBase.UzbekDB;

public class MyActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    Integer t = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button buttonrus = (Button) findViewById(R.id.buttonlanguagerus);
        Button buttonuzb = (Button) findViewById(R.id.buttonlanguageuzb);
        buttonrus.setOnClickListener(this);
        buttonuzb.setOnClickListener(this);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lltopmain);
        int x = getResources().getColor(R.color.Bckgr);
        linearLayout.setBackgroundColor(x);

        ImageView imageView1 = (ImageView) findViewById(R.id.imageview1);
        imageView1.setImageResource(getResources().getIdentifier("icon", "drawable", getPackageName()));


      //  if(t.equals(1)){
            RussianDB db = new RussianDB(this);
            db.open();
            if(db.isEmpty()){
                db.addDefault();
            }
            db.close();
            UzbekDB db1 = new UzbekDB(this);
            db1.open();
            if(db1.isEmpty()){
                db1.addDefault();
            }
            db1.close();
            t =0;
        //}


    }

    @Override
    public void onClick(View v) {
        //To change body of implemented methods use File | Settings | File Templates.
        Intent intent;
        switch (v.getId()){
            case R.id.buttonlanguagerus:
                intent = new Intent(this, CategoryActivity.class);
                intent.putExtra("nativelanguage", "rus");
                startActivity(intent);
                break;
            case R.id.buttonlanguageuzb:
                intent = new Intent(this, CategoryActivity.class);
                intent.putExtra("nativelanguage", "uzb");
                startActivity(intent);
                break;
        }
    }
}