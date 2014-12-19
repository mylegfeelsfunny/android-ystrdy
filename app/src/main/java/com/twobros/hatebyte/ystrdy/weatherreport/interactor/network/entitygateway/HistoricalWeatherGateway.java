package com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway;

import android.util.Log;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by scott on 12/17/14.
 */
public class HistoricalWeatherGateway extends AbstractWeatherGateway {

    private static final String TAG = " HistoricalWeatherGateway";
    private static final String FORCAST_API_KEY = "86557a3da6d70d397c20ca15a3447bfb";

    public static float parseForTemperature(JSONObject json) {
        double temp = RecordEntity.voidTemperature;
        try {
            JSONObject currently = (JSONObject) json.get("currently");
            temp = (Double) currently.get("temperature");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException "+TAG+": parseForTemperature" + e);
        }
        return (float)temp;
    }

    public static String parseForRegionName(JSONObject json) {
        String regionName = null;
        try {
            regionName = (String) json.get("timezone");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException "+TAG+": parseForRegionName" + e);
        }
        return regionName;
    }

    @Override
    public String url() {
        long time = recordEntity.date.getTime() / 1000;
        return "https://api.forecast.io/forecast/"+FORCAST_API_KEY+"/"+recordEntity.location.getLatitude()+","+recordEntity.location.getLongitude()+","+time+"?exclude=minutely,hourly,daily,alerts,flags";
    }

    public Boolean isValid() {
        if (recordEntity.temperature == RecordEntity.voidTemperature) {
            return false;
        }
        if (recordEntity.regionName == null) {
            return false;
        }
        return true;
    }

    @Override
    public void mapFromJSON(JSONObject json) {
        recordEntity.temperature = parseForTemperature(json);
        recordEntity.regionName = parseForRegionName(json);
    }

}
