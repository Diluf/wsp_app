package com.debugsire.wsp.WaterSafetyAndClimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

public class Distribution extends AppCompatActivity {
    Context context;
    Integer[] valuesMeter, valuesMaterials, valuesUnit, valuesInterm, valuesService,
            valuesIden, valuesRisk, valuesOverall;
    Methods methods;

    Spinner meter, materials, unit, interm, service;
    LinearLayout iden, risk, overall;
    Button meterR, materialsR, unitR, intermR, serviceR,
            idenR, riskR, overallR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);
        context = this;
        methods = new Methods();

        meter = findViewById(R.id.spinner_meter_Distribution);
//        materials = findViewById(R.id.);
//        unit = findViewById(R.id.spinner_intake_Treatment);
        interm = findViewById(R.id.spinner_inter_Distribution);
        service = findViewById(R.id.spinner_service_Distribution);

        iden = findViewById(R.id.ll_iden_Distribution);
        risk = findViewById(R.id.ll_riskMiti_Distribution);
        overall = findViewById(R.id.ll_overall_Distribution);

        meterR = findViewById(R.id.btn_resynchMeter_Distribution);
//        materialsR = findViewById(R.id.);
//        unitR = findViewById(R.id.spinner_intake_Treatment);
        intermR = findViewById(R.id.btn_resynchInter_Distribution);
        serviceR = findViewById(R.id.btn_resynchService_Distribution);
        idenR = findViewById(R.id.btn_resynchIden_Distribution);
        riskR = findViewById(R.id.btn_resynchRiskMitigation_Distribution);
        overallR = findViewById(R.id.btn_resynchOverall_Distribution);

        setSpinnerValues(MyConstants.ALL);

        meterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_METERING);
            }
        });


        intermR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_INTER);
            }
        });


        serviceR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_SERVICE);
            }
        });


        idenR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_IDENTIFIED);
            }
        });


        riskR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_RISK_MITIGATION);
            }
        });

        overallR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_OVERALL);
            }
        });


    }


    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_DISTRIBUTION_METERING) {
            valuesMeter = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_METERING,
                    valuesMeter, meter, false);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_MATERIAL) {
//

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_UNIT) {
//

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_INTER) {
            valuesInterm = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_INTER,
                    valuesInterm, interm, false);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_SERVICE) {
            valuesService = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_SERVICE,
                    valuesService, service, false);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_IDENTIFIED) {
            valuesIden = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_IDENTIFIED,
                    valuesIden, iden, true);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_RISK_MITIGATION) {

            valuesRisk = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_RISK_MITIGATION,
                    valuesRisk, risk, true);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_OVERALL) {
            valuesOverall = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_OVERALL,
                    valuesIden, overall, true);

        } else if (tableKey == MyConstants.ALL) {
            valuesMeter = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_METERING,
                    valuesMeter, meter, false);

            valuesInterm = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_INTER,
                    valuesInterm, interm, false);

//            valuesIntake = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_INTAKE,
//                    valuesIntake, intake, false);
//
//            valuesAvail = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_AVAIL,
//                    valuesAvail, avail, false);

            valuesService = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_SERVICE,
                    valuesService, service, false);

            valuesIden = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_IDENTIFIED,
                    valuesIden, iden, true);

            valuesRisk = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_RISK_MITIGATION,
                    valuesRisk, risk, true);

            valuesOverall = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_OVERALL,
                    valuesIden, overall, true);


        }
    }
}
