package com.example.Language_Cards;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Random;

public class WorkActivity2 extends Activity {
    Random random = new Random();
    private int systemLanguage;
    private String secretWord;
    private TextView[] bot_text = new TextView[4];
    private TextView top_word;
    private ImageView imageView;
    private Button button;
    private ArrayList<Integer> arrayList;
    private DataBase dataBase;
    private int languageLeft, languageRight;
    private String answer = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        languageLeft = Integer.parseInt(getIntent().getStringExtra("left"));
        languageRight = Integer.parseInt(getIntent().getStringExtra("right"));
        systemLanguage = Integer.parseInt(getIntent().getStringExtra("language"));
        bot_text[0] = (TextView) findViewById(R.id.bot_word); bot_text[0].setText("");
        bot_text[1] = (TextView) findViewById(R.id.bot_word2);bot_text[1].setText("");
        bot_text[2] = (TextView) findViewById(R.id.bot_word3);bot_text[2].setText("");
        bot_text[3] = (TextView) findViewById(R.id.bot_word4);bot_text[3].setText("");
        top_word = (TextView) findViewById(R.id.top_word);
        imageView = (ImageView) findViewById(R.id.image_image);
        button = (Button) findViewById(R.id.button_next);
        if (systemLanguage == 1) button.setText("Далее");
        else if (systemLanguage == 2) button.setText("Further");
        else button.setText("进一步");

        dataBase = new DataBase(this);
        dataBase.open();
        arrayList = dataBase.getArrayListOK();

