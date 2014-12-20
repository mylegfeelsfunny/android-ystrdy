package com.twobros.hatebyte.ystrdy.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;

import java.util.Date;

/**
 * Created by scott on 12/8/14.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    private Context context = null;

    @Override
    public void onReceive(Context c, Intent intent) {
        context = c;
//        databaseHelper = new RecordDatabaseAPI(context.getApplicationContext(), "LocationRecord.db");
//        databaseAPI = new RecordDatabaseAPI(databaseHelper);




        Location location = currentLocation();

        Date yesterday = YstrDate.ystrdy();
        Log.i(TAG, location.getLatitude()+","+location.getLongitude()+","+yesterday.getTime()/1000);

//        temperatureNow = new YahooAPI(context, this);
        //temperatureNow.requestWeatherData();

//        temperatureYstrdy = new ForcastioAPI(context, this);
//        temperatureYstrdy.requestWeatherData();


//        try {
//            databaseAPI.insertRecord(location.getLatitude(), location.getLongitude(), new Date(), 32.3f, "bridgewater",  false);
//        } catch (InvalidPropertiesFormatException e) {
//            Log.e(TAG, "InvalidPropertiesFormatException : " + e);
//        }
    }

    public void onCurrentTemperatureRecieved(RecordGateway record) {
//        String toastMess = "City ["+ record.cityName +"] Region["+record.regionName+"] Current temp ["+record.temperature+"] id["+record.woeid+"]";
//        Toast.makeText(context, toastMess, Toast.LENGTH_SHORT).show();
    }

    public void onCurrentTemperatureError() {

    }

    public void onYstrdyTemperatureRecieved(RecordGateway record) {
//        String toastMess = "City ["+ record.cityName +"] Region["+record.regionName+"] Current temp ["+record.temperature+"] id["+record.woeid+"]";
//        Toast.makeText(context, toastMess, Toast.LENGTH_SHORT).show();
    }

    public void onYstrdytTemperatureError() {

    }

    private Location currentLocation() {
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location netLoc = null;
        Location gpsLoc = null;
        Location finalLoc = null;

        if (gpsEnabled) {
            gpsLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (networkEnabled) {
            netLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (gpsLoc != null && netLoc != null) {
            if (gpsLoc.getAccuracy() >= netLoc.getAccuracy()) {
                finalLoc = gpsLoc;
            } else {
                finalLoc = netLoc;
            }
        } else {
            if (gpsLoc != null) {
                finalLoc = netLoc;
            } else if (netLoc != null) {
                finalLoc = gpsLoc;
            }
        }
        return finalLoc;
    }






}
