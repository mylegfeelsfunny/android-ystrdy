package com.twobros.hatebyte.ystrdy.network.current.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;

/**
 * Created by scott on 12/19/14.
 */
public class FakeCurrentLocationGateway extends CurrentLocationGateway {
    public boolean shouldReturnNull = false;

    public void setEntityGateway(JSONEGI implementation) {
        jsonGatewayImp = implementation;
    }

    public void setRecord(RecordEntity r) {
        recordEntity = r;
    }

}
