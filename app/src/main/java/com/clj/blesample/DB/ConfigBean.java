package com.clj.blesample.DB;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class ConfigBean extends DataSupport {


    @Column(unique = true,nullable = false)
    private int mId;
    @Column(nullable = false)
    private String mName;
    @Column(nullable = false)
    private String mAge;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAge() {
        return mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }


}
