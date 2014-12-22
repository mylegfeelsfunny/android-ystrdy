package com.twobros.hatebyte.ystrdy.weatherreport.boundary;

import android.os.AsyncTask;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.HistoricalInteractor;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.RecentDifferenceInteractor;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.SavedRecordInteractor;
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
        RecentDifferenceInteractor recentDifferenceInteractor = new RecentDifferenceInteractor();
        WeatherResponse wr = recentDifferenceInteractor.getReport(weatherRequest);

        if (wr == null) {
            SavedRecordInteractor savedRecordInteractor = new SavedRecordInteractor();
            wr = savedRecordInteractor.getReport(weatherRequest);
        }

        if (wr == null) {
            HistoricalInteractor differenceDataInteractor = new HistoricalInteractor();
            wr = differenceDataInteractor.getReport(weatherRequest);
        }

        return wr;

    }

    private class GetLatestDifferenceRecord extends AsyncTask<Object, Object, WeatherResponse> {
        @Override
        protected WeatherResponse doInBackground(Object... params) {
            return fetchReports();
        }
        @Override
        protected void onPostExecute(WeatherResponse wr) {
            // send back response difference
            delegate.onWeatherReportReturned(wr);
        }
    }



}
