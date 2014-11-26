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

import java.util.Date;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.*;


@Config(manifest = "./src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class LocationRecordTests {

    LocationRecordsDbConnector dbConnector;

    @Before
    public void setup() {
        dbConnector = new LocationRecordsDbConnector(Robolectric.getShadowApplication().getApplicationContext(), "testLocationRecord.db");
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
            long recordId = dbConnector.insertLocationRecord(1.1f, 0, new Date());
            fail("Should fail with Exception");
        } catch (Exception e) {
            assertEquals(e.getCause().getClass().getName(), CoreMatchers.is(Exception.class.getName()));
        }
    }

}