package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.EntityGatewayImplementation;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/14/14.
 */
public abstract class AbstractEntityGateway implements EntityGatewayImplementation.IEntityGateway {

    protected EntityGatewayImplementation entityGatewayImplementation;

    protected String tableName;
    protected String[] projection;
    protected String orderBy;
    protected String limit;
    protected String selectString;
    protected ContentValues contentValues;

    abstract public void setEntity(Object e);
    abstract public Object getEntity();
    abstract public void mapFromCursor(Cursor c);
    abstract public Boolean isValid();

    public String tableName() {
        return tableName;
    }
    public String[] projection() {
        return projection;
    }
    public String orderBy() {
        return orderBy;
    }
    public String limit() {
        return limit;
    }
    public String selectString() {
        return selectString;
    }
    public ContentValues contentValues() {
        return contentValues;
    }

    protected void reset() {
        orderBy = null;
        selectString = null;
        limit = null;
        projection = null;
    }

}