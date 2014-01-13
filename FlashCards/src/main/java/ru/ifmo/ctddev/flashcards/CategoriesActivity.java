package ru.ifmo.ctddev.flashcards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.analytics.tracking.android.EasyTracker;

import java.util.EnumMap;

import ru.ifmo.ctddev.flashcards.cards.Category;
import ru.ifmo.ctddev.flashcards.cards.Language;

public class CategoriesActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private CursorAdapter adapter;
    private DataStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_activity);

        getActionBar().setTitle(R.string.categories);

        loadLearningLanguage();

        store = new DataStore(this);

        ListView categories = (ListView) findViewById(R.id.categories_layout);
        registerForContextMenu(categories);
        adapter = new CategoriesCursorAdapter(this, store.getAllCategories());
        categories.setAdapter(adapter);

        categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapter.getItem(i);

                String eng = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.ENG_COLUMN));
                String rus = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.RUS_COLUMN));
                String fra = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.FRA_COLUMN));
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.ID_COLUMN));

                EnumMap<Language, String> title = new EnumMap<>(Language.class);
                title.put(Language.ENG, eng);
                title.put(Language.RUS, rus);
                title.put(Language.FRA, fra);

                final Category category = new Category(id, title);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.mode);
                View v = LayoutInflater.from(view.getContext()).inflate(R.layout.mode_choose_layout, null);
                builder.setView(v);
                final AlertDialog dialog = builder.show();

                v.findViewById(R.id.mode_word).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), WordOneCardActivity.class);
                        intent.putExtra(Constants.INTENT_CATEGORY, category);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

                v.findViewById(R.id.mode_translation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), TranslationOneCardActivity.class);
                        intent.putExtra(Constants.INTENT_CATEGORY, category);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

                v.findViewById(R.id.mode_word_to_translation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), WordToTranslationActivity.class);
                        intent.putExtra(Constants.INTENT_CATEGORY, category);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

                v.findViewById(R.id.mode_translation_to_word).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), TranslationToWordActivity.class);
                        intent.putExtra(Constants.INTENT_CATEGORY, category);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.changeCursor(new DataStore(this).getAllCategories());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_categories_menu, menu);
        MenuItem item = menu.findItem(R.id.languages_spinner);
        Spinner spinner = (Spinner) item.getActionView();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                R.layout.actionbar_spinner_item,
                createLanguageValues()
        );
        spinnerAdapter.setDropDownViewResource(R.layout.actionbar_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(Constants.LEARNING_LANGUAGE.ordinal());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.category_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.clear_rating:
                clearRating((int) info.id);
                return true;
            case R.id.view_words_list:
                goToCardsList((int) info.id);
                return true;
        }

        return super.onContextItemSelected(item);
    }

    private void loadLearningLanguage() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int langOrdinal = preferences.getInt(
                getString(R.string.pref_learning_language),
                Constants.LEARNING_LANGUAGE.ordinal()
        );

        Constants.LEARNING_LANGUAGE = Language.values()[langOrdinal];
    }

    private void goToCardsList(int categoryId) {
        Category category = store.getCategory(categoryId);
        Intent intent = new Intent(this, CardsListActivity.class);
        intent.putExtra(Constants.INTENT_CATEGORY, category);
        startActivity(intent);
    }

    private void clearRating(int categoryId) {
        store.clearRating(categoryId);
        adapter.changeCursor(store.getAllCategories());
    }

    private String[] createLanguageValues() {
        String[] res = new String[Language.values().length];

        for (int i = 0; i < Language.values().length; i++) {
            switch (Language.values()[i]) {
                case ENG:
                    res[i] = getString(R.string.eng);
                    break;
                case FRA:
                    res[i] = getString(R.string.fra);
                    break;
                case RUS:
                    res[i] = getString(R.string.rus);
                    break;
            }
        }

        return res;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Constants.LEARNING_LANGUAGE = Language.values()[i];
        adapter.notifyDataSetInvalidated();

        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.pref_learning_language), Constants.LEARNING_LANGUAGE.ordinal());
        editor.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
