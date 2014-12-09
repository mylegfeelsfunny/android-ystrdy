package com.tobros.hatebyte.ystrdy.network.yahooweather;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scott on 12/9/14.
 */
public class FetchTemperatureNow {

    private static final String YAHOO_APP_ID = "tpOBaa7i";
    private static final String TAG = "FetchTemperatureNow";


    private String weatherYQL = "http://query.yahooapis.com/v1/public/yql?q=QUERY&format=json&callback=?";



    public void getTemperature(Location location) {
        String sql="SELECT%20*%20FROM%20geo.placefinder%20WHERE%20text%3D%22"+ location.getLatitude() +"%2C"+ location.getLongitude() +"%22%20and%20gflags%3D%22R%22";
        String query = "https://query.yahooapis.com/v1/public/yql?q="+ sql;
        Log.i(TAG, query);
            new XMLRequestTask().execute(query);




    }

    private class XMLRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return loadXMLFromYahoo(urls[0]);
            } catch (IOException e) {
                return "Whoa, IOExceptionn : "+e;
            } catch (XmlPullParserException e) {
                return "Whoa, XmlPullParserException : "+e;
            }
        }

        @Override
        protected void onPostExecute(String results) {
        }

    }

    private String loadXMLFromYahoo(String query) throws XmlPullParserException, IOException {

        HttpURLConnection yahooHttpConn = null;

        StringBuilder total = new StringBuilder();

        try {
            yahooHttpConn = (HttpURLConnection) (new URL(query)).openConnection();
            yahooHttpConn.connect();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new InputStreamReader(yahooHttpConn.getInputStream()));

            BufferedReader r = new BufferedReader(new InputStreamReader(yahooHttpConn.getInputStream()));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            Log.i(TAG, total.toString());


        } catch (MalformedURLException malE) {
            Log.e(TAG, "MalformedURLException : " + malE);
        } catch (IOException ioe) {
            Log.e(TAG, "IOExceptionn : " + ioe);
        }

        return total.toString();
    }


}
