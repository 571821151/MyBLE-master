/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample.operation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.clj.blesample.ControlFragment;
import com.clj.blesample.R;
import com.clj.blesample.adapter.MatchedAdapter;

import java.util.ArrayList;
import java.util.List;

public class OperateActivity extends AppCompatActivity implements View.OnClickListener {
    private MatchedAdapter matchedAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private int currentPage = 0;
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
    private void initPage() {
        prepareFragment();
        changePage(0);
    }

    public void changePage(int page) {
        currentPage = page;
        updateFragment(page);
        if (currentPage == 1) {
            ((CharacteristicListFragment) fragments.get(1)).showData();
        } else if (currentPage == 2) {
            ((CharacteristicOperationFragment) fragments.get(2)).showData();
        }
    }

    private void prepareFragment() {
        fragments.add(new ControlFragment());
        fragments.add(new CharacteristicListFragment());
        fragments.add(new CharacteristicOperationFragment());
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
