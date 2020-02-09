package com.debugsire.wsp.WaterSafetyAndClimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

public class ExistingQa extends AppCompatActivity {

    Context context;
    Integer[] valuesWaterSafety, valuesWaterQualityParam, valuesWaterQualityTap, valuesAwareness, valuesMode, valuesFreq;
    Methods methods;


    Spinner waterSafety, waterQualityParam, freq;
    Button waterSafetyR, waterQualityParamR, waterQualityTapR, awarenessR, modeR, freqR;
    LinearLayout waterQualityTap, awareness, mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_qa);

        context = this;
        methods = new Methods();

        waterSafety = findViewById(R.id.spinner_waterSafety_ExistingQA);
        waterQualityParam = findViewById(R.id.spinner_waterQualityParam_ExistingQA);
        freq = findViewById(R.id.spinner_freq_ExistingQA);

        waterQualityTap = findViewById(R.id.ll_wateerQualityTap_ExistingQA);
        awareness = findViewById(R.id.ll_awareness_ExistingQA);
        mode = findViewById(R.id.ll_mode_ExistingQA);

        waterSafetyR = findViewById(R.id.btn_resynchWaterSafe_ExistingQA);
        waterQualityParamR = findViewById(R.id.btn_resynchWaterQualityParam_ExistingQA);
        waterQualityTapR = findViewById(R.id.btn_resynchWaterQualityTap_ExistingQA);
        awarenessR = findViewById(R.id.btn_resynchAwareness_ExistingQA);
        modeR = findViewById(R.id.btn_resynchMode_ExistingQA);
        freqR = findViewById(R.id.btn_resynchFreq_ExistingQA);


        setSpinnerValues(MyConstants.ALL);

        waterSafetyR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_WATER_SAFETY);
            }
        });


        waterQualityParamR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_PARAM);
            }
        });


        waterQualityTapR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_TAP);
            }
        });


        awarenessR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_AWARENESS);
            }
        });


        modeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_MODE);
            }
        });


        freqR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_FREQ);
            }
        });
    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_EXISTINGQA_WATER_SAFETY) {
            valuesWaterSafety = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_WATER_SAFETY,
                    valuesWaterSafety, waterSafety, false);
        } else if (tableKey == MyConstants.DL_EXISTINGQA_WATER_QUAL_PARAM) {
            valuesWaterQualityParam = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_PARAM,
                    valuesWaterQualityParam, waterQualityParam, false);
        } else if (tableKey == MyConstants.DL_EXISTINGQA_WATER_QUAL_TAP) {
            valuesWaterQualityTap = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_TAP,
                    valuesWaterQualityTap, waterQualityTap, false);
        } else if (tableKey == MyConstants.DL_EXISTINGQA_AWARENESS) {
            valuesAwareness = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_AWARENESS,
                    valuesAwareness, awareness, true);
        } else if (tableKey == MyConstants.DL_EXISTINGQA_MODE) {
            valuesMode = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_MODE,
                    valuesMode, mode, true);
        } else if (tableKey == MyConstants.DL_EXISTINGQA_FREQ) {
            valuesFreq = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_FREQ,
                    valuesFreq, freq, false);
        } else if (tableKey == MyConstants.ALL) {
            valuesWaterSafety = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_WATER_SAFETY,
                    valuesWaterSafety, waterSafety, false);

            valuesWaterQualityParam = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_PARAM,
                    valuesWaterQualityParam, waterQualityParam, false);

            valuesWaterQualityTap = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_TAP,
                    valuesWaterQualityTap, waterQualityTap, false);

            valuesAwareness = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_AWARENESS,
                    valuesAwareness, awareness, true);

            valuesMode = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_MODE,
                    valuesMode, mode, true);

            valuesFreq = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_FREQ,
                    valuesFreq, freq, false);
        }
    }
}
