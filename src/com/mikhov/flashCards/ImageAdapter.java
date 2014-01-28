package com.mikhov.flashCards;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ImageAdapter extends SimpleCursorAdapter {
    private int layout;

    public ImageAdapter(Context in_context, int in_layout, Cursor in_cursor, String[] in_from, int[] in_to) {
        super(in_context, in_layout, in_cursor, in_from, in_to);
        layout = in_layout;
    }

    @Override
     public void bindView(View view, Context in_context, Cursor in_cursor) {
        byte[] dbImage = in_cursor.getBlob(in_cursor.getColumnIndex(Database.QUESTIONS_COL_MINI_IMAGE));
        String dbQuestion = in_cursor.getString(in_cursor.getColumnIndex(Database.QUESTIONS_COL_QUESTION));
        String dbAnswer = in_cursor.getString(in_cursor.getColumnIndex(Database.QUESTIONS_COL_ANSWER));

        ImageView image = (ImageView) view.findViewById(R.id.preview_item_image);
        TextView question = (TextView) view.findViewById(R.id.preview_item_question);
        TextView answer = (TextView) view.findViewById(R.id.preview_item_answer);

        question.setText(dbQuestion);
        answer.setText(dbAnswer);
        image.setImageBitmap(scaleMini(BitmapFactory.decodeByteArray(dbImage, 0, dbImage.length)));
    }

    public Bitmap scaleMini(Bitmap b) {
        double nw, nh, scale;
        int bitmapWidth = b.getWidth();
        int bitmapHeight= b.getHeight();
        nh = 70;
        scale = nh / bitmapHeight;
        nw = scale * bitmapWidth;
        b = Bitmap.createScaledBitmap(b, (int) nw, (int) nh, false);
        return b;
    }

    @Override
    public View newView(Context in_context, Cursor in_cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) in_context.getSystemService(in_context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout, parent, false);
        return view;
    }
}