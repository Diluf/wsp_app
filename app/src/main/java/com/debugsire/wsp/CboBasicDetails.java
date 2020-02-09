package com.debugsire.wsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.google.android.material.textfield.TextInputLayout;

public class CboBasicDetails extends AppCompatActivity {

    private static final String TAG = "--------";
    Context context;
    Integer[] values;
    Methods methods;

    TextInputLayout cboName, ass, road, village, town;
    TextView lon, lat, alt, acc, lastUpdated;
    Spinner managementOfWSS;
    Button resynchWSS, refGPS;
    ImageView loaderGPS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbo_basic_details);

        context = this;
        methods = new Methods();

        initCompos();
        setEvents();
        setSpinnerValues();
        loadFields();


    }

    private void loadFields() {
        Cursor data = methods.getCursorBySelectedCBONum(context, "cboBasicDetails");
        while (data.moveToNext()) {
            cboName.getEditText().setText(data.getString(data.getColumnIndex("name")));
            road.getEditText().setText(data.getString(data.getColumnIndex("road")));
            village.getEditText().setText(data.getString(data.getColumnIndex("village")));
            town.getEditText().setText(data.getString(data.getColumnIndex("town")));
            lon.setText(data.getString(data.getColumnIndex("lon")));
            lat.setText(data.getString(data.getColumnIndex("lat")));
            alt.setText(data.getString(data.getColumnIndex("height")));
            acc.setText(data.getString(data.getColumnIndex("acc")));
        }
    }

    private void setEvents() {
        resynchWSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CBOBASICDETAILS_MANAGEMENT_OF_WSS);
            }
        });
    }

    private void initCompos() {
        cboName = findViewById(R.id.til_cboNameCboBasicDetails);
        ass = findViewById(R.id.til_assCboBasicDetails);
        road = findViewById(R.id.til_roadCboBasicDetails);
        village = findViewById(R.id.til_villageCboBasicDetails);
        town = findViewById(R.id.til_townCboBasicDetails);
        //
        lon = findViewById(R.id.tv_lonCboBasicDetails);
        lat = findViewById(R.id.tv_latCboBasicDetails);
        alt = findViewById(R.id.tv_altCboBasicDetails);
//        height = findViewById(R.id.tv_heightCboBasicDetails);
        acc = findViewById(R.id.tv_accCboBasicDetails);
        lastUpdated = findViewById(R.id.txt_lastGPSTimeActivityCboBasicDetails);
        //
        managementOfWSS = findViewById(R.id.spinner_managementOfWSS_CboBasicDetails);
        //
        resynchWSS = findViewById(R.id.btn_resynchManagementOfWSS_CboBasicDetails);
        refGPS = findViewById(R.id.btn_refGPSCboBasicDetails);
        //
        loaderGPS = findViewById(R.id.image_refCboBasicDetails);
    }

    public void setSpinnerValues() {
        values = methods.setSpinnerThings(context, MyConstants.DL_CBOBASICDETAILS_MANAGEMENT_OF_WSS, values, managementOfWSS, true);
    }

}
