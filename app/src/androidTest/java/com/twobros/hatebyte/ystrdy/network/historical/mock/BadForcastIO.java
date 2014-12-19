package com.twobros.hatebyte.ystrdy.network.historical.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.HistoricalWeatherGateway;

/**
 * Created by scott on 12/18/14.
 */
public class BadForcastIO extends HistoricalWeatherGateway implements JSONEGI.IJSONEntityGateway {
    public String url() {
        return "badURL";
    }
}
