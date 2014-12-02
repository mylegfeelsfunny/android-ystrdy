package com.tobros.hatebyte.ystrdy;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tobros.hatebyte.ystrdy.database.LocationRecordDbHelper;
import com.tobros.hatebyte.ystrdy.database.LocationRecordsDbConnector;
import com.tobros.hatebyte.ystrdy.database.YstrRecord.YstrRecord;

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
            long recordId = dbConnector.insertLocationRecord(1.1f, 1.03f, null, 32.3f, "bridgewater",  false);
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

//    @Test
//    public void testRetrieve_dateClosestToADayAgoBelow() {
//        // populate db with a record, 23 hours and 55 mins ago
//
//    }

    public void populateDbWithBunchOfYstrDates() {
        int x = 0;
        for (int i = 0; i < 24; i++) {
            try {
                dbConnector.insertLocationRecord(1.1f, 1.03f, new Date(), 32.3f, "bridgewater",  (x % 2 == 0));
            } catch (Throwable expected) {

            }
            x++;
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
        int twentyFourHours = (24 * 60 * 60 + 1) * 1000;
        int moreThanFiveMinutes = (5 * 60 + 1) * 1000;

        // find a number 24 hours ago, but more than five minutes more or less
        Random r = new Random();
        int low = moreThanFiveMinutes;
        int high = twentyFourHours;
        int random = r.nextInt(high - low) + low;
        if (isBefore == false) {
            random *= -1;
        }

        d.setTime(d.getTime() - (twentyFourHours + random));
        return d;
    }


}