/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample.operation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.clj.blesample.ControlActivity;
import com.clj.blesample.R;
import com.clj.blesample.adapter.MatchedAdapter;
import com.clj.blesample.comm.Utils;

public class OperateActivity extends AppCompatActivity implements View.OnClickListener {
    private MatchedAdapter matchedAdapter;
    private Button btn_link;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_page);
        matchedAdapter = new MatchedAdapter(this);
        ListView listView_device = findViewById(R.id.mainList);
        btn_link = findViewById(R.id.btn_connect_xy);
        btn_link.setOnClickListener(this);
        matchedAdapter.addItem("2123123");
        matchedAdapter.addItem("2123123s");
        matchedAdapter.addItem("2123123dd");
        listView_device.setAdapter(matchedAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_connect_xy:
                Utils.Toast(this, getApplicationContext().toString());
                Intent intent = new Intent(this, ControlActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
