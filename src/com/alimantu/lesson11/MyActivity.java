package com.alimantu.lesson11;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
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
import android.util.Log;
import android.view.*;

import com.alimantu.lesson11.LangBase.GermanLang;
import com.alimantu.lesson11.LangBase.EnglishLang;
import com.alimantu.lesson11.LangBase.RussianLang;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class MyActivity extends Activity
{

    private static final int DELETE = 1;
    public static String usedLang;
    public static String targetLang;
    public static int usedLangId;
    public static int targetLangId;
    public static String mode;
    public static String textButton1;
    public static String textButton2;
    public static String textButton3;
    public static String textButton4;

    private ListView listView;
    private Cursor cursor;
    private RussianLang russianLangDb;
    private EnglishLang englishLangDb;
    private GermanLang germanLangDb;
    private ArrayList<String> cat;
    private ActionBar actionBar;
    private String deleteRanks;
    private int titleId;
    private int width;
    private int res;
    private String smallPictPref;
    private ArrayList<String> cat_under;

    final static String marks = "D";

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);
        width = dimension.widthPixels;
        res = roundRes((int)( width * 0.1));
        smallPictPref = Integer.toString(res);
        titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");

        actionBar = (ActionBar) getActionBar();

        firstStart(usedLang);
        actionBarInit(usedLang);
        init();

        listView = (ListView) findViewById(R.id.listView);
        startManagingCursor(cursor);

        NewListAdapter adapter = new NewListAdapter(this, cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final String getCurrent = cat.get(i);
                builder.setTitle(mode);
                View current = LayoutInflater.from(view.getContext()).inflate(R.layout.mode_menu, null);
                builder.setView(current);

                AlertDialog alertDialog = builder.show();

                Button button1 = (Button) current.findViewById(R.id.button1);
                Button button2 = (Button) current.findViewById(R.id.button2);
                Button button3 = (Button) current.findViewById(R.id.button3);
                Button button4 = (Button) current.findViewById(R.id.button4);
                button1.setText(textButton1);
                button2.setText(textButton2);
                button3.setText(textButton3);
                button4.setText(textButton4);
                current.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), FCPicture.class);
                        intent.putExtra(getResources().getString(R.string.cat), getCurrent);
                        startActivity(intent);
                        finish();
                    }
                });

                current.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), FourPictures.class);
                        intent.putExtra(getResources().getString(R.string.cat), getCurrent);
                        startActivity(intent);
                        finish();
                    }
                });

                current.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), FCWord.class);
                        intent.putExtra(getResources().getString(R.string.cat), getCurrent);
                        startActivity(intent);
                        finish();
                    }
                });

                current.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), FourWords.class);
                        intent.putExtra(getResources().getString(R.string.cat), getCurrent);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

        registerForContextMenu(listView);
    }

    private int roundRes(int i) {
        int res = 48;
        if(abs(i - 72) <= abs(i - res) )
            res = 72;
        if(abs(i - 108) <= abs(i - res) )
            res = 108;
        return res;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onDestroy();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.layout.main_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, FCSettings.class);
                FCSettings.usedLang = usedLang;
                startActivity(intent);
                finish();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
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


            ArrayList<DictElem> arrayList = new AllCategoriesParser(this, cat).parse();

            zeroRate(arrayList.get(id));
            cursor.requery();
            NewListAdapter adapter = new NewListAdapter(this, cursor);
            listView.setAdapter(adapter);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void zeroRate(DictElem elem) {
        //To change body of created methods use File | Settings | File Templates.
        RussianLang russianLang = new RussianLang(this);
        EnglishLang englishLang = new EnglishLang(this);
        GermanLang germanLang = new GermanLang(this);
        switch(targetLangId)
        {
            case 0:
                russianLang.open();
                russianLang.update(elem.getRus(), russianLang.startRate);
                russianLang.close();
                break;
            case 1:
                englishLang.open();
                englishLang.update(elem.getEng(), englishLang.startRate);
                englishLang.close();
                break;
            case 2:
                germanLang.open();
                germanLang.update(elem.getRus(), germanLang.startRate);
                germanLang.close();
                break;
        }
    }

    private void init() {
        russianLangDb = new RussianLang(this);
        englishLangDb = new EnglishLang(this);
        germanLangDb = new GermanLang(this);
        russianLangDb.open();
        germanLangDb.open();
        englishLangDb.open();

        if (russianLangDb.isEmpty()) {
            russianLangDb.addDefault();
        }
        if (englishLangDb.isEmpty()) {
            englishLangDb.addDefault();
        }
        if (germanLangDb.isEmpty()) {
            germanLangDb.addDefault();
        }

        if (usedLang.equals(getResources().getString(R.string.rus))) {
            mode = getResources().getString(R.string.rus_title);
            cat_under = russianLangDb.getAllCategories();
            deleteRanks = getResources().getString(R.string.del_level_rus);
            textButton1 = getResources().getString(R.string.butt_rus_1);
            textButton2 = getResources().getString(R.string.butt_rus_2);
            textButton3 = getResources().getString(R.string.butt_rus_3);
            textButton4 = getResources().getString(R.string.butt_rus_4);
        }
        if (usedLang.equals(getResources().getString(R.string.eng))) {
            mode = getResources().getString(R.string.eng_title);
            cat_under = englishLangDb.getAllCategories();
            deleteRanks = getResources().getString(R.string.del_level_eng);
            textButton1 = getResources().getString(R.string.butt_eng_1);
            textButton2 = getResources().getString(R.string.butt_eng_2);
            textButton3 = getResources().getString(R.string.butt_eng_3);
            textButton4 = getResources().getString(R.string.butt_eng_4);
        }
        if (usedLang.equals(getResources().getString(R.string.de))) {
            mode = getResources().getString(R.string.de_title);
            cat_under = germanLangDb.getAllCategories();
            deleteRanks = getResources().getString(R.string.del_level_de);
            textButton1 = getResources().getString(R.string.butt_de_1);
            textButton2 = getResources().getString(R.string.butt_de_2);
            textButton3 = getResources().getString(R.string.butt_de_3);
            textButton4 = getResources().getString(R.string.butt_de_4);
        }

        if(targetLang.equals(getResources().getString(R.string.de)))
            cursor = germanLangDb.getAllData();

        if(targetLang.equals(getResources().getString(R.string.eng)))
            cursor = englishLangDb.getAllData();

        if(targetLang.equals(getResources().getString(R.string.rus)))
            cursor = russianLangDb.getAllData();

        cat = englishLangDb.getAllCategories();

    }

    private void firstStart(String usedLang)
    {

        if (usedLang == null) {
            MyActivity.usedLang = getResources().getString(R.string.rus);
            targetLang = getResources().getString(R.string.eng);
            usedLangId = 0;
            targetLangId = 1;
            mode = getResources().getString(R.string.rus_title);
            deleteRanks = getResources().getString(R.string.del_level_rus);
            textButton1 = getResources().getString(R.string.butt_rus_1);
            textButton2 = getResources().getString(R.string.butt_rus_2);
            textButton3 = getResources().getString(R.string.butt_rus_3);
            textButton4 = getResources().getString(R.string.butt_rus_4);
            actionBar.setTitle(R.string.ab_rus_to_eng);
        }

    }

    void actionBarInit(String usedLang){
        String[][] stringArray = new String[][]{
                getResources().getStringArray(R.array.ab_list_rus),
                getResources().getStringArray(R.array.ab_list_eng),
                getResources().getStringArray(R.array.ab_list_de)
        };

        actionBar.setTitle(stringArray[usedLangId][targetLangId]);
    }

    private class NewListAdapter extends CursorAdapter {

        private String category;
        private ArrayList<DictElem> result;
        private DictElem categoryWord;
        private int count = 0;
        private String name;
        private String link;
        private int id;
        private int position;

        public NewListAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        public NewListAdapter(Context context, Cursor c){
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.adapter, null);
           // return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            ((TextView) view.findViewById(R.id.cat)).setText(cursor.getString(cursor.getColumnIndexOrThrow(RussianLang.COLUMN_TITLE)));

            position = cursor.getPosition();

            category = cat.get(position);
            result = new CategoryParser(MyActivity.this, category).parse();
            categoryWord = result.get(count);
            result.remove(0);

            link = result.get(count).getEng().toLowerCase();

            try {
                name = "s" + smallPictPref + "_" + (URLEncoder.encode(link, "UTF-8").replace("+", "_")).toLowerCase();
                id = getResources().getIdentifier(name, "drawable", getPackageName());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Bitmap current = BitmapFactory.decodeResource(getResources(), id);

            ((ImageView) view.findViewById(R.id.catPict)).setImageBitmap(current);
            ((TextView) view.findViewById(R.id.cat_under)).setText(cat_under.get(position));

            String[] names= getResources().getStringArray(R.array.names);
            int[] colours = getResources().getIntArray(R.array.colours);

            int mark_id = cursor.getInt(cursor.getColumnIndexOrThrow(RussianLang.COLUMN_RES));

            ((TextView) view.findViewById(R.id.rate)).setText(names[mark_id]);
            ((TextView) view.findViewById(R.id.rate)).setTextColor(colours[mark_id]);

        }
    }
}
