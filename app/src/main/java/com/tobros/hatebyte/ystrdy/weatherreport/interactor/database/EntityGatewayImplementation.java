package com.tobros.hatebyte.ystrdy.weatherreport.interactor.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.tobros.hatebyte.ystrdy.YstrdyApp;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.IDatabaseAPI;
import com.tobros.hatebyte.ystrdy.weatherreport.interactor.database.database.YstrdyDatabaseAPI;

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

    protected IDatabaseAPI databaseAPI;

//    protected IDatabaseAPI getDatabaseAPI() {
//        return databaseAPI;
//    }

    private static EntityGatewayImplementation instance = null;
    public EntityGatewayImplementation() {
        databaseAPI = new YstrdyDatabaseAPI(YstrdyApp.getContext(), YstrdyDatabaseAPI.DATABASE_NAME);
    }
    public static EntityGatewayImplementation getInstance() {
        if(instance == null) {
            instance = new EntityGatewayImplementation();
        }
        return instance;
    }

    public long insert(IEntityGateway entityGateway) throws InvalidPropertiesFormatException {
        if (entityGateway.isValid() == false) {
            throw new InvalidPropertiesFormatException("Invalid Entity");
        }
        databaseAPI.open();
        long id = databaseAPI.insert(entityGateway.tableName(), entityGateway.contentValues());
        databaseAPI.close();
        return id;
    }

    public int count(IEntityGateway entityGateway) {
        databaseAPI.open();
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
        databaseAPI.open();
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
        databaseAPI.open();
        databaseAPI.delete(entityGateway.tableName(), entityGateway.selectString());
        databaseAPI.close();
    }



}
