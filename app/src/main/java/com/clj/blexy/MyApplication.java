package com.clj.blexy;


import android.util.Log;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            LitePal.initialize(this);
        } catch (Exception e) {
            Log.e("ble", "onCreate:  " + e.toString());

        }
    }
}
