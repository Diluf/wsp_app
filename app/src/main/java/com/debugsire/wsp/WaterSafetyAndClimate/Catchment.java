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

public class Catchment extends AppCompatActivity {

    Context context;
    Integer[] valuesNature, valuesRisksOfWater, valuesRisksForSource, valuesIssues, valuesRiskMiti;
    Methods methods;

    Spinner nature;
    LinearLayout risksOfWater, risksForSource, issues, riskMiti;
    Button natureR, risksOfWaterR, risksForSourceR, issuesR, riskMitiR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catchment);
        context = this;
        methods = new Methods();

        nature = findViewById(R.id.spinner_nature_Catchment);

        risksOfWater = findViewById(R.id.ll_risksOfWater_Catchment);
        risksForSource = findViewById(R.id.ll_risksForSource_Catchment);
        issues = findViewById(R.id.ll_issues_Catchment);
        riskMiti = findViewById(R.id.ll_riskMitigation_Catchment);

        natureR = findViewById(R.id.btn_resynchNature_Catchment);
        risksOfWaterR = findViewById(R.id.btn_resynchRisksOfWater_Catchment);
        risksForSourceR = findViewById(R.id.btn_resynchRisksForSource_Catchment);
        issuesR = findViewById(R.id.btn_resynchIssues_Catchment);
        riskMitiR = findViewById(R.id.btn_resynchRiskMitigation_Catchment);


        setSpinnerValues(MyConstants.ALL);

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


    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_CATCHMENT_NATURE) {
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