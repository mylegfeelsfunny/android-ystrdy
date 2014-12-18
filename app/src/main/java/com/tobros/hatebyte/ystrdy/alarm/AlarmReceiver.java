package com.tobros.hatebyte.ystrdy.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.yahooweather.YahooAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;
import com.tobros.hatebyte.ystrdy.date.YstrDate;

import java.util.Date;

/**
 * Created by scott on 12/8/14.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    private LocationManager locationManager;
    private Context context = null;
    YahooAPI temperatureNow = null;

    @Override
    public void onReceive(Context c, Intent intent) {
        context = c;
//        databaseHelper = new RecordDatabaseAPI(context.getApplicationContext(), "LocationRecord.db");
//        databaseAPI = new RecordDatabaseAPI(databaseHelper);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Date yesterday = new Date();
        yesterday.setTime(yesterday.getTime() - YstrDate.twentyFourHours());
        Log.i(TAG, location.getLatitude()+","+location.getLongitude()+","+yesterday.getTime()/1000);

//        temperatureNow = new YahooAPI(context, this);
        //temperatureNow.request();

//        temperatureYstrdy = new ForcastioAPI(context, this);
//        temperatureYstrdy.request();


//        try {
//            databaseAPI.insertRecord(location.getLatitude(), location.getLongitude(), new Date(), 32.3f, "bridgewater",  false);
//        } catch (InvalidPropertiesFormatException e) {
//            Log.e(TAG, "InvalidPropertiesFormatException : " + e);
//        }
    }

    public void onCurrentTemperatureRecieved(RecordEG record) {
//        String toastMess = "City ["+ record.cityName +"] Region["+record.regionName+"] Current temp ["+record.temperature+"] id["+record.woeid+"]";
//        Toast.makeText(context, toastMess, Toast.LENGTH_SHORT).show();
    }

    public void onCurrentTemperatureError() {

    }


    public void onYstrdyTemperatureRecieved(RecordEG record) {
//        String toastMess = "City ["+ record.cityName +"] Region["+record.regionName+"] Current temp ["+record.temperature+"] id["+record.woeid+"]";
//        Toast.makeText(context, toastMess, Toast.LENGTH_SHORT).show();
    }

    public void onYstrdytTemperatureError() {

    }









}
