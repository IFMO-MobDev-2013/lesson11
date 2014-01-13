package ru.ifmo.ctddev.flashcards;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import ru.ifmo.ctddev.flashcards.cards.Category;
import ru.ifmo.ctddev.flashcards.cards.Language;

public class CategoriesCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private DataStore store;

    public CategoriesCursorAdapter(Context context, Cursor c) {
        super(context, c);
        mInflater = LayoutInflater.from(context);
        store = new DataStore(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mInflater.inflate(R.layout.categories_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        String eng = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.ENG_COLUMN));
        String rus = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.RUS_COLUMN));
        String fra = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.FRA_COLUMN));
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseOpenHelper.ID_COLUMN));

        EnumMap<Language, String> title = new EnumMap<>(Language.class);
        title.put(Language.ENG, eng);
        title.put(Language.RUS, rus);
        title.put(Language.FRA, fra);

        final Category category = new Category(id, title);

        ((TextView) view.findViewById(R.id.learning_title)).setText(category.getTitle(Constants.LEARNING_LANGUAGE));
        ((TextView) view.findViewById(R.id.native_title)).setText(category.getTitle(Constants.NATIVE_LANGUAGE));

        ImageView icon = (ImageView) view.findViewById(R.id.category_icon);
        AssetManager am = context.getResources().getAssets();

        String iconPath = Constants.IMAGE_ASSETS + "/" + Constants.SMALL_IMAGE_SUBFOLDER + "/" + eng.toLowerCase() + ".jpg";
        try {
            InputStream is = am.open(iconPath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            icon.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int total = store.getCardCount(id);
        int learned = store.getLearnedCardCount(id);
        TextView wordsCount = (TextView) view.findViewById(R.id.words_count);
        wordsCount.setText(learned + "/" + total);

        ((ProgressBar) view.findViewById(R.id.progress_bar)).setProgress(total == 0 ? 100 : learned * 100 / total);
    }
}
