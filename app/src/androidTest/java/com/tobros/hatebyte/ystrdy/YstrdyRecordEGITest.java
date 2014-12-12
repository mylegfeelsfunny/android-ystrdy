package com.tobros.hatebyte.ystrdy;

import android.content.Context;

import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabase;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordEGI;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.NowRecordEntity;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.YstrdyRecordEntity;

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
import static org.mockito.Matchers.intThat;
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
    public void testInsert_ystrdyRecord() {
        long nowId = insertYstrdyRecord(20.0f, new Date(), 0);
        assertThat(nowId).isGreaterThan(0);
    }

    @Test
    public void testQuery_lastYstrdyRecordReturnsNullWithNoRecord() {
        YstrdyRecordEntity recordEntity = recordEGI.getLatestYstrdyRecord();
        assertThat(recordEntity).isNull();
    }

    @Test
    public void testQuery_lastYstrdyRecordReturnsLatestDate() {
        Date date = new Date();
        long delay = ((30) * 60 * 60 + 1) * 1000;
        date.setTime(date.getTime() - delay);

        insertYstrdyRecord(5.0f, date, 0);

        for (int i = 0; i < 12; i++) {
            delay = ((24 - i) * 60 * 60 + 1) * 1000;
            date.setTime(date.getTime() - delay);
            insertYstrdyRecord(20.0f, date, 0);
        }

        YstrdyRecordEntity recordEntity = recordEGI.getLatestYstrdyRecord();
        assertThat(recordEntity.difference).isEqualTo(5.0f);
    }

    @Test
    public void testQuery_numYstrdyRecordsReturnsZero() {
        long numYstrdyRecords = recordEGI.numYstrdyRecords();
        assertThat(numYstrdyRecords).isEqualTo(0);
    }

    @Test
    public void testQuery_numYstrdyRecordsReturns12() {
        for (int i=0; i<12; i++) {
            insertYstrdyRecord(20.0f, new Date(), 0);
        }
        int numYstrdyRecords = recordEGI.numYstrdyRecords();
        assertThat(numYstrdyRecords).isEqualTo(12);
    }

    @Test
    public void testQuery_lastYstrdyRecordReturnsRecord() {
        long nowId = insertYstrdyRecord(20.0f, new Date(), 0);
        Integer nowIdInteger = Integer.parseInt(String.valueOf(nowId));

        YstrdyRecordEntity recordEntity = recordEGI.getLatestYstrdyRecord();
        assertThat(nowId).isEqualTo(recordEntity.id);
    }


    public long insertYstrdyRecord(float difference, Date date, long nowId) {
        YstrdyRecordEntity record = new YstrdyRecordEntity();
        record.date = date;
        record.difference = difference;
        record.nowRecordId = nowId;

        long recordId = -1;
        try {
            recordId = recordEGI.insertYstrdyRecord(record);
        } catch (InvalidPropertiesFormatException expected) {

        }
        return recordId;
    }

    public long insertLocationRecord(float latitude, float longitude, Date date, float temp, String region) {
        NowRecordEntity record = new NowRecordEntity();
        record.latitude = latitude;
        record.longitude = longitude;
        record.date = date;
        record.temperature = temp;
        record.regionName = region;

        long recordId = 0;
        try {
            recordId = recordEGI.insertNowRecord(record);
        } catch (InvalidPropertiesFormatException expected) {

        }
        return recordId;
    }

}