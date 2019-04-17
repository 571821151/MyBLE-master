/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.clj.blesample.Fragment.ControlFragment;
import com.clj.blesample.Fragment.SetPositionFragment;
import com.clj.blesample.Fragment.SettingFragment;
import com.clj.blesample.operation.CharacteristicListFragment;
import com.clj.blesample.operation.CharacteristicOperationFragment;

import java.util.ArrayList;
import java.util.List;

public class ControlActivity extends AppCompatActivity {
    private List<Fragment> fragments = new ArrayList<>();
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initPage();

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
            Fragment fragment = fragments.get(i);
            if (i == position) {
                transaction.show(fragment);
            } else {
                transaction.hide(fragment);
            }
            transaction.commit();
        }
    }

}
