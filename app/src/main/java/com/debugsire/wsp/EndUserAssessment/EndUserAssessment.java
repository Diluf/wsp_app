package com.debugsire.wsp.EndUserAssessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.debugsire.wsp.R;
import com.debugsire.wsp.WaterSafetyAndClimate.ExistingQa;
import com.debugsire.wsp.WaterSafetyAndClimate.Observation;

public class EndUserAssessment extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_user_assessment);

        context = this;


        ((Button) findViewById(R.id.btn_basicInfo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, BasicInfoOfHousehold.class));
            }
        });

        ((Button) findViewById(R.id.btn_waterAd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, WaterAdequacy.class));
            }
        });

        ((Button) findViewById(R.id.btn_waterQ)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, WaterQuality.class));
            }
        });

        ((Button) findViewById(R.id.btn_householdStorage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, HouseholdStorage.class));
            }
        });

        ((Button) findViewById(R.id.btn_waterHealth)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, WaterHealth.class));
            }
        });

        ((Button) findViewById(R.id.btn_waterSaving)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, WaterSaving.class));
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
