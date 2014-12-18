package com.twobros.hatebyte.ystrdy.forcastio.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.JSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio.ForcastioGateway;

/**
 * Created by scott on 12/18/14.
 */
public class FakeForcastioGateway extends ForcastioGateway {

    public JSONEGI getForcastioGateway() {
        return new FakeJSONEGI();
    }

    public void setForcastioGateway(JSONEGI implementation) {
        jsonGatewayImp = implementation;
    }

}
