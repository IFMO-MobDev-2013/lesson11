package com.blumonk.FlashCards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.lang.reflect.Field;

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
    private String locale;
    private String study;
    private ListView categoriesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        Intent intent = getIntent();

        locale = intent.getStringExtra(MainActivity.NATIVE_LANG);
        study = intent.getStringExtra(MainActivity.STUDIED_LANG);

        modeSpinner = (Spinner) findViewById(R.id.mode);
        String[] modes = {getStringById("classic_" + locale), getStringById("fourpics_" + locale), getStringById("fourwords_" + locale)};
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, modes);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeAdapter);

        categoriesList = (ListView) findViewById(R.id.categories);
        final String[] categories = getStringArrayById("categories_" + locale);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categoriesList.setAdapter(categoriesAdapter);

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
                    startActivity(goToClassicMode);
                }
            }
        });
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
