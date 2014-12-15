package com.tobros.hatebyte.ystrdy.weatherreport.boundary;

import com.tobros.hatebyte.ystrdy.weatherreport.WeatherResponseModel;

/**
 * Created by scott on 12/13/14.
 */
public interface IWeatherReportBoundary {
    public void onWeatherReportReturned(WeatherResponseModel weatherResponseModel);
    public void onWeatherReportFailed();
}

