package com.tobros.hatebyte.ystrdy.difference;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.RecordEGI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by scott on 12/11/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DifferenceEGTest {

    RecordEGI mockRecordEGI;

    @Before
    public void setup() {
//        differenceEGI = mock(RecordEGI.class);
//        when(differenceEGI.getDatabaseName()).thenReturn("testLocationRecord.db");
    }

    @After
    public void teardown() {
//        differenceEGI.clearDatabase();
//        differenceEGI = null;
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
//        record.recordId = nowId;
//        record.date = date;
//
//        try {
//            differenceEGI.insertDifferenceRecord(record);
//        } catch (InvalidPropertiesFormatException expected) {
//
//        }
//    }

}
