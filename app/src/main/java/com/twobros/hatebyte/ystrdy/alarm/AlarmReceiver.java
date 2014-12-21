package com.twobros.hatebyte.ystrdy.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.SaveARecordInteractor;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;

/**
 * Created by scott on 12/8/14.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    private Context context = null;
    private SaveARecordInteractor saveARecordInteractor;

    @Override
    public void onReceive(Context c, Intent intent) {
        context = c;

        new SaveRecordTask().execute((Object[]) null);
    }

    private class SaveRecordTask extends AsyncTask<Object, Object, Boolean> {
        @Override
        protected Boolean doInBackground(Object... params) {
            WeatherRequest weatherRequest = new WeatherRequest();
            weatherRequest.location = currentLocation();

            saveARecordInteractor = new SaveARecordInteractor();
            return saveARecordInteractor.saveRecord(weatherRequest);
        }
        @Override
        protected void onPostExecute(Boolean saveComplete) {
            // send back response difference
            if (!saveComplete) {
                Toast.makeText(context, "Save Failed in Background", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Save Complete", Toast.LENGTH_SHORT).show();
            }
        }
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
