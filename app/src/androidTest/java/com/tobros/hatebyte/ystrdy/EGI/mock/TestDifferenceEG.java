package com.tobros.hatebyte.ystrdy.EGI.mock;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.DifferenceEG;

/**
 * Created by scott on 12/16/14.
 */
public class TestDifferenceEG extends DifferenceEG {

    public Boolean getTableNameWasCalled;
    public Boolean getProjectionWasCalled;
    public Boolean getOrderByWasCalled;
    public Boolean getLimitWasCalled;
    public Boolean getSelectStringWasCalled;
    public Boolean getContentValues;
    public Boolean isValidWasCalled;
    public Boolean mapFromCursorWasCalled;

    public Boolean hasDatabase() {
        return (entityGatewayImplementation != null);
    }

    @Override
    public String tableName() {
        getTableNameWasCalled = true;
        return tableName;
    }
    @Override
    public String[] projection() {
        getProjectionWasCalled = true;
        return projection;
    }
    @Override
    public String orderBy() {
        getOrderByWasCalled = true;
        return orderBy;
    }
    @Override
    public String limit() {
        getLimitWasCalled = true;
        return limit;
    }
    @Override
    public String selectString() {
        getSelectStringWasCalled = true;
        return selectString;
    }
    @Override
    public ContentValues contentValues() {
        getContentValues = true;
        return contentValues;
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
