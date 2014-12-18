package com.tobros.hatebyte.ystrdy.forcastio;

import com.tobros.hatebyte.ystrdy.forcastio.mock.FakeForcastioGateway;
import com.tobros.hatebyte.ystrdy.forcastio.mock.FakeJSONEGI;
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

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class ForcastioEGITest {

    FakeJSONEGI fakeJSONEGI;

    @Before
    public void setup() {
        fakeJSONEGI = new FakeJSONEGI();
    }

    @After
    public void teardown() {
        fakeJSONEGI = null;
    }

    @Test
    public void test_IOExceptionSendsNullString() {
        fakeJSONEGI.sendBackIOException = true;

        String responseString = fakeJSONEGI.get("badURL");
        assertThat(responseString).isNullOrEmpty();
    }

}