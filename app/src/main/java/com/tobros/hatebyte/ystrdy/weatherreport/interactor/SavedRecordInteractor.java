package com.tobros.hatebyte.ystrdy.weatherreport.interactor;

import android.os.AsyncTask;

import com.tobros.hatebyte.ystrdy.YstrdyApp;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;
import com.tobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.yahooweather.YahooAPI;

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
            yahooAPI = new RecordYahooAPI();
            yahooAPI.configure(YstrdyApp.getContext());
            yahooAPI.getCityInLocation(weatherRequest.location);
        } else {
            onWeatherResponseFailed();
        }
    }

    private void fetchTemperatureForLocationId() {
        yahooAPI.getTemperatureForLocationId(weatherResponse.woeid);
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
    private RecordYahooAPI yahooAPI = null;
    class RecordYahooAPI extends YahooAPI {
        @Override
        protected void onYahooCityResponseReceived(YahooCityResponseModel responseModel) {
            weatherResponse.cityName = responseModel.cityName;
            weatherResponse.regionName = responseModel.regionName;
            weatherResponse.woeid = responseModel.woeid;
            fetchTemperatureForLocationId();
        }

        @Override
        protected void onYahooCityResponseFailed() {
            onWeatherResponseFailed();
        }

        @Override
        protected  void onWeatherResponseRecieved(YahooWeatherResponseModel responseModel) {
            weatherResponse.temperature = responseModel.temperature;
            weatherResponse.date = responseModel.date;
            completeRequest();
        }

        @Override
        protected void onWeatherResponseFailed() {
            onWeatherResponseFailed();
        }
    }
}
