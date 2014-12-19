package com.twobros.hatebyte.ystrdy.network.current.mock;

import com.twobros.hatebyte.ystrdy.network.mock.FakeJSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;

/**
 * Created by scott on 12/19/14.
 */
public class FakeCurrentWeatherGateway extends CurrentWeatherGateway {

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
