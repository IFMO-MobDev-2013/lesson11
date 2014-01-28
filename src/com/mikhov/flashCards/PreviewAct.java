package com.mikhov.flashCards;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class PreviewAct extends ListActivity implements View.OnClickListener {

    public static enum TransitionType {
        SlideLeft
    }
    public static TransitionType transitionType;
    AlertDialog.Builder deletes;

    private static final int DELETE_ID = Menu.FIRST + 1;

    Database database;
    ImageAdapter simpleCursorAdapter;
    Cursor cursorQuestions;
    Intent intent;
    String category = "", task = "", oldCategory = "", del_question;
    TextView previewCategory;
    Button btnAdd, btnSave;
    AdapterView.AdapterContextMenuInfo adapterContextMenuInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        this.getListView().setDividerHeight(1);


        deletes = new AlertDialog.Builder(this);
        deletes.setTitle(getResources().getString(R.string.deleting));
        deletes.setMessage(getResources().getString(R.string.confirm_deleting));
        deletes.setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                if (database.getQuestionState(del_question, category) != 1) {
                    database.updateQuestion(del_question, category, 3);
                } else {
                    database.deleteQuestion(del_question, category);
                }
                fillData();
            }
        });
        deletes.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            }
        });
        deletes.setCancelable(true);
        deletes.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            }
        });

        previewCategory = (TextView) findViewById(R.id.preview_category);
        btnAdd = (Button)findViewById(R.id.btn_add_question);
        btnSave = (Button)findViewById(R.id.btn_save);
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);


        database = new Database(this);
        database.open();
        Bundle extras = getIntent().getExtras();
        category = extras.getSerializable("category").toString();
        task = extras.getSerializable("task").toString();
        if (task.equals("edit")) {
            oldCategory = extras.getSerializable("oldcategory").toString();
            database.updateQuestions(oldCategory, category);
            database.updateCategory(oldCategory, category);
        }
        fillData();
    }

    private void fillData() {
        cursorQuestions = database.getUndeletedQuestionsData(category);
        previewCategory.setText(getResources().getString(R.string.category_info) + " " + category);
        startManagingCursor(cursorQuestions);
        String[] from = new String[] { Database.QUESTIONS_COL_QUESTION, Database.QUESTIONS_COL_ANSWER, Database.QUESTIONS_COL_IMAGE };
        int[] to = new int[] { R.id.preview_item_question, R.id.preview_item_answer, R.id.preview_item_image };
        simpleCursorAdapter =  new ImageAdapter(this, R.layout.preview_item, cursorQuestions, from, to);
        setListAdapter(simpleCursorAdapter);
        registerForContextMenu(getListView());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        /*super.onListItemClick(l, v, position, id);
        adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        category = database.getCategory(adapterContextMenuInfo.id);
        this.finish();
        intent = new Intent(this, AddCardAct.class);
        intent.putExtra("category", category);
        intent.putExtra("task", "edit");
        intent.putExtra("question", "edit");
        intent.putExtra("global", task);
        startActivity(intent);
        transitionType = TransitionType.SlideLeft;
        overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out); */
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                del_question = database.getQuestion(adapterContextMenuInfo.id, category);
                deletes.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.delete);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_question:
                this.finish();
                intent = new Intent(this, AddQuestionAct.class);
                intent.putExtra("category", category);
                intent.putExtra("oldcategory", oldCategory);
                intent.putExtra("task", "add");
                intent.putExtra("global", task);
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                break;
            case R.id.btn_save:
                database.applyAllChanges();
                if (task.equals("add")) {
                    database.addCategory(category, 0, 0);
                    this.finish();
                    intent = new Intent(this, MyCardsAct.class);
                    startActivity(intent);
                    transitionType = TransitionType.SlideLeft;
                    overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                    break;
                } else if (task.equals("edit")) {
                    database.updateCategory(oldCategory, category);
                    this.finish();
                    intent = new Intent(this, MyCardsAct.class);
                    startActivity(intent);
                    transitionType = TransitionType.SlideLeft;
                    overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                    break;
                }
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(this, AddCardAct.class);
        intent.putExtra("oldcategory", oldCategory);
        intent.putExtra("category", category);
        intent.putExtra("task", task);
        startActivity(intent);
        overridePendingTransition(R.layout.slide_right_in, R.layout.slide_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
