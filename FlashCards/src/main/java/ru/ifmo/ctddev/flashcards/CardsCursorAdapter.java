package ru.ifmo.ctddev.flashcards;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;

import ru.ifmo.ctddev.flashcards.cards.Card;
import ru.ifmo.ctddev.flashcards.cards.Language;


public class CardsCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private DataStore store;

    public CardsCursorAdapter(Context context, Cursor c) {
        super(context, c);
        mInflater = LayoutInflater.from(context);
        store = new DataStore(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mInflater.inflate(R.layout.cards_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String eng = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.ENG_COLUMN));
        String rus = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.RUS_COLUMN));
        String fra = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.FRA_COLUMN));
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.ID_COLUMN));
        int categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.CATEGORY_ID_COLUMN));

        EnumMap<Language, String> title = new EnumMap<>(Language.class);
        title.put(Language.ENG, eng);
        title.put(Language.RUS, rus);
        title.put(Language.FRA, fra);

        final Card card = new Card(id, categoryId, title);

        ((TextView) view.findViewById(R.id.learning_title)).setText(card.getTitle(Constants.LEARNING_LANGUAGE));
        ((TextView) view.findViewById(R.id.native_title)).setText(card.getTitle(Constants.NATIVE_LANGUAGE));

        ImageView icon = (ImageView) view.findViewById(R.id.category_icon);
        AssetManager am = context.getResources().getAssets();

        String iconPath = Constants.IMAGE_ASSETS + "/"
                + Constants.SMALL_IMAGE_SUBFOLDER + "/"
                + eng.toLowerCase() + ".jpg";
        try {
            InputStream is = am.open(iconPath, AssetManager.ACCESS_STREAMING);
            Drawable d = Drawable.createFromStream(is, null);
            icon.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((ProgressBar) view.findViewById(R.id.progress_bar)).setProgress(
                (Constants.MAX_RATING - store.getRating(card.getId())) * 100 / Constants.MAX_RATING
        );
    }
}
