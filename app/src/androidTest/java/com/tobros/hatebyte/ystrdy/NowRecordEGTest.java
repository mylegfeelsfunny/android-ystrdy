package com.tobros.hatebyte.ystrdy;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabase;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherrecords.database.RecordEGI;
import com.tobros.hatebyte.ystrdy.weatherrecords.entity.NowRecordEntity;
import com.tobros.hatebyte.ystrdy.weatherrecords.gateway.NowRecordEG;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.mockito.Mockito.*;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class NowRecordEGTest {

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
    public void test_true() {
        assertThat(true);
    }

    @Test
    public void test_YstrRecordInitsWithCursor() {
        Date rDate = new Date();
        long recordId = 0;
        NowRecordEntity record = new NowRecordEntity();
        record.latitude = 1.1f;
        record.longitude = 1.03f;
        record.date = rDate;
        record.temperature = 32.3f;
        record.regionName = "bridgewater";

        try {
            recordId = recordEGI.insertNowRecord(record);
        } catch (Throwable expected) {

        }

        NowRecordEntity yr = recordEGI.getNowRecordById(recordId);
        assertThat(1.1f).isEqualTo(yr.latitude);
        assertThat(1.03f).isEqualTo(yr.longitude);
        assertThat(rDate.getTime()).isEqualTo(yr.date.getTime());
        assertThat(32.3f).isEqualTo(yr.temperature);
        assertThat("bridgewater").isEqualTo(yr.regionName);
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
