package com.twobros.hatebyte.ystrdy.weatherreport.interactor;

import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

import java.util.Date;

/**
 * Created by scott on 12/12/14.
 */
public abstract class RecentDifferenceInteractor {

    abstract void onWeatherResponse(WeatherResponse weatherResponse);
    abstract void onWeatherResponseFailed();

    protected DifferenceGateway differenceGateway;

    public RecentDifferenceInteractor() {
        differenceGateway = new DifferenceGateway();
    }

    public WeatherResponse getReport(WeatherRequest wr) {
        DifferenceEntity differenceEntity = differenceGateway.getLatestDifferenceRecord();
        WeatherResponse responseModel       = null;
        if (RecentDifferenceInteractor.isDifferenceYoungEnoughToRepeat(differenceEntity.date)) {
            responseModel       = new WeatherResponse();
            responseModel.difference            = differenceEntity.difference;
        }
        return responseModel;
    }

    public static Boolean isDifferenceYoungEnoughToRepeat(Date date) {
        return YstrDate.isWithinTwoHoursOfNow(date);
    }

}
