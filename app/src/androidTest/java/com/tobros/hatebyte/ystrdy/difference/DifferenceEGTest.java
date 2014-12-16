package com.tobros.hatebyte.ystrdy.difference;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import com.tobros.hatebyte.ystrdy.EGI.mock.TestDifferenceEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.DifferenceEntity;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.DifferenceEG;

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
 * Created by scott on 12/11/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DifferenceEGTest {

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
        TestDifferenceEG differenceEG = new TestDifferenceEG();
        assertThat(differenceEG.hasDatabase()).isTrue();
        assertThat(differenceEG.tableName()).isEqualTo("difference");
        assertThat(differenceEG.projection()).isEqualTo(DifferenceEG.projectionMap);
    }

    @Test
    public void test_mapFromCursorWithEmptyCursor() {
        DifferenceEG differenceEG = new DifferenceEG();
        differenceEG.mapFromCursor(emptyCursor);
        assertThat(differenceEG.getEntity()).isNull();
    }

    @Test
    public void test_mapFromCursorWithPopulatedCursor() {
        // add a record
        insertRecord();

        DifferenceEG differenceEG = new DifferenceEG();
        Cursor c = ystrdyDatabaseAPI.get("difference", differenceEG.projection(), null, null, "1");
        c.moveToFirst();
        differenceEG.mapFromCursor(c);
        assertThat(differenceEG.getEntity()).isNotNull();
    }

    @Test
    public void test_isNotValidWhenEntityIsNull() {
        DifferenceEG differenceEG = new DifferenceEG();
        assertThat(differenceEG.isValid()).isFalse();
    }

    @Test
    public void test_isNotValidWhenEntityDateNull() {
        DifferenceEG differenceEG = new DifferenceEG();
        DifferenceEntity differenceEntity = new DifferenceEntity();
        differenceEG.setEntity(differenceEntity);
        assertThat(differenceEG.isValid()).isFalse();
    }

    @Test
    public void test_isValid() {
        DifferenceEG differenceEG = new DifferenceEG();
        DifferenceEntity differenceEntity = new DifferenceEntity();
        differenceEntity.date = new Date();
        differenceEG.setEntity(differenceEntity);
        assertThat(differenceEG.isValid()).isTrue();
    }


    public void insertRecord() {
        ystrdyDatabaseAPI.insert("difference", differenceValues());
    }

    public ContentValues differenceValues() {
        ContentValues values = new ContentValues();
        values.put("difference", 4.f);
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
