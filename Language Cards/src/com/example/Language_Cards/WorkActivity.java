package com.example.Language_Cards;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Random;

public class WorkActivity extends Activity {
    Random random = new Random();
    private int systemLanguage;
    private String secretWord;
    private TextView top_text, bot_text;
    private ImageView imageView;
    private Button button;
    private ArrayList<Integer> arrayList;
    private DataBase dataBase;
    private int languageLeft, languageRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page);

        languageLeft = Integer.parseInt(getIntent().getStringExtra("left"));
        languageRight = Integer.parseInt(getIntent().getStringExtra("right"));
        systemLanguage = Integer.parseInt(getIntent().getStringExtra("language"));
        top_text = (TextView) findViewById(R.id.top_word);
        bot_text = (TextView) findViewById(R.id.bot_word);
        imageView = (ImageView) findViewById(R.id.image_image);
        button = (Button) findViewById(R.id.button_next);
        if (systemLanguage == 1) button.setText("Далее");
        else if (systemLanguage == 2) button.setText("Further");
        else button.setText("进一步");

        dataBase = new DataBase(this);
        dataBase.open();
        arrayList = dataBase.getArrayListOK();
        printRandItem(arrayList.get(random.nextInt(arrayList.size())));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printRandItem(arrayList.get(random.nextInt(arrayList.size())));
            }
        });

        bot_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bot_text.setText(secretWord);
            }
        });
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
        if (languageLeft == 1) top_text.setText(cursor.getString(cursor.getColumnIndex(DataBase.KEY_RUSSIAN)));
        if (languageLeft == 2) top_text.setText(cursor.getString(cursor.getColumnIndex(DataBase.KEY_ENGLISH)));
        if (languageLeft == 3) top_text.setText(cursor.getString(cursor.getColumnIndex(DataBase.KEY_CHINESE)));

        if (languageRight == 1) secretWord = cursor.getString(cursor.getColumnIndex(DataBase.KEY_RUSSIAN));
        if (languageRight == 2) secretWord = cursor.getString(cursor.getColumnIndex(DataBase.KEY_ENGLISH));
        if (languageRight == 3) secretWord = cursor.getString(cursor.getColumnIndex(DataBase.KEY_CHINESE));

        bot_text.setText("??????????????????");
    }
}
