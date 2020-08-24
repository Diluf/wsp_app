package com.debugsire.wsp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.MyLocationListener;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class CboBasicDetails extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private static final String TAG = "--------";
    Context context;
    Integer[] values;
    Methods methods;

    TextInputLayout cboName, ass, road, village, town;
    TextView lon, lat, alt, acc, lastUpdated;
    Spinner managementOfWSS;
    Button resynchWSS, refGPS, save;
    ImageView loaderGPS;
    LocationListener locationListener;
    LocationManager locationManager;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbo_basic_details);

        context = this;
        methods = new Methods();
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener(context, locationManager);

        initCompos();
        setEvents();
        setSpinnerValues();
        loadFields();


    }


    private void loadFields() {
        Cursor data;
        if (methods.isAvailOnDB("cboBasicDetailsFilled")) {
            data = methods.getCursor("cboBasicDetailsFilled");
        } else {
            data = methods.getCursorBySelectedCBONum(context, "cboBasicDetails");
        }

        while (data.moveToNext()) {
            cboName.getEditText().setText(data.getString(data.getColumnIndex("name")));
            road.getEditText().setText(data.getString(data.getColumnIndex("road")));
            village.getEditText().setText(data.getString(data.getColumnIndex("village")));
            town.getEditText().setText(data.getString(data.getColumnIndex("town")));
            lon.setText(data.getString(data.getColumnIndex("lon")));
            lat.setText(data.getString(data.getColumnIndex("lat")));
            alt.setText(data.getString(data.getColumnIndex("height")));
            acc.setText(data.getString(data.getColumnIndex("acc")));

            if (methods.isAvailOnDB("cboBasicDetailsFilled")) {
                methods.setSelectedItemForSpinner(data.getInt(data.getColumnIndex("manWss")), values, managementOfWSS);
            }
            id = data.getString(data.getColumnIndex("id"));

        }
    }

    private void setEvents() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean tilFieldsNull = methods.isTILFieldsNull(context,
                        cboName, town);
                boolean spinnerNull = methods.isSpinnerNull(context, managementOfWSS);

                if (!tilFieldsNull && !spinnerNull) {
                    final AlertDialog dialog = methods.getSaveConfirmationDialog(context, false);
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });

                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MyDB.setData("DELETE FROM cboBasicDetailsFilled");
                            MyDB.setData("INSERT INTO cboBasicDetailsFilled VALUES (" +
                                    " '" + id + "', " +
                                    " '" + Methods.getCBONum(context) + "', " +
                                    " '" + Methods.configNull(cboName.getEditText().getText().toString(), "") + "', " +
                                    " '" + Methods.configNull(ass.getEditText().getText().toString(), "") + "', " +
                                    " '" + Methods.configNull(road.getEditText().getText().toString(), "") + "', " +
                                    " '" + Methods.configNull(village.getEditText().getText().toString(), "") + "', " +
                                    " '" + Methods.configNull(town.getEditText().getText().toString(), "") + "', " +
                                    " '" + values[managementOfWSS.getSelectedItemPosition()] + "', " +
                                    " '" + lon.getText().toString().trim() + "', " +
                                    " '" + lat.getText().toString().trim() + "', " +
                                    " '" + alt.getText().toString().trim() + "', " +
                                    " '" + acc.getText().toString().trim() + "', " +
                                    " '" + methods.getLoggedUserNameAsString() + "', " +
                                    " '" + methods.getNowDateTime() + "' " +
                                    ")");
                            methods.showToast(getString(R.string.saved), context, MyConstants.MESSAGE_SUCCESS);
                            onBackPressed();
                        }
                    });
                    dialog.show();


                } else {
                    methods.showToast(getString(R.string.compulsory_cant_empty), context, MyConstants.MESSAGE_ERROR);
                }
            }
        });


        refGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = checkLocationPermission();
                if (b) {
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                    } else {
                        methods.showToast(getString(R.string.PLEASE_ENABLE_GPS), context, MyConstants.MESSAGE_INFO);
                    }
                }
            }
        });

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
        save = findViewById(R.id.btn_SaveCboBasicDetails);
        //
        loaderGPS = findViewById(R.id.image_refCboBasicDetails);
    }

    public void setSpinnerValues() {
        values = methods.setSpinnerThings(context, MyConstants.DL_CBOBASICDETAILS_MANAGEMENT_OF_WSS, values, managementOfWSS, true);
    }


    //////////////
    //////////////
    //////////////
    //////////////
    //////////////
    //////////////


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.LOCATION_PERMISSION_TITLE)
                        .setMessage(R.string.LOCATION_PERMISSION_BODY)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(CboBasicDetails.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            methods.loadAnim(refGPS, loaderGPS, context, false);
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                        } else {
                            methods.showToast(getString(R.string.PLEASE_ENABLE_GPS), context, MyConstants.MESSAGE_INFO);
                        }
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    public void setLocationDetails(double latitude, double longitude, double altitude, float accuracy, long time) {
        lat.setText(latitude + "");
        lon.setText(longitude + "");
        alt.setText(altitude + "");
        acc.setText(accuracy + "");
        lastUpdated.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(time)));
        methods.loadAnim(refGPS, loaderGPS, context, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
