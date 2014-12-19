package com.twobros.hatebyte.ystrdy.weatherreport.interactor;

import android.util.Log;

import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordEG;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.HistoricalWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceEG;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/12/14.
 */
public abstract class ForcastioInteractor {

    private static final String TAG = " ForcastioInteractor";

    abstract void onWeatherResponse(WeatherResponse weatherResponse);
    abstract void onWeatherResponseFailed();

    private HistoricalWeatherGateway forcastioGateway;
    private CurrentLocationGateway currentLocationGateway;
    private CurrentWeatherGateway currentWeatherGateway;

    public ForcastioInteractor() {
        forcastioGateway                        = new HistoricalWeatherGateway();
        currentLocationGateway                  = new CurrentLocationGateway();
        currentWeatherGateway                   = new CurrentWeatherGateway();
    }

    public WeatherResponse getReport(WeatherRequest weatherRequest) {
        RecordEntity ystrdyEntity               = new RecordEntity();

        // HISTORICAL
        // create record - date(ystrdy), latitude, longitude
        ystrdyEntity.location                   = weatherRequest.location;
        ystrdyEntity.date                       = YstrDate.ystrdy();

        // populate historical data - temperature, regionName
        ystrdyEntity                            = forcastioGateway.requestData(ystrdyEntity);
        if (ystrdyEntity == null) {
            return null;
        }

        RecordEntity todayEntity                = new RecordEntity();
        todayEntity.location                    = weatherRequest.location;
        todayEntity.date                        = new Date();

        // populate current data - cityName, regionName, woied
        todayEntity                             = currentLocationGateway.requestData(todayEntity);
        if (todayEntity == null) {
            return null;
        }

        // populate current data - temperature, regionName
        todayEntity                             = currentWeatherGateway.requestData(todayEntity);
        if (todayEntity == null) {
            return null;
        }

        // merge missing ystrday fields for yahoo city info
        ystrdyEntity.regionName                 = todayEntity.regionName;
        ystrdyEntity.cityName                   = todayEntity.cityName;
        ystrdyEntity.woeid                      = todayEntity.woeid;

        // save records
        long recordId                           = 0;
        try {
            RecordEG recordEG                   = new RecordEG();
            recordEG.setEntity(ystrdyEntity);
            recordId                            = recordEG.save();
            recordEG.setEntity(todayEntity);
            recordEG.save();
        } catch (InvalidPropertiesFormatException e) {
            Log.e(TAG, "InvalidPropertiesFormatException " + TAG + ": recordEG" + e);
            return null;
        }

        // save difference
        DifferenceEntity differenceEntity       = new DifferenceEntity();
        differenceEntity.difference             = ystrdyEntity.temperature - todayEntity.temperature;
        differenceEntity.recordId               = recordId;
        differenceEntity.date                   = todayEntity.date;
        try {
            DifferenceEG differenceEG           = new DifferenceEG();
            differenceEG.setEntity(ystrdyEntity);
            differenceEG.save();
        } catch (InvalidPropertiesFormatException e) {
            Log.e(TAG, "InvalidPropertiesFormatException " + TAG + ": differenceEntity" + e);
            return null;
        }

        // send back response difference
        WeatherResponse weatherResponse         = new WeatherResponse();
        weatherResponse.difference              = differenceEntity.difference;
        weatherResponse.ystrday                 = ystrdyEntity;
        weatherResponse.today                   = todayEntity;
        return weatherResponse;
    }

}
