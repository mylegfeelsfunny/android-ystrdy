package com.tobros.hatebyte.ystrdy;

import com.tobros.hatebyte.ystrdy.date.YstrDate;

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
import static org.junit.Assert.assertTrue;


@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class YstrDateTest {


    @Before
    public void setup() {}

    @Test
    public void test_dateIsOlderThanYesterday_returnsFalse() {
        Date date = new Date();
        date.setTime(date.getTime() - (YstrDate.firstDayTime() - 1));

        assertFalse(YstrDate.hasFirstDayPassed(date));
    }

    @Test
    public void test_dateIsOlderThanYesterday_returnsTrue() {
        Date date = new Date();
        date.setTime(date.getTime() - YstrDate.twentyFourHours());

        assertTrue(YstrDate.hasFirstDayPassed(date));
    }

}
