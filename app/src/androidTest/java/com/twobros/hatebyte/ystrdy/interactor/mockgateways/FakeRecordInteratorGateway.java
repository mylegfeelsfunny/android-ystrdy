package com.twobros.hatebyte.ystrdy.interactor.mockgateways;

import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.RecordGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.implementation.SQLDatabaseEGI;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by scott on 12/19/14.
 */
public class FakeRecordInteratorGateway extends RecordGateway {

    public Boolean shouldSendException = false;
    public Boolean shouldReturnTooYoungRecord = false;
    public Boolean shouldReturnTooOldRecord = false;

    //    public void setEntityGatewayImplementation(SQLDatabaseEGI e) {
//        sqlDatabaseEGI = e;
//    }
//    public SQLDatabaseEGI getEntityGatewayImplementation() {
//        return sqlDatabaseEGI;
//    }
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

//    public RecordEntity getClosestRecordFromYstrdy() {
//        RecordEntity re = new RecordEntity();
//        if (shouldReturnTooOldRecord) {
//            Date d = new Date();
//            d.setTime(d.getTime() - (YstrDate.thirtyHours() + 1));
//            re.date = d;
//            return re;
//        }
//        if (shouldReturnTooYoungRecord) {
//            Date d = new Date();
//            d.setTime(d.getTime() - (YstrDate.eighteenHours() - 1));
//            re.date = d;
//            return re;
//        }
//        return super.getClosestRecordFromYstrdy();
//    }

}
