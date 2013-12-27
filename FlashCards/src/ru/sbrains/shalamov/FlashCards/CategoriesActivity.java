package ru.sbrains.shalamov.FlashCards;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 19.12.13
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
public class CategoriesActivity extends Activity {

    SimpleCursorAdapter sca = null;

    public static final String KEY_ANS = "newCat";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_list);

        ListView lv = (ListView) findViewById(R.id.listViewCathegories);

        DbAdapter mDb = new DbAdapter(this);
        mDb.open();
        Cursor cursor = mDb.fetchCategories();



        String[] from = new String[]{DbConstants.KEY_CATEG, DbConstants.KEY_SIZE};
        int[] to = new int[]{R.id.textViewCatName, R.id.textViewCatSize};

        sca = new SimpleCursorAdapter(this, R.layout.list_item, cursor, from, to);
        lv.setAdapter(sca);
        //cursor.close();
        mDb.close();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = (Cursor) sca.getItem(i);
                String s = c.getString(c.getColumnIndex(DbAdapter.KEY_CATEG));
                Intent intent = getIntent();
                intent.putExtra(KEY_ANS, s);
                setResult(1, intent);
                finish();
            }
        });
    }
}
