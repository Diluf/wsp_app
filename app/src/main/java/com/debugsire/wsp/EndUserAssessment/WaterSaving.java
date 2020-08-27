package com.debugsire.wsp.EndUserAssessment;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

import java.util.ArrayList;

public class WaterSaving extends AppCompatActivity {

    Context context;
    Integer[] valuesDoMem, valuesWhatAre;
    Methods methods;

    Spinner doMem;
    LinearLayout whatAre;
    Button doMemR, whatAreR, save;
    LinearLayout optionalDoMem;

    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_saving);

        context = this;
        methods = new Methods();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");

        initCompos();
        setSpinnerValues(MyConstants.ALL);
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
            methods.removeEntry(context, tableName, dateTime_, true);
        }
        return true;
    }


    private void loadFields() {
        methods.configHeaderBar(context, dateTime_, headerWrapper);
        Cursor cursor = methods.getCursorFromDateTime(context, tableName, dateTime_, true);
        while (cursor.moveToNext()) {
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("doM")), valuesDoMem, doMem);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("ifYes")), valuesWhatAre, whatAre);
        }


    }

    private void setEvents() {
        doMemR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_SAVING_DO_MEM);
            }
        });

        whatAreR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_SAVING_ON_YES);
            }
        });


        doMem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesDoMem[i] == 1) {
                    optionalDoMem.setVisibility(View.VISIBLE);
                } else {
                    optionalDoMem.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, doMem);
                if (!spinnerNull) {
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
                                    valuesDoMem[doMem.getSelectedItemPosition()] + "",
                                    doMem.getSelectedItemPosition() == 1 ? methods.getCheckedValues(valuesWhatAre, whatAre) : "",
                                    Methods.getSelectedGenId(context)

                            );

                            methods.insertData(context, tableName, dateTime_, strings, true);
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
        doMem = findViewById(R.id.spinner_doMem_WaterSaving);

        whatAre = findViewById(R.id.ll_onYes_WaterSaving);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        doMemR = findViewById(R.id.btn_rDoMem_WaterSaving);
        whatAreR = findViewById(R.id.btn_rOnYes_WaterSaving);
        save = findViewById(R.id.btn_saveActivityWaterSaving);

        optionalDoMem = findViewById(R.id.ll_optionalOnYes_WaterSaving);
    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_WATER_SAVING_DO_MEM) {
            valuesDoMem = methods.setSpinnerThings(context, MyConstants.DL_WATER_SAVING_DO_MEM,
                    valuesDoMem, doMem, false);

        } else if (tableKey == MyConstants.DL_WATER_SAVING_ON_YES) {
            valuesWhatAre = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_SAVING_ON_YES,
                    valuesWhatAre, whatAre, true);

        } else if (tableKey == MyConstants.ALL) {
            valuesDoMem = methods.setSpinnerThings(context, MyConstants.DL_WATER_SAVING_DO_MEM,
                    valuesDoMem, doMem, false);

            valuesWhatAre = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_SAVING_ON_YES,
                    valuesWhatAre, whatAre, true);


        }
    }
}