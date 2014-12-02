package com.tobros.hatebyte.ystrdy.database;

import android.provider.BaseColumns;

/**
 * Created by scott on 11/26/14.
 */
public class LocationRecordContract {

    public static abstract class LocationRecord implements BaseColumns {

        public static final String TABLE_NAME                           = "locationrecords";
        public static final String COLUMN_LATITUDE                      = "latitude";
        public static final String COLUMN_LONGITUDE                     = "longitude";
        public static final String COLUMN_DATE                          = "date";
        public static final String COLUMN_TEMPERATURE                   = "temperature";
        public static final String COLUMN_IS_FIRST                      = "is_first";
        public static final String COLUMN_REGION_NAME                   = "region_name";

    }

}


