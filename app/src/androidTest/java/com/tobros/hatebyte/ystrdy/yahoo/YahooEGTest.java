package com.tobros.hatebyte.ystrdy.yahoo;

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
public class YahooEGTest {


    @Before
    public void setup() {
    }

    @After
    public void teardown() {

    }

    @Test
    public void test_vaid() {
        assertThat(true).isTrue();
    }

}
