package com.dronov.java.android.FlashCards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.dronov.java.android.FlashCards.databases.ChinaFlashCards;
import com.dronov.java.android.FlashCards.databases.EnglishFlashCards;
import com.dronov.java.android.FlashCards.databases.RussianFlashCards;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private static final int DELETE = 1;
    public static String fromLanguage;
    public static String toLanguage;
    public static String mode;

    private ListView listView;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private RussianFlashCards databaseRussian;
    private EnglishFlashCards databaseEnglish;
    private ChinaFlashCards databaseChina;
    private ArrayList<String> categories;
    private TextView textView;
    private String deleteRanks;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (fromLanguage == null) {
            fromLanguage = getResources().getString(R.string.russian);
            toLanguage = getResources().getString(R.string.english);
            mode = getResources().getString(R.string.select_mode_russian);
            deleteRanks = getResources().getString(R.string.delete_ranks_russian);
        }

        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);

        textView.setText(fromLanguage + " -> " + toLanguage);

        chooseDataBase();

        startManagingCursor(cursor);
        String [] from = new String[] {
                RussianFlashCards.COLUMN_TITLE,
                RussianFlashCards.COLUMN_NUMBER
        };

        int [] to = new int[] {
                R.id.category,
                R.id.rate
        };

        adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final String getCurrent = categories.get(i);
                builder.setTitle(mode);
                View current = LayoutInflater.from(view.getContext()).inflate(R.layout.alert_layout, null);
                builder.setView(current);

                AlertDialog alertDialog = builder.show();

                current.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), OneWordActivity.class);
                        intent.putExtra(getResources().getString(R.string.category), getCurrent);
                        startActivity(intent);
                        finish();
                    }
                });

                current.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), FourPicture.class);
                        intent.putExtra(getResources().getString(R.string.category), getCurrent);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChangeLanguage.class);
                startActivity(intent);
                finish();
            }
        });

        registerForContextMenu(listView);
    }

    private void chooseDataBase() {
        databaseRussian = new RussianFlashCards(this);
        databaseEnglish = new EnglishFlashCards(this);
        databaseChina = new ChinaFlashCards(this);
        databaseRussian.open();
        databaseChina.open();
        databaseEnglish.open();

        if (databaseRussian.isEmpty()) {
            databaseRussian.addDefault();
        }
        if (databaseEnglish.isEmpty()) {
            databaseEnglish.addDefault();
        }
        if (databaseChina.isEmpty()) {
            databaseChina.addDefault();
        }

        if (fromLanguage.equals(getResources().getString(R.string.russian))) {
            mode = getResources().getString(R.string.select_mode_russian);
            cursor = databaseRussian.getAllData();
            categories = databaseRussian.getAllCategories();
            deleteRanks = getResources().getString(R.string.delete_ranks_russian);
        }
        if (fromLanguage.equals(getResources().getString(R.string.english))) {
            mode = getResources().getString(R.string.select_mode_english);
            cursor = databaseEnglish.getAllData();
            deleteRanks = getResources().getString(R.string.delete_ranks_english);
        }
        if (fromLanguage.equals(getResources().getString(R.string.china))) {
            mode = getResources().getString(R.string.select_mode_china);
            cursor = databaseChina.getAllData();
            deleteRanks = getResources().getString(R.string.delete_ranks_china);
        }
        categories = databaseEnglish.getAllCategories();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);    //To change body of overridden methods use File | Settings | File Templates.
        menu.add(0, DELETE, 0, deleteRanks);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (DELETE == item.getItemId()) {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            int id = (int) adapterContextMenuInfo.id;
            --id;


            ArrayList<Word> arrayList = new ParseCategory(this, categories.get(id)).parse();

            addResultToDatabase(arrayList.get(id));
            cursor.requery();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();    //To change body of overridden methods use File | Settings | File Templates.
        finish();
    }

    public String getFromLanguage(Word word) {
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.russian)))
            return word.getRussian();
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.china)))
            return word.getChina();
        if (MainActivity.fromLanguage.equals(getResources().getString(R.string.english)))
            return word.getEnglish();
        return null;
    }

    public String getToLanguage(Word word) {
        if (MainActivity.toLanguage.equals(getResources().getString(R.string.russian)))
            return word.getRussian();
        if (MainActivity.toLanguage.equals(getResources().getString(R.string.china)))
            return word.getChina();
        if (MainActivity.toLanguage.equals(getResources().getString(R.string.english)))
            return word.getEnglish();
        return null;
    }

    private void addResultToDatabase(Word categoryWord) {
        RussianFlashCards database = new RussianFlashCards(this);
        database.open();
        database.update(categoryWord.getRussian(), "0/10");
        database.close();

        EnglishFlashCards database1 = new EnglishFlashCards(this);
        database1.open();
        database1.update(categoryWord.getEnglish(), "0/10");
        database1.close();

        ChinaFlashCards database2 = new ChinaFlashCards(this);
        database2.open();
        database2.update(categoryWord.getChina(), "0/10");
        database2.close();
    }

}
