package com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway;

import android.util.Log;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by scott on 12/18/14.
 */
public class CurrentLocationGateway extends AbstractWeatherGateway {

    private static final String TAG = " CurrentLocationGateway";
    private static final String YAHOO_API_KEY = "dj0yJmk9YnhCTWNBcU1YUG53JmQ9WVdrOVZYZzFPSEpETTJNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD00Mw--";

    public static JSONObject parseForLocation(JSONObject json) {
        JSONObject Result = null;
        try {
            JSONObject query = (JSONObject) json.get("query");
            JSONObject results = (JSONObject) query.get("results");
            Result = (JSONObject) results.get("Result");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException "+TAG+": parseForLocation" + e);
        } finally {
            return Result;
        }
    }

    @Override
    public void mapFromJSON(JSONObject json) {
        JSONObject locJson      = CurrentLocationGateway.parseForLocation(json);
        recordEntity.regionName = CurrentLocationGateway.valueStringForKey(locJson, "neighborhood");
        recordEntity.cityName   = CurrentLocationGateway.valueStringForKey(locJson, "city");
        recordEntity.woeid      = CurrentLocationGateway.valueStringForKey(locJson, "woeid");
    }

    @Override
    public String url() {
        String sql="SELECT%20*%20FROM%20geo.placefinder%20WHERE%20text%3D%22"+ recordEntity.location.getLatitude() +
                "%2C"+ recordEntity.location.getLongitude() +"%22%20and%20gflags%3D%22R%22";
        return "https://query.yahooapis.com/v1/public/yql?format=json&q="+ sql;
    }

    public Boolean isValid() {
        if (recordEntity.regionName == null) {
            return false;
        }
        if (recordEntity.cityName == null) {
            return false;
        }
        if (recordEntity.woeid == null) {
            return false;
        }
        return true;
    }


}