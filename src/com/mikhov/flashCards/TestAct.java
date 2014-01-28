package com.mikhov.flashCards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.*;
import java.util.Random;

public class TestAct extends Activity implements View.OnClickListener {

    Display display;
    Database database;
    Cursor imageCursor;
    ImageView questionImage;
    TextView question, answer;
    ImageButton btnExit, btnNext;
    String category_id = "", mode = "", category;
    boolean check = false;
    AlertDialog.Builder quits;
    int falses = 0, trues = 0, currentIndex, ans;
    Button[] vars;
    Bitmap imageBitmaps;
    byte[] image_byte;
    Random random;
    String[] dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        display = this.getWindowManager().getDefaultDisplay();
        dictionary = getResources().getStringArray(R.array.answers);
        initDialogs();

        Bundle extras = getIntent().getExtras();
        category_id = extras.getSerializable("category_id").toString();
        mode = extras.getSerializable("mode").toString();

        database = new Database(this);
        database.open();
        imageCursor = database.getQuestionsData(database.getCategory(Long.parseLong(category_id)));
        category = database.getCategory(Long.parseLong(category_id));

        if (mode.equals("yes")) {
            setContentView(R.layout.test_var);
            questionImage = (ImageView) findViewById(R.id.question_image_test_var);
            question = (TextView) findViewById(R.id.test_question_var);
            vars = new Button[4];
            vars[0] = (Button) findViewById(R.id.var1);
            vars[1] = (Button) findViewById(R.id.var2);
            vars[2] = (Button) findViewById(R.id.var3);
            vars[3] = (Button) findViewById(R.id.var4);
        } else {
            setContentView(R.layout.test);
            questionImage = (ImageView) findViewById(R.id.question_image_test);
            question = (TextView) findViewById(R.id.test_question);
            answer = (TextView) findViewById(R.id.test_answer);

            btnExit = (ImageButton) findViewById(R.id.btn_test_exit);
            btnNext = (ImageButton) findViewById(R.id.btn_test_next);

            btnExit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!check) {
                        quits.show();
                    } else {
                        falses++;
                        if (!imageCursor.moveToNext()) {
                            database.updateStats(category, falses, trues);
                            TestAct.this.finish();
                            Intent intent = new Intent(TestAct.this, MyCardsAct.class);
                            startActivity(intent);
                            overridePendingTransition(R.layout.slide_right_in, R.layout.slide_right_out);
                        } else {
                            question.setText(imageCursor.getString(imageCursor.getColumnIndex(Database.QUESTIONS_COL_QUESTION)));
                            answer.setText("???");
                            image_byte = imageCursor.getBlob(imageCursor.getColumnIndex(Database.QUESTIONS_COL_IMAGE));
                            imageBitmaps = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
                            questionImage.setImageBitmap(scaleLarge(imageBitmaps));
                        }
                        btnNext.setImageResource(R.drawable.next);
                        btnExit.setImageResource(R.drawable.exit);
                        check = !check;
                    }
                }
            });
            btnNext.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (check) {
                        trues++;
                        if (imageCursor.isLast() || !imageCursor.moveToNext()) {
                            database.updateStats(category, falses, trues);
                            TestAct.this.finish();
                            Intent intent = new Intent(TestAct.this, MyCardsAct.class);
                            startActivity(intent);
                            overridePendingTransition(R.layout.slide_right_in, R.layout.slide_right_out);
                        } else {
                            question.setText(imageCursor.getString(imageCursor.getColumnIndex(Database.QUESTIONS_COL_QUESTION)));
                            answer.setText("???");
                            image_byte = imageCursor.getBlob(imageCursor.getColumnIndex(Database.QUESTIONS_COL_IMAGE));
                            imageBitmaps = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
                            questionImage.setImageBitmap(scaleLarge(imageBitmaps));
                        }
                        btnNext.setImageResource(R.drawable.next);
                        btnExit.setImageResource(R.drawable.exit);
                    } else {
                        answer.setText(imageCursor.getString(imageCursor.getColumnIndex(Database.QUESTIONS_COL_ANSWER)));
                        btnNext.setImageResource(R.drawable.true_png);
                        btnExit.setImageResource(R.drawable.false_png);
                        currentIndex++;
                    }
                    check = !check;
                }
            });
        }
        if (mode.equals("yes")) {
            getNext();
        } else {
            if (imageCursor.moveToNext()) {
                question.setText(imageCursor.getString(imageCursor.getColumnIndex(Database.QUESTIONS_COL_QUESTION)));
                answer.setText("???");
                image_byte = imageCursor.getBlob(imageCursor.getColumnIndex(Database.QUESTIONS_COL_IMAGE));
                imageBitmaps = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
                questionImage.setImageBitmap(scaleLarge(imageBitmaps));
            } else {
                database.updateStats(category, falses, trues);
                TestAct.this.finish();
                Intent intent = new Intent(TestAct.this, MyCardsAct.class);
                startActivity(intent);
                overridePendingTransition(R.layout.slide_right_in, R.layout.slide_right_out);
            }
        }
    }

    public void getNext() {
        if (!imageCursor.moveToNext()) {
            database.updateStats(category, falses, trues);
            TestAct.this.finish();
            Intent intent = new Intent(TestAct.this, MyCardsAct.class);
            startActivity(intent);
            overridePendingTransition(R.layout.slide_right_in, R.layout.slide_right_out);
        } else {
            question.setText(imageCursor.getString(imageCursor.getColumnIndex(Database.QUESTIONS_COL_QUESTION)));
            image_byte = imageCursor.getBlob(imageCursor.getColumnIndex(Database.QUESTIONS_COL_IMAGE));
            imageBitmaps = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
            questionImage.setImageBitmap(scaleLarge(imageBitmaps));

            random = new Random();
            String trueAns = imageCursor.getString(imageCursor.getColumnIndex(Database.QUESTIONS_COL_ANSWER));
            String[] ansArr = new String[3];
            ansArr[0] = trueAns;
            ansArr[1] = trueAns;
            ansArr[2] = trueAns;
            while (ansArr[0].equals(trueAns)) {
                int randAns = random.nextInt(dictionary.length - 1);
                ansArr[0] = dictionary[randAns];
            }
            while (ansArr[1].equals(trueAns) || ansArr[1].equals(ansArr[0])) {
                int randAns = random.nextInt(dictionary.length - 1);
                ansArr[1] = dictionary[randAns];
            }
            while (ansArr[2].equals(trueAns) || ansArr[2].equals(ansArr[0]) || ansArr[2].equals(ansArr[1])) {
                int randAns = random.nextInt(dictionary.length - 1);
                ansArr[2] = dictionary[randAns];
            }

            ans = random.nextInt(4);
            vars[ans].setText(trueAns);
            int step = 0;
            for (int i = 0; i < 4; i++) {
                if (i != ans) {
                    vars[i].setText(ansArr[step]);
                    step++;
                }
                vars[i].setOnClickListener(this);
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.var1:
                if (ans == 0) {
                    trues++;
                } else {
                    falses++;
                }
                getNext();
                break;
            case R.id.var2:
                if (ans == 1) {
                    trues++;
                } else {
                    falses++;
                }
                getNext();
                break;
            case R.id.var3:
                if (ans == 2) {
                    trues++;
                } else {
                    falses++;
                }
                getNext();
                break;
            case R.id.var4:
                if (ans == 3) {
                    trues++;
                } else {
                    falses++;
                }
                getNext();
                break;
        }
    }

    public Bitmap scaleLarge(Bitmap b) {
        double nw, nh, scale;
        int displayWidth = display.getWidth();
        int bitmapWidth = b.getWidth();
        int bitmapHeight= b.getHeight();
        nw = displayWidth;
        scale = nw / bitmapWidth;
        nh = scale * bitmapHeight;
        b = Bitmap.createScaledBitmap(b, (int) nw, (int) nh, false);
        return b;
    }

    @Override
    public void onBackPressed() {
        quits.show();
    }

    public void initDialogs() {
        quits = new AlertDialog.Builder(this);
        quits.setTitle(getResources().getString(R.string.exit));
        quits.setMessage(getResources().getString(R.string.to_category));
        quits.setPositiveButton(getResources().getString(R.string.to_exit), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                TestAct.this.finish();
                Intent intent = new Intent(TestAct.this, MyCardsAct.class);
                startActivity(intent);
                overridePendingTransition(R.layout.slide_right_in, R.layout.slide_right_out);
            }
        });
        quits.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            }
        });
        quits.setCancelable(true);
        quits.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
