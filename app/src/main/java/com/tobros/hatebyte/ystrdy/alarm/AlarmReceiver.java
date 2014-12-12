package com.tobros.hatebyte.ystrdy.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.NowRecordEG;
import com.tobros.hatebyte.ystrdy.date.YstrDate;
import com.tobros.hatebyte.ystrdy.network.yahooweather.TemperatureNow;
import com.tobros.hatebyte.ystrdy.network.yahooweather.TemperatureYstrdy;

import java.util.Date;

/**
 * Created by scott on 12/8/14.
 */
public class AlarmReceiver extends BroadcastReceiver implements
        TemperatureNow.TemperaturNowDelegate,
        TemperatureYstrdy.TemperaturYstrdyDelegate {

    private static final String TAG = "AlarmReceiver";

    RecordDatabaseAPI dbConnector;
    RecordDatabaseAPI databaseHelper;
    private LocationManager locationManager;
    private Context context = null;
    TemperatureNow temperatureNow = null;
    TemperatureYstrdy temperatureYstrdy = null;

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

        temperatureNow = new TemperatureNow(context, this);
        //temperatureNow.request();

        temperatureYstrdy = new TemperatureYstrdy(context, this);
        temperatureYstrdy.request();


//        try {
//            databaseAPI.insertNowRecord(location.getLatitude(), location.getLongitude(), new Date(), 32.3f, "bridgewater",  false);
//        } catch (InvalidPropertiesFormatException e) {
//            Log.e(TAG, "InvalidPropertiesFormatException : " + e);
//        }
    }

    public void onCurrentTemperatureRecieved(NowRecordEG record) {
//        String toastMess = "City ["+ record.cityName +"] Region["+record.regionName+"] Current temp ["+record.temperature+"] id["+record.woeid+"]";
//        Toast.makeText(context, toastMess, Toast.LENGTH_SHORT).show();
    }

    public void onCurrentTemperatureError() {

    }


    public void onYstrdyTemperatureRecieved(NowRecordEG record) {
//        String toastMess = "City ["+ record.cityName +"] Region["+record.regionName+"] Current temp ["+record.temperature+"] id["+record.woeid+"]";
//        Toast.makeText(context, toastMess, Toast.LENGTH_SHORT).show();
    }

    public void onYstrdytTemperatureError() {

    }









}
