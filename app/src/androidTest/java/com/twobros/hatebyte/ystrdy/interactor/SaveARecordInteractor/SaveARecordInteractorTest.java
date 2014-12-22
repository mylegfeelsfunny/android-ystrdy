package com.twobros.hatebyte.ystrdy.interactor.savearecordinteractor;

import android.location.Location;

import com.twobros.hatebyte.ystrdy.egi.mock.TestEGI;
import com.twobros.hatebyte.ystrdy.interactor.savearecordinteractor.mock.FakeSaveARecordInteractor;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeCurrentLocationInteractorGateway;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeCurrentWeatherInteractorGateway;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeRecordInteratorGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by scott on 12/21/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class SaveARecordInteractorTest {

    FakeCurrentLocationInteractorGateway currentLocationGateway;
    FakeCurrentWeatherInteractorGateway currentWeatherGateway;
    FakeRecordInteratorGateway recordInteratorGateway;

    WeatherRequest weatherRequest;
    TestEGI testEGI;

    FakeSaveARecordInteractor saveARecordInteractor;

    @Before
    public void setup() {
        currentLocationGateway                  = new FakeCurrentLocationInteractorGateway();
        currentWeatherGateway                   = new FakeCurrentWeatherInteractorGateway();
        recordInteratorGateway                  = new FakeRecordInteratorGateway();
        testEGI                                 = new TestEGI();
        recordInteratorGateway.setEntityGatewayImplementation(testEGI);

        saveARecordInteractor                   = new FakeSaveARecordInteractor();
        saveARecordInteractor.setCurrentLocationGateway(currentLocationGateway);
        saveARecordInteractor.setCurrentWeatherGateway(currentWeatherGateway);
        saveARecordInteractor.setRecordGateway(recordInteratorGateway);

        Location location                       = new Location("dummyprovider");
        location.setLatitude(40.7143528);
        location.setLongitude(-74.0059731);

        weatherRequest                          = new WeatherRequest();
        weatherRequest.location                 = location;
    }

    @After
    public void teardown() {
        saveARecordInteractor                   = null;
        currentLocationGateway                  = null;
        currentWeatherGateway                   = null;
        recordInteratorGateway                  = null;
        weatherRequest                          = null;
    }

    @Test
    public void test_responseIsNullWhenCurrentLocationGatewayFails() {
        currentLocationGateway.shouldReturnNull = true;
        Boolean response = saveARecordInteractor.saveRecord(weatherRequest);

        assertThat(response).isFalse();
    }

    @Test
    public void test_responseIsNullWhenCurrentWeatherGatewayFails() {
        currentWeatherGateway.shouldReturnNull = true;
        Boolean response = saveARecordInteractor.saveRecord(weatherRequest);

        assertThat(response).isFalse();
    }

    @Test
    public void test_responseIsNullWhenRecordSaveFails() {
        recordInteratorGateway.shouldSendException = true;
        Boolean response = saveARecordInteractor.saveRecord(weatherRequest);

        assertThat(response).isFalse();
    }

    @Test
    public void test_responseWhenGood() {
        Boolean response = saveARecordInteractor.saveRecord(weatherRequest);

        assertThat(response).isTrue();

        int numRecords = recordInteratorGateway.numRecords();
        assertThat(numRecords).isEqualTo(1);
    }



}
