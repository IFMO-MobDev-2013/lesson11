package ru.sbrains.shalamov.FlashCards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 19.12.13
 * Time: 16:39
 * To change this template use File | Settings | File Templates.
 */
public class WordsMaster implements DbConstants {
    private DbAdapter mDb;
    private Queue<Word> wishQueue;
    private static final String TAG = "WORDSMASTER: ";
    private static final int INIT_WORDS_COUNT = 5;
    private static final int WORK_WORDS_COUNT = 10;
    int curCategory_id;

    /**
     * keeps the capacity of wishQueue correct;
     */
    private void invariant(int k) {
        //if (wishQueue.size() < k) {
        // запустить асинхронную операцию и заполнить очередь желаний
        // пока заглушка
        mDb.open();
        Cursor cursor = mDb.fetchWords(curCategory_id);
        if (cursor == null) return;
        if (cursor.getCount() == 0) {
            Log.w(TAG, "no records from DB fetched!!");
        }

        Word[] wa = new Word[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            Word w = new Word(
                    cursor.getString(cursor.getColumnIndex(KEY_EN)),
                    cursor.getString(cursor.getColumnIndex(KEY_RUS)),
                    cursor.getInt(cursor.getColumnIndex(KEY_IMG))
            );

            wa[i] = w;
            ++i;
        }

        Word w = null;
        int pos = 0;

        for (int j = 0; j < wa.length; ++j) {
            w = wa[j];
            pos = (int) (Math.random() * wa.length);
            wa[j] = wa[pos];
            wa[pos] = w;

        }
        for (int j = 0; j < wa.length; ++j) {
            wishQueue.add(wa[j]);
        }

        cursor.close();
        mDb.close();
        //}
    }

    public void makeQueue()
    {
        if(curCategory_id == 0)
            curCategory_id = 1;
        invariant(WORK_WORDS_COUNT);
    }

    public void checkCategory(String c) {
        mDb.open();
        if (curCategory_id != mDb.getCategId(c)) {
            curCategory_id = mDb.getCategId(c);
            wishQueue.clear();
            invariant(WORK_WORDS_COUNT);
        }
        mDb.close();
    }

    public WordsMaster(Context context, String categoryName) {
        mDb = new DbAdapter(context);

        mDb.open();

        curCategory_id = mDb.getCategId(categoryName);
        wishQueue = new ArrayDeque<Word>();
        mDb.close();
    }

    public Word getOne(String c) {
        checkCategory(c);

        if (wishQueue.size() == 0) {
            invariant(WORK_WORDS_COUNT);
        }
        return wishQueue.poll();
    }

    // for 4game. currently not necessary
    public Word[] getFour(int category_id) {
        if (wishQueue.size() < 4) {
            mDb.open();
            Cursor cursor = mDb.fetchWords(category_id);
            if (cursor.getCount() < 4) {
                Log.w(TAG, "not enough records from DB fetched!!");
                return null;
            }
            cursor.moveToFirst();

            Word[] wa = new Word[4];
            for (int i = 0; i < 4; ++i) {
                Word w = new Word(
                        cursor.getString(cursor.getColumnIndex(KEY_EN)),
                        cursor.getString(cursor.getColumnIndex(KEY_RUS)),
                        cursor.getInt(cursor.getColumnIndex(KEY_IMG))
                );
                wa[i] = w;
            }

            cursor.close();
            mDb.close();
            return wa;
        } else {
            invariant(WORK_WORDS_COUNT);
            Word[] wa = new Word[4];
            for (int i = 0; i < 4; ++i) {
                wa[i] = wishQueue.poll();
            }
            return wa;
        }
    }

    public void save(String en, boolean state) {
        mDb.open();
        Cursor c = null;

        int word_id = mDb.getWordId(en);
        c = mDb.fetchStat(word_id);
        c.moveToFirst();
        ContentValues values = new ContentValues();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        values.put(KEY_LAST, sdf.format(date));

        values.put(KEY_ROWID, c.getInt(c.getColumnIndex(KEY_ROWID)));
        values.put(KEY_SHOWN, c.getInt(c.getColumnIndex(KEY_SHOWN)) + 1);

        if (state)  // answer is correct
        {
            values.put(KEY_CORRECT, c.getString(c.getColumnIndex(KEY_CORRECT)) + 1);
        } else {
            values.put(KEY_CORRECT, c.getString(c.getColumnIndex(KEY_CORRECT)));
        }
        mDb.updateStat(values);

        try {
            c.close();
            mDb.close();
        } catch (Exception e) {
        }
    }


    // for 4game. currently not necessary
    public void save(String[] aen, boolean[] state) {
        mDb.open();
        Cursor c = null;
        for (int i = 0; i < aen.length; ++i) {
            int word_id = mDb.getWordId(aen[i]);
            c = mDb.fetchStat(word_id);
            c.moveToFirst();
            ContentValues values = new ContentValues();

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            values.put(KEY_LAST, sdf.format(date));

            values.put(KEY_ROWID, c.getInt(c.getColumnIndex(KEY_ROWID)));
            values.put(KEY_SHOWN, c.getInt(c.getColumnIndex(KEY_SHOWN)) + 1);

            if (state[i])  // answer is correct
            {
                values.put(KEY_CORRECT, c.getString(c.getColumnIndex(KEY_CORRECT)) + 1);
            } else {
                values.put(KEY_CORRECT, c.getString(c.getColumnIndex(KEY_CORRECT)));
            }
            mDb.updateStat(values);
        }
        try {
            c.close();
            mDb.close();
        } catch (Exception e) {
        }
    }

    public void addWords(String[] en, String[] ru, int[] img, String categoryName) {
        mDb.open();
        ContentValues cvC = new ContentValues();
        cvC.put(KEY_CATEG, categoryName);
        cvC.put(KEY_SIZE, en.length);
        long category_id = mDb.addCategory(cvC);
        long word_id = -1;
        ContentValues cvW = new ContentValues();
        ContentValues cvS = new ContentValues();

        for (int i = 0; i < en.length; ++i) {

            cvW.put(KEY_EN, en[i]);
            cvW.put(KEY_RUS, ru[i]);
            cvW.put(KEY_IMG, img[i]);
            cvW.put(KEY_CATID, category_id);

            word_id = mDb.addWord(cvW);

            cvS.put(KEY_SHOWN, 0);
            cvS.put(KEY_CORRECT, 0);
            cvS.put(KEY_LAST, "2013-12-12");
            cvS.put(KEY_WORDID, word_id);

            mDb.addStat(cvS);
        }
        mDb.close();
    }

    public void dropAll()
    {
        mDb.open();
        mDb.dropCategiries();
        mDb.dropStats();
        mDb.dropWORDS();
        mDb.close();
    }

    public void dropStat()
    {

//        mDb.open();
//
//        Cursor c =   mDb.fetchStat();
//
//        ContentValues values = new ContentValues();
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        while(c.moveToFirst())
//        {
//            values.put(KEY_LAST, sdf.format(date));
//            values.put(KEY_ROWID, c.getInt(c.getColumnIndex(KEY_ROWID)));
//            values.put(KEY_SHOWN, 0);
//            values.put(KEY_CORRECT, 0);
//            mDb.updateStat(values);
//        }
//
//        mDb.close();
    }
}
