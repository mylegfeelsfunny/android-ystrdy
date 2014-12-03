package com.tobros.hatebyte.ystrdy;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tobros.hatebyte.ystrdy.database.LocationRecordDbHelper;
import com.tobros.hatebyte.ystrdy.database.LocationRecordsDbConnector;
import com.tobros.hatebyte.ystrdy.database.YstrRecord.YstrRecord;
import com.tobros.hatebyte.ystrdy.date.YstrDate;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Random;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.*;


@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LocationRecordTest {

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
    public void testOpen_createsDB() {
        dbConnector.open();
        assertTrue(dbConnector.database.isOpen());
    }

    @Test
    public void testClose_closesDB() {
        dbConnector.open();
        dbConnector.close();
        assertFalse(dbConnector.database.isOpen());
    }

    @Test
    public void testInsert_throwsExceptionWithInvalidDate() {
        try {
            dbConnector.insertLocationRecord(1.1f, 1.03f, null, 32.3f, "bridgewater",  false);
            fail("Should fail with InvalidPropertiesFormatException");
        } catch (Throwable expected) {
            assertEquals(InvalidPropertiesFormatException.class, expected.getClass());
        }
    }

    @Test
    public void testRetrieve_dateClosestToADayAgo_24hours4Mins() {
        populateDbWithBunchOfYstrDates();

        try {
            dbConnector.insertLocationRecord(1.1f, 1.03f, dateFrom24Hours5Mins(), 32.3f, "24Hours5mins",  false);
            dbConnector.insertLocationRecord(1.1f, 1.03f, dateFrom24Hours4Mins(), 32.3f, "24Hours4mins",  false);
        } catch (InvalidPropertiesFormatException expected) {
        }
        YstrRecord yr = dbConnector.getClosestRecordFromYstrdy();
        assertThat("24Hours4mins").isEqualTo(yr.regionName);
    }

    @Test
    public void testRetrieve_dateClosestToADayAgo_23hours56Mins() {
        populateDbWithBunchOfYstrDates();

        try {
            dbConnector.insertLocationRecord(1.1f, 1.03f, dateFrom24Hours5Mins(), 32.3f, "24Hours5mins",  false);
            dbConnector.insertLocationRecord(1.1f, 1.03f, dateFrom24Hours4Mins(), 32.3f, "24Hours4mins",  false);
            dbConnector.insertLocationRecord(1.1f, 1.03f, dateFrom23Hours56Mins(), 32.3f, "23Hours56mins",  false);
            dbConnector.insertLocationRecord(1.1f, 1.03f, dateFrom23Hours55Mins(), 32.3f, "23Hours55mins",  false);
        } catch (InvalidPropertiesFormatException expected) {
        }
        YstrRecord yr = dbConnector.getClosestRecordFromYstrdy();
        assertThat("23Hours56mins").isEqualTo(yr.regionName);
    }

    @Test
    public void testRetrieve_dateClosestToADayAgoBelow() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 60 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        populateDbWithBunchOfYstrDates();
        try {
            dbConnector.insertLocationRecord((float)0, (float)0, d, 32.3f, "nearest",  false);
        } catch (InvalidPropertiesFormatException expected) {

        }

        YstrRecord yr = dbConnector.getClosestRecordFromYstrdy();
        assertThat(0.0f).isEqualTo(yr.latitude);
        assertThat(0.0f).isEqualTo(yr.longitude);
        assertThat("nearest").isEqualTo(yr.regionName);
    }

    @Test
    public void test_DbIsGreaterThan1DayOld_true() {
        // insert record, date set to 18 hours old
        // retrieve oldest record
        // assert has more than 1 day of records == true
    }

    @Test
    public void test_DbIsGreaterThan1DayOld_false() {
        // insert record, date set to 17 hours old
        // retrieve oldest record
        // assert has more than 1 day of records == false
    }



    public void populateDbWithBunchOfYstrDates() {
        for (int i = 1; i < 24; i++) {
            try {
                dbConnector.insertLocationRecord((float)i, (float)i, randomYstrDate((i % 2 == 0)), 32.3f, "bridgewater",  (i % 2 == 0));
            } catch (InvalidPropertiesFormatException expected) {

            }
        }
    }

    public Date dateFrom23Hours55Mins() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 55 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        return d;
    }

    public Date dateFrom24Hours5Mins() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 65 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        return d;
    }

    public Date dateFrom23Hours56Mins() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 56 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        return d;
    }

    public Date dateFrom24Hours4Mins() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 64 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        return d;
    }

    public Date randomYstrDate(Boolean isBefore) {
        Date d = new Date();
        long fourtyEightHours = (48 * 60 * 60 + 1) * 1000;
        long moreThanFiveMinutes = (6 * 60 + 1) * 1000;

        // find a number 24 hours ago, but more than five minutes more or less
        long low = 0;
        long high = 0;
        if (isBefore == false) {
            low = YstrDate.twentyFourHours() - (moreThanFiveMinutes);
            high = d.getTime();
        } else {
            high = YstrDate.twentyFourHours() - (moreThanFiveMinutes);
            low = fourtyEightHours;
        }
        long random = (long) Math.random() * (high - low) + low;
        d.setTime(d.getTime() - (YstrDate.twentyFourHours() + random));
        return d;
    }

}