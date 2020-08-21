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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

import java.util.ArrayList;

public class ClimateAndDdr extends AppCompatActivity {

    Context context;
    Integer[] valuesIsTheWater, valuesWaterIsAvailable, valuesWhatAreSources,
            valuesOnYesHowIt, valuesOnYesWhatAreThey, valuesOnYesWhatAreTheA, valuesOnYesWhatAreTheB,
            valuesOnNo, valuesWhatAreTheRecharge, valuesOptionsToReduce, valuesOptionsDrought, valuesOptionsFlood;
    Methods methods;

    Spinner isTheWater, waterIsAvailable, whatAreSources;
    LinearLayout onYesHowIt, onYesWhatAreThey, onYesWhatAreTheEffects,
            onNo, whatAreTheRecharge, optionsToReduce, optionsDrought, optionsFlood;
    Button isTheWaterR, waterIsAvailableR, whatAreSourcesR,
            onYesHowItR, onYesWhatAreTheyR, onYesWhatAreTheEffectsR,
            onNoR, whatAreTheRechargeR, optionsToReduceR, optionsDroughtR, optionsFloodR, save;
    LinearLayout optionalOnYes, optionalOnEffect, optionalOnNo;

    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climate_and_ddr);

        context = this;
        methods = new Methods();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");

        initCompos();
        setEvents();
        setSpinnerValues(MyConstants.ALL);
        loadFields();




        isTheWater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesIsTheWater[i] == 1) {
                    optionalOnYes.setVisibility(View.VISIBLE);

                } else {
                    optionalOnYes.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        waterIsAvailable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesIsTheWater[i] == 2) {
                    optionalOnNo.setVisibility(View.VISIBLE);

                } else {
                    optionalOnNo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//
//
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
//            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("isTheW")), valuesIsTheWater, isTheWater);
//            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("how")), valuesOnYesHowIt, onYesHowIt);
//            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("they")), valuesOnYesWhatAreThey, onYesWhatAreThey);
//            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("effe")), valuesOnYes, onYesWhatAreTheEffects);
//            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("waterIsA")), valuesWaterQualityParam, waterQualityParam);
//            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("whatAreS")), valuesWaterQualityParam, waterQualityParam);
//            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("whatAreT")), valuesWaterQualityTap, waterQualityTap);
//            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("water")), valuesAwareness, );
//            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("drought")), valuesOptionsDrought, optionsDrought);
//            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("flood")), valuesOptionsFlood, optionsFlood);
        }


    }


    private void setEvents() {
        isTheWaterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_IS_THE_WATER);
            }
        });

        waterIsAvailableR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_WATER_IS_AVAILABLE);
            }
        });

        whatAreSourcesR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_SOURCES);
            }
        });

        onYesHowItR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_ON_YES_HOW_IT_IMPACTS);
            }
        });

        onYesWhatAreTheyR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THEY);
            }
        });

        onYesWhatAreTheEffectsR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (onyesW)
//                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_IS_THE_WATER);
            }
        });

        onNoR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_ON_NO_WHAT_ARE_THE_REASONS);
            }
        });

        whatAreTheRechargeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_WATER);
            }
        });

        optionsToReduceR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_OPTIONS_TO_RED);
            }
        });

        optionsDroughtR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_DROUGHT);
            }
        });

        optionsFloodR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_FLOOD);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean spinnerNull = methods.isSpinnerNull(context, waterSafety, waterQualityParam, freq);
//                boolean multiCheckNull = methods.isMultiSelectorNull(context, waterQualityTap, awareness);
//                if (!spinnerNull && !multiCheckNull) {
//                    final AlertDialog dialog = methods.getSaveConfirmationDialog(context, dateTime_ != null);
//                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            ArrayList<String> strings = methods.getConfiguredStringForInsert(
//                                    valuesWaterSafety[waterSafety.getSelectedItemPosition()] + "",
//                                    valuesWaterQualityParam[waterQualityParam.getSelectedItemPosition()] + "",
//                                    methods.getCheckedValues(valuesWaterQualityTap, waterQualityTap),
//                                    methods.getCheckedValues(valuesAwareness, awareness),
//                                    methods.getCheckedValues(valuesMode, mode),
//                                    valuesFreq[freq.getSelectedItemPosition()] + ""
//
//                            );
//
//                            methods.insertData(context, tableName, dateTime_, strings);
//                            methods.showToast(getString(R.string.saved), context, MyConstants.MESSAGE_SUCCESS);
//                            onBackPressed();
//
//                        }
//                    });
//                    dialog.show();
//                } else {
//                    methods.showToast(getString(R.string.compulsory_cant_empty), context, MyConstants.MESSAGE_ERROR);
//                }
            }
        });
    }

    private void initCompos() {
        isTheWater = findViewById(R.id.spinner_isTheWater_ClimateAndDrr);
        waterIsAvailable = findViewById(R.id.spinner_waterIsAvail_ClimateAndDrr);
        whatAreSources = findViewById(R.id.spinner_whatAreTheS_ClimateAndDrr);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        onYesHowIt = findViewById(R.id.ll_onYesHowItImpacts_ClimateAndDrr);
        onYesWhatAreThey = findViewById(R.id.ll_onYesWhatAreThey_ClimateAndDrr);
        onYesWhatAreTheEffects = findViewById(R.id.ll_onYesAsBWhatAreTheEffects_ClimateAndDrr);
        onNo = findViewById(R.id.ll_onNoWhatAreTheReasons_ClimateAndDrr);
        whatAreTheRecharge = findViewById(R.id.ll_whatAreTheWaterRecharging_ClimateAndDrr);
        optionsToReduce = findViewById(R.id.ll_optionsToReduce_ClimateAndDrr);
        optionsDrought = findViewById(R.id.ll_optionsDrought_ClimateAndDrr);
        optionsFlood = findViewById(R.id.ll_optionsFlood_ClimateAndDrr);

        isTheWaterR = findViewById(R.id.btn_rIsTheWater_ClimateAndDrr);
        waterIsAvailableR = findViewById(R.id.btn_rWaterIsAvail_ClimateAndDrr);
        whatAreSourcesR = findViewById(R.id.btn_rWhatAreTheS_ClimateAndDrr);
        onYesHowItR = findViewById(R.id.btn_rOnYesHowItImpacts_ClimateAndDrr);
        onYesWhatAreTheyR = findViewById(R.id.btn_rOnYesWhatAreThey_ClimateAndDrr);
        onYesWhatAreTheEffectsR = findViewById(R.id.btn_rOnYesAsBWhatAreTheEffects_ClimateAndDrr);
        onNoR = findViewById(R.id.btn_rOnNoWhatAreTheReasons_ClimateAndDrr);
        whatAreTheRechargeR = findViewById(R.id.btn_rWhatAreTheWaterRecharging_ClimateAndDrr);
        optionsToReduceR = findViewById(R.id.btn_rOptionstoReduce_ClimateAndDrr);
        optionsDroughtR = findViewById(R.id.btn_rOptionsDrought_ClimateAndDrr);
        optionsFloodR = findViewById(R.id.btn_rOptionsFlood_ClimateAndDrr);
        save = findViewById(R.id.btn_saveFlood_ClimateAndDrr);

        optionalOnYes = findViewById(R.id.ll_optionalOnYes_ClimateAndDrr);
        optionalOnEffect = findViewById(R.id.ll_optionalOnEffect_ClimateAndDrr);
        optionalOnNo = findViewById(R.id.ll_optionalOnNo_ClimateAndDrr);
    }



    //
