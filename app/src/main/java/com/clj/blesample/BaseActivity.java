package com.clj.blesample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.clj.blesample.comm.LocalManageUtil;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        LocalManageUtil.saveSystemCurrentLanguage(newBase);
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }
}
