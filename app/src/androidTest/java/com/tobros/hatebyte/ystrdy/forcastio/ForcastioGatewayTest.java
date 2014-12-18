package com.tobros.hatebyte.ystrdy.forcastio;


import android.location.Location;

import com.tobros.hatebyte.ystrdy.forcastio.mock.FakeForcastioGateway;
import com.tobros.hatebyte.ystrdy.forcastio.mock.FakeJSONEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio.ForcastioGateway;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

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
public class ForcastioGatewayTest {

    FakeForcastioGateway forcastioGateway;
    WeatherResponse weatherResponse;

    @Before
    public void setup() {
        forcastioGateway = new FakeForcastioGateway();
        weatherResponse = new WeatherResponse();
    }

    @After
    public void teardown() {
        weatherResponse = null;
        forcastioGateway = null;
    }


    @Test
    public void test_parseForTemperature() {
        Float temperature = 0f;
        temperature  = ForcastioGateway.parseForTemperature(FakeJSONEGI.forcastioResponseString());
        assertThat(temperature).isEqualTo(45.9f);
    }

    @Test
    public void test_forcastIORequestReturnsWeatherResponse() {
        WeatherRequest weatherRequest = requestModel();
        WeatherResponse weatherResponse = null;
        weatherResponse = forcastioGateway.request(weatherRequest);
        assertThat(weatherResponse).isInstanceOf(WeatherResponse.class);
    }

    @Test
    public void test_forcastIOCanReturnSyncronousData() {
        WeatherRequest weatherRequest = requestModel();
        WeatherResponse weatherResponse = null;
        weatherResponse = forcastioGateway.request(weatherRequest);
        assertThat(weatherResponse.temperature).isEqualTo(45.9f);
        assertThat(weatherResponse.regionName).isEqualTo("America/New_York");
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidTemperature() {
        weatherResponse.regionName = "newyork";
        assertThat(ForcastioGateway.isValid(weatherResponse)).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidRegionName() {
        weatherResponse.temperature = 100.f;
        assertThat(ForcastioGateway.isValid(weatherResponse)).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidValid() {
        weatherResponse.temperature = 100.f;
        weatherResponse.regionName = "newyork";
        assertThat(ForcastioGateway.isValid(weatherResponse)).isTrue();
    }

    public WeatherRequest requestModel() {
        Location location = new Location("dummyprovider");
        location.setLatitude(40.7143528);
        location.setLongitude(-74.0059731);

        Date ystrday = new Date();
        ystrday.setTime(ystrday.getTime() - YstrDate.twentyFourHours());

        WeatherRequest rm = new WeatherRequest();
        rm.date = ystrday;
        rm.location = location;
        return rm;
    }


}
