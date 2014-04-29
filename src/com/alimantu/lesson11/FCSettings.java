package com.alimantu.lesson11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: PWR
 * Date: 29.03.14
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */

public class FCSettings extends Activity {

    public static String usedLang;

    private Spinner first, second;
    private String Title;
    private String ULang;
    private String TLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.fc_settings);

        init();

        first = (Spinner) findViewById(R.id.usedLanguage);
        second = (Spinner) findViewById(R.id.studiedLanguage);

        first.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.lang_list);
//

// Настраиваем адаптер
                ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(FCSettings.this, R.array.lang_list_rus, android.R.layout.simple_spinner_item);
                switch (selectedItemPosition) {
                    case 0:
                        adapter = ArrayAdapter.createFromResource(FCSettings.this, R.array.lang_list_rus, android.R.layout.simple_spinner_item);
                        break;
                    case 1:
                        adapter = ArrayAdapter.createFromResource(FCSettings.this, R.array.lang_list_eng, android.R.layout.simple_spinner_item);
                        break;
                    case 2:
                        adapter = ArrayAdapter.createFromResource(FCSettings.this, R.array.lang_list_de, android.R.layout.simple_spinner_item);
                        break;
                }
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


// Вызываем адаптер
                second.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyActivity.usedLang = first.getSelectedItem().toString();
                MyActivity.usedLangId = first.getSelectedItemPosition();
                if(second.getSelectedItem().toString().equals(getResources().getStringArray(R.array.lang_list_de)[0])
                        || second.getSelectedItem().toString().equals(getResources().getStringArray(R.array.lang_list_eng)[0]))
                {
                    MyActivity.targetLang = getResources().getStringArray(R.array.lang_list)[0];
                    MyActivity.targetLangId = 0;
                }
                else
                {
                    if(second.getSelectedItem().toString().equals(getResources().getStringArray(R.array.lang_list_de)[1])
                            || second.getSelectedItem().toString().equals(getResources().getStringArray(R.array.lang_list_rus)[0]))
                    {
                        MyActivity.targetLang = getResources().getStringArray(R.array.lang_list)[1];
                        MyActivity.targetLangId = 1;
                    }
                        else
                    {
                        MyActivity.targetLang = getResources().getStringArray(R.array.lang_list)[2];
                        MyActivity.targetLangId = 2;
                    }
                }
                Intent intent = new Intent(view.getContext(), MyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init()
    {
        if (usedLang.equals(getResources().getString(R.string.rus))) {

            Title = getResources().getString(R.string.fcs_title_rus);
            ULang = getResources().getString(R.string.fcs_ul_rus);
            TLang = getResources().getString(R.string.fcs_tl_rus);
        } else { if (usedLang.equals(getResources().getString(R.string.eng))) {
            Title = getResources().getString(R.string.fcs_title_eng);
            ULang = getResources().getString(R.string.fcs_ul_eng);
            TLang = getResources().getString(R.string.fcs_tl_eng);
        } else if (usedLang.equals(getResources().getString(R.string.de))) {
            Title = getResources().getString(R.string.fcs_title_de);
            ULang = getResources().getString(R.string.fcs_ul_de);
            TLang = getResources().getString(R.string.fcs_tl_de);
        }        }

        TextView titleText = (TextView) findViewById(R.id.fcs_textView);
        TextView ULText = (TextView) findViewById(R.id.fcs_textView1);
        TextView TLText = (TextView) findViewById(R.id.fcs_textView2);
        titleText.setText(Title);
        ULText.setText(ULang);
        TLText.setText(TLang);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();    //To change body of overridden methods use File | Settings | File Templates.
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
        finish();
    }

}
