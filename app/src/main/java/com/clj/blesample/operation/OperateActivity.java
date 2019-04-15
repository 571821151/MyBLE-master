/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample.operation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.clj.blesample.R;
import com.clj.blesample.adapter.MatchedAdapter;

public class OperateActivity extends AppCompatActivity implements View.OnClickListener {
    private MatchedAdapter matchedAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_page);
        matchedAdapter = new MatchedAdapter(this);
        ListView listView_device = findViewById(R.id.mainList);

        matchedAdapter.addItem("2123123");
        matchedAdapter.addItem("2123123s");
        matchedAdapter.addItem("2123123dd");
        listView_device.setAdapter(matchedAdapter);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
