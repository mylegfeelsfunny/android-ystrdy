package com.tobros.hatebyte.ystrdy.network.yahooweather;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.provider.yahooweather.YahooProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.NowRecordEG;

import java.util.Date;
import java.util.List;


/**
 * Created by scott on 12/9/14.
 */
public class TemperatureNow {

    public interface TemperaturNowDelegate {
        public void onCurrentTemperatureRecieved(NowRecordEG record);
        public void onCurrentTemperatureError();
    }

    private TemperaturNowDelegate delegate = null;
    private NowRecordEG record = null;
    private static final String YAHOO_API_KEY = "dj0yJmk9YnhCTWNBcU1YUG53JmQ9WVdrOVZYZzFPSEpETTJNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD00Mw--";
    private static final String TAG = "FetchTemperatureNow";
    private String weatherYQL = "http://query.yahooapis.com/v1/public/yql?q=QUERY&format=json&callback=?";

    final WeatherConfig yahooConfig = new WeatherConfig();
    private LocationManager locationManager;
    WeatherClient yahooClient = null;

    private Context context = null;
    private City city = null;

    public TemperatureNow(Context c, TemperaturNowDelegate d) {
        super();
        context = c;
        delegate = d;

        try {
            yahooConfig.ApiKey = YAHOO_API_KEY;
            yahooConfig.unitSystem = WeatherConfig.UNIT_SYSTEM.I;
            yahooClient = (new WeatherClient.ClientBuilder())
                    .attach(context)
                    .config(yahooConfig)
                    .provider(new YahooProviderType())
                    .httpClient(WeatherClientDefault.class)
                    .build();

        } catch (Throwable throwable) {
            Log.i(TAG, "Throwable : "+throwable);
        }
    }

    public void request()
    {
        record = null;
        getLocationId();
    }

    public void getLocationId() {
//        String sql="SELECT%20*%20FROM%20geo.placefinder%20WHERE%20text%3D%22"+ location.getLatitude() +"%2C"+ location.getLongitude() +"%22%20and%20gflags%3D%22R%22";
//        String query = "https://query.yahooapis.com/v1/public/yql?q="+ sql;
//        Log.i(TAG, query);

        try {

            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            final Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            yahooClient.searchCity(location.getLatitude(), location.getLongitude(), new WeatherClient.CityEventListener() {
                @Override
                public void onCityListRetrieved(List<City> cityList) {
                    // Here your logic when the data is available
                    city = cityList.get(cityList.size() - 1);
//                    record.latitude = (float) location.getLatitude();
//                    record.longitude = (float) location.getLongitude();
                    getTemperature(city.getId());
                }

                @Override
                public void onWeatherError(WeatherLibException wle) {

                }

                @Override
                public void onConnectionError(Throwable t) {

                }
            });

        } catch (Throwable throwable) {
            Log.i(TAG, "Throwablen : "+throwable);
        }
    }

    private void getTemperature(String locationId) {

        try {

            yahooClient.getCurrentCondition(new WeatherRequest(locationId), new WeatherClient.WeatherEventListener() {
                @Override
                public void onWeatherRetrieved(CurrentWeather currentWeather) {

//                    record.cityName = city.getName();
//                    record.regionName = city.getRegion();
//                    record.temperature = currentWeather.weather.temperature.getTemp();
//                    record.woeid = city.getId();
//                    record.date = new Date();

                    delegate.onCurrentTemperatureRecieved(record);
                }

                @Override
                public void onWeatherError(WeatherLibException e) {
                    Log.d("WL", "Weather Error - parsing data");
                    e.printStackTrace();
                }

                @Override
                public void onConnectionError(Throwable throwable) {
                    Log.d("WL", "Connection error");
                    throwable.printStackTrace();
                }
            });








//            WeatherRequest wr = new WeatherRequest((float)location.getLatitude(), (float)location.getLongitude());
//            forcastioClient.getHistoricalWeather(wr, yesterday, yesterdayPlus, new WeatherClient.HistoricalWeatherEventListener() {
//                @Override
//                public void onWeatherRetrieved(HistoricalWeather historicalWeather) {
//                    float currentTemp = historicalWeather.getHistoricalHourWeather(0).weather.temperature.getTemp();
//                    String toastMess = "City["+ city +"]  HISTORY temperature["+temperature+"]";
//                    Toast.makeText(context, toastMess, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onWeatherError(WeatherLibException e) {
//                    Log.d("WL", "Weather Error - parsing data");
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onConnectionError(Throwable throwable) {
//                    Log.d("WL", "Connection error");
//                    throwable.printStackTrace();
//                }
//            });

        }
        catch (Throwable t) {
            t.printStackTrace();
        }

    }



}
