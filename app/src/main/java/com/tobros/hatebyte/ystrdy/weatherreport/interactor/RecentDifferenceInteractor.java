package com.tobros.hatebyte.ystrdy.weatherreport.interactor;

import android.os.AsyncTask;

import com.tobros.hatebyte.ystrdy.YstrdyApp;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.DifferenceEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.RecordEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.DifferenceEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;
import com.tobros.hatebyte.ystrdy.weatherreport.WeatherRequestModel;
import com.tobros.hatebyte.ystrdy.weatherreport.WeatherResponseModel;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.RecordEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.DifferenceEntity;

import java.util.Date;

/**
 * Created by scott on 12/12/14.
 */
public abstract class RecentDifferenceInteractor {

    abstract void onWeatherResponse(WeatherResponseModel weatherResponseModel);
    abstract void onWeatherResponseFailed();

    DifferenceEG differenceEG;

    public RecentDifferenceInteractor() {}

    public void getReport() {
        new GetLatestDifferenceRecord().execute((Object[]) null);
    }

    public void completeRequest(DifferenceEntity differenceEntity) {
        if (RecentDifferenceInteractor.isDifferenceYoungEnoughToRepeat(differenceEntity.date)) {
            WeatherResponseModel responseModel  = new WeatherResponseModel();
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
            return null;//DifferenceEG.getLatestDifferenceRecord();
        }
        @Override
        protected void onPostExecute(DifferenceEntity differenceEntity) {
            completeRequest(differenceEntity);
        }
    }


}
