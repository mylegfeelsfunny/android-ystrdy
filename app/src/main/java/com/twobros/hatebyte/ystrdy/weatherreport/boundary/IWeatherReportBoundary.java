package com.twobros.hatebyte.ystrdy.weatherreport.boundary;

import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

/**
 * Created by scott on 12/13/14.
 */
public interface IWeatherReportBoundary {
    public void onWeatherReportReturned(WeatherResponse weatherResponse);
    public void onWeatherReportFailed();
}

