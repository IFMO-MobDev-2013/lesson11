package ru.ifmo.ctddev.isaev.orm;

import android.util.Log;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import ru.ifmo.ctddev.isaev.orm.Word;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * User: Xottab
 * Date: 05.12.13
 */
@DatabaseTable
public class Category implements Serializable {

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField(canBeNull = false)
    private int resID;

    @ForeignCollectionField(eager = true)
    private Collection<Word> wordList;

    @DatabaseField(canBeNull = false)
    private int status;

    public List<Word> getWords() {
        ArrayList<Word> result = new ArrayList<Word>();
        Iterator<Word> it = wordList.iterator();
        while (it.hasNext()) {
            result.add(it.next());
        }
        Log.i("there are ", result.size() + " words");
        return result;

    }

    public Category(int resID) {
        this.resID = resID;
        this.status = 0;
    }

    public Category() {
    }

    public int getStatus() {
        return status;
    }

    public int getResID() {
        return resID;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
