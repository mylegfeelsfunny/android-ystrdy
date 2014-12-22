package com.twobros.hatebyte.ystrdy.interactor.RecentDifferenceInteractor.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.RecentDifferenceInteractor;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;

/**
 * Created by scott on 12/22/14.
 */
public class FakeRecentDifferenceInteractor extends RecentDifferenceInteractor {

    public void setDifferenceGateway(DifferenceGateway dg) {
        differenceGateway = dg;
    }

    public void setRecordGateway(RecordGateway rg) {
        recordGateway = rg;
    }

}
