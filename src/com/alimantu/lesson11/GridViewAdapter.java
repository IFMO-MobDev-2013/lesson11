package com.alimantu.lesson11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 30.03.14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class GridViewAdapter extends ArrayAdapter<Bitmap>{

    private Context context;

    public GridViewAdapter(Context context, int textViewResourceId, List<Bitmap> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = new ImageView(getContext());
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setImageBitmap(getItem(position));
        return view;
    }
}
