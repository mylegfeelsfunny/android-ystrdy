package com.tobros.hatebyte.ystrdy.network.yahooweather;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.LocationProviderNotFoundException;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.provider.yahooweather.YahooProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by scott on 12/9/14.
 */
public class TemperatureNow {

    public interface TemperaturNowDelegate {
        public void onCurrentTemperatureRecieved(float temperature, String id, String city, String region);
        public void onCurrentTemperatureError();
    }

    private static final String YAHOO_APP_ID = "dj0yJmk9YnhCTWNBcU1YUG53JmQ9WVdrOVZYZzFPSEpETTJNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD00Mw--";
    private static final String TAG = "FetchTemperatureNow";
    private String weatherYQL = "http://query.yahooapis.com/v1/public/yql?q=QUERY&format=json&callback=?";

    final WeatherConfig config = new WeatherConfig();
    WeatherClient client = null;

    private Context context = null;
    private City city = null;
    private float temperature;
    private TemperaturNowDelegate delegate = null;

    public TemperatureNow(Context c, TemperaturNowDelegate d) {
        super();
        context = c;
        delegate = d;
        config.ApiKey = YAHOO_APP_ID;
        config.unitSystem = WeatherConfig.UNIT_SYSTEM.I;

        try {
            client = (new WeatherClient.ClientBuilder())
                    .attach(context)
                    .config(config)
                    .provider(new YahooProviderType())
                    .httpClient(WeatherClientDefault.class)
                    .build();
        } catch (Throwable throwable) {
            Log.i(TAG, "Throwable : "+throwable);
        }
    }

    public void request() {
        getLocationId();
    }

    public void getLocationId() {
//        String sql="SELECT%20*%20FROM%20geo.placefinder%20WHERE%20text%3D%22"+ location.getLatitude() +"%2C"+ location.getLongitude() +"%22%20and%20gflags%3D%22R%22";
//        String query = "https://query.yahooapis.com/v1/public/yql?q="+ sql;
//        Log.i(TAG, query);

        try {
            Criteria criteria = new Criteria();
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setCostAllowed(false);
            client.searchCityByLocation(criteria, new WeatherClient.CityEventListener() {
                @Override
                public void onCityListRetrieved(List<City> cityList) {
                    // Here your logic when the data is available
                    city= (City) cityList.get(cityList.size()-1);
                    getTemperature(city.getId());
                }

                @Override
                public void onWeatherError(WeatherLibException wle) {

                }

                @Override
                public void onConnectionError(Throwable t) {

                }
            });

        } catch(LocationProviderNotFoundException lpnfe) {
            Log.i(TAG, "LocationProviderNotFoundException : "+lpnfe);
        } catch (Throwable throwable) {
            Log.i(TAG, "Throwablen : "+throwable);
        }
    }

    private void getTemperature(String locationId) {

        try {
            client.getCurrentCondition(new WeatherRequest(locationId), new WeatherClient.WeatherEventListener() {
                @Override
                public void onWeatherRetrieved(CurrentWeather currentWeather) {
                    float currentTemp = currentWeather.weather.temperature.getTemp();
                    delegate.onCurrentTemperatureRecieved(currentTemp, city.getId(), city.getName(), city.getRegion());
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
        }
        catch (Throwable t) {
            t.printStackTrace();
        }

    }



}
