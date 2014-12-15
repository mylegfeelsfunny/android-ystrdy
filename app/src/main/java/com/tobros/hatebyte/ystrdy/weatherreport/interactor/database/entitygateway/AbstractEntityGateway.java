package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entity.RecordEntity;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/14/14.
 */
public abstract class AbstractEntityGateway {

    abstract public void mapFromCursor(Cursor c);
    abstract public ContentValues contentValues();
    abstract public void setEntity(Object e) throws InvalidPropertiesFormatException;
    abstract public Object getEntity();

    public static String[] projection;

}
