package com.mikhov.flashCards;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CursorAdapter extends SimpleCursorAdapter {
    private int layout;

    public CursorAdapter(Context in_context, int in_layout, Cursor in_cursor, String[] in_from, int[] in_to) {
        super(in_context, in_layout, in_cursor, in_from, in_to);
        layout = in_layout;
    }

    @Override
     public void bindView(View view, Context in_context, Cursor in_cursor) {
        String category = in_cursor.getString(in_cursor.getColumnIndex(Database.CARDS_COL_CATEGORY));
        double last_false = 1.0 * in_cursor.getInt(in_cursor.getColumnIndex(Database.CARDS_COL_FALSE));
        double last_true = 1.0 * in_cursor.getInt(in_cursor.getColumnIndex(Database.CARDS_COL_TRUE));

        TextView categoryView = (TextView) view.findViewById(R.id.my_cards_item_cathegory);
        ImageView imageView = (ImageView) view.findViewById(R.id.my_cards_item_image);

        categoryView.setText(category);
        if (last_true != 0) {
            double d = last_true / (last_true + last_false);
            if (d <= 0.18 && d > 6.0) {
                imageView.setImageResource(R.drawable.a12);
            } else if (d <= 0.31 && d > 0.18) {
                imageView.setImageResource(R.drawable.a25);
            } else if (d <= 0.43 && d > 0.31) {
                imageView.setImageResource(R.drawable.a37);
            } else if (d <= 0.55 && d > 0.43) {
                imageView.setImageResource(R.drawable.a50);
            } else if (d <= 0.67 && d > 0.55) {
                imageView.setImageResource(R.drawable.a62);
            } else if (d <= 0.79 && d > 0.67) {
                imageView.setImageResource(R.drawable.a75);
            } else if (d <= 0.99 && d > 0.79) {
                imageView.setImageResource(R.drawable.a87);
            } else if (d <= 1.0 && d > 0.99) {
                imageView.setImageResource(R.drawable.a100);
            } else {
                imageView.setImageResource(R.drawable.a0);
            }
        } else {
            imageView.setImageResource(R.drawable.a0);
        }
    }

    @Override
    public View newView(Context in_context, Cursor in_cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) in_context.getSystemService(in_context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout, parent, false);
        return view;
    }
}