package com.twobros.hatebyte.ystrdy.egi;

import com.twobros.hatebyte.ystrdy.egi.mock.FakeYstrdyDBAPI;
import com.twobros.hatebyte.ystrdy.egi.mock.TestEGI;
import com.twobros.hatebyte.ystrdy.egi.mock.TestRecordGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
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
 * Created by scott on 12/16/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class EGITest {

    TestEGI egi;

    @Before
    public void setup() {
        egi = new TestEGI();
        egi.setDataBaseAPI(new FakeYstrdyDBAPI());
    }

    @After
    public void teardown() {
        egi.close();
        egi = null;
    }

    @Test
    public void test_insertRecordThrowsExceptionWhenInvalid() {
        RecordGateway recordGateway = new RecordGateway();

        long rowid;
        try {
            rowid = egi.insert(recordGateway);
            fail("Should fail with InvalidPropertiesFormatException");
        } catch (Throwable expected) {
            assertEquals(InvalidPropertiesFormatException.class, expected.getClass());
        }
    }

    @Test
    public void test_insertCallsInterfaceMethod() {
        TestRecordGateway testRecordEG = insertFakeRecord();

        assertThat(testRecordEG.isValid()).isTrue();
        assertThat(testRecordEG.getTableNameWasCalled).isTrue();
        assertThat(testRecordEG.getContentValues).isTrue();
    }

    @Test
    public void test_getCallsInterfaceMethod() {
        TestRecordGateway testRecordEG = insertFakeRecord();

        TestRecordGateway retrieveRecord = new TestRecordGateway();
        egi.get(retrieveRecord);

        assertThat(retrieveRecord.getTableNameWasCalled).isTrue();
        assertThat(retrieveRecord.getProjectionWasCalled).isTrue();
        assertThat(retrieveRecord.getSelectStringWasCalled).isTrue();
        assertThat(retrieveRecord.getOrderByWasCalled).isTrue();
        assertThat(retrieveRecord.getLimitWasCalled).isTrue();
//        assertThat(retrieveRecord.mapFromCursorWasCalled).isTrue();
    }

    @Test
    public void test_deleteCallsInterfaceMethod() {
        TestRecordGateway testRecordEG = insertFakeRecord();

        egi.delete(testRecordEG);
        assertThat(testRecordEG.getTableNameWasCalled).isTrue();
        assertThat(testRecordEG.getSelectStringWasCalled).isTrue();
    }

    public TestRecordGateway insertFakeRecord() {
        RecordEntity record = new RecordEntity();
        record.date = new Date();

        TestRecordGateway testRecordEG = new TestRecordGateway();
        testRecordEG.setEntity(record);

        long rowid = 0;
        try {
            rowid = egi.insert(testRecordEG);
        } catch (Throwable expected) {

        }
        record.id = (int) (long) rowid;
        return testRecordEG;
    }

//    @Test
//    public void test_getRecord() {
//        long rowid = insertRecord();

//        String select = "_id = " + rowid;
//        Cursor c = ystrdyDatabaseAPI.get("record", recordProjection, select, null, null);
//        c.moveToFirst();
//        float longitude = c.getFloat(c.getColumnIndex("longitude"));
//        String woeid = c.getString(c.getColumnIndex("woeid"));
//
//        assertThat(longitude).isEqualTo(0.2f);
//        assertThat(woeid).isEqualTo("woeid");
//    }

//    @Test
//    public void test_deleteRecord() {
//        long rowid = insertRecord();
//
//        String select =  "_id = " + rowid;
//        Cursor c = ystrdyDatabaseAPI.get("record", recordProjection, select, null, null);
//        c.moveToFirst();
//        float longitude = c.getFloat(c.getColumnIndex("longitude"));
//        assertThat(longitude).isEqualTo(0.2f);
//
//        String dstring = "_id = " + rowid;
//        ystrdyDatabaseAPI.delete("record", dstring);
//
//        c = ystrdyDatabaseAPI.get("record", recordProjection, null, null, null);
//        assertThat(c.getCount()).isEqualTo(0);
//    }


}
