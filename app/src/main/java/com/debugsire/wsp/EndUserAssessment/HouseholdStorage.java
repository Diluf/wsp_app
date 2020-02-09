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

public class HouseholdStorage extends AppCompatActivity {

    Context context;
    Integer[] valuesHowStore, valuesIsThe, valuesHowOften, valuesHowClean;
    Methods methods;

    Spinner howStore, isThe, howOften, howClean;
    Button howStoreR, isTheR, howOftenR, howCleanR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_storage);
        context = this;
        methods = new Methods();

        howStore = findViewById(R.id.spinner_howDoYouStore_HouseHoldStorage);
        isThe = findViewById(R.id.spinner_isTheDrinking_HouseHoldStorage);
        howOften = findViewById(R.id.spinner_howOften_HouseHoldStorage);
        howClean = findViewById(R.id.spinner_howDoYouClean_HouseHoldStorage);

        howStoreR = findViewById(R.id.btn_rHowDoYouStore_HouseHoldStorage);
        isTheR = findViewById(R.id.btn_rIsTheDrinking_HouseHoldStorage);
        howOftenR = findViewById(R.id.btn_rHowOften_HouseHoldStorage);
        howCleanR = findViewById(R.id.btn_rHowDoYouClean_HouseHoldStorage);

        setSpinnerValues(MyConstants.ALL);

        howStoreR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_STORE);
            }
        });

        isTheR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_HOUSE_HOLD_IS_THE_D);
            }
        });

        howOftenR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_HOUSE_HOLD_HOW_OFTEN);
            }
        });

        howCleanR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_CLEAN);
            }
        });

    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_STORE) {
            valuesHowStore = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_STORE,
                    valuesHowStore, howStore, true);

        } else if (tableKey == MyConstants.DL_HOUSE_HOLD_IS_THE_D) {
            valuesIsThe = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_IS_THE_D,
                    valuesIsThe, isThe, false);

        } else if (tableKey == MyConstants.DL_HOUSE_HOLD_HOW_OFTEN) {
            valuesHowOften = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_OFTEN,
                    valuesHowOften, howOften, true);

        } else if (tableKey == MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_CLEAN) {
            valuesHowClean = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_CLEAN,
                    valuesHowClean, howClean, true);

        } else if (tableKey == MyConstants.ALL) {
            valuesHowStore = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_STORE,
                    valuesHowStore, howStore, true);

            valuesIsThe = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_IS_THE_D,
                    valuesIsThe, isThe, false);

            valuesHowOften = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_OFTEN,
                    valuesHowOften, howOften, true);

            valuesHowClean = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_CLEAN,
                    valuesHowClean, howClean, true);


        }
    }
}