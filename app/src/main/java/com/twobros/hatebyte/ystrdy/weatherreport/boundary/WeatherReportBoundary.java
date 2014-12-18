package com.twobros.hatebyte.ystrdy.weatherreport.boundary;

import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;

/**
 * Created by scott on 12/12/14.
 */
public class WeatherReportBoundary {

    WeatherRequest weatherRequest;
    IWeatherReportBoundary delegate;

    public void fetchReport(IWeatherReportBoundary d,  WeatherRequest wr) {
        delegate = d;
        weatherRequest = wr;
        fromDifferenceData();
    }

    public void fromDifferenceData() {
//        DifferenceDataInteractor differenceDataInteractor = new DifferenceDataInteractor(differenceDelegate);
//        differenceDataInteractor.getReport(weatherRequest);
    }

    public void fromRecordData() {
//        RecordInteractor recordInteractor = new RecordInteractor(recordDelegate);
//        recordInteractor.getReport(weatherRequest);
    }

    public void fromForcastio() {
//        ForcastioInteractor forcastioInteractor = new ForcastioInteractor(forcastioDelegate);
//        forcastioInteractor.getReport(weatherRequest);
    }


//    private WeatherBoundaryDelegate differenceDelegate = new WeatherBoundaryDelegate() {
//        @Override
//        public void onWeatherResponseFailed() {
//            fromRecordData();
//        }
//    };
//
//    private WeatherBoundaryDelegate recordDelegate = new WeatherBoundaryDelegate() {
//        @Override
//        public void onWeatherResponseFailed() {
//            fromForcastio();
//        }
//    };
//
//    private WeatherBoundaryDelegate forcastioDelegate = new WeatherBoundaryDelegate() {
//        @Override
//        public void onWeatherResponseFailed() {
//            onWeatherResponseFailed();
//        }
//    };
//
//    class WeatherBoundaryDelegate implements IWeatherReportInteractor {
//        public void onWeatherResponse(WeatherResponseModel weatherResponseModel) {
//            delegate.onWeatherReportReturned(weatherResponseModel);
//        }
//
//        public void onWeatherResponseFailed() {
//            delegate.onWeatherReportFailed();
//        }
//    }

}
