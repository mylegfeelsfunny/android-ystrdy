package com.twobros.hatebyte.ystrdy.weatherreport.interactor;

import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

import java.util.Date;

/**
 * Created by scott on 12/12/14.
 */
public class RecentDifferenceInteractor {

    protected DifferenceGateway differenceGateway;
    protected RecordGateway recordGateway;

    public RecentDifferenceInteractor() {
        recordGateway                               = new RecordGateway();
        differenceGateway                           = new DifferenceGateway();
    }

    public WeatherResponse getReport(WeatherRequest wr) {
        DifferenceEntity differenceEntity           = differenceGateway.getLatestDifferenceRecord();
        if (differenceEntity == null) {
            return null;
        }

        RecordEntity todayEntity                    = recordGateway.getRecordById(differenceEntity.todayRecordId);
        RecordEntity ystrdyEntity                   = recordGateway.getRecordById(differenceEntity.ystrdyRecordId);

        WeatherResponse responseModel               = null;
        if (RecentDifferenceInteractor.isDifferenceYoungEnoughToRepeat(differenceEntity.date)) {
            responseModel                           = new WeatherResponse();
            responseModel.difference                = differenceEntity.difference;
            responseModel.today                     = todayEntity;
            responseModel.ystrday                   = ystrdyEntity;
            responseModel.logString                 = "RecentDifferenceInteractor NEEDED";
            return responseModel;
        } else {
            return null;
        }
    }

    public static Boolean isDifferenceYoungEnoughToRepeat(Date date) {
        return YstrDate.isWithinTwoHoursOfNow(date);
    }

}
