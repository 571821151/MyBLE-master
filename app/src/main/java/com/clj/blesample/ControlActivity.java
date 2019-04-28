/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.clj.blesample.Fragment.ControlFragment;
import com.clj.blesample.Fragment.SetPositionFragment;
import com.clj.blesample.Fragment.SettingFragment;
import com.clj.fastble.data.BleDevice;

import java.util.ArrayList;
import java.util.List;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ControlActivity.class.getSimpleName();

    public static final String KEY_DATA = "key_data";

    private BleDevice bleDevice;

    private List<Fragment> fragments = new ArrayList<>();
    private int currentPage = 0;
    private LinearLayout btn_left;
    private LinearLayout btn_right;
    private ImageView imageView_left;
    private ImageView imageView_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initPage();
        btn_left = findViewById(R.id.btn_control_left);
        btn_left.setOnClickListener(this);
        btn_right = findViewById(R.id.btn_control_right);
        btn_right.setOnClickListener(this);
        imageView_left = findViewById(R.id.view_left);
        imageView_right = findViewById(R.id.view_right);

    }


    private void initPage() {

        prepareFragment();
        changePage(0);
        bleDevice = getIntent().getParcelableExtra(KEY_DATA);
    }

    public void changePage(int page) {
        currentPage = page;
        updateFragment(page);

    }

    private void prepareFragment() {
        fragments.add(new ControlFragment());
        fragments.add(new SettingFragment());
        fragments.add(new SetPositionFragment());
        for (Fragment fragment : fragments) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_main, fragment).hide(fragment).commit();
        }
    }

    private void updateFragment(int position) {
        if (position > fragments.size() - 1) {
            return;
        }

        for (int i = 0; i < fragments.size(); i++) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

            Fragment fragment = fragments.get(i);
            if (i == position) {
                transaction.show(fragment);
            } else {

                transaction.hide(fragment);
            }
            transaction.commit();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_control_left:
                changePage(0);
                changeBtnImage();

                break;
            case R.id.btn_control_right:
                changePage(1);
                changeBtnImage();
                break;
        }
    }

    private void changeBtnImage() {
        Log.d(TAG, "changeBtnImage: " + currentPage);
        switch (currentPage) {
            case 0:
                btn_left.setBackgroundResource(R.mipmap.brown_back);
                btn_right.setBackgroundResource(R.mipmap.white_back);
                imageView_left.setImageResource(R.mipmap.control_w);
                imageView_right.setImageResource(R.mipmap.mode_g);
                break;
            case 1:
                btn_left.setBackgroundResource(R.mipmap.white_back);
                btn_right.setBackgroundResource(R.mipmap.brown_back);
                imageView_left.setImageResource(R.mipmap.control);
                imageView_right.setImageResource(R.mipmap.mode);
                break;

        }
    }
}
