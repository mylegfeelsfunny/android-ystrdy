package com.twobros.hatebyte.ystrdy.difference;

import android.content.ContentValues;

import com.twobros.hatebyte.ystrdy.egi.mock.TestDifferenceGateway;
import com.twobros.hatebyte.ystrdy.egi.mock.TestEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;

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

/**
 * Created by scott on 12/11/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DifferenceGatewayUseCasesTest {

    TestDifferenceGateway differenceEG;
    TestEGI testEGI;

    @Before
    public void setup() {
        differenceEG = new TestDifferenceGateway();
        testEGI = new TestEGI();
        differenceEG.setEntityGatewayImplementation(testEGI);
    }

    @After
    public void teardown() {
        testEGI.getDataBaseAPI().clear();
        testEGI.close();
        differenceEG = null;
    }

    public ContentValues recordValues() {
        ContentValues values = new ContentValues();
        values.put(RecordGateway.COLUMN_LATITUDE, 0.1f);
        values.put(RecordGateway.COLUMN_LONGITUDE, 0.2f);
        values.put(RecordGateway.COLUMN_TEMPERATURE, 0.3f);
        values.put(RecordGateway.COLUMN_REGION_NAME, "region");
        values.put(RecordGateway.COLUMN_CITY_NAME, "scottville");
        values.put(RecordGateway.COLUMN_WOEID, "woeid");
        values.put(RecordGateway.COLUMN_DATE, new Date().getTime());
        return values;
    }

    @Test
    public void testInsert_throwsExceptionWithInvalidDate() {
        DifferenceEntity difference = new DifferenceEntity();
        difference.difference = 1.1f;
        difference.todayRecordId = 1;
        difference.ystrdyRecordId = 2;

        differenceEG.setEntity(difference);
        try {
            differenceEG.save();
            fail("Should fail with InvalidPropertiesFormatException");
        } catch (Throwable expected) {
            assertEquals(InvalidPropertiesFormatException.class, expected.getClass());
        }
    }

    @Test
    public void testInsert_differenceRecord() {
        DifferenceEntity difference = new DifferenceEntity();
        difference.difference = 1.1f;
        difference.todayRecordId = 1;
        difference.ystrdyRecordId = 2;
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

        insertDifferenceRecord(5.0f, date, 1, 2);

        for (int i = 0; i < 12; i++) {
            delay = ((24 - i) * 60 * 60 + 1) * 1000;
            date.setTime(date.getTime() - delay);
            insertDifferenceRecord(20.0f, date, i, i+1);
        }

        DifferenceEntity diffEntity = differenceEG.getLatestDifferenceRecord();
        assertThat(diffEntity.difference).isEqualTo(5.0f);
    }

    @Test
    public void testQuery_numYstrdyRecordsReturnsZero() {
        long numYstrdyRecords = differenceEG.numDifferenceRecords();
        assertThat(numYstrdyRecords).isEqualTo(0);
    }

    @Test
    public void testQuery_numYstrdyRecordsReturns12() {
        for (int i=0; i<12; i++) {
            insertDifferenceRecord(20.0f, new Date(), 1, 2);
        }
        int numYstrdyRecords = differenceEG.numDifferenceRecords();
        assertThat(numYstrdyRecords).isEqualTo(12);
    }

    @Test
    public void testQuery_lastYstrdyRecordReturnsRecorded() {
        long nowId = insertDifferenceRecord(20.0f, new Date(), 1, 2);
        int nowIdInt = (int)nowId;

        DifferenceEntity diffEntity = differenceEG.getLatestDifferenceRecord();
        assertThat(nowIdInt).isEqualTo(diffEntity.id);
        assertThat(1).isEqualTo(diffEntity.todayRecordId);
        assertThat(2).isEqualTo(diffEntity.ystrdyRecordId);
    }

    public long insertDifferenceRecord(float difference, Date date, int todayId,int ystrdyId ) {
        DifferenceEntity differenceEntity = new DifferenceEntity();
        differenceEntity.date = date;
        differenceEntity.difference = difference;
        differenceEntity.todayRecordId = todayId;
        differenceEntity.ystrdyRecordId = ystrdyId;

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
//        long todayRecordId = 0;
//        RecordEG recordEG = new RecordEG();
//
//        try {
//            recordEG.setEntity(record);
//            todayRecordId = recordEGI.insertRecord(recordEG);
//        } catch (InvalidPropertiesFormatException expected) {
//
//        }
//        return todayRecordId;
//    }

}