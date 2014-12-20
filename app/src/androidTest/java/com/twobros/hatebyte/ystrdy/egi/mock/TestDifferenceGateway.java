package com.twobros.hatebyte.ystrdy.egi.mock;

import android.content.ContentValues;
import android.database.Cursor;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.implementation.SQLDatabaseEGI;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;

/**
 * Created by scott on 12/16/14.
 */
public class TestDifferenceGateway extends DifferenceGateway {

    public Boolean getTableNameWasCalled;
    public Boolean getProjectionWasCalled;
    public Boolean getOrderByWasCalled;
    public Boolean getLimitWasCalled;
    public Boolean getSelectStringWasCalled;
    public Boolean getContentValues;
    public Boolean isValidWasCalled;
    public Boolean mapFromCursorWasCalled;

    public Boolean hasDatabase() {
        return (sqlDatabaseEGI != null);
    }

    public void setEntityGatewayImplementation(SQLDatabaseEGI e) {
        sqlDatabaseEGI = e;
    }
    public SQLDatabaseEGI getEntityGatewayImplementation() {
        return sqlDatabaseEGI;
    }

    @Override
    public String tableName() {
        getTableNameWasCalled = true;
        return super.tableName();
    }
    @Override
    public String[] projection() {
        getProjectionWasCalled = true;
        return super.projection();
    }
    @Override
    public String orderBy() {
        getOrderByWasCalled = true;
        return super.orderBy();
    }
    @Override
    public String limit() {
        getLimitWasCalled = true;
        return super.limit();
    }
    @Override
    public String selectString() {
        getSelectStringWasCalled = true;
        return super.selectString();
    }
    @Override
    public ContentValues contentValues() {
        getContentValues = true;
        return super.contentValues();
    }
    @Override
    public Boolean isValid() {
        isValidWasCalled = true;
        return super.isValid();
    }
    @Override
    public void mapFromCursor(Cursor c) {
        mapFromCursorWasCalled = true;
        super.mapFromCursor(c);
    }

}
