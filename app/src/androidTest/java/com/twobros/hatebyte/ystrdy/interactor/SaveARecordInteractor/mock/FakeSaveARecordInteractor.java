package com.twobros.hatebyte.ystrdy.interactor.savearecordinteractor.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.SaveARecordInteractor;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;

/**
 * Created by scott on 12/21/14.
 */
public class FakeSaveARecordInteractor extends SaveARecordInteractor {

    public void setCurrentWeatherGateway(CurrentWeatherGateway d) {
        currentWeatherGateway = d;
    }

    public void setCurrentLocationGateway(CurrentLocationGateway c) {
        currentLocationGateway = c;
    }

    public void setRecordGateway(RecordGateway rg) {
        recordGateway = rg;
    }

}
