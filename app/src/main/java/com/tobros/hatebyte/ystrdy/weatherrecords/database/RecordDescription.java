package com.tobros.hatebyte.ystrdy.weatherrecords.database;

import android.provider.BaseColumns;

/**
 * Created by scott on 11/26/14.
 */
public class RecordDescription {

    public static abstract class NowRecord implements BaseColumns {

        public static final String TABLE_NAME                           = "nowrecords";
        public static final String COLUMN_LATITUDE                      = "latitude";
        public static final String COLUMN_LONGITUDE                     = "longitude";
        public static final String COLUMN_DATE                          = "date";
        public static final String COLUMN_TEMPERATURE                   = "temperature";
        public static final String COLUMN_IS_FIRST                      = "is_first";
        public static final String COLUMN_REGION_NAME                   = "region_name";
        public static final String COLUMN_CITY_NAME                     = "city_name";
        public static final String COLUMN_WOEID                         = "woeid";

    }

    public static abstract class YstrdayRecord implements BaseColumns {

        public static final String TABLE_NAME                           = "ystrdyrecords";
        public static final String COLUMN_NOW_RECORD_ID                 = "nowRecordId";
        public static final String COLUMN_DIFFERENCE                    = "difference";
        public static final String COLUMN_DATE                          = "date";

    }

}


