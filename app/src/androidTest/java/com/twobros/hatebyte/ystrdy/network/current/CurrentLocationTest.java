package com.twobros.hatebyte.ystrdy.network.current;

import android.location.Location;

import com.twobros.hatebyte.ystrdy.network.current.mock.FakeCurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.network.mock.FakeJSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.HistoricalWeatherGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by scott on 12/16/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CurrentLocationTest {

    FakeCurrentLocationGateway currentWeatherGateway;
    RecordEntity recordEntity;
    FakeJSONEGI jsonEGI;

    @Before
    public void setup() {
        jsonEGI = new FakeJSONEGI();
        currentWeatherGateway = new FakeCurrentLocationGateway();
        currentWeatherGateway.setEntityGateway(jsonEGI);
        recordEntity = new RecordEntity();
    }

    @After
    public void teardown() {
        jsonEGI = null;
        recordEntity = null;
        currentWeatherGateway = null;
    }

    @Test
    public void test_parseForCityName() {
        JSONObject json = JSONEGI.parseJSONString(FakeJSONEGI.currentLocationResponseString());
        JSONObject locJson = CurrentLocationGateway.parseForLocation(json);
        String cityName  = CurrentLocationGateway.valueStringForKey(locJson, "city");

        assertThat(cityName).isEqualTo("New York");
    }

    @Test
    public void test_parseForRegionName() {
        JSONObject json = JSONEGI.parseJSONString(FakeJSONEGI.currentLocationResponseString());
        JSONObject locJson = CurrentLocationGateway.parseForLocation(json);
        String regionName  = CurrentLocationGateway.valueStringForKey(locJson, "neighborhood");

        assertThat(regionName).isEqualTo("Soho");
    }

    @Test
    public void test_parseForWoeid() {
        JSONObject json = JSONEGI.parseJSONString(FakeJSONEGI.currentLocationResponseString());
        JSONObject locJson = CurrentLocationGateway.parseForLocation(json);
        String woeid  = CurrentLocationGateway.valueStringForKey(locJson, "woeid");

        assertThat(woeid).isEqualTo("12761344");
    }

    @Test
    public void test_currentWeatherGateway_requestLocationData_recordEntity() {
        jsonEGI.sendCurrentsLocation = true;
        RecordEntity wr = requestModel();
        wr = currentWeatherGateway.requestData(wr);
        assertThat(wr).isInstanceOf(RecordEntity.class);
    }

    @Test
    public void test_currentWeatherGateway_requestLocationData_returnsRecordEntityWithLocationData() {
        jsonEGI.sendCurrentsLocation = true;
        RecordEntity wr = requestModel();
        wr = currentWeatherGateway.requestData(wr);
        assertThat(wr.cityName).isEqualTo("New York");
        assertThat(wr.regionName).isEqualTo("Soho");
        assertThat(wr.woeid).isEqualTo("12761344");
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutNoData() {
        RecordEntity wr = requestModel();
        currentWeatherGateway.setRecord(wr);
        assertThat(currentWeatherGateway.isValid()).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidRegionName() {
        recordEntity.cityName = "burbank";
        recordEntity.woeid = "112343";
        currentWeatherGateway.setRecord(recordEntity);
        assertThat(currentWeatherGateway.isValid()).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidCityName() {
        recordEntity.regionName = "upper middle barf";
        recordEntity.woeid = "112343";
        currentWeatherGateway.setRecord(recordEntity);
        assertThat(currentWeatherGateway.isValid()).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidWoeid() {
        recordEntity.regionName = "upper middle barf";
        recordEntity.cityName = "burbank";
        currentWeatherGateway.setRecord(recordEntity);
        assertThat(currentWeatherGateway.isValid()).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidWithData() {
        recordEntity.regionName = "upper middle barf";
        recordEntity.cityName = "burbank";
        recordEntity.woeid = "112343";
        currentWeatherGateway.setRecord(recordEntity);
        assertThat(currentWeatherGateway.isValid()).isTrue();
    }

    @Test
    public void test_weatherResponseIsInvalidValidWithIOException() {
        jsonEGI.sendBackIOException = true;
        RecordEntity wr = requestModel();
        wr = currentWeatherGateway.requestData(wr);

        assertThat(currentWeatherGateway.isValid()).isFalse();
    }


    public RecordEntity requestModel() {
        Location location = new Location("dummyprovider");
        location.setLatitude(40.7143528);
        location.setLongitude(-74.0059731);

        RecordEntity rm = new RecordEntity();
        rm.location = location;

        return rm;
    }



}
















