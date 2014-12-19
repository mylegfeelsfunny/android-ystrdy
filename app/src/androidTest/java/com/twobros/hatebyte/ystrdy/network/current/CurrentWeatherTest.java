package com.twobros.hatebyte.ystrdy.network.current;

import android.location.Location;

import com.twobros.hatebyte.ystrdy.network.current.mock.FakeCurrentLocationGateway;
import com.twobros.hatebyte.ystrdy.network.current.mock.FakeCurrentWeatherGateway;
import com.twobros.hatebyte.ystrdy.network.mock.FakeJSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.entitygateway.CurrentLocationGateway;
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
    public void test_valid() {
        assertThat(true).isTrue();
    }

//    @Test
//    public void test_parseForTemperature() {
//        JSONObject json = JSONEGI.parseJSONString(FakeJSONEGI.currentWeatherResponseString());
//        float temperture = CurrentWeatherGateway.parseForTemperature(json);
//
//        assertThat(temperture).isEqualTo(51f);
//    }


}
