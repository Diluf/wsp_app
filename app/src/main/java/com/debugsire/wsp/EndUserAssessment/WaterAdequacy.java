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

public class WaterAdequacy extends AppCompatActivity {

    Context context;
    Integer[] valuesDoYouGet, valuesDoYouUse, valuesPleaseMention,
            valuesWhatAre;
    Methods methods;

    Spinner doYouGet, doYouUse, pleaseMention;
    LinearLayout whatAre;
    Button doYouGetR, doYouUseR, pleaseMentionR,
            whatAreR;
    LinearLayout optionalWhatAre, optionalPleaseMention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_adequacy);
        context = this;
        methods = new Methods();

        doYouGet = findViewById(R.id.spinner_doYouGet_WaterAdequacy);
        doYouUse = findViewById(R.id.spinner_doYouUse_WaterAdequacy);
        pleaseMention = findViewById(R.id.spinner_pleaseMention_WaterAdequacy);

        whatAre = findViewById(R.id.ll_whatAre_WaterAdequacy);

        doYouGetR = findViewById(R.id.btn_rDoYouGet_WaterAdequacy);
        doYouUseR = findViewById(R.id.btn_rDoYouUse_WaterAdequacy);
        pleaseMentionR = findViewById(R.id.btn_rPleaseMention_WaterAdequacy);
        whatAreR = findViewById(R.id.btn_rWhatAre_WaterAdequacy);

        optionalWhatAre = findViewById(R.id.ll_optionalWhatAre_WaterAdequacy);
        optionalPleaseMention = findViewById(R.id.ll_optionalPleaseMention_WaterAdequacy);


        setSpinnerValues(MyConstants.ALL);

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