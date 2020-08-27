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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Catchment extends AppCompatActivity {

    Context context;
    Integer[] valuesArea, valuesNature, valuesRisksOfWater, valuesRisksForSource, valuesIssues, valuesRiskMiti;
    Methods methods;

    Spinner area, nature;
    LinearLayout risksOfWater, risksForSource, issues, riskMiti;
    TextInputLayout catchM, loca;
    Button areaR, natureR, risksOfWaterR, risksForSourceR, issuesR, riskMitiR, save;
    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catchment);

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
            methods.removeEntry(context, tableName, dateTime_, false);
        }
        return true;
    }

    private void loadFields() {
        methods.configHeaderBar(context, dateTime_, headerWrapper);
        Cursor cursor = methods.getCursorFromDateTime(context, tableName, dateTime_, false);
        while (cursor.moveToNext()) {
            catchM.getEditText().setText(cursor.getString(cursor.getColumnIndex("catchName")));
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("area")), valuesArea, area);
            loca.getEditText().setText(cursor.getString(cursor.getColumnIndex("loca")));
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("nature")), valuesNature, nature);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("riskOf")), valuesRisksOfWater, risksOfWater);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("riskFor")), valuesRisksForSource, risksForSource);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("issues")), valuesIssues, issues);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("riskMit")), valuesRiskMiti, riskMiti);
        }

    }

    private void setEvents() {
        areaR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CATCHMENT_AREA);
            }
        });

        natureR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CATCHMENT_NATURE);
            }
        });


        risksOfWaterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CATCHMENT_RISKS_OF_WATER);
            }
        });


        risksForSourceR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CATCHMENT_RISKS_FOR_SOURCE);
            }
        });


        issuesR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CATCHMENT_ISSUES);
            }
        });


        riskMitiR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CATCHMENT_RISK_MITIGATION);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tilFieldsNull = methods.isTILFieldsNull(context, loca);
                boolean spinnerNull = methods.isSpinnerNull(context, nature);
                boolean multiCheckNull = methods.isMultiSelectorNull(context, risksOfWater, risksForSource, issues, riskMiti);
                if (!spinnerNull && !multiCheckNull && !tilFieldsNull) {
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
                                    catchM.getEditText().getText().toString().trim(),
                                    valuesArea[area.getSelectedItemPosition()] + "",
                                    loca.getEditText().getText().toString().trim(),
                                    valuesNature[nature.getSelectedItemPosition()] + "",
                                    methods.getCheckedValues(valuesRisksOfWater, risksOfWater),
                                    methods.getCheckedValues(valuesRisksForSource, risksForSource),
                                    methods.getCheckedValues(valuesIssues, issues),
                                    methods.getCheckedValues(valuesRiskMiti, riskMiti)
                            );

                            methods.insertData(context, tableName, dateTime_, strings, false);
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
        area = findViewById(R.id.spinner_areaActivityCatchment);
        nature = findViewById(R.id.spinner_nature_Catchment);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        risksOfWater = findViewById(R.id.ll_risksOfWater_Catchment);
        risksForSource = findViewById(R.id.ll_risksForSource_Catchment);
        issues = findViewById(R.id.ll_issues_Catchment);
        riskMiti = findViewById(R.id.ll_riskMitigation_Catchment);

        catchM = findViewById(R.id.til_catchActivityCatchment);
        loca = findViewById(R.id.til_locaActivityCatchment);


        areaR = findViewById(R.id.btn_resynchAreaActivityCatchment);
        natureR = findViewById(R.id.btn_resynchNature_Catchment);
        risksOfWaterR = findViewById(R.id.btn_resynchRisksOfWater_Catchment);
        risksForSourceR = findViewById(R.id.btn_resynchRisksForSource_Catchment);
        issuesR = findViewById(R.id.btn_resynchIssues_Catchment);
        riskMitiR = findViewById(R.id.btn_resynchRiskMitigation_Catchment);
        save = findViewById(R.id.btn_saveActivityCatchment);
    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_CATCHMENT_AREA) {
            valuesArea = methods.setSpinnerThings(context, MyConstants.DL_CATCHMENT_AREA,
                    valuesArea, area, false);

        } else if (tableKey == MyConstants.DL_CATCHMENT_NATURE) {
            valuesNature = methods.setSpinnerThings(context, MyConstants.DL_CATCHMENT_NATURE,
                    valuesNature, nature, false);

        } else if (tableKey == MyConstants.DL_CATCHMENT_RISKS_OF_WATER) {
            valuesRisksOfWater = methods.setMultipleSelectorView(context, MyConstants.DL_CATCHMENT_RISKS_OF_WATER,
                    valuesRisksOfWater, risksOfWater, true);

        } else if (tableKey == MyConstants.DL_CATCHMENT_RISKS_FOR_SOURCE) {
            valuesRisksForSource = methods.setMultipleSelectorView(context, MyConstants.DL_CATCHMENT_RISKS_FOR_SOURCE,
                    valuesRisksForSource, risksForSource, true);

        } else if (tableKey == MyConstants.DL_CATCHMENT_ISSUES) {
            valuesIssues = methods.setMultipleSelectorView(context, MyConstants.DL_CATCHMENT_ISSUES,
                    valuesIssues, issues, true);

        } else if (tableKey == MyConstants.DL_CATCHMENT_RISK_MITIGATION) {
            valuesRiskMiti = methods.setMultipleSelectorView(context, MyConstants.DL_CATCHMENT_RISK_MITIGATION,
                    valuesRiskMiti, riskMiti, true);

        } else if (tableKey == MyConstants.ALL) {

            valuesArea = methods.setSpinnerThings(context, MyConstants.DL_CATCHMENT_AREA,
                    valuesArea, area, true);

            valuesNature = methods.setSpinnerThings(context, MyConstants.DL_CATCHMENT_NATURE,
                    valuesNature, nature, false);

            valuesRisksOfWater = methods.setMultipleSelectorView(context, MyConstants.DL_CATCHMENT_RISKS_OF_WATER,
                    valuesRisksOfWater, risksOfWater, true);

            valuesRisksForSource = methods.setMultipleSelectorView(context, MyConstants.DL_CATCHMENT_RISKS_FOR_SOURCE,
                    valuesRisksForSource, risksForSource, true);

            valuesIssues = methods.setMultipleSelectorView(context, MyConstants.DL_CATCHMENT_ISSUES,
                    valuesIssues, issues, true);

            valuesRiskMiti = methods.setMultipleSelectorView(context, MyConstants.DL_CATCHMENT_RISK_MITIGATION,
                    valuesRiskMiti, riskMiti, true);
        }
    }
}