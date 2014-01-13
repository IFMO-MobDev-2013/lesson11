package com.example.lesson11;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CategoriesActivity extends ListActivity {
    /**
     * Called when the activity is first created.
     */
    public final Category[] categories = new Category[10];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categories[0] = new Category(0, getResources().getString(R.string.school));
        categories[1] = new Category(1, getResources().getString(R.string.hospital));
        categories[2] = new Category(2, getResources().getString(R.string.cafe));
        categories[3] = new Category(3, getResources().getString(R.string.shopping));
        categories[4] = new Category(4, getResources().getString(R.string.house));
        categories[5] = new Category(5, getResources().getString(R.string.work));
        categories[6] = new Category(7, getResources().getString(R.string.food));
        categories[7] = new Category(6, getResources().getString(R.string.weather));
        categories[8] = new Category(8, getResources().getString(R.string.sports));
        categories[9] = new Category(9, getResources().getString(R.string.street));

        setListAdapter(new ArrayAdapter<Category> (this, R.layout.category, categories));
        //setContentView(R.layout.main);
    }

    @Override
    protected void  onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("id", categories[position].id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.statistics:
                startStatActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startStatActivity() {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }


}
