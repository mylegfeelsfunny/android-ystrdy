package com.twobros.hatebyte.ystrdy;

import android.content.Context;

/**
 * Created by scott on 12/13/14.
 */
public class YstrdyApp extends android.app.Application {

    private static YstrdyApp instance;

    public YstrdyApp() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

}
