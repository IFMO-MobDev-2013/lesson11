package com.blumonk.FlashCards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: blumonk
 * Date: 12/22/13
 * Time: 10:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChooseCategoryActivity extends Activity {

    public final static String NATIVE_CATEGORY_NAME = "com.blumonk.FlashCards.nativecategoryname";
    public final static String STUDIED_CATEGORY_NAME = "com.blumonk.FlashCards.studiedcategoryname";
    public final static String DEFAULT_CATEGORY_NAME = "com.blumonk.FlashCards.defaultcategoryname";

    private Spinner modeSpinner;
    private Button reset;
    private String locale;
    private String study;
    private ListView categoriesList;
    private DbAdapter dbAdapter;
    private HashMap<String, Integer> stats;
    private String[] categories;
    private String[] studiedCategories;
    private int[] results;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        Intent intent = getIntent();
        dbAdapter = new DbAdapter(this);

        locale = intent.getStringExtra(MainActivity.NATIVE_LANG);
        study = intent.getStringExtra(MainActivity.STUDIED_LANG);

        modeSpinner = (Spinner) findViewById(R.id.mode);
        reset = (Button) findViewById(R.id.resetprogress);
        reset.setText(getStringById("reset_" + locale));
        String[] modes = {getStringById("classic_" + locale), getStringById("fourpics_" + locale), getStringById("fourwords_" + locale)};
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, modes);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeAdapter);

        categoriesList = (ListView) findViewById(R.id.categories);
        categories = getStringArrayById("categories_" + locale);
        studiedCategories = getStringArrayById("categories_" + study);
        results = new int[studiedCategories.length];

        final String[] nativeCategories = getStringArrayById("categories_" + locale);
        final String[] studiedCategories = getStringArrayById("categories_" + study);
        final String[] engCategories = getStringArrayById("categories_en");

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (modeSpinner.getSelectedItemPosition() == 0) {
                    Intent goToClassicMode = new Intent(view.getContext(), ClassicModeActivity.class);
                    goToClassicMode.putExtra(NATIVE_CATEGORY_NAME, nativeCategories[i]);
                    goToClassicMode.putExtra(STUDIED_CATEGORY_NAME, studiedCategories[i]);
                    goToClassicMode.putExtra(DEFAULT_CATEGORY_NAME, engCategories[i]);
                    goToClassicMode.putExtra(MainActivity.NATIVE_LANG, locale);
                    startActivity(goToClassicMode);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        stats = dbAdapter.getAllResults();
        for (int i = 0; i < studiedCategories.length; ++i) {
            if (stats.containsKey(studiedCategories[i])) {
                results[i] = stats.get(studiedCategories[i]);
            } else {
                results[i] = 0;
            }
        }
        String[] toOutput = new String[categories.length];
        for (int i = 0; i < categories.length; ++i) {
            toOutput[i] = categories[i] + " (" + results[i] + "/10)";
        }
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, toOutput);
        categoriesList.setAdapter(categoriesAdapter);
    }

    public void resetStatistics(View view) {
        dbAdapter.resetStats();
        onStart();
    }

    public String getStringById(String idName) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(idName, "string", packageName);
        return getResources().getString(resId);
    }

    public String[] getStringArrayById(String idName) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(idName, "array", packageName);
        return getResources().getStringArray(resId);
    }

}
