package com.twobros.hatebyte.ystrdy.weatherreport.interactor;

import android.os.AsyncTask;

import com.twobros.hatebyte.ystrdy.YstrdyApp;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.dataapi.YstrdyDatabaseAPI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordEG;
import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;

import java.util.Date;

/**
 * Created by scott on 12/12/14.
 */
public abstract class SavedRecordInteractor {

    abstract void onWeatherResponse(WeatherResponse weatherResponse);
    abstract void onWeatherResponseFailed();

    private WeatherRequest weatherRequest;
    private WeatherResponse weatherResponse;
    protected RecordEG recordEG;

    public SavedRecordInteractor() {
    }

    public void getReport(WeatherRequest wr) {
        weatherRequest = wr;
        weatherResponse = new WeatherResponse();
        recordEG = new RecordEG();
        new GetRecordFromYstrdy().execute((Object[]) null);
    }

    public YstrdyDatabaseAPI getRecordDatabase() {
        return new YstrdyDatabaseAPI(YstrdyApp.getContext(), YstrdyDatabaseAPI.DATABASE_NAME);
    }

    private static Boolean isValidTimeFrame(Date date) {
        return (YstrDate.isOlderThanEighteenHours(date) && YstrDate.isYoungerThanThirtyHours(date));
    }

    private void fetchCityWithLocation(RecordEntity recordEntity) {
        if (SavedRecordInteractor.isValidTimeFrame(recordEntity.date)) {
            // fetch from yahoo
        } else {
        }
    }

    private void fetchTemperatureForLocationId() {
    }

    private void completeRequest() {
        onWeatherResponse(weatherResponse);
    }


    // Database Task
    private class GetRecordFromYstrdy extends AsyncTask<Object, Object, RecordEntity> {
        @Override
        protected RecordEntity doInBackground(Object... params) {
            return recordEG.getClosestRecordFromYstrdy();
        }
        @Override
        protected void onPostExecute(RecordEntity recordEntity) {
            fetchCityWithLocation(recordEntity);
        }
    }

    // API query using Volley
}
