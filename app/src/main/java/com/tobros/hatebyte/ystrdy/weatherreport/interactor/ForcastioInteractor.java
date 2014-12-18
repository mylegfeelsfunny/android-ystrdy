package com.tobros.hatebyte.ystrdy.weatherreport.interactor;

import com.tobros.hatebyte.ystrdy.YstrdyApp;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio.ForcastioGateway;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.DifferenceEG;

/**
 * Created by scott on 12/12/14.
 */
public abstract class ForcastioInteractor {

    abstract void onWeatherResponse(WeatherResponse weatherResponse);
    abstract void onWeatherResponseFailed();

    ;
    private ForcastioGateway forcastioGateway;
    private DifferenceEG  differenceEG;
    private RecordEG recordEG;

    private WeatherRequest weatherRequest;

    public ForcastioInteractor() {

    }

    public void getReport(WeatherRequest wr) {
        recordEG                                = new RecordEG();
        differenceEG                            = new DifferenceEG();
        forcastioGateway                        = new ForcastioGateway();

        weatherRequest = new WeatherRequest();
        forcastioGateway.request(weatherRequest);
    }

    public YstrdyDatabaseAPI getRecordDatabase() {
        return new YstrdyDatabaseAPI(YstrdyApp.getContext(), YstrdyDatabaseAPI.DATABASE_NAME);
    }


}
