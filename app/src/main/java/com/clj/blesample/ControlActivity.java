/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.clj.blesample.Fragment.ControlFragment;
import com.clj.blesample.Fragment.SetPositionFragment;
import com.clj.blesample.Fragment.SettingFragment;
import com.clj.blesample.operation.CharacteristicListFragment;
import com.clj.blesample.operation.CharacteristicOperationFragment;

import java.util.ArrayList;
import java.util.List;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Fragment> fragments = new ArrayList<>();
    private int currentPage = 0;
    private Button btn_left;
    private Button btn_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initPage();
        btn_left = findViewById(R.id.btn_control_left);
        btn_left.setOnClickListener(this);
        btn_right = findViewById(R.id.btn_control_right);
        btn_right.setOnClickListener(this);
    }

    private void initPage() {

        prepareFragment();
        changePage(0);
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
        switch (currentPage) {
            case 0:

                btn_left.setBackgroundResource(R.mipmap.btn_left);
                btn_right.setBackgroundResource(R.mipmap.btn_right);

                break;
            case 1:
                btn_left.setBackgroundResource(R.mipmap.control);
                btn_right.setBackgroundResource(R.mipmap.mode);
                break;

        }
    }
}
