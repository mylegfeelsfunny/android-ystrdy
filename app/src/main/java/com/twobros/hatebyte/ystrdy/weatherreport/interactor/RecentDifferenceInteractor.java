package com.twobros.hatebyte.ystrdy.weatherreport.interactor;

import android.os.AsyncTask;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;

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

    public void getReport() {
        differenceGateway = new DifferenceGateway();
        new GetLatestDifferenceRecord().execute((Object[]) null);
    }

    public void completeRequest(DifferenceEntity differenceEntity) {
        if (RecentDifferenceInteractor.isDifferenceYoungEnoughToRepeat(differenceEntity.date)) {
            WeatherResponse responseModel  = new WeatherResponse();
            responseModel.difference            = differenceEntity.difference;
            onWeatherResponse(responseModel);
        } else {
            onWeatherResponseFailed();
        }
    }

    public static Boolean isDifferenceYoungEnoughToRepeat(Date date) {
        return YstrDate.isWithinTwoHoursOfNow(date);
    }

    private class GetLatestDifferenceRecord extends AsyncTask<Object, Object, DifferenceEntity> {
        @Override
        protected DifferenceEntity doInBackground(Object... params) {
            return differenceGateway.getLatestDifferenceRecord();
        }
        @Override
        protected void onPostExecute(DifferenceEntity differenceEntity) {
            completeRequest(differenceEntity);
        }
    }


}
