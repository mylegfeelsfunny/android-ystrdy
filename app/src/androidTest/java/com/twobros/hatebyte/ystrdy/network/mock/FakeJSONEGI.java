package com.twobros.hatebyte.ystrdy.network.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by scott on 12/18/14.
 */
public class FakeJSONEGI extends JSONEGI {

    public Boolean sendBackIOException = false;
    public Boolean sendHistorical = false;
    public Boolean sendCurrentsLocation = false;
    public Boolean sendCurrentWeather = false;

    public static String historicalResponseString() {
        return "{\"latitude\":40.7221881,\"longitude\":-73.9996643,\"timezone\":\"America/New_York\",\"offset\":-5,\"currently\":{\"time\":1418770507," +
                "\"summary\":\"Drizzle\",\"icon\":\"rain\",\"precipIntensity\":0.0067,\"precipProbability\":0.61,\"precipType\":\"rain\",\"temperature\":45.9,\"apparentTemperature\":45.9," +
                "\"dewPoint\":42.16,\"humidity\":0.87,\"windSpeed\":2.73,\"windBearing\":116,\"visibility\":8,\"cloudCover\":0.91,\"pressure\":1014.11,\"ozone\":285.88}}\n";
    }

    public static String currentLocationResponseString() {
        return "{\"query\":{\"count\":1,\"created\":\"2014-12-17T21:48:35Z\",\"lang\":\"en-US\",\"results\":{\"Result\":{\"quality\":\"87\"," +
                "\"addressMatchType\":\"POINT_ADDRESS\",\"latitude\":\"40.722256\",\"longitude\":\"-73.999679\",\"offsetlat\":\"40.722256\",\"offsetlon\":\"-73.999679\"," +
                "\"radius\":\"400\",\"name\":\"40.7222561,-73.9996793\",\"line1\":\"501 Broadway\",\"line2\":\"New York, NY 10012-4401\",\"line3\":null,\"line4\":\"United States\"," +
                "\"house\":\"501\",\"street\":\"Broadway\",\"xstreet\":null,\"unittype\":null,\"unit\":null,\"postal\":\"10012-4401\",\"neighborhood\":\"Soho\",\"city\":\"New York\"," +
                "\"county\":\"New York County\",\"state\":\"New York\",\"country\":\"United States\",\"countrycode\":\"US\",\"statecode\":\"NY\",\"countycode\":\"NY\",\"uzip\":\"10012\"," +
                "\"hash\":\"31156DE87A326542\",\"woeid\":\"12761344\",\"woetype\":\"11\"}}}}";
    }

    public static String currentWeatherResponseString() {
        return "{\"query\":{\"count\":1,\"created\":\"2014-12-17T21:59:04Z\",\"lang\":\"en-US\",\"results\":{\"channel\":{\"item\":{\"condition\":{\"code\":\"26\"," +
                "\"date\":\"Wed, 17 Dec 2014 3:50 pm EST\",\"temp\":\"51\",\"text\":\"Cloudy\"}}}}}}";
    }

    @Override
    public JSONObject get(IJSONEntityGateway eg) {
        if (sendBackIOException) {
            return super.get(eg);
        }
        if (sendHistorical) {
            return JSONEGI.parseJSONString(FakeJSONEGI.historicalResponseString());
        }
        if (sendCurrentsLocation) {
            return JSONEGI.parseJSONString(FakeJSONEGI.currentLocationResponseString());
        }
        if (sendCurrentWeather) {
            return JSONEGI.parseJSONString(FakeJSONEGI.currentWeatherResponseString());
        }
        return JSONEGI.parseJSONString(FakeJSONEGI.historicalResponseString());
    }

    @Override
    protected String fetch(String url) throws IOException {
        throw new IOException();
    }

}