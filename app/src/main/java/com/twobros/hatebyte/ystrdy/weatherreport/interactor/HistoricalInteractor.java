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
        historicalGateway                       = new HistoricalWeatherGateway();
        currentLocationGateway                  = new CurrentLocationGateway();
        currentWeatherGateway                   = new CurrentWeatherGateway();
        recordGateway                           = new RecordGateway();
        differenceGateway                       = new DifferenceGateway();
    }

    public WeatherResponse getReport(WeatherRequest wr) {
        WeatherRequest weatherRequest = wr;

        // create record for ystrdy
        RecordEntity ystrdyEntity                            = new RecordEntity();
        // populate record with date(ystrdy), latitude, longitude
        ystrdyEntity.location                   = weatherRequest.location;
        ystrdyEntity.date                       = YstrDate.ystrdy();
        // populate historical data - temperature, regionName
        ystrdyEntity                            = historicalGateway.requestData(ystrdyEntity);
        if (ystrdyEntity == null) {
            return null;
        }

        // create record for now
        RecordEntity todayEntity                      = new RecordEntity();
        // populate record with date(now), latitude, longitude
        todayEntity.location                          = wr.location;
        todayEntity.date                              = new Date();
        // populate record with cityName, regionName, woied
        todayEntity                                   = currentLocationGateway.requestData(todayEntity);
        if (todayEntity == null) {
            return null;
        }
        // populate record with temperature, regionName
        todayEntity                                   = currentWeatherGateway.requestData(todayEntity);
        if (todayEntity == null) {
            return null;
        }

        // merge missing ystrday fields for yahoo city info
        ystrdyEntity.regionName                 = todayEntity.regionName;
        ystrdyEntity.cityName                   = todayEntity.cityName;
        ystrdyEntity.woeid                      = todayEntity.woeid;

        // save records
        long todayId                            = 0;
        long ystrdyId                           = 0;
        try {
            recordGateway.setEntity(ystrdyEntity);
            ystrdyId                            = recordGateway.save();
            recordGateway.setEntity(todayEntity);
            todayId                             = recordGateway.save();
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
        differenceEntity.todayRecordId          = (int)todayId;
        differenceEntity.ystrdyRecordId         = (int)ystrdyId;
        differenceEntity.date                   = todayEntity.date;
        try {
            differenceGateway.setEntity(differenceEntity);
            differenceGateway.save();
        } catch (InvalidPropertiesFormatException e) {
            Log.e(TAG, "InvalidPropertiesFormatException " + TAG + ": differenceEntity" + e);
            return null;
        }

        // send back response
        WeatherResponse weatherResponse         = new WeatherResponse();
        weatherResponse.difference              = differenceEntity.difference;
        weatherResponse.ystrday                 = ystrdyEntity;
        weatherResponse.today                   = todayEntity;
        weatherResponse.logString               = "HistoricalInteractor NEEDED";
        return weatherResponse;
    }

}
