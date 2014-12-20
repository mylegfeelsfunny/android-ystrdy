package com.twobros.hatebyte.ystrdy.interactor.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.HistoricalInteractor;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.HistoricalWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;

/**
 * Created by scott on 12/19/14.
 */
public class FakeHistoricalInteractor extends HistoricalInteractor {


    public void setCurrentWeatherGateway(CurrentWeatherGateway d) {
        currentWeatherGateway = d;
    }

    public void setCurrentLocationGateway(CurrentLocationGateway c) {
        currentLocationGateway = c;
    }

    public void setHistoricalWeatherGateway(HistoricalWeatherGateway h) {
        historicalGateway = h;
    }

    public void setRecordGateway(RecordGateway rg) {
        recordGateway = rg;
    }

    public void setDifferenceGateway(DifferenceGateway dg) {
        differenceGateway = dg;
    }

}
