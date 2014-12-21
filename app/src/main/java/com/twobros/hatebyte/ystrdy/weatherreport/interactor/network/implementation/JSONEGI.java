package com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by scott on 12/17/14.
 */
public class JSONEGI {

    private static final String TAG = " JSONEGI";

    private OkHttpClient client = new OkHttpClient();

    public interface IJSONEntityGateway {
        public String url();
        public void mapFromJSON(JSONObject json);
    }

    public JSONObject get(IJSONEntityGateway eg) {
        String jsonString = null;
        try {
            jsonString = fetch(eg.url());
        } catch (IOException e) {
            Log.e(TAG, "IOException : " + e);
        } finally {
            return JSONEGI.parseJSONString(jsonString);
        }
    }

    protected String fetch(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static JSONObject parseJSONString(String jsonString) {
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException : parseForRegionName " + e);
        } finally {
            return json;
        }
    }

}
