package com.twobros.hatebyte.ystrdy.interactor.historical;

import android.location.Location;

import com.twobros.hatebyte.ystrdy.egi.mock.TestEGI;
import com.twobros.hatebyte.ystrdy.egi.mock.TestRecordGateway;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeCurrentLocationInteractorGateway;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeCurrentWeatherInteractorGateway;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeDifferenceInteratorGateway;
import com.twobros.hatebyte.ystrdy.interactor.historical.mock.FakeHistoricalInteractor;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeHistoricalIteratorGateway;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeRecordInteratorGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by scott on 12/16/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class HistoricalInteractorTest {

    FakeHistoricalIteratorGateway historicalWeatherGateway;
    FakeCurrentLocationInteractorGateway currentLocationGateway;
    FakeCurrentWeatherInteractorGateway currentWeatherGateway;
    FakeDifferenceInteratorGateway differenceInteratorGateway;
    FakeRecordInteratorGateway recordInteratorGateway;

    FakeHistoricalInteractor historicalInteractor;
    WeatherRequest weatherRequest;
    TestRecordGateway recordEG;
    TestEGI testEGI;

    @Before
    public void setup() {
        currentLocationGateway                  = new FakeCurrentLocationInteractorGateway();
        currentWeatherGateway                   = new FakeCurrentWeatherInteractorGateway();
        historicalWeatherGateway                = new FakeHistoricalIteratorGateway();
        recordInteratorGateway                  = new FakeRecordInteratorGateway();
        differenceInteratorGateway              = new FakeDifferenceInteratorGateway();
        recordEG                                = new TestRecordGateway();
        testEGI                                 = new TestEGI();
        recordInteratorGateway.setEntityGatewayImplementation(testEGI);
        differenceInteratorGateway.setEntityGatewayImplementation(testEGI);

        historicalInteractor                    = new FakeHistoricalInteractor();
        historicalInteractor.setCurrentLocationGateway(currentLocationGateway);
        historicalInteractor.setCurrentWeatherGateway(currentWeatherGateway);
        historicalInteractor.setHistoricalWeatherGateway(historicalWeatherGateway);
        historicalInteractor.setRecordGateway(recordInteratorGateway);
        historicalInteractor.setDifferenceGateway(differenceInteratorGateway);

        Location location                       = new Location("dummyprovider");
        location.setLatitude(40.7143528);
        location.setLongitude(-74.0059731);

        weatherRequest                          = new WeatherRequest();
        weatherRequest.location                 = location;
    }

    @After
    public void teardown() {
        historicalInteractor                    = null;
        currentLocationGateway                  = null;
        currentWeatherGateway                   = null;
        historicalWeatherGateway                = null;
        recordInteratorGateway                  = null;
        differenceInteratorGateway              = null;
        weatherRequest                          = null;
    }

    @Test
    public void test_responseIsNullWhenHistoricalGatewayFails() {
        historicalWeatherGateway.shouldReturnNull = true;
        WeatherResponse response = historicalInteractor.getReport(weatherRequest);

        assertThat(response).isNull();
    }

    @Test
    public void test_responseIsNullWhenCurrentLocationGatewayFails() {
        currentLocationGateway.shouldReturnNull = true;
        WeatherResponse response = historicalInteractor.getReport(weatherRequest);

        assertThat(response).isNull();
    }

    @Test
    public void test_responseIsNullWhenCurrentWeatherGatewayFails() {
        currentWeatherGateway.shouldReturnNull = true;
        WeatherResponse response = historicalInteractor.getReport(weatherRequest);

        assertThat(response).isNull();
    }


    @Test
    public void test_responseIsNullWhenRecordSaveFails() {
        recordInteratorGateway.shouldSendException = true;
        WeatherResponse response = historicalInteractor.getReport(weatherRequest);

        assertThat(response).isNull();
    }

    @Test
    public void test_responseIsNullWhenDifferenceSaveFails() {
        differenceInteratorGateway.shouldSendException = true;
        WeatherResponse response = historicalInteractor.getReport(weatherRequest);

        assertThat(response).isNull();
    }

    @Test
    public void test_responseFully() {
        WeatherResponse response = historicalInteractor.getReport(weatherRequest);

        RecordEntity recordEntity = recordInteratorGateway.getEarliestRecord();
        DifferenceEntity differenceEntity = differenceInteratorGateway.getLatestDifferenceRecord();

        int recordCount = recordInteratorGateway.numRecords();
        int differenceCount = differenceInteratorGateway.numDifferenceRecords();

        assertThat(recordCount).isEqualTo(2);
        assertThat(differenceCount).isEqualTo(1);

        assertThat(recordEntity.date).isEqualTo(response.ystrday.date);
        assertThat(response.difference).isEqualTo(5.1f);
    }



}
