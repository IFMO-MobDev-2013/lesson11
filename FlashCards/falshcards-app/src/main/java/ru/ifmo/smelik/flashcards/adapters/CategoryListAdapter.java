package ru.ifmo.smelik.flashcards.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.ifmo.smelik.flashcards.Category;
import ru.ifmo.smelik.flashcards.R;
import ru.ifmo.smelik.flashcards.Utils;
import ru.ifmo.smelik.flashcards.database.DatabaseTable;

/**
 * Created by Nick Smelik on 12.01.14.
 */
public class CategoryListAdapter extends BaseAdapter {

    Context context;
    DatabaseTable table;
    ArrayList<Category> currentState;

    public CategoryListAdapter(Context context, DatabaseTable table) {
        this.context = context;
        this.table = table;
        this.currentState = table.getAll();
    }

    public void refresh() {
        currentState = table.getAll();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return currentState.size();
    }

    @Override
    public Object getItem(int position) {
        return currentState.get(position);
    }

    @Override
    public long getItemId(int position) {
        return currentState.get(position).getId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.category_list_item, parent, false);
        Category category = currentState.get(position);
        TextView categoryName = (TextView) view.findViewById(R.id.categoryName);
        ImageView categoryImage = (ImageView) view.findViewById(R.id.categoryImage);
        ImageView achievment = (ImageView) view.findViewById(R.id.achivement);
        String[] categories = Utils.getLocalizedResources(context, Utils.localeFROM).getStringArray(R.array.categories);
        categoryName.setText(categories[position]);
        categoryImage.setImageDrawable(Utils.loadImageFromAsset(context, category.getWords().get(0)));
        int raiting = category.getStatus();
        if (raiting >= 10 && raiting < 20) {
            achievment.setImageResource(R.drawable.a);
        } else if (raiting >= 20 && raiting < 30) {
            achievment.setImageResource(R.drawable.b);
        } else if (raiting >= 30 && raiting < 40) {
            achievment.setImageResource(R.drawable.c);
        } else if (raiting >= 40 && raiting < 50) {
            achievment.setImageResource(R.drawable.d);
        }
        return view;
    }
}
