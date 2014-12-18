package com.twobros.hatebyte.ystrdy.forcastio;


import android.location.Location;

import com.twobros.hatebyte.ystrdy.forcastio.mock.FakeForcastioGateway;
import com.twobros.hatebyte.ystrdy.forcastio.mock.FakeJSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio.ForcastioGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

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

    RecordEntity recordEntity;
//    DifferenceEntity differenceEntity;

    @Before
    public void setup() {
        forcastioGateway = new FakeForcastioGateway();
        recordEntity = new RecordEntity();
    }

    @After
    public void teardown() {
        recordEntity = null;
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
        RecordEntity wr = requestModel();
        wr = forcastioGateway.request(wr);
        assertThat(wr).isInstanceOf(RecordEntity.class);
    }

    @Test
    public void test_forcastIOCanReturnSyncronousData() {
        RecordEntity wr = requestModel();
        wr = forcastioGateway.request(wr);
        assertThat(wr.temperature).isEqualTo(45.9f);
        assertThat(wr.regionName).isEqualTo("America/New_York");
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidTemperature() {
        RecordEntity wr = requestModel();
        assertThat(ForcastioGateway.isValid(wr)).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidRegionName() {
        recordEntity.temperature = 100.f;
        assertThat(ForcastioGateway.isValid(recordEntity)).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidValid() {
        recordEntity.temperature = 100.f;
        recordEntity.regionName = "newyork";
        assertThat(ForcastioGateway.isValid(recordEntity)).isTrue();
    }

    @Test
    public void test_weatherResponseIsInvalidValidWithIOException() {
        FakeJSONEGI fakeJSONEGI = new FakeJSONEGI();
        fakeJSONEGI.sendBackIOException = true;
        forcastioGateway.setForcastioGateway(fakeJSONEGI);

        RecordEntity wr = requestModel();
        wr = forcastioGateway.request(wr);

        assertThat(ForcastioGateway.isValid(wr)).isFalse();
    }

    public RecordEntity requestModel() {
        Location location = new Location("dummyprovider");
        location.setLatitude(40.7143528);
        location.setLongitude(-74.0059731);

        Date ystrday = new Date();
        ystrday.setTime(ystrday.getTime() - YstrDate.twentyFourHours());

        RecordEntity rm = new RecordEntity();
        rm.date = ystrday;
        rm.location = location;

        return rm;
    }


}
