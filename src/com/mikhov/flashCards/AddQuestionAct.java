package com.mikhov.flashCards;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddQuestionAct extends Activity implements View.OnClickListener {

    public static enum TransitionType {
        SlideLeft
    }
    public static TransitionType transitionType;

    Intent intent;
    Button ok;
    EditText addQuestion, addAnswer;
    Database database;
    Bitmap qImage, mqImage;
    TextView addQuestionCategory;
    String category = "", task = "", global = "", oldCategory = "";
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);
        addQuestionCategory = (TextView) findViewById(R.id.add_question_category);
        addQuestion = (EditText) findViewById(R.id.add_question);
        addAnswer = (EditText) findViewById(R.id.add_answer);


        database = new Database(this);
        database.open();
        ok = (Button)findViewById(R.id.btn_add_question_ok);
        ok.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        category = extras.getSerializable("category").toString();
        oldCategory = extras.getSerializable("oldcategory").toString();
        task = extras.getSerializable("task").toString();
        global = extras.getSerializable("global").toString();
        addQuestionCategory.setText("Категория вопросов: " + category);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_question_ok:
                if (addQuestion.getText().toString().equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.fill_question), Toast.LENGTH_SHORT).show();
                } else if (addAnswer.getText().toString().equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.fill_answer), Toast.LENGTH_SHORT).show();
                } else {
                    if (database.uniqQuestion(category, addQuestion.getText().toString())) {
                        qImage = BitmapFactory.decodeResource(getResources(), R.drawable.no_image);
                        mqImage = BitmapFactory.decodeResource(getResources(), R.drawable.mno_image);
                        database.addQuestion(category, addQuestion.getText().toString(), addAnswer.getText().toString(), qImage, mqImage, 1);
                        this.finish();
                        intent = new Intent(this, PreviewAct.class);
                        intent.putExtra("category", category);
                        intent.putExtra("task", global);
                        intent.putExtra("oldcategory", oldCategory);
                        startActivity(intent);
                        transitionType = TransitionType.SlideLeft;
                        overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.unique_question), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        intent = new Intent(this, PreviewAct.class);
        intent.putExtra("category", category);
        intent.putExtra("task", global);
        intent.putExtra("oldcategory", oldCategory);
        startActivity(intent);
        transitionType = TransitionType.SlideLeft;
        overridePendingTransition(R.layout.slide_right_in, R.layout.slide_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
