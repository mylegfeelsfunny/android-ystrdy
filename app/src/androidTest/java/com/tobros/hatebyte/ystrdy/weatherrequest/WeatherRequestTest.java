package com.tobros.hatebyte.ystrdy.weatherrequest;

import com.tobros.hatebyte.ystrdy.egi.mock.FakeYstrdyDBAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.EntityGatewayImplementation;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.tobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.fest.assertions.api.Assertions.*;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class WeatherRequestTest {

    WeatherRequest weatherRequest;

    @Before
    public void setup() {
        weatherRequest = new WeatherRequest();
    }

    @After
    public void teardown() {
        weatherRequest = null;
    }

    @Test
    public void test_vaid() {
        assertThat(true).isTrue();
    }

}
