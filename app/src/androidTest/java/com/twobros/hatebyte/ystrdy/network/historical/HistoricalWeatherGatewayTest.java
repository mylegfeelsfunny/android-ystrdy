package com.twobros.hatebyte.ystrdy.network.historical;


import android.location.Location;

import com.twobros.hatebyte.ystrdy.network.historical.mock.FakeHistoricalWeatherGateway;
import com.twobros.hatebyte.ystrdy.network.mock.FakeJSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.implementation.JSONEGI;
import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.HistoricalWeatherGateway;

import org.json.JSONObject;
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
public class HistoricalWeatherGatewayTest {

    FakeHistoricalWeatherGateway forcastioGateway;
    RecordEntity recordEntity;
    FakeJSONEGI jsonEGI;

    @Before
    public void setup() {
        jsonEGI = new FakeJSONEGI();
        forcastioGateway = new FakeHistoricalWeatherGateway();
        forcastioGateway.setEntityGateway(jsonEGI);
        jsonEGI.sendHistorical = true;
        recordEntity = new RecordEntity();
    }

    @After
    public void teardown() {
        jsonEGI = null;
        recordEntity = null;
        forcastioGateway = null;
    }


    @Test
    public void test_parseForTemperature() {
        Float temperature = 0f;
        JSONObject json = JSONEGI.parseJSONString(FakeJSONEGI.historicalResponseString());
        temperature  = HistoricalWeatherGateway.parseForTemperature(json);
        assertThat(temperature).isEqualTo(45.9f);
    }

    @Test
    public void test_forcastIORequestReturnsRecordEntity() {
        RecordEntity wr = requestModel();
        wr = forcastioGateway.requestData(wr);
        assertThat(wr).isInstanceOf(RecordEntity.class);
    }

    @Test
    public void test_forcastIOCanPopulateFromJSON() {
        RecordEntity wr = requestModel();
        wr = forcastioGateway.requestData(wr);
        assertThat(wr.temperature).isEqualTo(45.9f);
        assertThat(wr.regionName).isEqualTo("America/New_York");
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidTemperature() {
        RecordEntity wr = requestModel();
        forcastioGateway.setRecord(wr);
        assertThat(forcastioGateway.isValid()).isFalse();
    }

    @Test
    public void test_weatherResponseIsInvalidWithoutValidRegionName() {
        recordEntity.temperature = 100.f;
        forcastioGateway.setRecord(recordEntity);
        assertThat(forcastioGateway.isValid()).isFalse();
    }

    @Test
    public void test_weatherResponseIsdValid() {
        recordEntity.temperature = 100.f;
        recordEntity.regionName = "newyork";
        forcastioGateway.setRecord(recordEntity);
        assertThat(forcastioGateway.isValid()).isTrue();
    }

    @Test
    public void test_weatherResponseIsInvalidValidWithIOException() {
        jsonEGI.sendBackIOException = true;
        RecordEntity wr = requestModel();
        wr = forcastioGateway.requestData(wr);
        assertThat(forcastioGateway.isValid()).isFalse();
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
