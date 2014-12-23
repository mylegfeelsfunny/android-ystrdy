package com.twobros.hatebyte.ystrdy.network.current;

import android.location.Location;

import com.twobros.hatebyte.ystrdy.network.current.mock.FakeCurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.network.mock.FakeJSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;

import org.json.JSONObject;
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
public class CurrentLocationTest {

    FakeCurrentLocationGateway currentLocationGateway;
    RecordEntity recordEntity;
    FakeJSONEGI jsonEGI;

    @Before
    public void setup() {
        jsonEGI = new FakeJSONEGI();
        currentLocationGateway = new FakeCurrentLocationGateway();
        currentLocationGateway.setEntityGateway(jsonEGI);
        recordEntity = new RecordEntity();
    }

    @After
    public void teardown() {
        jsonEGI = null;
        recordEntity = null;
        currentLocationGateway = null;
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
        wr = currentLocationGateway.requestData(wr);
        assertThat(wr).isInstanceOf(RecordEntity.class);
    }

    @Test
    public void test_currentWeatherGateway_requestLocationData_returnsRecordEntityWithLocationData() {
        jsonEGI.sendCurrentsLocation = true;
        RecordEntity wr = requestModel();
        wr = currentLocationGateway.requestData(wr);
        assertThat(wr.cityName).isEqualTo("New York");
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutNoData() {
        RecordEntity wr = requestModel();
        currentLocationGateway.setRecord(wr);
        assertThat(currentLocationGateway.isValid()).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidRegionName() {
        recordEntity.cityName = "burbank";
        recordEntity.woeid = "112343";
        currentLocationGateway.setRecord(recordEntity);
        assertThat(currentLocationGateway.isValid()).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidCityName() {
        recordEntity.regionName = "upper middle barf";
        recordEntity.woeid = "112343";
        currentLocationGateway.setRecord(recordEntity);
        assertThat(currentLocationGateway.isValid()).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidWoeid() {
        recordEntity.regionName = "upper middle barf";
        recordEntity.cityName = "burbank";
        currentLocationGateway.setRecord(recordEntity);
        assertThat(currentLocationGateway.isValid()).isFalse();
    }

    @Test
    public void test_weatherResponseIsValidWithData() {
        recordEntity.regionName = "upper middle barf";
        recordEntity.cityName = "burbank";
        recordEntity.woeid = "112343";
        currentLocationGateway.setRecord(recordEntity);
        assertThat(currentLocationGateway.isValid()).isTrue();
    }

    @Test
    public void test_weatherResponseIsInvalidValidWithIOException() {
        jsonEGI.sendBackIOException = true;
        RecordEntity wr = currentLocationGateway.requestData(requestModel());

        assertThat(currentLocationGateway.isValid()).isFalse();
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
















