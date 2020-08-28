package com.debugsire.wsp.WaterSafetyAndClimate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Observation extends AppCompatActivity {

    Context context;
    Methods methods;

    TextInputLayout textInputLayout;
    Button save;

    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);

        context = this;
        methods = new Methods();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");

        initCompos();
        setEvents();
        loadFields();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        methods.setOptionsMenuRemove(menu, dateTime_);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0) {
            if (tableName.equalsIgnoreCase("obsEU")) {
                methods.removeEntry(context, tableName, dateTime_, true);

            } else {
                methods.removeEntry(context, tableName, dateTime_, false);

            }
        }
        return true;
    }


    private void loadFields() {
        methods.configHeaderBar(context, dateTime_, headerWrapper);
        Cursor cursor = methods.getCursorFromDateTime(context, tableName, dateTime_, false);
        while (cursor.moveToNext()) {
            textInputLayout.getEditText().setText(cursor.getString(cursor.getColumnIndex("obs")));
        }


    }

    private void setEvents() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tilFieldsNull = methods.isTILFieldsNull(context, textInputLayout);
                if (!tilFieldsNull) {
                    final AlertDialog dialog = methods.getSaveConfirmationDialog(context, dateTime_ != null);
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });

                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ArrayList<String> strings = methods.getConfiguredStringForInsert(
                                    textInputLayout.getEditText().getText().toString().trim(),
                                    Methods.getSelectedGenId(context)
                            );


                            if (tableName.equalsIgnoreCase("obsEU")) {
                                methods.insertData(context, tableName, dateTime_, strings, true);
                            } else {
                                strings.remove(1);
                                methods.insertData(context, tableName, dateTime_, strings, false);
                            }
                            methods.showToast(getString(R.string.saved), context, MyConstants.MESSAGE_SUCCESS);
                            onBackPressed();

                        }
                    });
                    dialog.show();
                } else {
                    methods.showToast(getString(R.string.compulsory_cant_empty), context, MyConstants.MESSAGE_ERROR);
                }
            }
        });
    }

    private void initCompos() {
        textInputLayout = findViewById(R.id.til_obsActivityObservationZero);
        save = findViewById(R.id.btn_saveActivityObservationZero);
        headerWrapper = findViewById(R.id.rl_wrapperTop);
    }
}
