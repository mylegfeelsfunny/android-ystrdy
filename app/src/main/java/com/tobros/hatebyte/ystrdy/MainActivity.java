package com.tobros.hatebyte.ystrdy;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tobros.hatebyte.ystrdy.alarm.AlarmReceiver;
import com.tobros.hatebyte.ystrdy.weatherreport.boundary.IWeatherReportBoundary;
import com.tobros.hatebyte.ystrdy.weatherreport.boundary.WeatherReportBoundary;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio.ForcastioEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.yahooweather.YahooAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class MainActivity extends Activity implements IWeatherReportBoundary {
    private static final String TAG = "MainActivity";

    private LocationManager locationManager;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    WeatherReportBoundary weatherReportBoundary;
    ForcastioEGI forcastioEGI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getTempDifference
            // is there a record less than 2 hours -> YES -> fetch YstrdyRecord -> return YstrdyRecord
                // is there a record older than 24 hours -> YES - fetch NowRecord from 24 hours -> hit Yahoo -> save Yahoo NowRecord -> create YstrdyRecord -> return YstrdyRecord
                    // hit Yahoo -> -> save Yahoo NowRecord -> hit forcast.io -> create forcast.io NowRecord -> YstrdyRecord -> return YstrdyRecord


        // Retrieve a PendingIntent to perform a broadcast
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
//        long threeHours = (3 * 60 * 60 + 1) * 1000;
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), threeHours, pendingIntent);

        //RecordDatabase recordDatabase = new RecordDatabase(getApplicationContext(), "testLocationRecord.db");

//        mockRecordEGI = mock(RecordEGI.class);
        //recordEGI = new RecordEGI(recordDatabase);



        forcastioEGI = new ForcastioEGI();

        yahooAPI = new RecordYahooAPI();
        yahooAPI.configure(YstrdyApp.getContext());

    }

    public void resetDB(View view) {
        //recordEGI.clearDatabase();
        //Toast.makeText(this, "I'm running. + \n" + recordEGI.numRecords() + " records", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.location = location;

        Date ystrday = new Date();
        ystrday.setTime(ystrday.getTime() - YstrDate.twentyFourHours());

        weatherRequest.date = ystrday;

//        WeatherResponseModel weatherResponseModel = forcastioAPISema.request(weatherRequest);

        yahooAPI.getCityInLocation(weatherRequest.location);
//        forcastioGatewayImplementation.request(weatherRequest);

        format();

//        Intent service = new Intent(this, GPSService.class);
//        service.putExtra(GPSService.UPDATE_RATE, 5000);
//        startService(service);
//        bindService(new Intent(ILocationRecordInterface.class.getName()), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void format() {
//        String jsonString= "{\"query\":{\"count\":1,\"created\":\"2014-12-17T21:48:35Z\",\"lang\":\"en-US\",\"results\":{\"Result\":{\"quality\":\"87\"," +
//                "\"addressMatchType\":\"POINT_ADDRESS\",\"latitude\":\"40.722256\",\"longitude\":\"-73.999679\",\"offsetlat\":\"40.722256\",\"offsetlon\":\"-73.999679\"," +
//                "\"radius\":\"400\",\"name\":\"40.7222561,-73.9996793\",\"line1\":\"501 Broadway\",\"line2\":\"New York, NY 10012-4401\",\"line3\":null,\"line4\":\"United States\"," +
//                "\"house\":\"501\",\"street\":\"Broadway\",\"xstreet\":null,\"unittype\":null,\"unit\":null,\"postal\":\"10012-4401\",\"neighborhood\":\"Soho\",\"city\":\"New York\"," +
//                "\"county\":\"New York County\",\"state\":\"New York\",\"country\":\"United States\",\"countrycode\":\"US\",\"statecode\":\"NY\",\"countycode\":\"NY\",\"uzip\":\"10012\"," +
//                "\"hash\":\"31156DE87A326542\",\"woeid\":\"12761344\",\"woetype\":\"11\"}}}}";
//        JSONObject json = null;
//
//        try {
//            json = new JSONObject(jsonString);
//            JSONObject query = (JSONObject) json.get("query");
//            JSONObject results = (JSONObject) query.get("results");
//            JSONObject Result = (JSONObject) results.get("Result");
//            String woeid = (String) Result.get("woeid");
//            Log.i(TAG, "WOEID : " + woeid);
//        } catch (JSONException e) {
//            Log.i(TAG, "json : " + e);
//        }

//        String jsonString= "{\"query\":{\"count\":1,\"created\":\"2014-12-17T21:59:04Z\",\"lang\":\"en-US\",\"results\":{\"channel\":{\"item\":{\"condition\":{\"code\":\"26\"," +
//                "\"date\":\"Wed, 17 Dec 2014 3:50 pm EST\",\"temp\":\"51\",\"text\":\"Cloudy\"}}}}}}";
//        JSONObject json = null;
//
//        try {
//            json = new JSONObject(jsonString);
//            JSONObject query = (JSONObject) json.get("query");
//            JSONObject results = (JSONObject) query.get("results");
//            JSONObject channel = (JSONObject) results.get("channel");
//            JSONObject item = (JSONObject) channel.get("item");
//            JSONObject condition = (JSONObject) item.get("condition");
//
//            float temp = Float.parseFloat((String)condition.get("temp"));
//            Log.i(TAG, "TEMERATURE : " + temp);
//        } catch (JSONException e) {
//            Log.i(TAG, "json : " + e);
//        }

        String jsonString= "{\"latitude\":40.7221881,\"longitude\":-73.9996643,\"timezone\":\"America/New_York\",\"offset\":-5,\"currently\":{\"time\":1418770507," +
                "\"summary\":\"Drizzle\",\"icon\":\"rain\",\"precipIntensity\":0.0067,\"precipProbability\":0.61,\"precipType\":\"rain\",\"temperature\":45.9,\"apparentTemperature\":45.9,"+
                "\"dewPoint\":42.16,\"humidity\":0.87,\"windSpeed\":2.73,\"windBearing\":116,\"visibility\":8,\"cloudCover\":0.91,\"pressure\":1014.11,\"ozone\":285.88}}\n";
        JSONObject json = null;

        try {
            json = new JSONObject(jsonString);
            JSONObject currently = (JSONObject) json.get("currently");
            Double temp = (Double) currently.get("temperature");
            Log.i(TAG, "TEMERATURE : " + temp);
        } catch (JSONException e) {
            Log.i(TAG, "json : " + e);
        }

    }


    // API query using Volley
    private RecordYahooAPI yahooAPI = null;
    class RecordYahooAPI extends YahooAPI {
        @Override
        protected void onYahooCityResponseReceived(YahooCityResponseModel responseModel) {
        }

        @Override
        protected void onYahooCityResponseFailed() {
            onWeatherResponseFailed();
        }

        @Override
        protected  void onWeatherResponseRecieved(YahooWeatherResponseModel responseModel) {
        }

        @Override
        protected void onWeatherResponseFailed() {
            onWeatherResponseFailed();
        }
    }

    public void onWeatherReportReturned(WeatherResponse weatherResponse) {

    }

    public void onWeatherReportFailed() {

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
