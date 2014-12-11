package com.tobros.hatebyte.ystrdy;

import android.content.Context;

import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabase;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordEGI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by scott on 12/11/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class YstrdyRecordEGITest {

    RecordEGI recordEGI;

    @Before
    public void setup() {
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        RecordDatabase recordDatabase = new RecordDatabase(context, "testLocationRecord.db");
        recordEGI = new RecordEGI(recordDatabase);
    }

    @After
    public void teardown() {
        recordEGI.clearDatabase();
        recordEGI = null;
    }

    @Test
    public void test_true() {
        assertThat(true);
    }

}