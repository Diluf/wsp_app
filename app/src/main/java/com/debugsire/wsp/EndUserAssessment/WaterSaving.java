package com.debugsire.wsp.EndUserAssessment;

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

public class WaterSaving extends AppCompatActivity {

    Context context;
    Integer[] valuesDoMem, valuesWhatAre;
    Methods methods;

    Spinner doMem;
    LinearLayout whatAre;
    Button doMemR, whatAreR;
    LinearLayout optionalDoMem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_saving);
        context = this;
        methods = new Methods();

        doMem = findViewById(R.id.spinner_doMem_WaterSaving);

        whatAre = findViewById(R.id.ll_onYes_WaterSaving);

        doMemR = findViewById(R.id.btn_rDoMem_WaterSaving);
        whatAreR = findViewById(R.id.btn_rOnYes_WaterSaving);

        optionalDoMem = findViewById(R.id.ll_optionalOnYes_WaterSaving);
        setSpinnerValues(MyConstants.ALL);

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