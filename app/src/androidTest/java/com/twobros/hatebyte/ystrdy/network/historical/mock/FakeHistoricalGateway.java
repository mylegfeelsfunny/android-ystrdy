package com.twobros.hatebyte.ystrdy.network.historical.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.HistoricalWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;

/**
 * Created by scott on 12/18/14.
 */
public class FakeHistoricalGateway extends HistoricalWeatherGateway {
    public boolean shouldReturnNull = false;

    public void setEntityGateway(JSONEGI implementation) {
        jsonGatewayImp = implementation;
    }

    public void setRecord(RecordEntity r) {
        recordEntity = r;
    }


}
