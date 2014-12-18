package com.twobros.hatebyte.ystrdy.weatherreport.interactor.network;

import android.content.ContentValues;
import android.database.Cursor;
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

import java.io.IOException;

/**
 * Created by scott on 12/17/14.
 */
public class JSONEGI {

    private static final String TAG = " JSONEGI";

    public interface IJSONEntityGateway {
        public String url();
    }

    public String get(IJSONEntityGateway eg) {
        String result = null;
        try {
            result = fetch(eg.url());
        } catch (IOException e) {
            Log.e(TAG, "IOException : " + e);
        } finally {
            return result;
        }
    }

    OkHttpClient client = new OkHttpClient();
    protected String fetch(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
