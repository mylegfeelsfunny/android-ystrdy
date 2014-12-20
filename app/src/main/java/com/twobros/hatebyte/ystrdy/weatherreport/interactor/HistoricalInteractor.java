package com.twobros.hatebyte.ystrdy.weatherreport.interactor;

import android.util.Log;

import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.HistoricalWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/12/14.
 */
public class HistoricalInteractor {

    private static final String TAG = " ForcastioInteractor";

    protected HistoricalWeatherGateway historicalGateway;
    protected CurrentLocationGateway currentLocationGateway;
    protected CurrentWeatherGateway currentWeatherGateway;
    protected RecordGateway recordGateway;
    protected DifferenceGateway differenceGateway;

    public HistoricalInteractor() {
        historicalGateway = new HistoricalWeatherGateway();
        currentLocationGateway                  = new CurrentLocationGateway();
        currentWeatherGateway                   = new CurrentWeatherGateway();
        recordGateway                           = new RecordGateway();
        differenceGateway                       = new DifferenceGateway();
    }

    public WeatherResponse getReport(WeatherRequest weatherRequest) {
        RecordEntity ystrdyEntity               = new RecordEntity();

        // HISTORICAL
        // create record - date(ystrdy), latitude, longitude
        ystrdyEntity.location                   = weatherRequest.location;
        ystrdyEntity.date                       = YstrDate.ystrdy();

        // populate historical data - temperature, regionName
        ystrdyEntity                            = historicalGateway.requestData(ystrdyEntity);
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
            recordGateway.setEntity(ystrdyEntity);
            recordId                            = recordGateway.save();
            recordGateway.setEntity(todayEntity);
            recordGateway.save();
        } catch (InvalidPropertiesFormatException e) {
            Log.e(TAG, "InvalidPropertiesFormatException " + TAG + ": recordEG" + e);
            return null;
        }

        // save difference
        DifferenceEntity differenceEntity       = new DifferenceEntity();
        BigDecimal tTemp                        = new BigDecimal(todayEntity.temperature);
        BigDecimal yTemp                        = new BigDecimal(ystrdyEntity.temperature);
        BigDecimal diff                         = tTemp.subtract(yTemp);
        differenceEntity.difference             = diff.setScale(3, BigDecimal.ROUND_CEILING).floatValue();
        differenceEntity.recordId               = recordId;
        differenceEntity.date                   = todayEntity.date;
        try {
            differenceGateway.setEntity(differenceEntity);
            differenceGateway.save();
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