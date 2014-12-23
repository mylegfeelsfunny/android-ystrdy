package com.twobros.hatebyte.ystrdy.interactor.savedrecordinteractor;

import android.location.Location;

import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.egi.mock.TestEGI;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeCurrentLocationInteractorGateway;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeCurrentWeatherInteractorGateway;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeRecordInteratorGateway;
import com.twobros.hatebyte.ystrdy.interactor.savedrecordinteractor.mock.FakeSavedRecordInteractor;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by scott on 12/21/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class SavedRecordInteractorTest {

    FakeCurrentLocationInteractorGateway currentLocationGateway;
    FakeCurrentWeatherInteractorGateway currentWeatherGateway;
    FakeRecordInteratorGateway recordInteratorGateway;

    WeatherRequest weatherRequest;
    TestEGI testEGI;

    FakeSavedRecordInteractor savedRecordInteractor;
    Location location;

    @Before
    public void setup() {
        currentLocationGateway                  = new FakeCurrentLocationInteractorGateway();
        currentWeatherGateway                   = new FakeCurrentWeatherInteractorGateway();
        recordInteratorGateway                  = new FakeRecordInteratorGateway();
        testEGI                                 = new TestEGI();
        recordInteratorGateway.setEntityGatewayImplementation(testEGI);

        savedRecordInteractor                   = new FakeSavedRecordInteractor();
        savedRecordInteractor.setCurrentLocationGateway(currentLocationGateway);
        savedRecordInteractor.setCurrentWeatherGateway(currentWeatherGateway);
        savedRecordInteractor.setRecordGateway(recordInteratorGateway);

        location                                = new Location("dummyprovider");
        location.setLatitude(40.7143528);
        location.setLongitude(-74.0059731);

        weatherRequest                          = new WeatherRequest();
        weatherRequest.location                 = location;
    }

    @After
    public void teardown() {
        testEGI.getDataBaseAPI().clear();
        savedRecordInteractor                   = null;
        currentLocationGateway                  = null;
        currentWeatherGateway                   = null;
        recordInteratorGateway                  = null;
        weatherRequest                          = null;
    }
    // is there a record older than 24 hours -> YES - fetch NowRecord from 24 hours ->
    // hit Yahoo -> save Yahoo NowRecord -> create YstrdyRecord -> return YstrdyRecord

    @Test
    public void test_NullIfClosestRecordIsOlderThan30Hours() {
        Date d = new Date();
        d.setTime(d.getTime() - (YstrDate.thirtyHours() + 100));
        insertLocationRecord(d);

        WeatherResponse response = savedRecordInteractor.getReport(weatherRequest);

        assertThat(response).isNull();
    }

    @Test
    public void test_NullIfClosestRecordIsYoungerThan18Hours() {
        Date d = new Date();
        d.setTime(d.getTime() - (YstrDate.eighteenHours() - 100));
        insertLocationRecord(d);

        WeatherResponse response = savedRecordInteractor.getReport(weatherRequest);

        assertThat(response).isNull();
    }

    @Test
    public void test_responseIsNullWhenCurrentLocationGatewayFails() {
        insertLocationRecord(goodYstrdyDate());

        currentLocationGateway.shouldReturnNull = true;
        WeatherResponse response = savedRecordInteractor.getReport(weatherRequest);

        assertThat(response).isNull();
    }

    @Test
    public void test_responseIsNullWhenCurrentWeatherGatewayFails() {
        insertLocationRecord(goodYstrdyDate());

        currentWeatherGateway.shouldReturnNull = true;
        WeatherResponse response = savedRecordInteractor.getReport(weatherRequest);

        assertThat(response).isNull();
    }

    @Test
    public void test_responseWhenGood() {
        insertLocationRecord(goodYstrdyDate());

        WeatherResponse response = savedRecordInteractor.getReport(weatherRequest);

        assertThat(response.difference).isEqualTo(21);
        assertThat(response.today.temperature).isEqualTo(51);
        assertThat(response.ystrday.temperature).isEqualTo(30);
    }

    public Date goodYstrdyDate() {
        Date d = new Date();
        d.setTime(d.getTime() - (YstrDate.twentyFourHours()));
        return d;
    }

    public long insertLocationRecord(Date date) {
        RecordEntity record = new RecordEntity();
        record.location.setLatitude(location.getLatitude());
        record.location.setLongitude(location.getLongitude());
        record.date = date;
        record.temperature = 30;
        record.regionName = "Hello";

        RecordGateway recordGateway = new RecordGateway();
        recordGateway.setEntity(record);

        long rId = 0;
        try {
            rId = testEGI.insert(recordGateway);
        } catch (InvalidPropertiesFormatException expected) {

        }
        return rId;
    }




}
