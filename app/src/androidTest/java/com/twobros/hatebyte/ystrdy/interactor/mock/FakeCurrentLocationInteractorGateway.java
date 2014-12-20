package com.twobros.hatebyte.ystrdy.interactor.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;

/**
 * Created by scott on 12/19/14.
 */
public class FakeCurrentLocationInteractorGateway extends CurrentLocationGateway {
    public boolean shouldReturnNull = false;

    public void setEntityGateway(JSONEGI implementation) {
        jsonGatewayImp = implementation;
    }

    public void setRecord(RecordEntity r) {
        recordEntity = r;
    }

    @Override
    public RecordEntity requestData(RecordEntity re) {
        if (shouldReturnNull) {
            return null;
        }
        re.regionName = "Soho";
        re.cityName   = "New York";
        re.woeid      = "12761344";
        return re;
    }

}
