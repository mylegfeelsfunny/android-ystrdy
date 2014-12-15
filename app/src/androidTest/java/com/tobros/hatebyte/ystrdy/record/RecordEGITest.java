package com.tobros.hatebyte.ystrdy.record;

import android.content.Context;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.RecordEGI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.RecordEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import static org.mockito.Mockito.*;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.fest.assertions.api.Assertions.*;


@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class RecordEGITest {

    RecordEGI recordEGI;

    @Before
    public void setup() {
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        YstrdyDatabaseAPI ystrdyDatabaseAPI = new YstrdyDatabaseAPI(context, "testLocationRecord.db");
        recordEGI = new RecordEGI();
        recordEGI.databaseAPI = ystrdyDatabaseAPI;

    }

    @After
    public void teardown() {
        recordEGI.databaseAPI.clear();
        recordEGI = null;
    }

//    @Test
//    public void testInsert_throwsExceptionWithInvalidDate() {
//        RecordEntity record = new RecordEntity();
//        record.latitude = 1.1f;
//        record.longitude = 1.03f;
//        record.temperature = 32.3f;
//        record.regionName = "bridgewater";
//        try {
//            recordEGI.insertRecord(record);
//            fail("Should fail with InvalidPropertiesFormatException");
//        } catch (Throwable expected) {
//            assertEquals(InvalidPropertiesFormatException.class, expected.getClass());
//        }
//    }

    @Test
    public void testQuery_numRecords_0Records() {
        int numrecords = recordEGI.numRecords();
        assertThat(numrecords).isEqualTo(0);
    }

    @Test
    public void testQuery_numRecords_23Records() {
        populateDbWithBunchOfYstrDates();
        int numrecords = recordEGI.numRecords();
        assertThat(numrecords).isEqualTo(23);
    }

    @Test
    public void testQuery_dateClosestToADayAgo_24hours4Mins() {
        populateDbWithBunchOfYstrDates();

        insertLocationRecord(1.1f, 1.03f, dateFrom24Hours5Mins(), 32.3f, "24Hours5mins");
        insertLocationRecord(1.1f, 1.03f, dateFrom24Hours4Mins(), 32.3f, "24Hours4mins");

        RecordEG recordEG = recordEGI.getClosestRecordFromYstrdy();
        RecordEntity yr = recordEG.getEntity();
        assertThat("24Hours4mins").isEqualTo(yr.regionName);
    }

    @Test
    public void testQuery_dateClosestToADayAgo_23hours56Mins() {
        populateDbWithBunchOfYstrDates();

        insertLocationRecord(1.1f, 1.03f, dateFrom24Hours5Mins(), 32.3f, "24Hours5mins");
        insertLocationRecord(1.1f, 1.03f, dateFrom24Hours4Mins(), 32.3f, "24Hours4mins");
        insertLocationRecord(1.1f, 1.03f, dateFrom23Hours56Mins(), 32.3f, "23Hours56mins");
        insertLocationRecord(1.1f, 1.03f, dateFrom23Hours55Mins(), 32.3f, "23Hours55mins");

        RecordEG recordEG = recordEGI.getClosestRecordFromYstrdy();
        RecordEntity yr = recordEG.getEntity();
        assertThat("23Hours56mins").isEqualTo(yr.regionName);
    }

    @Test
    public void testQuery_dateClosestToADayAgoBelow() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 60 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        populateDbWithBunchOfYstrDates();

        insertLocationRecord((float)0, (float)0, d, 32.3f, "nearest");

        RecordEG recordEG = recordEGI.getClosestRecordFromYstrdy();
        RecordEntity yr = recordEG.getEntity();
        assertThat(0.0).isEqualTo(yr.latitude);
        assertThat(0.0).isEqualTo(yr.longitude);
        assertThat("nearest").isEqualTo(yr.regionName);
    }

    @Test
    public void testQuery_firstRecord_new() {
        RecordEG recordEG = recordEGI.getEarliestRecord();
        RecordEntity yr = recordEG.getEntity();
        assertNull(yr);
    }

    @Test
    public void testQuery_firstRecord() {
        populateDbWithBunchOfYstrDates();

        Date thirtyDaysAgoDate = new Date();
        long thirtyDaysAgo = (long) 30 * ((24 * 60 * 60 + 1) * 1000);
        thirtyDaysAgoDate.setTime(thirtyDaysAgoDate.getTime() - thirtyDaysAgo);

        Date thirtyOneDaysAgoDate = new Date();
        long thirtyOneDaysAgo = (long) 31 * ((24 * 60 * 60 + 1) * 1000);
        thirtyOneDaysAgoDate.setTime(thirtyOneDaysAgoDate.getTime() - thirtyOneDaysAgo);

        insertLocationRecord((float)0, (float)0, thirtyDaysAgoDate, 32.3f, thirtyDaysAgoDate.toString());
        insertLocationRecord((float)0, (float)0, thirtyOneDaysAgoDate, 32.3f, thirtyOneDaysAgoDate.toString());

        RecordEG recordEG = recordEGI.getEarliestRecord();
        RecordEntity yr = recordEG.getEntity();

        assertThat(yr.date).isEqualTo(thirtyOneDaysAgoDate);
        assertThat(yr.regionName).isEqualTo(thirtyOneDaysAgoDate.toString());
    }

    @Test
    public void testDelete_oldRecords() {
        populateDbWithBunchOfYstrDates();

        Date thirtyDaysAgoDate = new Date();
        long thirtyDaysAgo = (long) 30 * ((24 * 60 * 60 + 1) * 1000);
        thirtyDaysAgoDate.setTime(thirtyDaysAgoDate.getTime() - thirtyDaysAgo);
        insertLocationRecord((float)0, (float)0, thirtyDaysAgoDate, 32.3f, thirtyDaysAgoDate.toString());

        int numrecords = recordEGI.numRecords();

        assertThat(numrecords).isEqualTo(24);
        recordEGI.deleteExpiredRecords();

        int newnumrecords = recordEGI.numRecords();
        assertThat(newnumrecords).isEqualTo(23);
    }

    public void insertLocationRecord(float latitude, float longitude, Date date, float temp, String region) {
        RecordEntity record = new RecordEntity();
        record.latitude = latitude;
        record.longitude = longitude;
        record.date = date;
        record.temperature = temp;
        record.regionName = region;

        RecordEG recordEG = new RecordEG();
        try {
            recordEG.setEntity(record);
            recordEGI.insertRecord(recordEG);
        } catch (InvalidPropertiesFormatException expected) {

        }
    }

    public void populateDbWithBunchOfYstrDates() {
        for (int i = 1; i < 24; i++) {
            Date d = randomYstrDate((i % 2 == 0));
            insertLocationRecord((float)i, (float)i, d, 32.3f, d.toString());
        }
    }

    public Date dateFrom23Hours55Mins() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 55 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        return d;
    }

    public Date dateFrom24Hours5Mins() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 65 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        return d;
    }

    public Date dateFrom23Hours56Mins() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 56 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        return d;
    }

    public Date dateFrom24Hours4Mins() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 64 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        return d;
    }

    public Date randomYstrDate(Boolean isBefore) {
        Date d = new Date();
        long fourtyEightHours = (48 * 60 * 60 + 1) * 1000;
        long moreThanFiveMinutes = (6 * 60 + 1) * 1000;

        // find a number 24 hours ago, but more than five minutes more or less
        long low = 0;
        long high = 0;
        if (isBefore == false) {
            low = YstrDate.twentyFourHours() - (moreThanFiveMinutes);
            high = d.getTime();
        } else {
            high = YstrDate.twentyFourHours() - (moreThanFiveMinutes);
            low = fourtyEightHours;
        }
        long random = (long) Math.random() * (high - low) + low;
        d.setTime(d.getTime() - (YstrDate.twentyFourHours() + random));
        return d;
    }

}