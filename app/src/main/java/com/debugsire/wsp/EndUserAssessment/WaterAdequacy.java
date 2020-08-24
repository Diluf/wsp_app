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
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class WaterAdequacy extends AppCompatActivity {

    Context context;
    Integer[] valuesDoYouGet, valuesDoYouUse, valuesPleaseMention,
            valuesWhatAre;
    Methods methods;

    TextInputLayout what;
    Spinner doYouGet, doYouUse, pleaseMention;
    LinearLayout whatAre;
    Button doYouGetR, doYouUseR, pleaseMentionR,
            whatAreR, save;
    LinearLayout optionalWhatAre, optionalPleaseMention;


    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_adequacy);

        context = this;
        methods = new Methods();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");

        initCompos();
        setEvents();
        setSpinnerValues(MyConstants.ALL);
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
            methods.removeEntry(context, tableName, dateTime_);
        }
        return true;
    }


    private void loadFields() {
        methods.configHeaderBar(context, dateTime_, headerWrapper);
        Cursor cursor = methods.getCursorFromDateTime(context, tableName, dateTime_);
        while (cursor.moveToNext()) {
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("doYouG")), valuesDoYouGet, doYouGet);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("ifNo")), valuesWhatAre, whatAre);
            what.getEditText().setText(cursor.getString(cursor.getColumnIndex("whatIs")));
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("doYouUse")), valuesDoYouUse, doYouUse);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("ifYes")), valuesPleaseMention, pleaseMention);
        }


    }

    private void setEvents() {
        doYouGetR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_AD_DO_YOU_GET);
            }
        });

        doYouUseR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_AD_DO_YOU_USE);
            }
        });

        pleaseMentionR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_AD_PLEASE_MENTION);
            }
        });

        whatAreR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_AD_WHAT_ARE);
            }
        });

        doYouGet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesDoYouGet[i] == 2) {
                    optionalWhatAre.setVisibility(View.VISIBLE);
                } else {
                    optionalWhatAre.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        doYouUse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesDoYouGet[i] == 1) {
                    optionalPleaseMention.setVisibility(View.VISIBLE);
                } else {
                    optionalPleaseMention.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, doYouGet, doYouUse);
                boolean tilFieldsNull = methods.isTILFieldsNull(context, what);
                if (!spinnerNull && !tilFieldsNull) {
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
                                    valuesDoYouGet[doYouGet.getSelectedItemPosition()] + "",
                                    doYouGet.getSelectedItemPosition() == 2 ? methods.getCheckedValues(valuesWhatAre, whatAre) : "",
                                    what.getEditText().getText().toString().trim(),
                                    valuesDoYouUse[doYouUse.getSelectedItemPosition()] + "",
                                    doYouUse.getSelectedItemPosition() == 1 ? valuesPleaseMention[pleaseMention.getSelectedItemPosition()] + "" : ""

                            );

                            methods.insertData(context, tableName, dateTime_, strings);
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
        what = findViewById(R.id.til_whatActivityWaterAdequacy);

        doYouGet = findViewById(R.id.spinner_doYouGet_WaterAdequacy);
        doYouUse = findViewById(R.id.spinner_doYouUse_WaterAdequacy);
        pleaseMention = findViewById(R.id.spinner_pleaseMention_WaterAdequacy);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        whatAre = findViewById(R.id.ll_whatAre_WaterAdequacy);

        doYouGetR = findViewById(R.id.btn_rDoYouGet_WaterAdequacy);
        doYouUseR = findViewById(R.id.btn_rDoYouUse_WaterAdequacy);
        pleaseMentionR = findViewById(R.id.btn_rPleaseMention_WaterAdequacy);
        whatAreR = findViewById(R.id.btn_rWhatAre_WaterAdequacy);
        save = findViewById(R.id.btn_saveActivityWaterAdequacy);

        optionalWhatAre = findViewById(R.id.ll_optionalWhatAre_WaterAdequacy);
        optionalPleaseMention = findViewById(R.id.ll_optionalPleaseMention_WaterAdequacy);
    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_WATER_AD_DO_YOU_GET) {
            valuesDoYouGet = methods.setSpinnerThings(context, MyConstants.DL_WATER_AD_DO_YOU_GET,
                    valuesDoYouGet, doYouGet, false);

        } else if (tableKey == MyConstants.DL_WATER_AD_WHAT_ARE) {
            valuesWhatAre = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_AD_WHAT_ARE,
                    valuesWhatAre, whatAre, true);

        } else if (tableKey == MyConstants.DL_WATER_AD_DO_YOU_USE) {
            valuesDoYouUse = methods.setSpinnerThings(context, MyConstants.DL_WATER_AD_DO_YOU_USE,
                    valuesDoYouUse, doYouUse, false);

        } else if (tableKey == MyConstants.DL_WATER_AD_PLEASE_MENTION) {
            valuesPleaseMention = methods.setSpinnerThings(context, MyConstants.DL_WATER_AD_PLEASE_MENTION,
                    valuesPleaseMention, pleaseMention, true);

        } else if (tableKey == MyConstants.ALL) {
            valuesDoYouGet = methods.setSpinnerThings(context, MyConstants.DL_WATER_AD_DO_YOU_GET,
                    valuesDoYouGet, doYouGet, false);

            valuesWhatAre = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_AD_WHAT_ARE,
                    valuesWhatAre, whatAre, true);

            valuesDoYouUse = methods.setSpinnerThings(context, MyConstants.DL_WATER_AD_DO_YOU_USE,
                    valuesDoYouUse, doYouUse, false);

            valuesPleaseMention = methods.setSpinnerThings(context, MyConstants.DL_WATER_AD_PLEASE_MENTION,
                    valuesPleaseMention, pleaseMention, true);


        }
    }
}