//
    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_CLIMATE_IS_THE_WATER) {
            valuesIsTheWater = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_IS_THE_WATER,
                    valuesIsTheWater, isTheWater, false);

        } else if (tableKey == MyConstants.DL_CLIMATE_ON_YES_HOW_IT_IMPACTS) {
            valuesOnYesHowIt = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_HOW_IT_IMPACTS,
                    valuesOnYesHowIt, onYesHowIt, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THEY) {
            valuesOnYesWhatAreThey = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THEY,
                    valuesOnYesWhatAreThey, onYesWhatAreThey, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_A) {
            valuesOnYesWhatAreTheA = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_A,
                    valuesOnYesWhatAreTheA, onYesWhatAreTheEffects, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_B) {
            valuesOnYesWhatAreTheB = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_B,
                    valuesOnYesWhatAreTheB, onYesWhatAreTheEffects, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_WATER_IS_AVAILABLE) {
            valuesWaterIsAvailable = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_WATER_IS_AVAILABLE,
                    valuesWaterIsAvailable, waterIsAvailable, false);

        } else if (tableKey == MyConstants.DL_CLIMATE_ON_NO_WHAT_ARE_THE_REASONS) {
            valuesOnNo = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_NO_WHAT_ARE_THE_REASONS,
                    valuesOnNo, onNo, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_WHAT_ARE_THE_SOURCES) {
            valuesWhatAreSources = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_SOURCES,
                    valuesWhatAreSources, whatAreSources, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_WHAT_ARE_THE_WATER) {
            valuesWhatAreTheRecharge = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_WATER,
                    valuesWhatAreTheRecharge, whatAreTheRecharge, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_OPTIONS_TO_RED) {
            valuesOptionsToReduce = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_TO_RED,
                    valuesOptionsToReduce, optionsToReduce, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_DROUGHT) {
            valuesOptionsDrought = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_DROUGHT,
                    valuesOptionsDrought, optionsDrought, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_FLOOD) {
            valuesOptionsFlood = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_FLOOD,
                    valuesOptionsFlood, optionsFlood, false);

        } else if (tableKey == MyConstants.ALL) {
            valuesIsTheWater = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_IS_THE_WATER,
                    valuesIsTheWater, isTheWater, false);

            valuesOnYesHowIt = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_HOW_IT_IMPACTS,
                    valuesOnYesHowIt, onYesHowIt, true);

            valuesOnYesWhatAreThey = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THEY,
                    valuesOnYesWhatAreThey, onYesWhatAreThey, true);

            valuesOnYesWhatAreTheA = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_A,
                    valuesOnYesWhatAreTheA, onYesWhatAreTheEffects, true);

            valuesOnYesWhatAreTheB = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_B,
                    valuesOnYesWhatAreTheB, onYesWhatAreTheEffects, true);

            valuesWaterIsAvailable = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_WATER_IS_AVAILABLE,
                    valuesWaterIsAvailable, waterIsAvailable, false);

            valuesOnNo = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_NO_WHAT_ARE_THE_REASONS,
                    valuesOnNo, onNo, true);

            valuesWhatAreSources = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_SOURCES,
                    valuesWhatAreSources, whatAreSources, true);

            valuesWhatAreTheRecharge = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_WATER,
                    valuesWhatAreTheRecharge, whatAreTheRecharge, true);

            valuesOptionsToReduce = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_TO_RED,
                    valuesOptionsToReduce, optionsToReduce, true);

            valuesOptionsDrought = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_DROUGHT,
                    valuesOptionsDrought, optionsDrought, true);

            valuesOptionsFlood = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_FLOOD,
                    valuesOptionsFlood, optionsFlood, false);

        }
    }

}