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

public class WaterQuality extends AppCompatActivity {

    Context context;
    Integer[] valuesDoYouSat, valuesDoYouTreat,
            valuesForWhat, valuesPleasePro, valuesWhatAre, valuesIfWater;
    Methods methods;

    Spinner doYouSat, doYouTreat;
    LinearLayout forWhat, pleasePro, whatAre, ifWater;
    Button doYouSatR, doYouTreatR,
            forWhatR, pleaseProR, whatAreR, ifWaterR, save;
    LinearLayout optionalPleasePro, optionalWhatAre;

    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_quality);

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
            methods.removeEntry(context, tableName, dateTime_);
        }
        return true;
    }


    private void loadFields() {
        methods.configHeaderBar(context, dateTime_, headerWrapper);
        Cursor cursor = methods.getCursorFromDateTime(context, tableName, dateTime_);
        while (cursor.moveToNext()) {
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("forW")), valuesForWhat, forWhat);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("doYouS")), valuesDoYouSat, doYouSat);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("pleaseP")), valuesPleasePro, pleasePro);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("doYouT")), valuesDoYouTreat, doYouTreat);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("whatAre")), valuesWhatAre, whatAre);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("ifWater")), valuesIfWater, ifWater);
        }


    }

    private void setEvents() {
        doYouSatR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_QU_DO_YOU_SAT);
            }
        });

        doYouTreatR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_QU_DO_YOU_TREAT);
            }
        });

        forWhatR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_QU_FOR_WHAT);
            }
        });

        pleaseProR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_QU_PLEASE_PRO);
            }
        });

        whatAreR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_QU_WHAT_ARE);
            }
        });

        ifWaterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_QU_IF_WATER);
            }
        });

        doYouSat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesDoYouSat[i] == 2) {
                    optionalPleasePro.setVisibility(View.VISIBLE);
                } else {
                    optionalPleasePro.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        doYouTreat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesDoYouTreat[i] == 2) {
                    optionalWhatAre.setVisibility(View.VISIBLE);
                } else {
                    optionalWhatAre.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, doYouSat, doYouTreat);
                boolean multiCheckNull = methods.isMultiSelectorNull(context, forWhat);
                if (!spinnerNull && !multiCheckNull) {
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
                                    methods.getCheckedValues(valuesForWhat, forWhat),
                                    valuesDoYouSat[doYouSat.getSelectedItemPosition()] + "",
                                    doYouSat.getSelectedItemPosition() == 2 ? methods.getCheckedValues(valuesPleasePro, pleasePro) : "",
                                    valuesDoYouTreat[doYouTreat.getSelectedItemPosition()] + "",
                                    doYouTreat.getSelectedItemPosition() == 2 ? methods.getCheckedValues(valuesWhatAre, whatAre) : "",
                                    methods.getCheckedValues(valuesIfWater, ifWater)

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
        doYouTreat = findViewById(R.id.spinner_doYouTreat_WaterQuality);
        doYouSat = findViewById(R.id.spinner_doYouSat_WaterQuality);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        forWhat = findViewById(R.id.ll_forWhat_WaterQuality);
        pleasePro = findViewById(R.id.ll_pleasePro_WaterQuality);
        whatAre = findViewById(R.id.ll_whatAre_WaterQuality);
        ifWater = findViewById(R.id.ll_ifWater_WaterQuality);

        doYouSatR = findViewById(R.id.btn_rDoYouSat_WaterQuality);
        doYouTreatR = findViewById(R.id.btn_rDoYouTreat_WaterQuality);
        forWhatR = findViewById(R.id.btn_rForWhat_WaterQuality);
        pleaseProR = findViewById(R.id.btn_rPleasePro_WaterQuality);
        whatAreR = findViewById(R.id.btn_rWhatAre_WaterQuality);
        ifWaterR = findViewById(R.id.btn_rIfWater_WaterQuality);
        save = findViewById(R.id.btn_saveActivityWaterQuality);

        optionalPleasePro = findViewById(R.id.ll_optional_pleasePro_WaterQuality);
        optionalWhatAre = findViewById(R.id.ll_optional_whatAre_WaterQuality);

    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_WATER_QU_FOR_WHAT) {
            valuesForWhat = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_QU_FOR_WHAT,
                    valuesForWhat, forWhat, true);

        } else if (tableKey == MyConstants.DL_WATER_QU_DO_YOU_SAT) {
            valuesDoYouSat = methods.setSpinnerThings(context, MyConstants.DL_WATER_QU_DO_YOU_SAT,
                    valuesDoYouSat, doYouSat, false);

        } else if (tableKey == MyConstants.DL_WATER_QU_PLEASE_PRO) {
            valuesPleasePro = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_QU_PLEASE_PRO,
                    valuesPleasePro, pleasePro, true);

        } else if (tableKey == MyConstants.DL_WATER_QU_DO_YOU_TREAT) {
            valuesDoYouTreat = methods.setSpinnerThings(context, MyConstants.DL_WATER_QU_DO_YOU_TREAT,
                    valuesDoYouTreat, doYouTreat, false);

        } else if (tableKey == MyConstants.DL_WATER_QU_WHAT_ARE) {
            valuesWhatAre = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_QU_WHAT_ARE,
                    valuesWhatAre, whatAre, true);

        } else if (tableKey == MyConstants.DL_WATER_QU_IF_WATER) {
            valuesIfWater = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_QU_IF_WATER,
                    valuesIfWater, ifWater, true);

        } else if (tableKey == MyConstants.ALL) {
            valuesForWhat = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_QU_FOR_WHAT,
                    valuesForWhat, forWhat, true);

            valuesDoYouSat = methods.setSpinnerThings(context, MyConstants.DL_WATER_QU_DO_YOU_SAT,
                    valuesDoYouSat, doYouSat, false);

            valuesPleasePro = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_QU_PLEASE_PRO,
                    valuesPleasePro, pleasePro, true);

            valuesDoYouTreat = methods.setSpinnerThings(context, MyConstants.DL_WATER_QU_DO_YOU_TREAT,
                    valuesDoYouTreat, doYouTreat, false);

            valuesWhatAre = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_QU_WHAT_ARE,
                    valuesWhatAre, whatAre, true);

            valuesIfWater = methods.setMultipleSelectorView(context, MyConstants.DL_WATER_QU_IF_WATER,
                    valuesIfWater, ifWater, true);

        }
    }
}