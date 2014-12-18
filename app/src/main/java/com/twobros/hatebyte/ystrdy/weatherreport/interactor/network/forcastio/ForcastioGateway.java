package com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio;

import android.util.Log;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.JSONEGI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by scott on 12/17/14.
 */
public class ForcastioGateway implements JSONEGI.IJSONEntityGateway {

    private static final String TAG = " ForcastioGateway";
    private static final String FORCAST_API_KEY = "86557a3da6d70d397c20ca15a3447bfb";
    protected JSONEGI jsonGatewayImp;
    private RecordEntity recordEntity;

    public static float parseForTemperature(String jsonString) {
        double temp = RecordEntity.voidTemperature;
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

    public String url() {
        long time = recordEntity.date.getTime() / 1000;
        return "https://api.forecast.io/forecast/"+FORCAST_API_KEY+"/"+recordEntity.location.getLatitude()+","+recordEntity.location.getLongitude()+","+time+"?exclude=minutely,hourly,daily,alerts,flags";
    }

    public static Boolean isValid(RecordEntity recordEntity) {
        if (recordEntity.temperature == RecordEntity.voidTemperature) {
            return false;
        }
        if (recordEntity.regionName == null) {
            return false;
        }
        return true;
    }

    public ForcastioGateway() {
        jsonGatewayImp = getForcastioGateway();
    }

    public JSONEGI getForcastioGateway() {
        return new JSONEGI();
    }

    public RecordEntity request(RecordEntity re)  {
        recordEntity = re;
        String jsonString = jsonGatewayImp.get(this);
        if (jsonString != null) {
            recordEntity.temperature = parseForTemperature(jsonString);
            recordEntity.regionName = parseForRegionName(jsonString);
        }
        return recordEntity;
    }

}
