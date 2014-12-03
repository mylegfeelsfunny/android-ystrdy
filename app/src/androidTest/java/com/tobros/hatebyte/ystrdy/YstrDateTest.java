package com.tobros.hatebyte.ystrdy;

import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.database.LocationRecordDbHelper;
import com.tobros.hatebyte.ystrdy.database.LocationRecordsDbConnector;
import com.tobros.hatebyte.ystrdy.database.YstrRecord.YstrRecord;
import com.tobros.hatebyte.ystrdy.date.YstrDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.*;


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
