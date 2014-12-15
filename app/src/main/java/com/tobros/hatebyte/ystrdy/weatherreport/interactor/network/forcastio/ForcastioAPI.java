package com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by scott on 12/10/14.
 */
public abstract class ForcastioAPI {

    public class ForcastioResponseModel {
        float temperature;
    }
    public class ForcastioRequestModel {
        public Location location;
        public Date date;
    }

    abstract void onHistoricalWeatherResponse(ForcastioResponseModel hwrm);
    abstract void onHistoricalWeatherResponseFailed();

    private static final String TAG                     = "TemperatureYstrDy";
    private static final String FORCAST_API_KEY         = "86557a3da6d70d397c20ca15a3447bfb";

    private Context context;
    public ForcastioAPI(Context c) {
        context = c;
    }

    public void request(ForcastioRequestModel hwrm) {
        Date yesterday = new Date();

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ForcastioAPI.url(hwrm);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject currently = response.getJSONObject("currently");
                    ForcastioResponseModel h = new ForcastioResponseModel();
                    h.temperature = (float) currently.getDouble("temperature");
                    onHistoricalWeatherResponse(h);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONException : " +e);
                    onHistoricalWeatherResponseFailed();
                }

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "VolleyError : " +error);
                onHistoricalWeatherResponseFailed();
            }

        });

        queue.add(jsObjRequest);
    }

    public static String url(ForcastioRequestModel hwrm) {
        long time = hwrm.date.getTime() / 1000;
        return  "https://api.forecast.io/forecast/"+FORCAST_API_KEY+"/"+hwrm.location.getLatitude()+","+hwrm.location.getLongitude()+","+time+"?exclude=minutely,hourly,daily,alerts,flags";
    }

}
