package com.tobros.hatebyte.ystrdy.YstrdyDatabase;

import android.content.Context;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.DifferenceEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.fest.assertions.api.Assertions.*;

/**
 * Created by scott on 12/14/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)

public class YstrdyDatabaseAPITest {

    YstrdyDatabaseAPI ystrdyDatabaseAPI;

    @Before
    public void setup() {
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        ystrdyDatabaseAPI = new YstrdyDatabaseAPI(context, "testLocationRecord.db");
    }

    @After
    public void teardown() {
        ystrdyDatabaseAPI.close();
        ystrdyDatabaseAPI = null;
    }

    @Test
    public void testOpen_createsDB() {
        ystrdyDatabaseAPI.open();
        assertTrue(ystrdyDatabaseAPI.database.isOpen());
    }

    @Test
    public void testClose_closesDB() {
        ystrdyDatabaseAPI.open();
        ystrdyDatabaseAPI.close();
        assertFalse(ystrdyDatabaseAPI.database.isOpen());
    }


//    @Test
//    public void test_insert() {
//
//    }
//
//    @Test
//    public void test_get() {
//
//    }
//
//    @Test
//    public void test_delete() {
//
//    }




}
