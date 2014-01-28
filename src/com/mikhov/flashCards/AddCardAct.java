package com.mikhov.flashCards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class AddCardAct extends Activity implements View.OnClickListener {

    public static enum TransitionType {
        SlideLeft
    }
    public static TransitionType transitionType;

    Intent intent;
    EditText addCategory;
    Button next;
    Database database;
    String category = "", task = "", oldCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);
        addCategory = (EditText) findViewById(R.id.add_category);
        next = (Button)findViewById(R.id.btn_add_category_next);
        next.setOnClickListener(this);

        database = new Database(this);
        database.open();

        Bundle extras = getIntent().getExtras();
        task = extras.getSerializable("task").toString();
        category = extras.getSerializable("category").toString();
        oldCategory = extras.getSerializable("oldcategory").toString();
        addCategory.setText(category);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_category_next:
                if (addCategory.getText().toString().equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.fill_category), Toast.LENGTH_SHORT).show();
                } if (task.equals("add")) {
                    if (!addCategory.getText().toString().equals(category)) {
                        database.updateQuestions(category, addCategory.getText().toString());
                    }
                    if (database.uniqCategory(addCategory.getText().toString())) {
                        this.finish();
                        intent = new Intent(this, PreviewAct.class);
                        intent.putExtra("category", addCategory.getText().toString());
                        intent.putExtra("task", task);
                        intent.putExtra("withImages", false);
                        startActivity(intent);
                        transitionType = TransitionType.SlideLeft;
                        overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.unique_category), Toast.LENGTH_SHORT).show();
                    }
                } else if (task.equals("edit")) {
                    if (addCategory.getText().toString().equals(category) || addCategory.getText().toString().equals(oldCategory) || database.uniqCategory(addCategory.getText().toString())) {
                        this.finish();
                        intent = new Intent(this, PreviewAct.class);
                        intent.putExtra("category", addCategory.getText().toString());
                        intent.putExtra("oldcategory", category);
                        intent.putExtra("task", task);
                        intent.putExtra("withImages", false);
                        startActivity(intent);
                        transitionType = TransitionType.SlideLeft;
                        overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.unique_category), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (task.equals("add")) {
            if (database.uniqCategory(category)) {
                database.deleteQuestionsWithCategory(category);
            }
        } else if (task.equals("edit")) {
            database.cancelAllChanges();
            if (database.uniqCategory(oldCategory)) {
                database.updateQuestions(category, oldCategory);
                database.updateCategory(category, oldCategory);
            }
        }
        this.finish();
        Intent intent = new Intent(this, MyCardsAct.class);
        intent.putExtra("category", category);
        intent.putExtra("task", task);
        startActivity(intent);
        overridePendingTransition(R.layout.slide_right_in, R.layout.slide_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}