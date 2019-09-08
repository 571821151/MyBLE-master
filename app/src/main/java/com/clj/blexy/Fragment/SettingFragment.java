package com.clj.blexy.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.clj.blexy.ControlActivity;
import com.clj.blexy.R;
import com.clj.blexy.comm.BleUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {
    private ImageButton btn_setting_position;


    private ImageButton layout_p1;
    private ImageButton layout_p2;
    private ImageButton layout_p3;
    private ImageButton layout_flat;


    private ControlActivity controlActivity;

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
        layout_p1 = v.findViewById(R.id.layout_p1);

        layout_p2 = v.findViewById(R.id.layout_p2);
        layout_p3 = v.findViewById(R.id.layout_p3);
        layout_flat = v.findViewById(R.id.layout_flat);


        layout_p1.setOnClickListener(this);
        layout_p2.setOnClickListener(this);
        layout_p3.setOnClickListener(this);
        layout_flat.setOnClickListener(this);
        controlActivity = ((ControlActivity) getActivity());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_setting_position:
                controlActivity.changePage(2);
                break;
            case R.id.layout_p1:
                BleUtils.writeBleCode(controlActivity, BleUtils.MEMORY_POSITION_ONE);
                break;
            case R.id.layout_p2:
                BleUtils.writeBleCode(controlActivity, BleUtils.MEMORY_POSITION_TWO);
                break;
            case R.id.layout_p3:
                BleUtils.writeBleCode(controlActivity, BleUtils.MEMORY_POSITION_THREE);

                break;
            case R.id.layout_flat:
                BleUtils.writeBleCode(controlActivity, BleUtils.RESET);

                break;
        }
    }
}
