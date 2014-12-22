package com.twobros.hatebyte.ystrdy.weatherreport.interactor;

import android.widget.Toast;

import com.twobros.hatebyte.ystrdy.YstrdyApp;
import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
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

        WeatherResponse responseModel               = null;
        if (RecentDifferenceInteractor.isDifferenceYoungEnoughToRepeat(differenceEntity.date)) {
            Toast.makeText(YstrdyApp.getContext(), "RecentDifferenceInteractor NEEDED", Toast.LENGTH_SHORT).show();
            responseModel                           = new WeatherResponse();
            responseModel.difference                = differenceEntity.difference;
            return responseModel;
        } else {
            return null;
        }
    }

    public static Boolean isDifferenceYoungEnoughToRepeat(Date date) {
        return YstrDate.isWithinTwoHoursOfNow(date);
    }

}
