package com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.dataapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;

/**
 * Created by scott on 12/12/14.
 */
public class YstrdyDatabaseAPI extends SQLiteOpenHelper implements IDatabaseAPI {

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " integer";
    private static final String TYPE_DOUBLE = " double";
    private static final String TYPE_LONG = " long";
    private static final String TYPE_FLOAT = " float";
    private static final String TYPE_BOOL = " boolean";
    private static final String COMMA_SEP = ", ";
    public static final String DATABASE_NAME = "TemperatureRecords.db";
    public static final int DATA_VERSION = 1;

    private static final String SQL_CREATE_RECORDS =
            "CREATE TABLE " +
                    RecordGateway.TABLE_NAME + " (" +
                    RecordGateway.COLUMN_ID + TYPE_INTEGER + " primary key autoincrement" + COMMA_SEP +
                    RecordGateway.COLUMN_LATITUDE + TYPE_FLOAT + COMMA_SEP +
                    RecordGateway.COLUMN_LONGITUDE + TYPE_FLOAT + COMMA_SEP +
                    RecordGateway.COLUMN_DATE + TYPE_LONG + COMMA_SEP +
                    RecordGateway.COLUMN_REGION_NAME + TYPE_TEXT + COMMA_SEP +
                    RecordGateway.COLUMN_CITY_NAME + TYPE_TEXT + COMMA_SEP +
                    RecordGateway.COLUMN_WOEID + TYPE_TEXT + COMMA_SEP +
                    RecordGateway.COLUMN_TEMPERATURE + TYPE_FLOAT + ")";
    private static final String SQL_DELETE_RECORDS =
            "DROP TABLE IF EXISTS " + RecordGateway.TABLE_NAME;

    private static final String SQL_CREATE_DIFFERENCE_RECORDS =
            "CREATE TABLE " +
                    DifferenceGateway.TABLE_NAME + " (" +
                    DifferenceGateway.COLUMN_ID + TYPE_INTEGER + " primary key autoincrement" + COMMA_SEP +
                    DifferenceGateway.COLUMN_DIFFERENCE + TYPE_FLOAT + COMMA_SEP +
                    DifferenceGateway.COLUMN_DATE + TYPE_LONG + COMMA_SEP +
                    DifferenceGateway.COLUMN_YSTRDY_RECORD_ID + TYPE_INTEGER + COMMA_SEP +
                    DifferenceGateway.COLUMN_TODAY_RECORD_ID + TYPE_INTEGER + ")";
    private static final String SQL_DELETE_DIFFERENCE_RECORDS =
            "DROP TABLE IF EXISTS " + DifferenceGateway.TABLE_NAME;

    public SQLiteDatabase database;

    public YstrdyDatabaseAPI(Context context, String databaseName) {
        super(context, databaseName, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RECORDS);
        db.execSQL(SQL_CREATE_DIFFERENCE_RECORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_RECORDS);
        db.execSQL(SQL_DELETE_DIFFERENCE_RECORDS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void open() {
        database = getWritableDatabase();
    }

    public long insert(String tableName, ContentValues contentValues) {
        long newRowId = database.insert(
                tableName,
                null,
                contentValues
        );
        return newRowId;
    }

    public Cursor get(String tableName, String[] projection, String select, String orderBy, String limit) {
        Cursor c = database.query(
                tableName,
                projection,
                select,
                null,
                null,
                null,
                orderBy,
                limit
        );
        return c;
    }

    public void delete(String tableName, String where) {
        database.delete(tableName, where, null);
    }

    public void clear() {
        open();
        onDowngrade(database, YstrdyDatabaseAPI.DATA_VERSION, YstrdyDatabaseAPI.DATA_VERSION + 1);
        close();
    }

}
