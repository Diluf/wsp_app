package com.debugsire.wsp.WaterSafetyAndClimate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

import java.util.ArrayList;

public class Treatment extends AppCompatActivity {

    private static final String TAG = "********* ";
    Context context;
    Integer[] valuesSourceType, valuesSourceIs, valuesIntake, valuesAvail,
            valuesIndicate, valuesSpecial, valuesOther, valuesWaterQ, valuesCurrent, valuesMiti;
    Methods methods;

    RelativeLayout headerWrapper;
    Spinner sourceType, sourceIs, intake, avail;
    LinearLayout indicate, special, other, waterQ, current, miti, optionalOnAvail;
    Button sourceTypeR, sourceIsR, intakeR, availR,
            indicateR, specialR, otherR, waterQR, currentR, mitiR, save;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

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
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("sType")), valuesSourceType, sourceType);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("sProt")), valuesSourceIs, sourceIs);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("intake")), valuesIntake, intake);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("avail")), valuesAvail, avail);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("indi")), valuesIndicate, indicate);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("spec")), valuesSpecial, special);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("other")), valuesOther, other);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("wq")), valuesWaterQ, waterQ);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("currentR")), valuesCurrent, current);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("riskMit")), valuesMiti, miti);
        }
    }

    private void setEvents() {
        sourceTypeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_TREATMENTSYSTEM_SOURCE_TYPE);
            }
        });


        sourceIsR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_TREATMENTSYSTEM_SOURCE_IS_PRO);
            }
        });


        intakeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_TREATMENTSYSTEM_INTAKE);
            }
        });


        availR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_TREATMENTSYSTEM_AVAIL);
            }
        });


        indicateR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_TREATMENTSYSTEM_INDICATE);
            }
        });


        specialR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_TREATMENTSYSTEM_SPECIAL);
            }
        });

        otherR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_TREATMENTSYSTEM_OTHER);
            }
        });

        waterQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_TREATMENTSYSTEM_WATER_Q);
            }
        });

        currentR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_TREATMENTSYSTEM_CURRENT);
            }
        });

        mitiR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_TREATMENTSYSTEM_RISK_MITIGATION);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, sourceType, sourceIs, intake, avail);
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
                            Integer avail = valuesAvail[Treatment.this.avail.getSelectedItemPosition()];
                            ArrayList<String> strings = methods.getConfiguredStringForInsert(
                                    valuesSourceType[sourceType.getSelectedItemPosition()] + "",
                                    valuesSourceIs[sourceIs.getSelectedItemPosition()] + "",
                                    valuesIntake[intake.getSelectedItemPosition()] + "",
                                    avail + "",
                                    avail == 1 || avail == 2 ? methods.getCheckedValues(valuesIndicate, indicate) : "",
                                    avail == 1 || avail == 2 ? methods.getCheckedValues(valuesSpecial, special) : "",
                                    avail == 1 || avail == 2 ? methods.getCheckedValues(valuesOther, other) : "",
                                    methods.getCheckedValues(valuesWaterQ, waterQ),
                                    methods.getCheckedValues(valuesCurrent, current),
                                    methods.getCheckedValues(valuesMiti, miti)

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

        avail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int value = valuesAvail[i];
                Log.d(TAG, "onItemSelected: " + value);
                if (value == 1 || value == 2) {
                    optionalOnAvail.setVisibility(View.VISIBLE);
                } else {
                    optionalOnAvail.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initCompos() {
        sourceType = findViewById(R.id.spinner_sourceType_Treatment);
        sourceIs = findViewById(R.id.spinner_sourceIsPro_Treatment);
        intake = findViewById(R.id.spinner_intake_Treatment);
        avail = findViewById(R.id.spinner_avail_Treatment);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        indicate = findViewById(R.id.ll_indicate_Treatment);
        special = findViewById(R.id.ll_special_Treatment);
        other = findViewById(R.id.ll_other_Treatment);
        waterQ = findViewById(R.id.ll_waterQ_Treatment);
        current = findViewById(R.id.ll_current_Treatment);
        miti = findViewById(R.id.ll_riskMiti_Treatment);
        optionalOnAvail = findViewById(R.id.ll_optionalOnAvail_Treatment);

        sourceTypeR = findViewById(R.id.btn_resynchSourceType_Treatment);
        sourceIsR = findViewById(R.id.btn_resynchSourceIsPro_Treatment);
        intakeR = findViewById(R.id.btn_resynchIntake_Treatment);
        availR = findViewById(R.id.btn_resynchAvail_Treatment);
        indicateR = findViewById(R.id.btn_resynchIndicate_Treatment);
        specialR = findViewById(R.id.btn_resynchSpecial_Treatment);
        otherR = findViewById(R.id.btn_resynchOther_Treatment);
        waterQR = findViewById(R.id.btn_resynchWaterQ_Treatment);
        currentR = findViewById(R.id.btn_resynchCurrent_Treatment);
        mitiR = findViewById(R.id.btn_resynchRiskMiti_Treatment);
        save = findViewById(R.id.btn_saveActivityTreatment);
    }


    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_TREATMENTSYSTEM_SOURCE_TYPE) {
            valuesSourceType = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_SOURCE_TYPE,
                    valuesSourceType, sourceType, false);

        } else if (tableKey == MyConstants.DL_TREATMENTSYSTEM_SOURCE_IS_PRO) {
            valuesSourceIs = methods.setSpinnerThings(context, MyConstants.DL_CATCHMENT_RISKS_OF_WATER,
                    valuesSourceIs, sourceIs, false);

        } else if (tableKey == MyConstants.DL_TREATMENTSYSTEM_INTAKE) {
            valuesIntake = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_INTAKE,
                    valuesIntake, intake, false);

        } else if (tableKey == MyConstants.DL_TREATMENTSYSTEM_AVAIL) {
            valuesAvail = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_AVAIL,
                    valuesAvail, avail, false);

        } else if (tableKey == MyConstants.DL_TREATMENTSYSTEM_INDICATE) {
            valuesIndicate = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_INDICATE,
                    valuesIndicate, indicate, false);

        } else if (tableKey == MyConstants.DL_TREATMENTSYSTEM_SPECIAL) {
            valuesSpecial = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_SPECIAL,
                    valuesSpecial, special, true);

        } else if (tableKey == MyConstants.DL_TREATMENTSYSTEM_OTHER) {
            valuesOther = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_OTHER,
                    valuesOther, other, false);

        } else if (tableKey == MyConstants.DL_TREATMENTSYSTEM_WATER_Q) {
            valuesWaterQ = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_WATER_Q,
                    valuesWaterQ, waterQ, true);

        } else if (tableKey == MyConstants.DL_TREATMENTSYSTEM_CURRENT) {
            valuesCurrent = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_CURRENT,
                    valuesCurrent, current, true);

        } else if (tableKey == MyConstants.DL_TREATMENTSYSTEM_RISK_MITIGATION) {
            valuesMiti = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_RISK_MITIGATION,
                    valuesMiti, miti, true);

        } else if (tableKey == MyConstants.ALL) {

            valuesSourceType = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_SOURCE_TYPE,
                    valuesSourceType, sourceType, false);

            valuesSourceIs = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_SOURCE_IS_PRO,
                    valuesSourceIs, sourceIs, false);

            valuesIntake = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_INTAKE,
                    valuesIntake, intake, false);

            valuesAvail = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_AVAIL,
                    valuesAvail, avail, false);

            valuesIndicate = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_INDICATE,
                    valuesIndicate, indicate, false);

            valuesSpecial = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_SPECIAL,
                    valuesSpecial, special, true);

            valuesOther = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_OTHER,
                    valuesOther, other, false);

            valuesWaterQ = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_WATER_Q,
                    valuesWaterQ, waterQ, true);

            valuesCurrent = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_CURRENT,
                    valuesCurrent, current, true);

            valuesMiti = methods.setMultipleSelectorView(context, MyConstants.DL_TREATMENTSYSTEM_RISK_MITIGATION,
                    valuesMiti, miti, true);

        }
    }
}
