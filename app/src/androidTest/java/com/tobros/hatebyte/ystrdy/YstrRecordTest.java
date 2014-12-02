package com.tobros.hatebyte.ystrdy;

import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.database.LocationRecordDbHelper;
import com.tobros.hatebyte.ystrdy.database.LocationRecordsDbConnector;
import com.tobros.hatebyte.ystrdy.database.YstrRecord.YstrRecord;

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
public class YstrRecordTest {

    LocationRecordsDbConnector dbConnector;
    LocationRecordDbHelper databaseHelper;

    @Before
    public void setup() {
        databaseHelper = new LocationRecordDbHelper(Robolectric.getShadowApplication().getApplicationContext(), "testLocationRecord.db");
        dbConnector = new LocationRecordsDbConnector(databaseHelper);
    }

    @After
    public void teardown() {
        dbConnector.clearDatabase();
        dbConnector = null;
    }

    @Test
    public void test_YstrRecordInitsWithCursor() {
        Date rDate = new Date();
        long recordId = 0;
        try {
            recordId = dbConnector.insertLocationRecord(1.1f, 1.03f, rDate, 32.3f, "bridgewater",  false);
        } catch (Throwable expected) {
        }

        YstrRecord yr = dbConnector.getRecordById(recordId);
        assertThat(1.1f).isEqualTo(yr.latitude);
        assertThat(1.03f).isEqualTo(yr.longitude);
        assertThat(rDate.getTime()).isEqualTo(yr.date.getTime());
        assertThat(32.3f).isEqualTo(yr.temperature);
        assertThat("bridgewater").isEqualTo(yr.regionName);
        assertThat(false).isEqualTo(yr.isFirst);
    }

}
