package com.tobros.hatebyte.ystrdy;

import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabase;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordEGI;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.YstrdyRecordEntity;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.YstrdyRecordEG;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by scott on 12/11/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class YstrdyRecordEGTest {

    RecordEGI mockRecordEGI;

    @Before
    public void setup() {
//        recordEGI = mock(RecordEGI.class);
//        when(recordEGI.getDatabaseName()).thenReturn("testLocationRecord.db");
    }

    @After
    public void teardown() {
//        recordEGI.clearDatabase();
//        recordEGI = null;
    }

    @Test
    public void test_true() {
        assertThat(true);
    }


//    @Test
//    public void testInsert_nowRecord() {
//
//    }
//
//    public void insertLocationRecord(Date date, float difference, int nowId) {
//        YstrdyRecordEntity record = new YstrdyRecordEntity();
//        record.difference = difference;
//        record.nowRecordId = nowId;
//        record.date = date;
//
//        try {
//            recordEGI.insertYstrdyRecord(record);
//        } catch (InvalidPropertiesFormatException expected) {
//
//        }
//    }

}
