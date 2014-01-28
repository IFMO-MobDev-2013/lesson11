package com.mikhov.flashCards.Async;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.mikhov.flashCards.Database;
import com.mikhov.flashCards.R;

public final class Task extends AsyncTask<Void, String, Boolean> {
    
    protected final Resources mResources;
    
    private Boolean mResult;
    private String mProgressMessage;
    private IProgressTracker mProgressTracker;
    Database database;
    Context context;
    Bitmap[] miniDefaultImages;

    public Task(Resources resources, Context in_context) {
        context = in_context;
        mResources = resources;
        mProgressMessage = resources.getString(com.mikhov.flashCards.R.string.task_starting);
    }

    public void setProgressTracker(IProgressTracker progressTracker) {
	mProgressTracker = progressTracker;
        if (mProgressTracker != null) {
            mProgressTracker.onProgress(mProgressMessage);
            if (mResult != null) {
                mProgressTracker.onComplete();
            }
        }
    }

    @Override
    protected void onCancelled() {
	    mProgressTracker = null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        mProgressMessage = values[0];
        if (mProgressTracker != null) {
            mProgressTracker.onProgress(mProgressMessage);
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mResult = result;
        if (mProgressTracker != null) {
            mProgressTracker.onComplete();
        }
        mProgressTracker = null;
    }

    @Override
    protected Boolean doInBackground(Void... arg0) {
        database = new Database(context);
        database.open();
        String added = mResources.getString(com.mikhov.flashCards.R.string.task_working) + ":\n";
        String[] defaultCategories, defaultQuestions, defaultAnswers;
        Bitmap[] defaultImages = new Bitmap[10];
        miniDefaultImages = new Bitmap[10];

        defaultCategories = mResources.getStringArray(R.array.categories);
        defaultQuestions = mResources.getStringArray(R.array.questions);
        defaultAnswers = mResources.getStringArray(R.array.answers);

        for (int i = 0; i < defaultCategories.length; i++) {
            if (!database.uniqCategory(defaultCategories[i]) && defaultCategories[i].indexOf("(Default)") == -1) {
                defaultCategories[i] += " (Default)";
            }
        }

        publishProgress(added);
        if (database.uniqCategory(defaultCategories[0])) {
            database.addCategory(defaultCategories[0], 0, 0);
            defaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.q1);
            defaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.q2);
            defaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.q3);
            defaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.q4);
            defaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.q5);
            defaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.q6);
            defaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.q7);
            defaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.q8);
            defaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.q9);
            defaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.q10);

            miniDefaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.mq1);
            miniDefaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.mq2);
            miniDefaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.mq3);
            miniDefaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.mq4);
            miniDefaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.mq5);
            miniDefaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.mq6);
            miniDefaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.mq7);
            miniDefaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.mq8);
            miniDefaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.mq9);
            miniDefaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.mq10);
            for (int j = 0; j < 10; j++) {
                database.addQuestion(defaultCategories[0], defaultQuestions[j], defaultAnswers[j], defaultImages[j], miniDefaultImages[j], 0);
            }
            added += "\n  \"" + defaultCategories[0] + "\"";
        }
        publishProgress(added); if (isCancelled()) {
            return false;
        }
        if (database.uniqCategory(defaultCategories[1])) {
            database.addCategory(defaultCategories[1], 0, 0);
            defaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.q11);
            defaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.q12);
            defaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.q13);
            defaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.q14);
            defaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.q15);
            defaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.q16);
            defaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.q17);
            defaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.q18);
            defaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.q19);
            defaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.q20);

            miniDefaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.mq11);
            miniDefaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.mq12);
            miniDefaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.mq13);
            miniDefaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.mq14);
            miniDefaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.mq15);
            miniDefaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.mq16);
            miniDefaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.mq17);
            miniDefaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.mq18);
            miniDefaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.mq19);
            miniDefaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.mq20);
            for (int j = 0; j < 10; j++) {
                database.addQuestion(defaultCategories[1], defaultQuestions[j + 10], defaultAnswers[j + 10], defaultImages[j], miniDefaultImages[j], 0);
            }
            added += "\n  \"" + defaultCategories[1] + "\"";
        }
        publishProgress(added);
        publishProgress(added); if (isCancelled()) {
            return false;
        }
        if (database.uniqCategory(defaultCategories[2])) {
            database.addCategory(defaultCategories[2], 0, 0);
            defaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.q21);
            defaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.q22);
            defaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.q23);
            defaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.q24);
            defaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.q25);
            defaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.q26);
            defaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.q27);
            defaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.q28);
            defaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.q29);
            defaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.q30);

            miniDefaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.mq21);
            miniDefaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.mq22);
            miniDefaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.mq23);
            miniDefaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.mq24);
            miniDefaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.mq25);
            miniDefaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.mq26);
            miniDefaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.mq27);
            miniDefaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.mq28);
            miniDefaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.mq29);
            miniDefaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.mq30);
            for (int j = 0; j < 10; j++) {
                database.addQuestion(defaultCategories[2], defaultQuestions[j + 20], defaultAnswers[j + 20], defaultImages[j], miniDefaultImages[j], 0);
            }
            added += "\n  \"" + defaultCategories[2] + "\"";
        }
        publishProgress(added);
        publishProgress(added); if (isCancelled()) {
            return false;
        }
        if (database.uniqCategory(defaultCategories[3])) {
            database.addCategory(defaultCategories[3], 0, 0);
            defaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.q31);
            defaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.q32);
            defaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.q33);
            defaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.q34);
            defaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.q35);
            defaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.q36);
            defaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.q37);
            defaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.q38);
            defaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.q39);
            defaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.q40);

            miniDefaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.mq31);
            miniDefaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.mq32);
            miniDefaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.mq33);
            miniDefaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.mq34);
            miniDefaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.mq35);
            miniDefaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.mq36);
            miniDefaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.mq37);
            miniDefaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.mq38);
            miniDefaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.mq39);
            miniDefaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.mq40);
            for (int j = 0; j < 10; j++) {
                database.addQuestion(defaultCategories[3], defaultQuestions[j + 30], defaultAnswers[j + 30], defaultImages[j], miniDefaultImages[j], 0);
            }
            added += "\n  \"" + defaultCategories[3] + "\"";
        }
        publishProgress(added);
        publishProgress(added); if (isCancelled()) {
            return false;
        }
        if (database.uniqCategory(defaultCategories[4])) {
            database.addCategory(defaultCategories[4], 0, 0);
            defaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.q41);
            defaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.q42);
            defaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.q43);
            defaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.q44);
            defaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.q45);
            defaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.q46);
            defaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.q47);
            defaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.q48);
            defaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.q49);
            defaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.q50);

            miniDefaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.mq41);
            miniDefaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.mq42);
            miniDefaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.mq43);
            miniDefaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.mq44);
            miniDefaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.mq45);
            miniDefaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.mq46);
            miniDefaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.mq47);
            miniDefaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.mq48);
            miniDefaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.mq49);
            miniDefaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.mq50);
            for (int j = 0; j < 10; j++) {
                database.addQuestion(defaultCategories[4], defaultQuestions[j + 40], defaultAnswers[j + 40], defaultImages[j], miniDefaultImages[j], 0);
            }
            added += "\n  \"" + defaultCategories[4] + "\"";
        }
        publishProgress(added);
        publishProgress(added); if (isCancelled()) {
            return false;
        }
        if (database.uniqCategory(defaultCategories[5])) {
            database.addCategory(defaultCategories[5], 0, 0);
            defaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.q51);
            defaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.q52);
            defaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.q53);
            defaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.q54);
            defaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.q55);
            defaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.q56);
            defaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.q57);
            defaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.q58);
            defaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.q59);
            defaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.q60);

            miniDefaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.mq51);
            miniDefaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.mq52);
            miniDefaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.mq53);
            miniDefaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.mq54);
            miniDefaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.mq55);
            miniDefaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.mq56);
            miniDefaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.mq57);
            miniDefaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.mq58);
            miniDefaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.mq59);
            miniDefaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.mq60);
            for (int j = 0; j < 10; j++) {
                database.addQuestion(defaultCategories[5], defaultQuestions[j + 50], defaultAnswers[j + 50], defaultImages[j], miniDefaultImages[j], 0);
            }
            added += "\n  \"" + defaultCategories[5] + "\"";
        }
        publishProgress(added);
        publishProgress(added); if (isCancelled()) {
            return false;
        }
        if (database.uniqCategory(defaultCategories[6])) {
            database.addCategory(defaultCategories[6], 0, 0);
            defaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.q61);
            defaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.q62);
            defaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.q63);
            defaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.q64);
            defaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.q65);
            defaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.q66);
            defaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.q67);
            defaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.q68);
            defaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.q69);
            defaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.q70);

            miniDefaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.mq61);
            miniDefaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.mq62);
            miniDefaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.mq63);
            miniDefaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.mq64);
            miniDefaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.mq65);
            miniDefaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.mq66);
            miniDefaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.mq67);
            miniDefaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.mq68);
            miniDefaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.mq69);
            miniDefaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.mq70);
            for (int j = 0; j < 10; j++) {
                database.addQuestion(defaultCategories[6], defaultQuestions[j + 60], defaultAnswers[j + 60], defaultImages[j], miniDefaultImages[j], 0);
            }
            added += "\n  \"" + defaultCategories[6] + "\"";
        }
        publishProgress(added);
        publishProgress(added); if (isCancelled()) {
            return false;
        }
        if (database.uniqCategory(defaultCategories[7])) {
            database.addCategory(defaultCategories[7], 0, 0);
            defaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.q71);
            defaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.q72);
            defaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.q73);
            defaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.q74);
            defaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.q75);
            defaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.q76);
            defaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.q77);
            defaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.q78);
            defaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.q79);
            defaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.q80);

            miniDefaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.mq71);
            miniDefaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.mq72);
            miniDefaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.mq73);
            miniDefaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.mq74);
            miniDefaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.mq75);
            miniDefaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.mq76);
            miniDefaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.mq77);
            miniDefaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.mq78);
            miniDefaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.mq79);
            miniDefaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.mq80);
            for (int j = 0; j < 10; j++) {
                database.addQuestion(defaultCategories[7], defaultQuestions[j + 70], defaultAnswers[j + 70], defaultImages[j], miniDefaultImages[j], 0);
            }
            added += "\n  \"" + defaultCategories[7] + "\"";
        }
        publishProgress(added);
        publishProgress(added); if (isCancelled()) {
            return false;
        }
        if (database.uniqCategory(defaultCategories[8])) {
            database.addCategory(defaultCategories[8], 0, 0);
            defaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.q81);
            defaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.q82);
            defaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.q83);
            defaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.q84);
            defaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.q85);
            defaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.q86);
            defaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.q87);
            defaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.q88);
            defaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.q89);
            defaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.q90);

            miniDefaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.mq81);
            miniDefaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.mq82);
            miniDefaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.mq83);
            miniDefaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.mq84);
            miniDefaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.mq85);
            miniDefaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.mq86);
            miniDefaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.mq87);
            miniDefaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.mq88);
            miniDefaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.mq89);
            miniDefaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.mq90);
            for (int j = 0; j < 10; j++) {
                database.addQuestion(defaultCategories[8], defaultQuestions[j + 80], defaultAnswers[j + 80], defaultImages[j], miniDefaultImages[j], 0);
            }
            added += "\n  \"" + defaultCategories[8] + "\"";
        }
        publishProgress(added);
        publishProgress(added); if (isCancelled()) {
            return false;
        }
        if (database.uniqCategory(defaultCategories[9])) {
            database.addCategory(defaultCategories[9], 0, 0);
            defaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.q91);
            defaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.q92);
            defaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.q93);
            defaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.q94);
            defaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.q95);
            defaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.q96);
            defaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.q97);
            defaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.q98);
            defaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.q99);
            defaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.q100);

            miniDefaultImages[0] = BitmapFactory.decodeResource(mResources, R.drawable.mq91);
            miniDefaultImages[1] = BitmapFactory.decodeResource(mResources, R.drawable.mq92);
            miniDefaultImages[2] = BitmapFactory.decodeResource(mResources, R.drawable.mq93);
            miniDefaultImages[3] = BitmapFactory.decodeResource(mResources, R.drawable.mq94);
            miniDefaultImages[4] = BitmapFactory.decodeResource(mResources, R.drawable.mq95);
            miniDefaultImages[5] = BitmapFactory.decodeResource(mResources, R.drawable.mq96);
            miniDefaultImages[6] = BitmapFactory.decodeResource(mResources, R.drawable.mq97);
            miniDefaultImages[7] = BitmapFactory.decodeResource(mResources, R.drawable.mq98);
            miniDefaultImages[8] = BitmapFactory.decodeResource(mResources, R.drawable.mq99);
            miniDefaultImages[9] = BitmapFactory.decodeResource(mResources, R.drawable.mq100);
            for (int j = 0; j < 10; j++) {
                database.addQuestion(defaultCategories[9], defaultQuestions[j + 90], defaultAnswers[j + 90], defaultImages[j], miniDefaultImages[j], 0);
            }
            added += "\n  \"" + defaultCategories[9] + "\"";
        }
        publishProgress(added);
        return true;
    }
}