package com.twobros.hatebyte.ystrdy.network;

import com.twobros.hatebyte.ystrdy.network.historical.mock.BadForcastIO;
import com.twobros.hatebyte.ystrdy.network.mock.FakeJSONEGI;

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

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class JSONEGITest {

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

        JSONObject json = fakeJSONEGI.get(new BadForcastIO());
        assertThat(json).isNull();
    }


}