package com.debugsire.wsp.WaterSafetyAndClimate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.debugsire.wsp.R;

public class WaterSafetyAndClimate extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_safety_and_climate);

        context = this;

        ((Button) findViewById(R.id.btn_exQA)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ExistingQa.class));
            }
        });


        ((Button) findViewById(R.id.btn_catch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Catchment.class));
            }
        });


        ((Button) findViewById(R.id.btn_treat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Treatment.class));
            }
        });


        ((Button) findViewById(R.id.btn_dist)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Distribution.class));
            }
        });


        ((Button) findViewById(R.id.btn_cli)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ClimateAndDdr.class));
            }
        });


        ((Button) findViewById(R.id.btn_gov)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Governance.class));
            }
        });


        ((Button) findViewById(R.id.btn_obs)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Observation.class));
            }
        });



    }
}
