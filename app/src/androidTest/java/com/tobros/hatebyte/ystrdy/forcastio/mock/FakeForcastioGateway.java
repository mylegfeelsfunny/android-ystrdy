package com.tobros.hatebyte.ystrdy.forcastio.mock;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.IJSONGatewayImplementation;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio.ForcastioGateway;

/**
 * Created by scott on 12/18/14.
 */
public class FakeForcastioGateway extends ForcastioGateway {

    public IJSONGatewayImplementation getForcastioGateway() {
        return new FakeJSONEGI();
    }

    public void setForcastioGateway(IJSONGatewayImplementation implementation) {
        jsonGatewayImp = implementation;
    }

}
