package com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio;

import android.util.Log;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.IJSONGatewayImplementation;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by scott on 12/17/14.
 */
public class ForcastioGateway {

    private static final String TAG = " ForcastioGateway";
    private static final String FORCAST_API_KEY         = "86557a3da6d70d397c20ca15a3447bfb";
    IJSONGatewayImplementation jsonGatewayImp;

    public static float parseForTemperature(String jsonString) {
        double temp = WeatherResponse.voidTemperature;
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONObject currently = (JSONObject) json.get("currently");
            temp = (Double) currently.get("temperature");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException : parseForTemperature" + e);
        }
        return (float)temp;
    }

    public static String parseForRegionName(String jsonString) {
        String regionName = null;
        try {
            JSONObject json = new JSONObject(jsonString);
            regionName = (String) json.get("timezone");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException : parseForRegionName" + e);
        }
        return regionName;
    }

    public static String url(WeatherRequest requestModel) {
        long time = requestModel.date.getTime() / 1000;
        return "https://api.forecast.io/forecast/"+FORCAST_API_KEY+"/"+requestModel.location.getLatitude()+","+requestModel.location.getLongitude()+","+time+"?exclude=minutely,hourly,daily,alerts,flags";
    }

    public static Boolean isValid(WeatherResponse weatherResponse) {
        if (weatherResponse.temperature == WeatherResponse.voidTemperature) {
            return false;
        }
        if (weatherResponse.regionName == null) {
            return false;
        }
        return true;
    }

    public ForcastioGateway() {
        jsonGatewayImp = getForcastioGateway();
    }

    public IJSONGatewayImplementation getForcastioGateway() {
        return new ForcastioGatewayImplementation();
    }

    public WeatherResponse request(WeatherRequest weatherRequest)  {
        String jsonString = jsonGatewayImp.get(ForcastioGateway.url(weatherRequest));
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.temperature = parseForTemperature(jsonString);
        weatherResponse.regionName = parseForRegionName(jsonString);
        return weatherResponse;
    }

}
