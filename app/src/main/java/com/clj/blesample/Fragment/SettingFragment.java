package com.clj.blesample.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.clj.blesample.ControlActivity;
import com.clj.blesample.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {
    private ImageButton btn_setting_position;

    public SettingFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        btn_setting_position = v.findViewById(R.id.btn_setting_position);
        btn_setting_position.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_setting_position:
                ((ControlActivity) getActivity()).changePage(2);
                break;
        }
    }
}
