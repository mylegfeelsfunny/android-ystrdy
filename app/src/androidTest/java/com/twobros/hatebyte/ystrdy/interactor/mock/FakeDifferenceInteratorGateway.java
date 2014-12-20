package com.twobros.hatebyte.ystrdy.interactor.mock;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.implementation.SQLDatabaseEGI;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/19/14.
 */
public class FakeDifferenceInteratorGateway extends DifferenceGateway {

    public Boolean shouldSendException = false;

    public void setEntityGatewayImplementation(SQLDatabaseEGI e) {
        sqlDatabaseEGI = e;
    }
    public SQLDatabaseEGI getEntityGatewayImplementation() {
        return sqlDatabaseEGI;
    }

    public long save() throws InvalidPropertiesFormatException {
        if (shouldSendException) {
            throw new InvalidPropertiesFormatException("Invalid Entity");
        }
        return super.save();
    }

}
