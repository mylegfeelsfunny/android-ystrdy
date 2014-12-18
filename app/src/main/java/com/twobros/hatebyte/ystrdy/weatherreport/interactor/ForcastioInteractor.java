package com.twobros.hatebyte.ystrdy.weatherreport.interactor;

import android.location.Location;

import com.twobros.hatebyte.ystrdy.YstrdyApp;
import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio.ForcastioGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.yahooweather.YahooGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.DifferenceEG;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/12/14.
 */
public abstract class ForcastioInteractor {

    abstract void onWeatherResponse(WeatherResponse weatherResponse);
    abstract void onWeatherResponseFailed();

    ;
    private ForcastioGateway forcastioGateway;
    private YahooGateway yahooGateway;

    private WeatherRequest weatherRequest;

    public ForcastioInteractor() {
        forcastioGateway                = new ForcastioGateway();
        yahooGateway                    = new YahooGateway();
    }

    public WeatherResponse getReport(WeatherRequest weatherRequest) {

//        weatherRequest = new WeatherRequest();
//        weatherRequest.date =
//        weatherRequest.location =

        RecordEntity ystrdyEntity       = new RecordEntity();

        // HISTORICAL
        // create record - date(ystrdy), latitude, longitude
        ystrdyEntity.location           = weatherRequest.location;
        ystrdyEntity.date               = YstrDate.ystrdy();

        // populate historical data - temperature, regionName
        ystrdyEntity                    = forcastioGateway.request(ystrdyEntity);

        RecordEntity todayEntity        = new RecordEntity();
        todayEntity.location            = weatherRequest.location;
        todayEntity.date                = new Date();

        // populate current data - temperature, regionName
        todayEntity                     = yahooGateway.request(todayEntity);

        // merge missing ystrday fields for yahoo city info
        ystrdyEntity.regionName         = todayEntity.regionName;
        ystrdyEntity.cityName           = todayEntity.cityName;
        ystrdyEntity.woeid              = todayEntity.woeid;

        // save records
        long recordId                   = 0;
        try {
            RecordEG recordEG           = new RecordEG();
            recordEG.setEntity(ystrdyEntity);
            recordId = recordEG.save();
            recordEG.setEntity(todayEntity);
            recordEG.save();
        } catch (InvalidPropertiesFormatException e) {

        }

        // save difference
        DifferenceEntity differenceEntity   = new DifferenceEntity();
        differenceEntity.difference         = ystrdyEntity.temperature - todayEntity.temperature;
        differenceEntity.recordId           = recordId;
        differenceEntity.date               = todayEntity.date;
        try {
            DifferenceEG differenceEG       = new DifferenceEG();
            differenceEG.setEntity(ystrdyEntity);
            differenceEG.save();
        } catch (InvalidPropertiesFormatException e) {

        }

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.difference      = differenceEntity.difference;
        weatherResponse.ystrday         = ystrdyEntity;
        weatherResponse.today           = todayEntity;
        return weatherResponse;
    }

    public YstrdyDatabaseAPI getRecordDatabase() {
        return new YstrdyDatabaseAPI(YstrdyApp.getContext(), YstrdyDatabaseAPI.DATABASE_NAME);
    }


}
