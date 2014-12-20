package com.twobros.hatebyte.ystrdy.interactor.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.HistoricalWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;

/**
 * Created by scott on 12/19/14.
 */
public class FakeHistoricalIteratorGateway extends HistoricalWeatherGateway {

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
        re.temperature = 45.9f;
        re.regionName = "America/New_York";
        return re;
    }

}
