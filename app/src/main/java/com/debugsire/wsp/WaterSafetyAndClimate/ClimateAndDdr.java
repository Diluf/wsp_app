package com.debugsire.wsp.WaterSafetyAndClimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

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
            onNoR, whatAreTheRechargeR, optionsToReduceR, optionsDroughtR, optionsFloodR;
    LinearLayout optionalOnYes, optionalOnEffect, optionalOnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climate_and_ddr);
        context = this;
        methods = new Methods();

        isTheWater = findViewById(R.id.spinner_isTheWater_ClimateAndDrr);
        waterIsAvailable = findViewById(R.id.spinner_waterIsAvail_ClimateAndDrr);
        whatAreSources = findViewById(R.id.spinner_whatAreTheS_ClimateAndDrr);

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

        optionalOnYes = findViewById(R.id.ll_optionalOnYes_ClimateAndDrr);
        optionalOnEffect = findViewById(R.id.ll_optionalOnEffect_ClimateAndDrr);
        optionalOnNo = findViewById(R.id.ll_optionalOnNo_ClimateAndDrr);

        setSpinnerValues(MyConstants.ALL);

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