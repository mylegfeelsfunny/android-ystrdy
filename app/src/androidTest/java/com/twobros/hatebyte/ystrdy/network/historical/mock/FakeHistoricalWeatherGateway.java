package com.twobros.hatebyte.ystrdy.network.historical.mock;

import com.twobros.hatebyte.ystrdy.network.mock.FakeJSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.HistoricalWeatherGateway;

/**
 * Created by scott on 12/18/14.
 */
public class FakeHistoricalWeatherGateway extends HistoricalWeatherGateway {

    @Override
    public JSONEGI getEntityGateway() {
        return new FakeJSONEGI();
    }

    public void setEntityGateway(JSONEGI implementation) {
        jsonGatewayImp = implementation;
    }

    public void setRecord(RecordEntity r) {
        recordEntity = r;
    }

}
