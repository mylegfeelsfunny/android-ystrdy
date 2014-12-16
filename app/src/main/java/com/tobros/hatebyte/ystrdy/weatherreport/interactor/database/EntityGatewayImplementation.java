package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.YstrdyApp;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.IDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.AbstractEntityGateway;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.DifferenceEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.entitygateway.RecordEG;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.date.YstrDate;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/15/14.
 */
public class EntityGatewayImplementation {

    public interface IEntityGateway {
        public String tableName();
        public String[] projection();
        public String orderBy();
        public String limit();
        public String selectString();
        public ContentValues contentValues();
        public Boolean isValid();
        public void mapFromCursor(Cursor c);

        public void setEntity(Object e);
        public Object getEntity();
    }

    public IDatabaseAPI databaseAPI;

    public IDatabaseAPI getDatabaseAPI() {
        if (databaseAPI == null) {
            databaseAPI = new YstrdyDatabaseAPI(YstrdyApp.getContext(), YstrdyDatabaseAPI.DATABASE_NAME);
        }
        return databaseAPI;
    }

    public long insert(IEntityGateway entityGateway) throws InvalidPropertiesFormatException {
        if (entityGateway.isValid() == false) {
            throw new InvalidPropertiesFormatException("Invalid Entity");
        }
        getDatabaseAPI().open();
        long id = databaseAPI.insert(entityGateway.tableName(), entityGateway.contentValues());
        databaseAPI.close();
        return id;
    }

    public int count(IEntityGateway entityGateway) {
        getDatabaseAPI().open();
        Cursor c = databaseAPI.get(
                entityGateway.tableName(),
                entityGateway.projection(),
                null,
                null,
                null);
        int num = c.getCount();
        databaseAPI.close();
        return num;
    }

    public IEntityGateway get(IEntityGateway entityGateway) {
        getDatabaseAPI().open();
        Cursor c = databaseAPI.get(
                entityGateway.tableName(),
                entityGateway.projection(),
                entityGateway.selectString(),
                entityGateway.orderBy(),
                entityGateway.limit()
        );
        if (c.getCount() > 0) {
            c.moveToFirst();
            entityGateway.mapFromCursor(c);
        }
        databaseAPI.close();
        return entityGateway;
    }

    public void delete(IEntityGateway entityGateway) {
        getDatabaseAPI().open();
        databaseAPI.delete(entityGateway.tableName(), entityGateway.selectString());
        databaseAPI.close();
    }



}