        printRandItem(arrayList.get(random.nextInt(arrayList.size())));
        printRandItem(arrayList.get(random.nextInt(arrayList.size())));
        printRandItem(arrayList.get(random.nextInt(arrayList.size())));
        printRandItem(arrayList.get(random.nextInt(arrayList.size())));
        int t = random.nextInt(4);
        while (bot_text[t].getText().toString().equals("") == false) {
            t = random.nextInt(4);
        }
        bot_text[t].setText(answer);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = "";
                for (int i = 0; i < 4; i++)
                        bot_text[i].setText("");
                printRandItem(arrayList.get(random.nextInt(arrayList.size())));
                printRandItem(arrayList.get(random.nextInt(arrayList.size())));
                printRandItem(arrayList.get(random.nextInt(arrayList.size())));
                printRandItem(arrayList.get(random.nextInt(arrayList.size())));
                int t = random.nextInt(4);
                while (bot_text[t].getText().toString().equals("") == false) {
                    t = random.nextInt(4);
                }
                bot_text[t].setText(answer);
            }
        });

        bot_text[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bot_text[0].getText().toString().equals(answer))
                    goodToast();
                else
                    badToast();
            }
        });

        bot_text[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bot_text[1].getText().toString().equals(answer))
                    goodToast();
                else
                    badToast();
            }
        });

        bot_text[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bot_text[2].getText().toString().equals(answer))
                    goodToast();
                else
                    badToast();
            }
        });

        bot_text[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bot_text[3].getText().toString().equals(answer))
                    goodToast();
                else
                    badToast();
            }
        });

    }

    private void goodToast() {
        String sms = "";
        if (systemLanguage == 1) sms = "ПРАВИЛЬНО!";
        if (systemLanguage == 2) sms = "TRUE!";
        if (systemLanguage == 3) sms = "正确地!";

        Toast toast = Toast.makeText(WorkActivity2.this, sms, 3000);
        toast.show();
    }

    private void badToast() {
        String sms = "";
        if (systemLanguage == 1) sms = "НЕТ!";
        if (systemLanguage == 2) sms = "FALSE!";
        if (systemLanguage == 3) sms = "谎言!";

        Toast toast = Toast.makeText(WorkActivity2.this, sms, 3000);
        toast.show();
    }

    private void printRandItem(int n) {
        if (n == 1) printAnimal();
        else if (n == 2) printHome();
        else if (n == 3) printBuy();
        else if (n == 4) printSport();
        else if (n == 5) printProduct();
        else if (n == 6) printLove();
        else if (n == 7) printCity();
        else if (n == 8) printTravel();
        else if (n == 9) printRestaurent();
        else  printWork();
    }

    private void printWork() {
        int t = random.nextInt(10) + 1;
        if (answer.equals("") == false) {
            String tmp = dataBase.getAnswer("Работа", t, languageRight);
            t = random.nextInt(4);
            while (bot_text[t].getText().toString().equals("") == false) {
                t = random.nextInt(4);
            }
            bot_text[t].setText(tmp);
            return;
        }

        if (t == 1) imageView.setImageResource(R.drawable.working);
        if (t == 2) imageView.setImageResource(R.drawable.plant);
        if (t == 3) imageView.setImageResource(R.drawable.company);
        if (t == 4) imageView.setImageResource(R.drawable.collective);
        if (t == 5) imageView.setImageResource(R.drawable.wages);
        if (t == 6) imageView.setImageResource(R.drawable.economist);
        if (t == 7) imageView.setImageResource(R.drawable.programmer);
        if (t == 8) imageView.setImageResource(R.drawable.trainee);
        if (t == 9) imageView.setImageResource(R.drawable.general_manager);
        if (t == 10) imageView.setImageResource(R.drawable.head);
        setText(dataBase.getCursor("Работа", t));
    }

    private void printRestaurent() {
        int t = random.nextInt(10) + 1;
        if (answer.equals("") == false) {
            String tmp = dataBase.getAnswer("В ресторане", t, languageRight);
            t = random.nextInt(4);
            while (bot_text[t].getText().toString().equals("") == false) {
                t = random.nextInt(4);
            }
            bot_text[t].setText(tmp);return;
        }
        if (t == 1) imageView.setImageResource(R.drawable.waiter);
        if (t == 2) imageView.setImageResource(R.drawable.dish);
        if (t == 3) imageView.setImageResource(R.drawable.order);
        if (t == 4) imageView.setImageResource(R.drawable.account);
        if (t == 5) imageView.setImageResource(R.drawable.tip);
        if (t == 6) imageView.setImageResource(R.drawable.bar_counter);
        if (t == 7) imageView.setImageResource(R.drawable.cocktail);
        if (t == 8) imageView.setImageResource(R.drawable.drinks);
        if (t == 9) imageView.setImageResource(R.drawable.menu);
        if (t == 10) imageView.setImageResource(R.drawable.wine);
        setText(dataBase.getCursor("В ресторане", t));
    }

    private void printTravel() {
        int t = random.nextInt(10) + 1;
        if (answer.equals("") == false) {
            String tmp = dataBase.getAnswer("Путешествия", t, languageRight);
            t = random.nextInt(4);
            while (bot_text[t].getText().toString().equals("") == false) {
                t = random.nextInt(4);
            }
            bot_text[t].setText(tmp);
            return;
        }
        if (t == 1) imageView.setImageResource(R.drawable.flight);
        if (t == 2) imageView.setImageResource(R.drawable.hitch_hiking);
        if (t == 3) imageView.setImageResource(R.drawable.highway);
        if (t == 4) imageView.setImageResource(R.drawable.beach);
        if (t == 5) imageView.setImageResource(R.drawable.mountains);
        if (t == 6) imageView.setImageResource(R.drawable.sea);
        if (t == 7) imageView.setImageResource(R.drawable.liner);
        if (t == 8) imageView.setImageResource(R.drawable.tent);
        if (t == 9) imageView.setImageResource(R.drawable.halt);
        if (t == 10) imageView.setImageResource(R.drawable.route);
        setText(dataBase.getCursor("Путешествия", t));
    }

    private void printCity() {
        int t = random.nextInt(10) + 1;
        if (answer.equals("") == false) {
            String tmp = dataBase.getAnswer("В городе", t, languageRight);
            t = random.nextInt(4);
            while (bot_text[t].getText().toString().equals("") == false) {
                t = random.nextInt(4);
            }
            bot_text[t].setText(tmp);    return;
        }
        if (t == 1) imageView.setImageResource(R.drawable.traffic_light);
        if (t == 2) imageView.setImageResource(R.drawable.street);
        if (t == 3) imageView.setImageResource(R.drawable.crossroads);
        if (t == 4) imageView.setImageResource(R.drawable.bridge);
        if (t == 5) imageView.setImageResource(R.drawable.shop);
        if (t == 6) imageView.setImageResource(R.drawable.monument);
        if (t == 7) imageView.setImageResource(R.drawable.park);
        if (t == 8) imageView.setImageResource(R.drawable.map);
        if (t == 9) imageView.setImageResource(R.drawable.hotel);
        if (t == 10) imageView.setImageResource(R.drawable.hospital);
        setText(dataBase.getCursor("В городе", t));
    }

    private void printLove() {
        int t = random.nextInt(10) + 1;
        if (answer.equals("") == false) {
            String tmp = dataBase.getAnswer("Любовь и отношения", t, languageRight);
            t = random.nextInt(4);
            while (bot_text[t].getText().toString().equals("") == false) {
                t = random.nextInt(4);
            }
            bot_text[t].setText(tmp);           return;
        }
        if (t == 1) imageView.setImageResource(R.drawable.couple);
        if (t == 2) imageView.setImageResource(R.drawable.love);
        if (t == 3) imageView.setImageResource(R.drawable.friendship);
        if (t == 4) imageView.setImageResource(R.drawable.trust);
        if (t == 5) imageView.setImageResource(R.drawable.jealousy);
        if (t == 6) imageView.setImageResource(R.drawable.family);
        if (t == 7) imageView.setImageResource(R.drawable.wedding);
        if (t == 8) imageView.setImageResource(R.drawable.parents);
        if (t == 9) imageView.setImageResource(R.drawable.care);
        if (t == 10) imageView.setImageResource(R.drawable.child);
        setText(dataBase.getCursor("Любовь и отношения", t));
    }

    private void printProduct() {
        int t = random.nextInt(10) + 1;
        if (answer.equals("") == false) {
            String tmp = dataBase.getAnswer("Продукты", t, languageRight);
            t = random.nextInt(4);
            while (bot_text[t].getText().toString().equals("") == false) {
                t = random.nextInt(4);
            }
            bot_text[t].setText(tmp);                  return;
        }
        if (t == 1) imageView.setImageResource(R.drawable.milk);
        if (t == 2) imageView.setImageResource(R.drawable.fruit);
        if (t == 3) imageView.setImageResource(R.drawable.fish);
        if (t == 4) imageView.setImageResource(R.drawable.bread);
        if (t == 5) imageView.setImageResource(R.drawable.eggs);
        if (t == 6) imageView.setImageResource(R.drawable.groats);
        if (t == 7) imageView.setImageResource(R.drawable.sugar);
        if (t == 8) imageView.setImageResource(R.drawable.tea);
        if (t == 9) imageView.setImageResource(R.drawable.cheese);
        if (t == 10) imageView.setImageResource(R.drawable.meat);
        setText(dataBase.getCursor("Продукты", t));
    }

    private void printSport() {
        int t = random.nextInt(10) + 1;
        if (answer.equals("") == false) {
            String tmp = dataBase.getAnswer("Спорт и здоровье", t, languageRight);
            t = random.nextInt(4);
            while (bot_text[t].getText().toString().equals("") == false) {
                t = random.nextInt(4);
            }
            bot_text[t].setText(tmp);                         return;
        }
        if (t == 1) imageView.setImageResource(R.drawable.football);
        if (t == 2) imageView.setImageResource(R.drawable.basketball);
        if (t == 3) imageView.setImageResource(R.drawable.swimming);
        if (t == 4) imageView.setImageResource(R.drawable.heel_and_toe_walk);
        if (t == 5) imageView.setImageResource(R.drawable.fitness);
        if (t == 6) imageView.setImageResource(R.drawable.yoga);
        if (t == 7) imageView.setImageResource(R.drawable.skiing);
        if (t == 8) imageView.setImageResource(R.drawable.healthy_eating);
        if (t == 9) imageView.setImageResource(R.drawable.day_regimen);
        if (t == 10) imageView.setImageResource(R.drawable.running);
        setText(dataBase.getCursor("Спорт и здоровье", t));
    }

    private void printBuy() {
        int t = random.nextInt(10) + 1;
        if (answer.equals("") == false) {
            String tmp = dataBase.getAnswer("Покупки", t, languageRight);
            t = random.nextInt(4);
            while (bot_text[t].getText().toString().equals("") == false) {
                t = random.nextInt(4);
            }
            bot_text[t].setText(tmp);                                return;
        }
        if (t == 1) imageView.setImageResource(R.drawable.handbag);
        if (t == 2) imageView.setImageResource(R.drawable.purse);
        if (t == 3) imageView.setImageResource(R.drawable.dress);
        if (t == 4) imageView.setImageResource(R.drawable.shirt);
        if (t == 5) imageView.setImageResource(R.drawable.perfume);
        if (t == 6) imageView.setImageResource(R.drawable.mug);
        if (t == 7) imageView.setImageResource(R.drawable.scarf);
        if (t == 8) imageView.setImageResource(R.drawable.camera);
        if (t == 9) imageView.setImageResource(R.drawable.bouquet_of_flowers);
        if (t == 10) imageView.setImageResource(R.drawable.computer);
        setText(dataBase.getCursor("Покупки", t));
    }

    private void printHome() {
        int t = random.nextInt(10) + 1;
        if (answer.equals("") == false) {
            String tmp = dataBase.getAnswer("В доме", t, languageRight);
            t = random.nextInt(4);
            while (bot_text[t].getText().toString().equals("") == false) {
                t = random.nextInt(4);
            }
            bot_text[t].setText(tmp);                                       return;
        }
        if (t == 1) imageView.setImageResource(R.drawable.fireplace);
        if (t == 2) imageView.setImageResource(R.drawable.kitchen);
        if (t == 3) imageView.setImageResource(R.drawable.furniture);
        if (t == 4) imageView.setImageResource(R.drawable.bed);
        if (t == 5) imageView.setImageResource(R.drawable.tv);
        if (t == 6) imageView.setImageResource(R.drawable.window);
        if (t == 7) imageView.setImageResource(R.drawable.living);
        if (t == 8) imageView.setImageResource(R.drawable.hanger);
        if (t == 9) imageView.setImageResource(R.drawable.bath);
        if (t == 10) imageView.setImageResource(R.drawable.cupboard);
        setText(dataBase.getCursor("В доме", t));
    }

    private void printAnimal() {
        int t = random.nextInt(10) + 1;
        if (answer.equals("") == false) {
            String tmp = dataBase.getAnswer("Животные", t, languageRight);
            t = random.nextInt(4);
            while (bot_text[t].getText().toString().equals("") == false) {
                t = random.nextInt(4);
            }
            bot_text[t].setText(tmp);                                              return;
        }
        if (t == 1) imageView.setImageResource(R.drawable.cat);
        if (t == 2) imageView.setImageResource(R.drawable.dog);
        if (t == 3) imageView.setImageResource(R.drawable.crocodile);
        if (t == 4) imageView.setImageResource(R.drawable.cow);
        if (t == 5) imageView.setImageResource(R.drawable.pig);
        if (t == 6) imageView.setImageResource(R.drawable.horse);
        if (t == 7) imageView.setImageResource(R.drawable.hamster);
        if (t == 8) imageView.setImageResource(R.drawable.tiger);
        if (t == 9) imageView.setImageResource(R.drawable.hippopotamus);
        if (t == 10) imageView.setImageResource(R.drawable.elephant);
        setText(dataBase.getCursor("Животные", t));
    }

    private void setText(Cursor cursor) {
        if (languageLeft == 1) top_word.setText(cursor.getString(cursor.getColumnIndex(DataBase.KEY_RUSSIAN)));
        if (languageLeft == 2) top_word.setText(cursor.getString(cursor.getColumnIndex(DataBase.KEY_ENGLISH)));
        if (languageLeft == 3) top_word.setText(cursor.getString(cursor.getColumnIndex(DataBase.KEY_CHINESE)));

        if (languageRight == 1) answer = cursor.getString(cursor.getColumnIndex(DataBase.KEY_RUSSIAN));
        if (languageRight == 2) answer = cursor.getString(cursor.getColumnIndex(DataBase.KEY_ENGLISH));
        if (languageRight == 3) answer = cursor.getString(cursor.getColumnIndex(DataBase.KEY_CHINESE));
    }
}
