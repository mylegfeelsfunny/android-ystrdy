package com.twobros.hatebyte.ystrdy.weatherreport.boundary;

import android.os.AsyncTask;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.HistoricalInteractor;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

/**
 * Created by scott on 12/12/14.
 */
public class WeatherReportBoundary {

    WeatherRequest weatherRequest;
    IWeatherReportBoundary delegate;
    HistoricalInteractor differenceDataInteractor;

    public void fetchReport(IWeatherReportBoundary d,  WeatherRequest wr) {
        delegate = d;
        weatherRequest = wr;

        new GetLatestDifferenceRecord().execute((Object[]) null);
    }

    public WeatherResponse fetchReports() {
        differenceDataInteractor = new HistoricalInteractor();
        return differenceDataInteractor.getReport(weatherRequest);
    }

    private class GetLatestDifferenceRecord extends AsyncTask<Object, Object, WeatherResponse> {
        @Override
        protected WeatherResponse doInBackground(Object... params) {
            differenceDataInteractor = new HistoricalInteractor();
            return differenceDataInteractor.getReport(weatherRequest);
        }
        @Override
        protected void onPostExecute(WeatherResponse wr) {
            // send back response difference
            delegate.onWeatherReportReturned(wr);
        }
    }



}
