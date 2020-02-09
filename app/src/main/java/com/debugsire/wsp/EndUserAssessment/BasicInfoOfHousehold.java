package com.debugsire.wsp.EndUserAssessment;

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

public class BasicInfoOfHousehold extends AppCompatActivity {

    Context context;
    Integer[] valuesDesig, valuesGender, valuesPreLan;
    Methods methods;

    Spinner desig, gender, preLan;
    Button desigR, genderR, preLanR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info_of_household);
        context = this;
        methods = new Methods();

        desig = findViewById(R.id.spinner_desig_BasicInfo);
        gender = findViewById(R.id.spinner_gender_BasicInfo);
        preLan = findViewById(R.id.spinner_pre_BasicInfo);

        desigR = findViewById(R.id.btn_rDesig_BasicInfo);
        genderR = findViewById(R.id.btn_rGender_BasicInfo);
        preLanR = findViewById(R.id.btn_rPre_BasicInfo);


        setSpinnerValues(MyConstants.ALL);

        desigR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_BASIC_INFO_DESIG);
            }
        });


        genderR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_BASIC_INFO_GENDER);
            }
        });


        preLanR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_BASIC_INFO_PRE_LAN);
            }
        });

    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_BASIC_INFO_DESIG) {
            valuesDesig = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_DESIG,
                    valuesDesig, desig, false);

        } else if (tableKey == MyConstants.DL_BASIC_INFO_GENDER) {
            valuesGender = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_GENDER,
                    valuesGender, gender, false);

        } else if (tableKey == MyConstants.DL_BASIC_INFO_PRE_LAN) {
            valuesPreLan = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_PRE_LAN,
                    valuesPreLan, preLan, false);

        } else if (tableKey == MyConstants.ALL) {
            valuesDesig = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_DESIG,
                    valuesDesig, desig, false);

            valuesGender = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_GENDER,
                    valuesGender, gender, false);

            valuesPreLan = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_PRE_LAN,
                    valuesPreLan, preLan, false);

        }
    }
}