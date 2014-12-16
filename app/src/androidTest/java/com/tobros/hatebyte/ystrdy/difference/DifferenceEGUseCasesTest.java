package com.tobros.hatebyte.ystrdy.difference;

import android.content.ContentValues;
import android.content.Context;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.DifferenceEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.RecordEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.DifferenceEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.RecordEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.DifferenceEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;

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
import static org.mockito.Mockito.mock;

/**
 * Created by scott on 12/11/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DifferenceEGUseCasesTest {

    DifferenceEGI differenceEGI;
    RecordEGI recordEGI;

    @Before
    public void setup() {
//        Context context = Robolectric.getShadowApplication().getApplicationContext();
//        YstrdyDatabaseAPI ystrdyDatabaseAPI = new YstrdyDatabaseAPI(context, "testLocationRecord.db");
//        differenceEGI = new DifferenceEGI();
//        differenceEGI.databaseAPI = ystrdyDatabaseAPI;
//
//        recordEGI = new RecordEGI();
//        recordEGI.databaseAPI = ystrdyDatabaseAPI;
    }

    @After
    public void teardown() {
//        differenceEGI.databaseAPI.clear();
//        differenceEGI = null;
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
    public void test_avoid() {
        assertThat(true).isTrue();
    }

//    @Test
//    public void testInsert_ystrdyRecord() {
//        long nowId = insertYstrdyRecord(20.0f, new Date(), 0);
//        assertThat(nowId).isGreaterThan(0);
//    }
//
//    @Test
//    public void testQuery_lastYstrdyRecordReturnsNullWithNoRecord() {
//
//        DifferenceEG differenceEG = differenceEGI.getLatestDifferenceRecord();
//        DifferenceEntity recordEntity = differenceEG.getEntity();
//
//        assertThat(recordEntity).isNull();
//    }
//
//    @Test
//    public void testQuery_lastYstrdyRecordReturnsLatestDate() {
//        Date date = new Date();
//        long delay = ((30) * 60 * 60 + 1) * 1000;
//        date.setTime(date.getTime() - delay);
//
//        insertYstrdyRecord(5.0f, date, 0);
//
//        for (int i = 0; i < 12; i++) {
//            delay = ((24 - i) * 60 * 60 + 1) * 1000;
//            date.setTime(date.getTime() - delay);
//            insertYstrdyRecord(20.0f, date, 0);
//        }
//
//        DifferenceEG differenceEG = differenceEGI.getLatestDifferenceRecord();
//        DifferenceEntity recordEntity = differenceEG.getEntity();
//
//        assertThat(recordEntity.difference).isEqualTo(5.0f);
//    }
//
//    @Test
//    public void testQuery_numYstrdyRecordsReturnsZero() {
//        long numYstrdyRecords = differenceEGI.numDifferenceRecords();
//        assertThat(numYstrdyRecords).isEqualTo(0);
//    }
//
//    @Test
//    public void testQuery_numYstrdyRecordsReturns12() {
//        for (int i=0; i<12; i++) {
//            insertYstrdyRecord(20.0f, new Date(), 0);
//        }
//        int numYstrdyRecords = differenceEGI.numDifferenceRecords();
//        assertThat(numYstrdyRecords).isEqualTo(12);
//    }
//
//    @Test
//    public void testQuery_lastYstrdyRecordReturnsRecord() {
//        long nowId = insertYstrdyRecord(20.0f, new Date(), 0);
//        Integer nowIdInteger = Integer.parseInt(String.valueOf(nowId));
//
//        DifferenceEG differenceEG = differenceEGI.getLatestDifferenceRecord();
//        DifferenceEntity recordEntity = differenceEG.getEntity();
//        assertThat(nowId).isEqualTo(recordEntity.id);
//    }
//
//
//    public long insertYstrdyRecord(float difference, Date date, long nowId) {
//        DifferenceEntity record = new DifferenceEntity();
//        record.date = date;
//        record.difference = difference;
//        record.recordId = nowId;
//
//        long recordId = -1;
//        DifferenceEG differenceEG = new DifferenceEG();
//
//        try {
//            differenceEG.setEntity(record);
//            recordId = differenceEGI.insertDifferenceRecord(differenceEG);
//        } catch (InvalidPropertiesFormatException expected) {
//
//        }
//        return recordId;
//    }
//
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