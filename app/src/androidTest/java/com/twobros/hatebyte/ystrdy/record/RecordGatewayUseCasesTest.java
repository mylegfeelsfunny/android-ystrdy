package com.twobros.hatebyte.ystrdy.record;

import com.twobros.hatebyte.ystrdy.egi.mock.TestRecordGateway;
import com.twobros.hatebyte.ystrdy.egi.mock.TestEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;
import com.twobros.hatebyte.ystrdy.date.YstrDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.fest.assertions.api.Assertions.*;


@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class RecordGatewayUseCasesTest {

    TestRecordGateway recordEG;
    TestEGI testEGI;

    @Before
    public void setup() {
        recordEG = new TestRecordGateway();
        testEGI = new TestEGI();
        recordEG.setEntityGatewayImplementation(testEGI);
    }

    @After
    public void teardown() {
        testEGI.getDataBaseAPI().clear();
        testEGI.close();
        recordEG = null;
    }

//    public ContentValues differenceValues() {
//        ContentValues values = new ContentValues();
//        values.put(DifferenceEntity.COLUMN_DIFFERENCE, 4.f);
//        values.put(DifferenceEntity.COLUMN_DATE, new Date().getTime());
//        return values;
//    }

    @Test
    public void testInsert_throwsExceptionWithInvalidDate() {
        RecordEntity record = new RecordEntity();
        record.location.setLatitude(1.1f);
        record.location.setLongitude(1.03f);
        record.temperature = 32.3f;
        record.regionName = "bridgewater";

        recordEG.setEntity(record);
        try {
            recordEG.save();
            fail("Should fail with InvalidPropertiesFormatException");
        } catch (Throwable expected) {
            assertEquals(InvalidPropertiesFormatException.class, expected.getClass());
        }
    }

    @Test
    public void testInsert() {
        RecordEntity record = new RecordEntity();
        record.location.setLatitude(1.1f);
        record.location.setLongitude(1.03f);
        record.temperature = 32.3f;
        record.regionName = "bridgewater";
        record.date = new Date();

        recordEG.setEntity(record);

        long rowid = 0;
        try {
            rowid = recordEG.save();
        } catch (Throwable expected) {
        }

        assertThat(rowid).isGreaterThan(0);
    }

    @Test
    public void testQuery_numRecords_0Records() {
        int numrecords = recordEG.numRecords();
        assertThat(numrecords).isEqualTo(0);
    }

    @Test
    public void testQuery_numRecords_23Records() {
        populateDbWithBunchOfYstrDates();
        int numrecords = recordEG.numRecords();
        assertThat(numrecords).isEqualTo(23);
    }

    @Test
    public void testQuery_recordFromDateClosestToADayAgo_24hours4Mins() {
        populateDbWithBunchOfYstrDates();

        insertLocationRecord(1.1f, 1.03f, dateFrom24Hours5Mins(), 32.3f, "24Hours5mins");
        insertLocationRecord(1.1f, 1.03f, dateFrom24Hours4Mins(), 32.3f, "24Hours4mins");

        RecordEntity record = recordEG.getClosestRecordFromYstrdy();
        assertThat("24Hours4mins").isEqualTo(record.regionName);
    }

    @Test
    public void testQuery_recordFromDateClosestToADayAgo_23hours56Mins() {
        populateDbWithBunchOfYstrDates();

        insertLocationRecord(1.1f, 1.03f, dateFrom24Hours5Mins(), 32.3f, "24Hours5mins");
        insertLocationRecord(1.1f, 1.03f, dateFrom24Hours4Mins(), 32.3f, "24Hours4mins");
        insertLocationRecord(1.1f, 1.03f, dateFrom23Hours56Mins(), 32.3f, "23Hours56mins");
        insertLocationRecord(1.1f, 1.03f, dateFrom23Hours55Mins(), 32.3f, "23Hours55mins");

        RecordEntity record = recordEG.getClosestRecordFromYstrdy();
        assertThat("23Hours56mins").isEqualTo(record.regionName);
    }

    @Test
    public void testQuery_recordFromDateClosestToADayAgoBelow() {
        Date d = new Date();
        long twentyFourHours5Mins = (24 * 60 * 60 + 1) * 1000;
        d.setTime(d.getTime() - twentyFourHours5Mins);
        populateDbWithBunchOfYstrDates();

        insertLocationRecord((float)0, (float)0, d, 32.3f, "nearest");

        RecordEntity record = recordEG.getClosestRecordFromYstrdy();
        assertThat(0.0).isEqualTo(record.location.getLatitude());
        assertThat(0.0).isEqualTo(record.location.getLongitude());
        assertThat("nearest").isEqualTo(record.regionName);
    }

    @Test
    public void testQuery_firstRecord_new() {
        RecordEntity record = recordEG.getEarliestRecord();
        assertNull(record);
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

        RecordEntity record = recordEG.getEarliestRecord();

        assertThat(record.date).isEqualTo(thirtyOneDaysAgoDate);
        assertThat(record.regionName).isEqualTo(thirtyOneDaysAgoDate.toString());
    }

    @Test
    public void testDelete_oldRecords() {
        populateDbWithBunchOfYstrDates();

        Date thirtyDaysAgoDate = new Date();
        long thirtyDaysAgo = (long) 30 * ((24 * 60 * 60 + 1) * 1000);
        thirtyDaysAgoDate.setTime(thirtyDaysAgoDate.getTime() - thirtyDaysAgo);
        insertLocationRecord((float)0, (float)0, thirtyDaysAgoDate, 32.3f, thirtyDaysAgoDate.toString());

        int numrecords = recordEG.numRecords();

        assertThat(numrecords).isEqualTo(24);
        recordEG.deleteExpiredRecords();

        int newnumrecords = recordEG.numRecords();
        assertThat(newnumrecords).isEqualTo(23);
    }

    public void insertLocationRecord(float latitude, float longitude, Date date, float temp, String region) {
        RecordEntity record = new RecordEntity();
        record.location.setLatitude(latitude);
        record.location.setLongitude(longitude);
        record.date = date;
        record.temperature = temp;
        record.regionName = region;

        RecordGateway r = new RecordGateway();
        r.setEntity(record);
        try {
            testEGI.insert(r);
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