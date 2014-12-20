package com.twobros.hatebyte.ystrdy.network.current;

import android.location.Location;

import com.twobros.hatebyte.ystrdy.network.current.mock.FakeCurrentWeatherGateway;
import com.twobros.hatebyte.ystrdy.network.mock.FakeJSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentWeatherGateway;
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
public class CurrentWeatherTest {

    FakeCurrentWeatherGateway currentWeatherGateway;
    RecordEntity recordEntity;
    FakeJSONEGI jsonEGI;

    @Before
    public void setup() {
        jsonEGI = new FakeJSONEGI();
        currentWeatherGateway = new FakeCurrentWeatherGateway();
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
    public void test_parseForTemperature() {
        JSONObject json = JSONEGI.parseJSONString(FakeJSONEGI.currentWeatherResponseString());
        float temperature = CurrentWeatherGateway.parseForTemperature(json);

        assertThat(temperature).isEqualTo(51);
    }

    @Test
    public void test_currentWeatherGateway_requestWeatherDate_recordEntity() {
        jsonEGI.sendCurrentWeather = true;
        RecordEntity wr = currentWeatherGateway.requestData(requestModel());
        assertThat(wr).isInstanceOf(RecordEntity.class);
    }

    @Test
    public void test_currentWeatherGateway_requestWeatherData_populatesRecordEntity() {
        jsonEGI.sendCurrentWeather = true;
        RecordEntity wr = currentWeatherGateway.requestData(requestModel());
        assertThat(wr.temperature).isEqualTo(51);
    }

    @Test
    public void test_weatherResponseIsValidWithData() {
        recordEntity.temperature = 51;
        currentWeatherGateway.setRecord(recordEntity);
        assertThat(currentWeatherGateway.isValid()).isTrue();
    }

    @Test
    public void test_weatherResponseIsInvalidValidWithIOException() {
        jsonEGI.sendBackIOException = true;
        RecordEntity wr = currentWeatherGateway.requestData(requestModel());

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
