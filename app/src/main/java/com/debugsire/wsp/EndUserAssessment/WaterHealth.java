package com.debugsire.wsp.EndUserAssessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

public class WaterHealth extends AppCompatActivity {

    Context context;
    Integer[] valuesDidAnyone, valuesDidYou;
    Methods methods;

    Spinner didAnyone, didYou;
    Button didAnyoneR, didYouR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_health);
        context = this;
        methods = new Methods();

        didAnyone = findViewById(R.id.spinner_didAnyone_WaterHealth);
        didYou = findViewById(R.id.spinner_didYou_WaterHealth);

        didAnyoneR = findViewById(R.id.btn_rDidAnyone_WaterHealth);
        didYouR = findViewById(R.id.btn_rDidYou_WaterHealth);

        setSpinnerValues(MyConstants.ALL);

        didAnyoneR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_HEALTH_DID_ANY);
            }
        });

        didYouR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_HEALTH_DID_YOU);
            }
        });

    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_WATER_HEALTH_DID_ANY) {
            valuesDidAnyone = methods.setSpinnerThings(context, MyConstants.DL_WATER_HEALTH_DID_ANY,
                    valuesDidAnyone, didAnyone, false);

        } else if (tableKey == MyConstants.DL_WATER_HEALTH_DID_YOU) {
            valuesDidYou = methods.setSpinnerThings(context, MyConstants.DL_WATER_HEALTH_DID_YOU,
                    valuesDidYou, didYou, false);

        } else if (tableKey == MyConstants.ALL) {
            valuesDidAnyone = methods.setSpinnerThings(context, MyConstants.DL_WATER_HEALTH_DID_ANY,
                    valuesDidAnyone, didAnyone, false);

            valuesDidYou = methods.setSpinnerThings(context, MyConstants.DL_WATER_HEALTH_DID_YOU,
                    valuesDidYou, didYou, false);


        }
    }
}