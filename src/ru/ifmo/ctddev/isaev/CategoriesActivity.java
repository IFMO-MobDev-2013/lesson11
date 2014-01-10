package ru.ifmo.ctddev.isaev;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ru.ifmo.ctddev.isaev.FlashCards.R;
import ru.ifmo.ctddev.isaev.orm.Category;

import java.sql.SQLException;
import java.util.List;

public class CategoriesActivity extends MyActivity {
    ListView categories;
    LayoutInflater inflater;
    String[] localizedCategories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String from = null;
        String to = null;
        categories = (ListView) findViewById(R.id.categoryListView);
        List<Category> values = null;
        try {
            values = General.categoryDao.queryForAll();
        } catch (SQLException e) {
            Log.e("oh my god dat ril exception", "", e);
        }
        localizedCategories = getLocalizedResources(this, General.fromLocale).getStringArray(R.array.category);
        final CategoryListAdapter adapter = new CategoryListAdapter(this, android.R.layout.simple_list_item_1, values);
        categories.setAdapter(adapter);
        categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                final Dialog dialog = new Dialog(CategoriesActivity.this);
                dialog.setContentView(R.layout.mode_choose);
                dialog.setTitle(R.string.choose_mode);
                dialog.show();
                Button view1 = (Button) dialog.findViewById(R.id.button);
                Button view2 = (Button) dialog.findViewById(R.id.button2);
                Button view3 = (Button) dialog.findViewById(R.id.button3);
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SingleTrainingActivity.class);
                        intent.putExtra("cat", adapter.getItem(i));
                        startActivity(intent);
                    }
                });
                view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), PictureToWordsModeActivity.class);
                        intent.putExtra("cat", adapter.getItem(i));
                        startActivity(intent);
                    }
                });
                view3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), WordToPicturesModeActivity.class);
                        intent.putExtra("cat", adapter.getItem(i));
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public class CategoryListAdapter extends ArrayAdapter<Category> {

        public CategoryListAdapter(Context context, int resource, List<Category> objects) {
            super(context, resource, objects);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        public int getMark(Category cat) {
            Log.i("cat status:  ", String.valueOf(cat.getStatus()));
            return cat.getStatus() / cat.getWords().size() + 1;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            LinearLayout category = (LinearLayout) inflater.inflate(R.layout.category, null);
            TextView descr = (TextView) category.findViewById(R.id.categoryDescription);
            descr.setText(localizedCategories[pos]);
            ImageView image = (ImageView) category.findViewById(R.id.categoryMark);
            image.setImageResource(getResources().getIdentifier(getResources().getString(R.id.star) + getMark(getItem(pos)), "drawable", getPackageName()));
            return category;
        }
    }
}
