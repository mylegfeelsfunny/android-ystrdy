package com.tobros.hatebyte.ystrdy.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.tobros.hatebyte.ystrdy.database.LocationRecordDbHelper;
import com.tobros.hatebyte.ystrdy.database.LocationRecordsDbConnector;
import com.tobros.hatebyte.ystrdy.location.GPSService;
import com.tobros.hatebyte.ystrdy.network.yahooweather.FetchTemperatureNow;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/8/14.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    LocationRecordsDbConnector dbConnector;
    LocationRecordDbHelper databaseHelper;
    private LocationManager locationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        databaseHelper = new LocationRecordDbHelper(context.getApplicationContext(), "LocationRecord.db");
        dbConnector = new LocationRecordsDbConnector(databaseHelper);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        FetchTemperatureNow ft = new FetchTemperatureNow();
        ft.getTemperature(location);

        try {
            dbConnector.insertLocationRecord(location.getLatitude(), location.getLongitude(), new Date(), 32.3f, "bridgewater",  false);
        } catch (InvalidPropertiesFormatException e) {
            Log.e(TAG, "InvalidPropertiesFormatException : " + e);
        }
    }





}
