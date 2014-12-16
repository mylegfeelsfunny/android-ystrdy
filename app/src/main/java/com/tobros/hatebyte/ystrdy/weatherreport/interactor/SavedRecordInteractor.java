package com.tobros.hatebyte.ystrdy.weatherreport.interactor;

import android.os.AsyncTask;

import com.tobros.hatebyte.ystrdy.YstrdyApp;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;
import com.tobros.hatebyte.ystrdy.weatherreport.WeatherRequestModel;
import com.tobros.hatebyte.ystrdy.weatherreport.WeatherResponseModel;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.RecordEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.RecordEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.yahooweather.YahooAPI;

import java.util.Date;

/**
 * Created by scott on 12/12/14.
 */
public abstract class SavedRecordInteractor {

    abstract void onWeatherResponse(WeatherResponseModel weatherResponseModel);
    abstract void onWeatherResponseFailed();

    private WeatherRequestModel weatherRequest;
    private WeatherResponseModel weatherResponseModel;

    RecordEGI recordEGI;

    public SavedRecordInteractor() {}

    public void getReport(WeatherRequestModel wr) {
        weatherRequest = wr;
        weatherResponseModel = new WeatherResponseModel();
//        recordEGI = new RecordEGI();
//        new GetRecordFromYstrdy().execute((Object[]) null);
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
        yahooAPI.getTemperatureForLocationId(weatherResponseModel.woeid);
    }

    private void completeRequest() {
        onWeatherResponse(weatherResponseModel);
    }


    // Database Task
    private class GetRecordFromYstrdy extends AsyncTask<Object, Object, RecordEntity> {
        @Override
        protected RecordEntity doInBackground(Object... params) {
            return null;//recordEGI.getClosestRecordFromYstrdy().getEntity();
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
            weatherResponseModel.cityName = responseModel.cityName;
            weatherResponseModel.regionName = responseModel.regionName;
            weatherResponseModel.woeid = responseModel.woeid;
            fetchTemperatureForLocationId();
        }

        @Override
        protected void onYahooCityResponseFailed() {
            onWeatherResponseFailed();
        }

        @Override
        protected  void onWeatherResponseRecieved(YahooWeatherResponseModel responseModel) {
            weatherResponseModel.temperature = responseModel.temperature;
            weatherResponseModel.date = responseModel.date;
            completeRequest();
        }

        @Override
        protected void onWeatherResponseFailed() {
            onWeatherResponseFailed();
        }
    }
}
