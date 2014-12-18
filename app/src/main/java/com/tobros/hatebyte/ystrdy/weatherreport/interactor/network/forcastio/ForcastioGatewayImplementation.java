package com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio;

import android.content.Context;
import android.util.Log;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
////import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.IJSONGatewayImplementation;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by scott on 12/17/14.
 */
public class ForcastioGatewayImplementation implements IJSONGatewayImplementation {

    private static final String TAG = " ForcastioGatewayImplementation";

    public String get(String url) {
        String result = null;
        try {
            result = fetch(url);
        } catch (IOException e) {
            Log.e(TAG, "IOException : " + e);
        } finally {
            return result;
        }
    }

    OkHttpClient client = new OkHttpClient();
    String fetch(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
