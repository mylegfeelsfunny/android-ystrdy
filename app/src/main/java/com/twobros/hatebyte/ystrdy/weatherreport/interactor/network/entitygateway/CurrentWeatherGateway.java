package com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway;

import android.util.Log;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by scott on 12/19/14.
 */
public class CurrentWeatherGateway extends AbstractWeatherGateway {

    private static final String TAG = " CurrentWeatherGateway";
    private static final String YAHOO_API_KEY = "dj0yJmk9YnhCTWNBcU1YUG53JmQ9WVdrOVZYZzFPSEpETTJNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD00Mw--";

    public static float parseForTemperature(JSONObject json) {
        float temp = RecordEntity.voidTemperature;
        try {
            JSONObject currently = (JSONObject) json.get("currently");
            Double tempDouble = (Double) currently.get("temperature");
            temp = tempDouble.floatValue();
        } catch (JSONException e) {
            Log.e(TAG, "JSONException " + TAG + ": parseForTemperature" + e);
        } finally {
            return temp;
        }
    }

    @Override
    public String url() {
        return "https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20%3D%20" +
                recordEntity.woeid+"&format=json";
    }

}
