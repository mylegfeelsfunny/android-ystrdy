package com.tobros.hatebyte.ystrdy.weatherreport.interactor;

import com.tobros.hatebyte.ystrdy.YstrdyApp;
import com.tobros.hatebyte.ystrdy.weatherreport.WeatherRequestModel;
import com.tobros.hatebyte.ystrdy.weatherreport.WeatherResponseModel;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.RecordEGI;

/**
 * Created by scott on 12/12/14.
 */
public abstract class ForcastioInteractor {

    abstract void onWeatherResponse(WeatherResponseModel weatherResponseModel);
    abstract void onWeatherResponseFailed();

    private WeatherRequestModel weatherRequest;
    RecordEGI recordEGI;

    public ForcastioInteractor() {
    }

    public void getReport(WeatherRequestModel wr) {
//        recordEGI                               = new RecordEGI();
    }

    public YstrdyDatabaseAPI getRecordDatabase() {
        return new YstrdyDatabaseAPI(YstrdyApp.getContext(), YstrdyDatabaseAPI.DATABASE_NAME);
    }

}
