package com.twobros.hatebyte.ystrdy;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.twobros.hatebyte.ystrdy.weatherreport.boundary.IWeatherReportBoundary;
import com.twobros.hatebyte.ystrdy.weatherreport.boundary.WeatherReportBoundary;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.implementation.SQLDatabaseEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;


public class MainActivity extends Activity implements IWeatherReportBoundary {
    private static final String TAG = " MainActivity";
    private static final String HAS_SET_ALARM = "MainActivity has_set_alarm";

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
    }

    public void resetDB(View view) {
        sqlDatabaseEGI.clear();
        numRecordsTextView.setText("numRecords = " + recordGateway.numRecords());
        numDifferencesTextView.setText("numRecords = " + differenceGateway.numDifferenceRecords());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!hasTriggeredAlarm()) {
            triggerAlarm();
        }

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

    private void triggerAlarm() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();  // Put the values from the UI
        editor.putBoolean(HAS_SET_ALARM, true); // value to store
        editor.commit();

        Intent intent = new Intent("com.twobros.hatebyte.ystrdy.MY_TIMER");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long now = System.currentTimeMillis();
        long interval = 60 * 60 * 1000; // 1 hour
        alarmManager.cancel(pendingIntent); // cancel any existing alarms
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, now, interval, pendingIntent); // Schedule timer for one hour from now and every hour after that
    }

    private Boolean hasTriggeredAlarm() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        return preferences.getBoolean(HAS_SET_ALARM, false);
    }

    public void onWeatherReportReturned(WeatherResponse weatherResponse) {
        tempTextView.setText("difference = "+weatherResponse.difference + "\n\n" + weatherResponse.today.toString() + "\n\n" + weatherResponse.ystrday.toString());
        Toast.makeText(this, weatherResponse.logString, Toast.LENGTH_SHORT).show();
        numRecordsTextView.setText("numRecords = " + recordGateway.numRecords());
        numDifferencesTextView.setText("numDifferences = " + differenceGateway.numDifferenceRecords());
    }

    public void onWeatherReportFailed() {
        tempTextView.setText("FAILED!!");
    }

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
