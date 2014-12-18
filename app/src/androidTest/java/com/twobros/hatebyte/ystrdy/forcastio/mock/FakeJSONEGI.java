package com.twobros.hatebyte.ystrdy.forcastio.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.JSONEGI;

import java.io.IOException;

/**
 * Created by scott on 12/18/14.
 */
public class FakeJSONEGI extends JSONEGI {

    public Boolean sendBackIOException = false;

    public static String forcastioResponseString() {
        return "{\"latitude\":40.7221881,\"longitude\":-73.9996643,\"timezone\":\"America/New_York\",\"offset\":-5,\"currently\":{\"time\":1418770507," +
                "\"summary\":\"Drizzle\",\"icon\":\"rain\",\"precipIntensity\":0.0067,\"precipProbability\":0.61,\"precipType\":\"rain\",\"temperature\":45.9,\"apparentTemperature\":45.9," +
                "\"dewPoint\":42.16,\"humidity\":0.87,\"windSpeed\":2.73,\"windBearing\":116,\"visibility\":8,\"cloudCover\":0.91,\"pressure\":1014.11,\"ozone\":285.88}}\n";
    }

    @Override
    public String get(IJSONEntityGateway eg) {
        if (sendBackIOException == true) {
            return super.get(eg);
        }
        return FakeJSONEGI.forcastioResponseString();
    }

    @Override
    protected String fetch(String url) throws IOException {
        throw new IOException();
    }

}