package ru.ifmo.ctddev.isaev;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import com.yandex.metrica.Counter;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: Xottab
 * Date: 06.01.14
 */
public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);
    }

    public Drawable getImageByCategory(int catNumber, int wordNumber) {

        InputStream ims = null;
        try {
            ims = getAssets().open(General.CategoryEnum.values()[catNumber - 1].folderName + "/" + wordNumber + ".jpg");
        } catch (IOException e) {
            Log.e("error", "error", e);
        }
        return Drawable.createFromStream(ims, null);
    }

    @Override
    public void onStart() {

        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        Counter.sharedInstance().onResumeActivity(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Counter.sharedInstance().onPauseActivity(this);
    }
}
