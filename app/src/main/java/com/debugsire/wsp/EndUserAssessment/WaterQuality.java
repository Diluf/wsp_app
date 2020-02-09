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

public class WaterQuality extends AppCompatActivity {

    Context context;
    Integer[] valuesDoYouSat, valuesDoYouTreat,
            valuesForWhat, valuesPleasePro, valuesWhatAre, valuesIfWater;
    Methods methods;

    Spinner doYouSat, doYouTreat;
    LinearLayout forWhat, pleasePro, whatAre, ifWater;
    Button doYouSatR, doYouTreatR,
            forWhatR, pleaseProR, whatAreR, ifWaterR;
    LinearLayout optionalPleasePro, optionalWhatAre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_quality);
        context = this;
        methods = new Methods();

        doYouSat = findViewById(R.id.spinner_doYouSat_WaterQuality);
        doYouTreat = findViewById(R.id.spinner_doYouTreat_WaterQuality);

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


        optionalPleasePro = findViewById(R.id.ll_optional_pleasePro_WaterQuality);
        optionalWhatAre = findViewById(R.id.ll_optional_whatAre_WaterQuality);


        setSpinnerValues(MyConstants.ALL);

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