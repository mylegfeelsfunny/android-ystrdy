package com.twobros.hatebyte.ystrdy.record;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import com.twobros.hatebyte.ystrdy.egi.mock.TestRecordGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.dataapi.YstrdyDatabaseAPI;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.RecordEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class RecordGatewayTest {

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
    public void test_defaultProperties() {
        TestRecordGateway recordEG = new TestRecordGateway();
        assertThat(recordEG.hasDatabase()).isTrue();
        assertThat(recordEG.tableName()).isEqualTo("record");
        assertThat(recordEG.projection()).isEqualTo(RecordGateway.projectionMap);
    }

    @Test
    public void test_mapFromCursorWithEmptyCursor() {
        RecordGateway recordGateway = new RecordGateway();
        recordGateway.mapFromCursor(emptyCursor);
        assertThat(recordGateway.getEntity()).isNull();
    }

    @Test
    public void test_mapFromCursorWithPopulatedCursor() {
        insertRecord();
        RecordGateway recordGateway = new RecordGateway();
        Cursor c = ystrdyDatabaseAPI.get("record", recordGateway.projection(), null, null, "1");
        c.moveToFirst();
        recordGateway.mapFromCursor(c);
        assertThat(recordGateway.getEntity()).isNotNull();
    }

    @Test
    public void test_isNotValidWhenEntityIsNull() {
        RecordGateway recordGateway = new RecordGateway();
        assertThat(recordGateway.isValid()).isFalse();
    }

    @Test
    public void test_isNotValidWhenEntityDateNull() {
        RecordGateway recordGateway = new RecordGateway();
        RecordEntity recordEntity = new RecordEntity();
        recordGateway.setEntity(recordEntity);
        assertThat(recordGateway.isValid()).isFalse();
    }

    @Test
    public void test_isValid() {
        RecordGateway recordGateway = new RecordGateway();
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.date = new Date();
        recordGateway.setEntity(recordEntity);
        assertThat(recordGateway.isValid()).isTrue();
    }

    public void insertRecord() {
        ystrdyDatabaseAPI.insert("record", recordValues());
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

    private Cursor emptyCursor = new Cursor() {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public int getPosition() {
            return 0;
        }

        @Override
        public boolean move(int i) {
            return false;
        }

        @Override
        public boolean moveToPosition(int i) {
            return false;
        }

        @Override
        public boolean moveToFirst() {
            return false;
        }

        @Override
        public boolean moveToLast() {
            return false;
        }

        @Override
        public boolean moveToNext() {
            return false;
        }

        @Override
        public boolean moveToPrevious() {
            return false;
        }

        @Override
        public boolean isFirst() {
            return false;
        }

        @Override
        public boolean isLast() {
            return false;
        }

        @Override
        public boolean isBeforeFirst() {
            return false;
        }

        @Override
        public boolean isAfterLast() {
            return false;
        }

        @Override
        public int getColumnIndex(String s) {
            return 0;
        }

        @Override
        public int getColumnIndexOrThrow(String s) throws IllegalArgumentException {
            return 0;
        }

        @Override
        public String getColumnName(int i) {
            return null;
        }

        @Override
        public String[] getColumnNames() {
            return new String[0];
        }

        @Override
        public int getColumnCount() {
            return 0;
        }

        @Override
        public byte[] getBlob(int i) {
            return new byte[0];
        }

        @Override
        public String getString(int i) {
            return null;
        }

        @Override
        public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {

        }

        @Override
        public short getShort(int i) {
            return 0;
        }

        @Override
        public int getInt(int i) {
            return 0;
        }

        @Override
        public long getLong(int i) {
            return 0;
        }

        @Override
        public float getFloat(int i) {
            return 0;
        }

        @Override
        public double getDouble(int i) {
            return 0;
        }

        @Override
        public int getType(int i) {
            return 0;
        }

        @Override
        public boolean isNull(int i) {
            return false;
        }

        @Override
        public void deactivate() {

        }

        @Override
        public boolean requery() {
            return false;
        }

        @Override
        public void close() {

        }

        @Override
        public boolean isClosed() {
            return false;
        }

        @Override
        public void registerContentObserver(ContentObserver contentObserver) {

        }

        @Override
        public void unregisterContentObserver(ContentObserver contentObserver) {

        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void setNotificationUri(ContentResolver contentResolver, Uri uri) {

        }

        @Override
        public boolean getWantsAllOnMoveCalls() {
            return false;
        }

        @Override
        public Bundle getExtras() {
            return null;
        }

        @Override
        public Bundle respond(Bundle bundle) {
            return null;
        }

    };

}
