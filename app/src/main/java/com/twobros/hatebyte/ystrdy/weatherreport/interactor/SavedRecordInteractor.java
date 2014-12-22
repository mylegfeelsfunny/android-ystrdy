package com.twobros.hatebyte.ystrdy.weatherreport.interactor;

import android.util.Log;
import android.widget.Toast;

import com.twobros.hatebyte.ystrdy.YstrdyApp;
import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentWeatherGateway;
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
public class SavedRecordInteractor {

    private static final String TAG                 = " SavedRecordInteractor";

    protected CurrentLocationGateway currentLocationGateway;
    protected CurrentWeatherGateway currentWeatherGateway;
    protected RecordGateway recordGateway;
    protected DifferenceGateway differenceGateway;

    public SavedRecordInteractor() {
        currentLocationGateway                      = new CurrentLocationGateway();
        currentWeatherGateway                       = new CurrentWeatherGateway();
        recordGateway                               = new RecordGateway();
        differenceGateway                           = new DifferenceGateway();
    }

    public WeatherResponse getReport(WeatherRequest wr) {

        // create record for yesterday
        RecordEntity ystrdyEntity                   = new RecordEntity();
        // populate record with ystrdy data from db
        recordGateway.setEntity(ystrdyEntity);
        ystrdyEntity = recordGateway.getClosestRecordFromYstrdy();

        if (ystrdyEntity == null || SavedRecordInteractor.fitsInTimeWindow(ystrdyEntity.date) == false) {
            return null;
        }

        // create record for now
        RecordEntity todayEntity                      = new RecordEntity();
        // populate record with date(ystrdy), latitude, longitude
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

        // save record to db
        recordGateway.setEntity(todayEntity);
        long todayRecordId = 0;
        try {
            todayRecordId = recordGateway.save();
        } catch (InvalidPropertiesFormatException e) {
            Log.e(TAG, "InvalidPropertiesFormatException : SaveARecordInteractor :" + e);
            return null;
        }
        if (todayRecordId == 0) {
            return null;
        }

        // save difference
        DifferenceEntity differenceEntity       = new DifferenceEntity();
        BigDecimal tTemp                        = new BigDecimal(todayEntity.temperature);
        BigDecimal yTemp                        = new BigDecimal(ystrdyEntity.temperature);
        BigDecimal diff                         = tTemp.subtract(yTemp);
        differenceEntity.difference             = diff.setScale(3, BigDecimal.ROUND_CEILING).floatValue();
        differenceEntity.todayRecordId          = (int)todayRecordId;
        differenceEntity.ystrdyRecordId         = ystrdyEntity.id;
        differenceEntity.date                   = todayEntity.date;
        try {
            differenceGateway.setEntity(differenceEntity);
            differenceGateway.save();
        } catch (InvalidPropertiesFormatException e) {
            Log.e(TAG, "InvalidPropertiesFormatException " + TAG + ": differenceEntity" + e);
            return null;
        }

        Toast.makeText(YstrdyApp.getContext(), "SavedRecordInteractor NEEDED", Toast.LENGTH_SHORT).show();

        // send back response
        WeatherResponse weatherResponse         = new WeatherResponse();
        weatherResponse.difference              = differenceEntity.difference;
        weatherResponse.ystrday                 = ystrdyEntity;
        weatherResponse.today                   = todayEntity;
        return weatherResponse;
    }

    private static Boolean fitsInTimeWindow(Date date) {
        return (YstrDate.isOlderThanEighteenHours(date) && YstrDate.isYoungerThanThirtyHours(date));
    }

}
