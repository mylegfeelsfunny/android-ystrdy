package com.twobros.hatebyte.ystrdy.sqldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.dataapi.YstrdyDatabaseAPI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by scott on 12/14/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)

public class YstrdyDatabaseAPITest {

    YstrdyDatabaseAPI ystrdyDatabaseAPI;

    @Before
    public void setup() {
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        ystrdyDatabaseAPI = new YstrdyDatabaseAPI(context, "testLocationRecord.db");
        ystrdyDatabaseAPI.open();
    }

    @After
    public void teardown() {
        ystrdyDatabaseAPI.close();
        ystrdyDatabaseAPI.clear();
        ystrdyDatabaseAPI = null;
    }

    @Test
    public void testOpen_createsDB() {
        ystrdyDatabaseAPI.open();
        assertTrue(ystrdyDatabaseAPI.database.isOpen());
    }

    @Test
    public void testClose_closesDB() {
        ystrdyDatabaseAPI.open();
        ystrdyDatabaseAPI.close();
        assertFalse(ystrdyDatabaseAPI.database.isOpen());
    }

    @Test
    public void test_insertRecordModel() {
        long rowid = insertRecord();
        assertThat(rowid).isGreaterThan(0);
    }

    @Test
    public void test_insertDifferenceModel() {
        long rowid = insertDifference();
        assertThat(rowid).isGreaterThan(0);
    }

    @Test
    public void test_getRecordModelFromEmptyDB() {
        Cursor c = ystrdyDatabaseAPI.get("record", recordProjection, null, "date ASC", "1");
        assertThat(c.getCount()).isEqualTo(0);
    }

    @Test
    public void test_getRecordModel() {
        long rowid = insertRecord();

        String select = "_id = " + rowid;
        Cursor c = ystrdyDatabaseAPI.get("record", recordProjection, select, null, null);
        c.moveToFirst();
        float longitude = c.getFloat(c.getColumnIndex("longitude"));
        String woeid = c.getString(c.getColumnIndex("woeid"));

        assertThat(longitude).isEqualTo(0.2f);
        assertThat(woeid).isEqualTo("woeid");
    }

    @Test
    public void test_getDifferenceModel() {
        long rowid = insertDifference();

        String select = "_id = " + rowid;
        Cursor c = ystrdyDatabaseAPI.get("difference", differenceProjection, select, null, null);
        c.moveToFirst();
        float diff = c.getFloat(c.getColumnIndex("difference"));

        assertThat(diff).isEqualTo(4.f);
    }

    @Test
    public void test_deleteRecordModel() {
        long rowid = insertRecord();

        String select =  "_id = " + rowid;
        Cursor c = ystrdyDatabaseAPI.get("record", recordProjection, select, null, null);
        c.moveToFirst();
        float longitude = c.getFloat(c.getColumnIndex("longitude"));
        assertThat(longitude).isEqualTo(0.2f);

        String dstring = "_id = " + rowid;
        ystrdyDatabaseAPI.delete("record", dstring);

        c = ystrdyDatabaseAPI.get("record", recordProjection, null, null, null);
        assertThat(c.getCount()).isEqualTo(0);
    }

    @Test
    public void test_deleteDifferenceModel() {
        long rowid = insertDifference();

        String select =  "_id = " + rowid;
        Cursor c = ystrdyDatabaseAPI.get("difference", differenceProjection, select, null, null);
        c.moveToFirst();
        float diff = c.getFloat(c.getColumnIndex("difference"));
        assertThat(diff).isEqualTo(4.f);

        String dstring = "_id = " + rowid;
        ystrdyDatabaseAPI.delete("difference", dstring);

        c = ystrdyDatabaseAPI.get("difference", differenceProjection, null, null, null);
        assertThat(c.getCount()).isEqualTo(0);
    }

    public ContentValues recordValues() {
        ContentValues values = new ContentValues();
        values.put("latitude", 0.1f);
        values.put("longitude", 0.2f);
        values.put("temperature", 0.3f);
        values.put("region_name", "region");
        values.put("city_name", "scottville");
        values.put("woeid", "woeid");
        values.put("date", new Date().getTime());
        return values;
    }

    public ContentValues differenceValues() {
        ContentValues values = new ContentValues();
        values.put("difference", 4.f);
        values.put("date", new Date().getTime());
        return values;
    }

    public static String[] recordProjection = new String[]{
            "latitude",
            "longitude",
            "temperature",
            "region_name",
            "city_name",
            "woeid",
            "date",
            "_id"
    };

    public static String[] differenceProjection = new String[]{
            "difference",
            "date",
            "today_record_id",
            "ystrdy_record_id",
            "_id"
    };

    public long insertRecord() {
        return ystrdyDatabaseAPI.insert("record", recordValues());
    }
    public long insertDifference() {
        return ystrdyDatabaseAPI.insert("difference", differenceValues());
    }


}
