package com.tobros.hatebyte.ystrdy.location;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.tobros.hatebyte.ystrdy.ILocationRecordInterface;

/**
 * Created by scott on 12/4/14.
 */
public class GPSService extends Service {

    private static final String TAG = "GPSService";
    public static final String GPS_SERVICE = "com.tobros.hatebyte.ystrdy.location.aidl.GPSService.SERVICE";
    public static final String UPDATE_RATE = "com.tobros.hatebyte.ystrdy.location.gpsservice.update_rate";
    private LocationManager locationManager;
    private long updateRate = -1;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        updateRate = intent.getIntExtra(UPDATE_RATE, -1);
        if (updateRate == -1) {
            updateRate = 5000;
        }

//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
//        criteria.setPowerRequirement(Criteria.POWER_LOW);
        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

        // best provider
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, updateRate, 0, trackListener);

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        if (locationManager != null) {
            locationManager.removeUpdates(trackListener);
            locationManager = null;
        }

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return locationInterfaceBinder;
    }

    private final ILocationRecordInterface.Stub locationInterfaceBinder = new ILocationRecordInterface.Stub() {
        @Override
        public Location getLastLocation() throws RemoteException {
        return currentLocation;
        }
    };

    private Location currentLocation = null;
    private long lastTime = -1;

    private LocationListener trackListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;
            Log.i(TAG, "Latitude:" + currentLocation.getLatitude() + ", Longitude:" + currentLocation.getLongitude());
            Toast.makeText(getApplicationContext(), "Latitude:" + currentLocation.getLatitude() + ", Longitude:" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
