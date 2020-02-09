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

public class Governance extends AppCompatActivity {

    Context context;
    Integer[] valuesFairLand, valuesConflicts, valuesIsThere,
            valuesInclusive, valuesTrans, valuesOnYesConflicts, valuesOnYesIsThere;
    Methods methods;

    Spinner fairLand, conflicts, isThere;
    LinearLayout inclusive, trans, onYesConflicts, onYesIsThere;
    Button fairLandR, conflictsR, isThereR,
            inclusiveR, transR, onYesConflictsR, onYesIsThereR;

    LinearLayout optionalOnYesConflicts, optionalOnYesIsThere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_governance);
        context = this;
        methods = new Methods();

        fairLand = findViewById(R.id.spinner_fairLand_Governance);
        conflicts = findViewById(R.id.spinner_conflicts_Governance);
        isThere = findViewById(R.id.spinner_isThere_Governance);

        inclusive = findViewById(R.id.ll_inclusive_Governance);
        trans = findViewById(R.id.ll_trans_Governance);
        onYesConflicts = findViewById(R.id.ll_conflicts_Governance);
        onYesIsThere = findViewById(R.id.ll_isThere_Governance);

        fairLandR = findViewById(R.id.btn_rFairLand_Governance);
        conflictsR = findViewById(R.id.btn_rConflicts_Governance);
        isThereR = findViewById(R.id.btn_rIsThere_Governance);
        inclusiveR = findViewById(R.id.btn_rInclusive_Governance);
        transR = findViewById(R.id.btn_rTrans_Governance);
        onYesConflictsR = findViewById(R.id.btn_rOptionalOnYesConflicts_Governance);
        onYesIsThereR = findViewById(R.id.btn_rOptionalOnYesIsThere_Governance);

        optionalOnYesConflicts = findViewById(R.id.ll_optionalOnYesConflicts_Governance);
        optionalOnYesIsThere = findViewById(R.id.ll_optionalOnYesIsThere_Governance);

        setSpinnerValues(MyConstants.ALL);

        fairLandR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_FAIR_LAND);
            }
        });

        conflictsR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_CONFLICTS);
            }
        });

        isThereR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_IS_THERE);
            }
        });

        inclusiveR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_INCLUSIVE);
            }
        });

        transR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_TRANS);
            }
        });

        onYesConflictsR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_ONYES_CONFLICTS);
            }
        });

        onYesIsThereR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_ONYES_IS_THERE);
            }
        });

        conflicts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesConflicts[i] == 1) {
                    optionalOnYesConflicts.setVisibility(View.VISIBLE);
                } else {
                    optionalOnYesConflicts.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        isThere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesIsThere[i] == 1) {
                    optionalOnYesIsThere.setVisibility(View.VISIBLE);
                } else {
                    optionalOnYesIsThere.setVisibility(View.GONE);
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
            valuesFairLand = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_FAIR_LAND,
                    valuesFairLand, fairLand, true);


        } else if (tableKey == MyConstants.DL_GOVERNANCE_INCLUSIVE) {
            valuesInclusive = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_INCLUSIVE,
                    valuesInclusive, inclusive, true);

        } else if (tableKey == MyConstants.DL_GOVERNANCE_TRANS) {
            valuesTrans = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_TRANS,
                    valuesTrans, trans, true);

        } else if (tableKey == MyConstants.DL_GOVERNANCE_CONFLICTS) {
            valuesConflicts = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_CONFLICTS,
                    valuesConflicts, conflicts, false);

        } else if (tableKey == MyConstants.DL_GOVERNANCE_ONYES_CONFLICTS) {
            valuesOnYesConflicts = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_ONYES_CONFLICTS,
                    valuesOnYesConflicts, onYesConflicts, true);

        } else if (tableKey == MyConstants.DL_GOVERNANCE_IS_THERE) {
            valuesIsThere = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_IS_THERE,
                    valuesIsThere, isThere, false);

        } else if (tableKey == MyConstants.DL_GOVERNANCE_ONYES_IS_THERE) {
            valuesOnYesIsThere = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_ONYES_IS_THERE,
                    valuesOnYesIsThere, onYesIsThere, true);

        } else if (tableKey == MyConstants.ALL) {
            valuesFairLand = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_FAIR_LAND,
                    valuesFairLand, fairLand, true);

            valuesInclusive = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_INCLUSIVE,
                    valuesInclusive, inclusive, true);

            valuesTrans = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_TRANS,
                    valuesTrans, trans, true);

            valuesConflicts = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_CONFLICTS,
                    valuesConflicts, conflicts, false);

            valuesOnYesConflicts = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_ONYES_CONFLICTS,
                    valuesOnYesConflicts, onYesConflicts, true);

            valuesIsThere = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_IS_THERE,
                    valuesIsThere, isThere, false);

            valuesOnYesIsThere = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_ONYES_IS_THERE,
                    valuesOnYesIsThere, onYesIsThere, true);


        }
    }

}