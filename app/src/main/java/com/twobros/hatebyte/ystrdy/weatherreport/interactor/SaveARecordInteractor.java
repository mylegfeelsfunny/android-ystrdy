package com.twobros.hatebyte.ystrdy.weatherreport.interactor;

import android.util.Log;

import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/21/14.
 */
public class SaveARecordInteractor {

    private static final String TAG = " SaveARecordInteractor";

    protected RecordGateway recordGateway;
    protected CurrentLocationGateway currentLocationGateway;
    protected CurrentWeatherGateway currentWeatherGateway;

    public SaveARecordInteractor() {
        currentLocationGateway                      = new CurrentLocationGateway();
        currentWeatherGateway                       = new CurrentWeatherGateway();
        recordGateway                               = new RecordGateway();
    }

    public Boolean saveRecord(WeatherRequest wr) {
        // send back response difference
        RecordEntity nowEntity                      = new RecordEntity();

        // HISTORICAL
        // create record - date(ystrdy), latitude, longitude
        nowEntity.location                          = wr.location;
        nowEntity.date                              = new Date();

        // populate record with cityName, regionName, woied
        nowEntity                                   = currentLocationGateway.requestData(nowEntity);
        if (nowEntity == null) {
            return false;
        }

        // record with temperature, regionName
        nowEntity                                   = currentWeatherGateway.requestData(nowEntity);
        if (nowEntity == null) {
            return false;
        }

        // save record to db
        recordGateway.setEntity(nowEntity);
        long recordId = 0;
        try {
            recordId = recordGateway.save();
        } catch (InvalidPropertiesFormatException e) {
            Log.e(TAG, "InvalidPropertiesFormatException : SaveARecordInteractor :" + e);
            return false;
        }

        return recordId > 0;
    }

}
