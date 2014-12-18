package com.tobros.hatebyte.ystrdy.weatherreport.request;

import java.util.Date;

/**
 * Created by scott on 12/12/14.
 */
public class WeatherResponse {

    public float temperature;
    public float difference;
    public String cityName;
    public String regionName;
    public String woeid;
    public Date date;

    public static float voidTemperature = -1000;

    public WeatherResponse() {
        temperature = WeatherResponse.voidTemperature;
        regionName = null;
    }
}
