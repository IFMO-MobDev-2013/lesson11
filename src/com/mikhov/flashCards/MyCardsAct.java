package com.mikhov.flashCards;

import com.mikhov.flashCards.Async.AsyncTaskManager;
import com.mikhov.flashCards.Async.OnTaskCompleteListener;
import com.mikhov.flashCards.Async.Task;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MyCardsAct extends ListActivity implements View.OnClickListener, OnTaskCompleteListener {

    public static enum TransitionType {
        SlideLeft
    }
    public static TransitionType transitionType;
    private AsyncTaskManager mAsyncTaskManager;

    AlertDialog.Builder deletes, modes;

    private static final int EDIT_ID = Menu.FIRST + 1;
    private static final int DELETE_ID = Menu.FIRST + 2;

    Database database;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursorCards;
    TextView tv;
    ImageButton btnAbout, btnAddCards, btnAddAll;
    Intent intent;
    String category, del_category, mode = "";
    long del_category_id, show_category_id;
    AdapterView.AdapterContextMenuInfo adapterContextMenuInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cards);
        mAsyncTaskManager = new AsyncTaskManager(this, this);
        mAsyncTaskManager.handleRetainedTask(getLastNonConfigurationInstance());
        this.getListView().setDividerHeight(1);
        database = new Database(this);
        database.open();

        initDialogs();

        btnAddAll = (ImageButton) findViewById(R.id.btn_add_all);
        btnAddCards = (ImageButton) findViewById(R.id.btn_add_cards);
        btnAbout = (ImageButton) findViewById(R.id.btn_about);
        btnAddAll.setOnClickListener(this);
        btnAddCards.setOnClickListener(this);
        btnAbout.setOnClickListener(this);

        fillData();
    }

    private void fillData() {
        cursorCards = database.getAllCardsData();
        startManagingCursor(cursorCards);
        String[] from = new String[] { Database.CARDS_COL_CATEGORY };
        int[] to = new int[] { R.id.my_cards_item_cathegory };
        simpleCursorAdapter = new CursorAdapter(this, R.layout.my_cards_item, cursorCards, from, to);
        setListAdapter(simpleCursorAdapter);
        registerForContextMenu(getListView());
        tv = (TextView) findViewById(R.id.my_cards_empty);
        if (database.cardsIsNotEmpty()) {
            tv.setText("");
        } else {
            tv.setText(R.string.empty);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        show_category_id = id;
        modes.show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case EDIT_ID:
                adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                category = database.getCategory(adapterContextMenuInfo.id);
                this.finish();
                intent = new Intent(this, AddCardAct.class);
                intent.putExtra("category", category);
                intent.putExtra("oldcategory", category);
                intent.putExtra("task", "edit");
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                break;
            case DELETE_ID:
                adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                del_category_id = adapterContextMenuInfo.id;
                del_category = database.getCategory(del_category_id);
                deletes.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, EDIT_ID, 0, R.string.edit);
        menu.add(0, DELETE_ID, 0, R.string.delete);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_cards:
                this.finish();
                intent = new Intent(this, AddCardAct.class);
                intent.putExtra("task", "add");
                intent.putExtra("category", "");
                intent.putExtra("oldcategory", "");
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
                break;
            case R.id.btn_about:
                Toast.makeText(this, getResources().getString(R.string.by), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_add_all:
                mAsyncTaskManager.setupTask(new Task(getResources(), this));
                fillData();
                break;
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return mAsyncTaskManager.retainTask();
    }

    @Override
    public void onTaskComplete(Task task) {
        if (task.isCancelled()) {
            Toast.makeText(this, R.string.task_cancelled, Toast.LENGTH_LONG).show();
        } else {
            try {
                Toast.makeText(this, getString(R.string.task_completed), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fillData();
    }

    public void initDialogs() {
        deletes = new AlertDialog.Builder(this);
        deletes.setTitle(getResources().getString(R.string.deleting));
        deletes.setMessage(getResources().getString(R.string.confirm_deleting));
        deletes.setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                database.deleteCategory(del_category_id);
                database.deleteQuestionsWithCategory(del_category);
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

        modes = new AlertDialog.Builder(this);
        modes.setTitle(getResources().getString(R.string.mode));
        modes.setMessage(getResources().getString(R.string.mode_question));
        modes.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                mode = "yes";
                Intent intent = new Intent(MyCardsAct.this, TestAct.class);
                intent.putExtra("category_id", show_category_id);
                intent.putExtra("mode", mode);
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
            }
        });
        modes.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                mode = "no";
                Intent intent = new Intent(MyCardsAct.this, TestAct.class);
                intent.putExtra("category_id", show_category_id);
                intent.putExtra("mode", mode);
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.layout.slide_left_in, R.layout.slide_left_out);
            }
        });
        modes.setCancelable(true);
        modes.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
