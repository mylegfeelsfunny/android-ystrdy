package com.twobros.hatebyte.ystrdy;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.twobros.hatebyte.ystrdy.alarm.AlarmReceiver;
import com.twobros.hatebyte.ystrdy.weatherreport.boundary.IWeatherReportBoundary;
import com.twobros.hatebyte.ystrdy.weatherreport.boundary.WeatherReportBoundary;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.implementation.SQLDatabaseEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;


public class MainActivity extends Activity implements IWeatherReportBoundary {
    private static final String TAG = " MainActivity";

    private LocationManager locationManager;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    WeatherReportBoundary weatherReportBoundary;

    private TextView tempTextView;
    private TextView numRecordsTextView;
    private TextView numDifferencesTextView;

    private SQLDatabaseEGI sqlDatabaseEGI = SQLDatabaseEGI.getInstance();
    private RecordGateway recordGateway = new RecordGateway();
    private DifferenceGateway differenceGateway = new DifferenceGateway();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempTextView = (TextView) findViewById(R.id.tempTextView);
        numRecordsTextView = (TextView) findViewById(R.id.numRecordsTextView);
        numDifferencesTextView = (TextView) findViewById(R.id.numDifferencesTextView);

        // getTempDifference
            // is there a record less than 2 hours -> YES -> fetch YstrdyRecord -> return YstrdyRecord
                // is there a record older than 24 hours -> YES - fetch NowRecord from 24 hours -> hit Yahoo -> save Yahoo NowRecord -> create YstrdyRecord -> return YstrdyRecord
                    // hit Yahoo -> -> save Yahoo NowRecord -> hit forcast.io -> create forcast.io NowRecord -> YstrdyRecord -> return YstrdyRecord

        // Retrieve a PendingIntent to perform a broadcast
        alarmManager                    = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent              = new Intent(this, AlarmReceiver.class);
        pendingIntent                   = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        long oneHour                    = (60 * 60 + 1) * 1000;

        alarmManager.cancel(pendingIntent); // cancel any existing alarms
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 1000,
                oneHour, pendingIntent);



//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), oneHour, pendingIntent);
    }

    public void resetDB(View view) {
        sqlDatabaseEGI.clear();
        numRecordsTextView.setText("numRecords = " + recordGateway.numRecords());
        numDifferencesTextView.setText("numRecords = " + differenceGateway.numDifferenceRecords());
    }

    @Override
    public void onResume() {
        super.onResume();
        numRecordsTextView.setText("numRecords = " + recordGateway.numRecords());
        numDifferencesTextView.setText("numRecords = " + differenceGateway.numDifferenceRecords());
        tempTextView.setText("fetching...");

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.location = location;

        weatherReportBoundary = new WeatherReportBoundary();
        weatherReportBoundary.fetchReport(this, weatherRequest);

    }

    public void onWeatherReportReturned(WeatherResponse weatherResponse) {
        tempTextView.setText("difference = "+weatherResponse.difference);
        Toast.makeText(this, weatherResponse.logString, Toast.LENGTH_SHORT).show();
        numRecordsTextView.setText("numRecords = " + recordGateway.numRecords());
        numDifferencesTextView.setText("numDifferences = " + differenceGateway.numDifferenceRecords());
    }

    public void onWeatherReportFailed() {
        tempTextView.setText("FAILED!!");
    }


    // service connect interface
    ILocationRecordInterface locationRecordInterface = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            locationRecordInterface = ILocationRecordInterface.Stub.asInterface(service);

//            try {
//                Location l = locationRecordInterface.getLastLocation();
//                Log.i(TAG, l.getLatitude() + ", " + l.getLongitude());
//            } catch (RemoteException e) {
//                Log.e(TAG, "RemoteException : "+e);
//            } catch (NullPointerException nulle) {
//                Log.e(TAG, "NullPointerException : "+nulle);
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            locationRecordInterface = null;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
