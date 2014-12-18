package com.tobros.hatebyte.ystrdy.difference;

import android.content.ContentValues;

import com.tobros.hatebyte.ystrdy.egi.mock.TestDifferenceEG;
import com.tobros.hatebyte.ystrdy.egi.mock.TestEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by scott on 12/11/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DifferenceEGUseCasesTest {

    TestDifferenceEG differenceEG;
    TestEGI testEGI;

    @Before
    public void setup() {
        differenceEG = new TestDifferenceEG();
        testEGI = new TestEGI();
        differenceEG.setEntityGatewayImplementation(testEGI);
    }

    @After
    public void teardown() {
        testEGI.getDatabaseAPI().clear();
        differenceEG = null;
    }

    public ContentValues recordValues() {
        ContentValues values = new ContentValues();
        values.put(RecordEntity.COLUMN_LATITUDE, 0.1f);
        values.put(RecordEntity.COLUMN_LONGITUDE, 0.2f);
        values.put(RecordEntity.COLUMN_TEMPERATURE, 0.3f);
        values.put(RecordEntity.COLUMN_REGION_NAME, "region");
        values.put(RecordEntity.COLUMN_CITY_NAME, "scottville");
        values.put(RecordEntity.COLUMN_WOEID, "woeid");
        values.put(RecordEntity.COLUMN_DATE, new Date().getTime());
        return values;
    }

    @Test
    public void testInsert_throwsExceptionWithInvalidDate() {
        DifferenceEntity difference = new DifferenceEntity();
        difference.difference = 1.1f;
        difference.recordId = 1;
//        difference.date = new Date();

        differenceEG.setEntity(difference);
        try {
            differenceEG.save();
            fail("Should fail with InvalidPropertiesFormatException");
        } catch (Throwable expected) {
            assertEquals(InvalidPropertiesFormatException.class, expected.getClass());
        }
    }

    @Test
    public void testInsert_ystrdyRecord() {
        DifferenceEntity difference = new DifferenceEntity();
        difference.difference = 1.1f;
        difference.recordId = 1;
        difference.date = new Date();

        differenceEG.setEntity(difference);

        long rowid = 0;
        try {
            rowid = differenceEG.save();
        } catch (Throwable expected) {
        }

        assertThat(rowid).isGreaterThan(0);
    }

    @Test
    public void testQuery_lastYstrdyRecordReturnsNullWithNoRecord() {
        DifferenceEntity difference = differenceEG.getLatestDifferenceRecord();
        assertThat(difference).isNull();
    }

    @Test
    public void testQuery_lastYstrdyRecordReturnsLatestDate() {
        Date date = new Date();
        long delay = ((30) * 60 * 60 + 1) * 1000;
        date.setTime(date.getTime() - delay);

        insertDifferenceRecord(5.0f, date, 0);

        for (int i = 0; i < 12; i++) {
            delay = ((24 - i) * 60 * 60 + 1) * 1000;
            date.setTime(date.getTime() - delay);
            insertDifferenceRecord(20.0f, date, 0);
        }

        DifferenceEntity recordEntity = differenceEG.getLatestDifferenceRecord();
        assertThat(recordEntity.difference).isEqualTo(5.0f);
    }

    @Test
    public void testQuery_numYstrdyRecordsReturnsZero() {
        long numYstrdyRecords = differenceEG.numDifferenceRecords();
        assertThat(numYstrdyRecords).isEqualTo(0);
    }

    @Test
    public void testQuery_numYstrdyRecordsReturns12() {
        for (int i=0; i<12; i++) {
            insertDifferenceRecord(20.0f, new Date(), 0);
        }
        int numYstrdyRecords = differenceEG.numDifferenceRecords();
        assertThat(numYstrdyRecords).isEqualTo(12);
    }

    @Test
    public void testQuery_lastYstrdyRecordReturnsRecorded() {
        long nowId = insertDifferenceRecord(20.0f, new Date(), 0);
        int nowIdInt = (int)nowId;

        DifferenceEntity recordEntity = differenceEG.getLatestDifferenceRecord();
        assertThat(nowIdInt).isEqualTo(recordEntity.id);
    }

    public long insertDifferenceRecord(float difference, Date date, long nowId) {
        DifferenceEntity differenceEntity = new DifferenceEntity();
        differenceEntity.date = date;
        differenceEntity.difference = difference;
        differenceEntity.recordId = nowId;

        differenceEG.setEntity(differenceEntity);

        long rowid = -1;
        try {
            rowid = differenceEG.save();
        } catch (Throwable expected) {
        }
        return rowid;
    }

//    public long insertLocationRecord(float latitude, float longitude, Date date, float temp, String region) {
//        RecordEntity record = new RecordEntity();
//        record.latitude = latitude;
//        record.longitude = longitude;
//        record.date = date;
//        record.temperature = temp;
//        record.regionName = region;
//
//        long recordId = 0;
//        RecordEG recordEG = new RecordEG();
//
//        try {
//            recordEG.setEntity(record);
//            recordId = recordEGI.insertRecord(recordEG);
//        } catch (InvalidPropertiesFormatException expected) {
//
//        }
//        return recordId;
//    }

}