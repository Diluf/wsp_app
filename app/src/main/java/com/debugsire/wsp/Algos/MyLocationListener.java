package com.debugsire.wsp.Algos;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.debugsire.wsp.CboBasicDetails;

public class MyLocationListener implements LocationListener {

    private static final String TAG = "MyLocationListener";

    LocationManager locationManager;
    Context context;

    public MyLocationListener(Context context, LocationManager locationManager) {
        this.context = context;
        this.locationManager = locationManager;
    }


    @Override
    public void onLocationChanged(Location location) {
        double latitude = roundValue(location.getLatitude());
        double longitude = roundValue(location.getLongitude());
        double altitude = roundValue(location.getAltitude());
        float accuracy = location.getAccuracy();
        long time = location.getTime();

        if (context instanceof CboBasicDetails) {
            ((CboBasicDetails) context).setLocationDetails(latitude, longitude, altitude, accuracy, time);
        }

        locationManager.removeUpdates(this);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    /////
    /////
    /////
    /////
    /////

    public static double roundValue(double value) {
        long factor = (long) Math.pow(10, 4);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}