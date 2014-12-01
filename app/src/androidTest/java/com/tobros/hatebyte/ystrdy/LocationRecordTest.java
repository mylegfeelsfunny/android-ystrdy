package com.tobros.hatebyte.ystrdy;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tobros.hatebyte.ystrdy.database.LocationRecordDbHelper;
import com.tobros.hatebyte.ystrdy.database.LocationRecordsDbConnector;

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
        ShadowLog.stream = System.out;

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
    public void testInsert_throwsExceptionWithInvalidData() {
        try {
            long recordId = dbConnector.insertLocationRecord(1.1f, 0.0f, new Date());
            fail("Should fail with Exception");
        } catch (Throwable expected) {
            assertEquals(InvalidPropertiesFormatException.class, expected.getClass());
        }
    }

}