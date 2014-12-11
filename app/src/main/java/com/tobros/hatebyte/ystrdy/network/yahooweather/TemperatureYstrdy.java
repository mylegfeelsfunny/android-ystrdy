package com.tobros.hatebyte.ystrdy.network.yahooweather;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tobros.hatebyte.ystrdy.database.YstrRecord.YstrRecord;
import com.tobros.hatebyte.ystrdy.date.YstrDate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by scott on 12/10/14.
 */
public class TemperatureYstrdy {

    public interface TemperaturYstrdyDelegate {
        public void onYstrdyTemperatureRecieved(YstrRecord record);
        public void onYstrdytTemperatureError();
    }

    private static final String TAG = "TemperatureYstrDy";
    private LocationManager locationManager;
    private Context context = null;
    private static final String FORCAST_API_KEY = "86557a3da6d70d397c20ca15a3447bfb";
    private TemperaturYstrdyDelegate delegate = null;

    public TemperatureYstrdy(Context c, TemperaturYstrdyDelegate d) {
        context = c;
        delegate = d;
    }

    public void request() {
        Date yesterday = new Date();
        yesterday.setTime(yesterday.getTime() - YstrDate.twentyFourHours());
        long time = yesterday.getTime() / 1000;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.forecast.io/forecast/"+FORCAST_API_KEY+"/"+location.getLatitude()+","+location.getLongitude()+","+time+"?exclude=minutely,hourly,daily,alerts,flags";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject currently = response.getJSONObject("currently");
                    double temp = currently.getDouble("temperature");
                    Log.i(TAG, temp + "");
                    Log.i(TAG, response.toString());
                } catch (JSONException e) {
                    Log.e(TAG, "JSONException : " +e);
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });

        queue.add(jsObjRequest);

    }

}
