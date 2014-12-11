package com.tobros.hatebyte.ystrdy;

import android.content.Context;

import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabase;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordEGI;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.NowRecordEntity;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.NowRecordEG;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.mockito.Mockito.*;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class NowRecordEGTest {

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

    @Test
    public void test_YstrRecordInitsWithCursor() {

        Date rDate = new Date();
        long recordId = 0;
        NowRecordEntity record = new NowRecordEntity();
        record.latitude = 1.1f;
        record.longitude = 1.03f;
        record.date = rDate;
        record.temperature = 32.3f;
        record.regionName = "bridgewater";
        record.isFirst = false;

        try {
            recordId = recordEGI.insertNowRecord(record);
        } catch (Throwable expected) {

        }

        NowRecordEntity yr = recordEGI.getNowRecordById(recordId);
        assertThat(1.1f).isEqualTo(yr.latitude);
        assertThat(1.03f).isEqualTo(yr.longitude);
        assertThat(rDate.getTime()).isEqualTo(yr.date.getTime());
        assertThat(32.3f).isEqualTo(yr.temperature);
        assertThat("bridgewater").isEqualTo(yr.regionName);
        assertThat(false).isEqualTo(yr.isFirst);
    }

}
