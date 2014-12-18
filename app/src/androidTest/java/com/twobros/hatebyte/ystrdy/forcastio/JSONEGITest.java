package com.twobros.hatebyte.ystrdy.forcastio;

import com.twobros.hatebyte.ystrdy.forcastio.mock.FakeJSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.JSONEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.forcastio.ForcastioGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.yahooweather.YahooAPI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.network.yahooweather.YahooGateway;

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

        String responseString = fakeJSONEGI.get(new BadYahoo());
        assertThat(responseString).isNullOrEmpty();
    }

    class BadYahoo extends ForcastioGateway implements JSONEGI.IJSONEntityGateway {
        public String url() {
            return "badURL";
        }
    }

}