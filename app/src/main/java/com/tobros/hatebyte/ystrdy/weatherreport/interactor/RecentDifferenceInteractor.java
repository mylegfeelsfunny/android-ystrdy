package com.tobros.hatebyte.ystrdy.weatherreport.interactor;

import android.os.AsyncTask;

import com.tobros.hatebyte.ystrdy.YstrdyApp;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.DifferenceEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
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

    private WeatherRequestModel weatherRequest;
    RecordEGI recordEGI;
    DifferenceEGI differenceEGI;

    public RecentDifferenceInteractor() {}

    public void getReport(WeatherRequestModel wr) {
        recordEGI = new RecordEGI();
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

    public YstrdyDatabaseAPI getRecordDatabase() {
        return new YstrdyDatabaseAPI(YstrdyApp.getContext(), YstrdyDatabaseAPI.DATABASE_NAME);
    }

    public static Boolean isDifferenceYoungEnoughToRepeat(Date date) {
        return YstrDate.isWithinTwoHoursOfNow(date);
    }

    private class GetLatestDifferenceRecord extends AsyncTask<Object, Object, DifferenceEntity> {
        @Override
        protected DifferenceEntity doInBackground(Object... params) {
            return differenceEGI.getLatestDifferenceRecord().getEntity();
        }
        @Override
        protected void onPostExecute(DifferenceEntity differenceEntity) {
            completeRequest(differenceEntity);
        }
    }


}
