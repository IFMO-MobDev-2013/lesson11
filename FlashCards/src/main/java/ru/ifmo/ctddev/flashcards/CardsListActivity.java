package ru.ifmo.ctddev.flashcards;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import ru.ifmo.ctddev.flashcards.cards.Category;

public class CardsListActivity extends Activity {

    private DataStore store;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_list_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Category category = (Category) getIntent().getSerializableExtra(Constants.INTENT_CATEGORY);
        setTitle(category.getTitle(Constants.LEARNING_LANGUAGE));

        store = new DataStore(this);

        ListView categories = (ListView) findViewById(R.id.categories_layout);
        adapter = new CardsCursorAdapter(
                this,
                store.getAllCards((category.getId()))
        );
        categories.setAdapter(adapter);
    }
}
