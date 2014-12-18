package com.tobros.hatebyte.ystrdy.egi;

import com.tobros.hatebyte.ystrdy.egi.mock.TestEGI;
import com.tobros.hatebyte.ystrdy.egi.mock.TestRecordEG;
import com.tobros.hatebyte.ystrdy.egi.mock.FakeYstrdyDBAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.EntityGatewayImplementation;
import com.tobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;

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
        RecordEG recordEG = new RecordEG();

        long rowid;
        try {
            rowid = egi.insert(recordEG);
            fail("Should fail with InvalidPropertiesFormatException");
        } catch (Throwable expected) {
            assertEquals(InvalidPropertiesFormatException.class, expected.getClass());
        }
    }

    @Test
    public void test_insertCallsInterfaceMethod() {
        TestRecordEG testRecordEG = insertFakeRecord();

        assertThat(testRecordEG.isValid()).isTrue();
        assertThat(testRecordEG.getTableNameWasCalled).isTrue();
        assertThat(testRecordEG.getContentValues).isTrue();
    }

    @Test
    public void test_getCallsInterfaceMethod() {
        TestRecordEG testRecordEG = insertFakeRecord();

        TestRecordEG retrieveRecord = new TestRecordEG();
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
        TestRecordEG testRecordEG = insertFakeRecord();

        egi.delete(testRecordEG);
        assertThat(testRecordEG.getTableNameWasCalled).isTrue();
        assertThat(testRecordEG.getSelectStringWasCalled).isTrue();
    }

    public TestRecordEG insertFakeRecord() {
        RecordEntity record = new RecordEntity();
        record.date = new Date();

        TestRecordEG testRecordEG = new TestRecordEG();
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
