package com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.yahooweather;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

//import com.survivingwithandroid.weather.lib.WeatherClient;
//import com.survivingwithandroid.weather.lib.WeatherConfig;
//import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
//import com.survivingwithandroid.weather.lib.model.City;
//import com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault;
//import com.survivingwithandroid.weather.lib.model.CurrentWeather;
//import com.survivingwithandroid.weather.lib.provider.yahooweather.YahooProviderType;
//import com.survivingwithandroid.weather.lib.request.WeatherRequest;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;

import java.util.Date;
import java.util.List;


/**
 * Created by scott on 12/9/14.
 */
public abstract class YahooAPI {

    public class YahooCityResponseModel {
        public String cityName;
        public String regionName;
        public String woeid;
    }
    public class YahooWeatherResponseModel {
        public float temperature;
        public Date date;
    }

    abstract protected void onYahooCityResponseReceived(YahooCityResponseModel responseModel);
    abstract protected void onYahooCityResponseFailed();
    abstract protected void onWeatherResponseRecieved(YahooWeatherResponseModel responseModel);
    abstract protected void onWeatherResponseFailed();

    private RecordEG record = null;
    private static final String YAHOO_API_KEY = "dj0yJmk9YnhCTWNBcU1YUG53JmQ9WVdrOVZYZzFPSEpETTJNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD00Mw--";
    private static final String TAG = " YahooAPI";
    private static final String getTemperatureForLocationId_TAG = " getTemperatureForLocationId";
    private static final String getCityInLocation_TAG = " getCityInLocation";

    private String weatherYQL = "http://query.yahooapis.com/v1/public/yql?q=QUERY&format=json&callback=?";

//    final WeatherConfig yahooConfig = new WeatherConfig();
    private LocationManager locationManager;
//    WeatherClient yahooClient = null;

    private Context context = null;
//    private City city = null;

    public YahooAPI() {}
    public void configure(Context c) {
        context = c;

//        try {
//            yahooConfig.ApiKey = YAHOO_API_KEY;
//            yahooConfig.unitSystem = WeatherConfig.UNIT_SYSTEM.I;
//            yahooClient = (new WeatherClient.ClientBuilder())
//                    .attach(context)
//                    .config(yahooConfig)
//                    .provider(new YahooProviderType())
//                    .httpClient(WeatherClientDefault.class)
//                    .build();
//
//        } catch (Throwable throwable) {
//            Log.i(TAG, "Throwable : "+throwable);
//        }
    }


    public void getCityInLocation(Location location) {
        String sql="SELECT%20*%20FROM%20geo.placefinder%20WHERE%20text%3D%22"+ location.getLatitude() +"%2C"+ location.getLongitude() +"%22%20and%20gflags%3D%22R%22";
        String query = "https://query.yahooapis.com/v1/public/yql?format=json&q="+ sql;
        Log.i(TAG, query);

//        try {
//
//            yahooClient.searchCity(location.getLatitude(), location.getLongitude(), new WeatherClient.CityEventListener() {
//                @Override
//                public void onCityListRetrieved(List<City> cityList) {
//                    city = cityList.get(cityList.size() - 1);
//                    YahooCityResponseModel responseModel = new YahooCityResponseModel();
//                    responseModel.cityName = city.getName();
//                    responseModel.woeid = city.getId();
//                    responseModel.regionName = city.getRegion();
//                    onYahooCityResponseReceived(responseModel);
//                }
//
//                @Override
//                public void onWeatherError(WeatherLibException wle) {
//                    Log.e(getCityInLocation_TAG, "onWeatherError - parsing data : "+wle);
//
//                    onYahooCityResponseFailed();
//                }
//
//                @Override
//                public void onConnectionError(Throwable t) {
//                    onYahooCityResponseFailed();
//                    Log.e(getCityInLocation_TAG, "onConnectionError - parsing data : "+t);
//                }
//            });
//
//        } catch (Throwable throwable) {
//            Log.i(getCityInLocation_TAG, "Throwable : "+throwable);
//        }
    }

    public void getTemperatureForLocationId(String locationId) {
        String query = "https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20%3D%20"+locationId+"&format=json";
        Log.i(TAG, query);

//        try {
//            yahooClient.getCurrentCondition(new WeatherRequest(locationId), new WeatherClient.WeatherEventListener() {
//                @Override
//                public void onWeatherRetrieved(CurrentWeather currentWeather) {
//                    YahooWeatherResponseModel responseModel = new YahooWeatherResponseModel();
//                    responseModel.temperature = currentWeather.weather.temperature.getTemp();
//                    responseModel.date = new Date();
//                    onWeatherResponseRecieved(responseModel);
//                }
//
//                @Override
//                public void onWeatherError(WeatherLibException e) {
//                    Log.e(getTemperatureForLocationId_TAG, "WeatherLibException - parsing data : "+e);
//                    onWeatherResponseFailed();
//                }
//
//                @Override
//                public void onConnectionError(Throwable throwable) {
//                    Log.e(getTemperatureForLocationId_TAG, "onConnectionError - parsing data : "+throwable);
//                    onWeatherResponseFailed();
//                }
//            });
//        }
//        catch (Throwable t) {
//            Log.e(getTemperatureForLocationId_TAG, "Throwable : " + t);
//        }

    }



}
