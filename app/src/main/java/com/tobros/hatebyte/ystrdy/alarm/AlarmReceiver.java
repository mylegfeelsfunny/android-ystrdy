package com.tobros.hatebyte.ystrdy.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.tobros.hatebyte.ystrdy.database.LocationRecordDbHelper;
import com.tobros.hatebyte.ystrdy.database.LocationRecordsDbConnector;
import com.tobros.hatebyte.ystrdy.network.yahooweather.TemperatureNow;

/**
 * Created by scott on 12/8/14.
 */
public class AlarmReceiver extends BroadcastReceiver implements TemperatureNow.TemperaturNowDelegate {

    private static final String TAG = "AlarmReceiver";

    LocationRecordsDbConnector dbConnector;
    LocationRecordDbHelper databaseHelper;
    private LocationManager locationManager;
    private Context context = null;
    TemperatureNow temperatureNow = null;

    @Override
    public void onReceive(Context c, Intent intent) {
        context = c;
        databaseHelper = new LocationRecordDbHelper(context.getApplicationContext(), "LocationRecord.db");
        dbConnector = new LocationRecordsDbConnector(databaseHelper);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        temperatureNow = new TemperatureNow(context, this);
        temperatureNow.request();

//        try {
//            dbConnector.insertLocationRecord(location.getLatitude(), location.getLongitude(), new Date(), 32.3f, "bridgewater",  false);
//        } catch (InvalidPropertiesFormatException e) {
//            Log.e(TAG, "InvalidPropertiesFormatException : " + e);
//        }
    }

    public void onCurrentTemperatureRecieved(float temperature, String id, String city, String region) {
        String toastMess = "City ["+ city +"] Region["+region+"] Current temp ["+temperature+"] id["+id+"]";
        Toast.makeText(context, toastMess, Toast.LENGTH_SHORT).show();
    }

    public void onCurrentTemperatureError() {

    }








}
