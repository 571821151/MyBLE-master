package com.clj.blesample.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.clj.blesample.ControlActivity;
import com.clj.blesample.R;
import com.clj.blesample.comm.BleUtils;
import com.clj.fastble.data.BleDevice;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetPositionFragment extends Fragment implements View.OnClickListener {


    private ControlActivity controlActivity;

    private ImageButton btn_one;
    private ImageButton btn_two;
    private ImageButton btn_three;
    private Button btn_set;

    private Button btn_cancel_set;
    private String request_code;

    public SetPositionFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set_position, container, false);
        btn_one = v.findViewById(R.id.btn_set_one);
        btn_one.setOnClickListener(this);


        btn_two = v.findViewById(R.id.btn_set_two);
        btn_two.setOnClickListener(this);
        btn_three = v.findViewById(R.id.btn_set_three);
        btn_three.setOnClickListener(this);

        btn_set = v.findViewById(R.id.btn_set);
        btn_set.setOnClickListener(this);
        btn_cancel_set = v.findViewById(R.id.btn_cancel_set);
        btn_cancel_set.setOnClickListener(this);
        controlActivity = (ControlActivity) getActivity();
        return v;

    }

    @Override
    public void onClick(View view) {
        btn_one.setBackgroundResource(R.mipmap.icon_p1_b);
        btn_two.setBackgroundResource(R.mipmap.icon_p2);
        btn_three.setBackgroundResource(R.mipmap.icon_p3);
//        BleUtils.writeBleCode(controlActivity, BleUtils.SET_CODE);
        switch (view.getId()) {
            case R.id.btn_set_one:
                btn_one.setBackgroundResource(R.mipmap.icon_p1);
                request_code = BleUtils.SET_MEMORY_POSITION_ONE;

                break;
            case R.id.btn_set_two:
                btn_two.setBackgroundResource(R.mipmap.icon_p2_w);
                request_code = BleUtils.SET_MEMORY_POSITION_TWO;
                break;
            case R.id.btn_set_three:
                btn_three.setBackgroundResource(R.mipmap.icon_p3_w);
                request_code = BleUtils.SET_MEMORY_POSITION_THREE;

                break;
            case R.id.btn_set:
                request_code = BleUtils.SET_MEMORY_POSITION_ONE;
                BleUtils.writeBleCode(controlActivity, request_code);
                BleUtils.writeBleCode(controlActivity, BleUtils.SET_CODE);
                ((ControlActivity) getActivity()).changePage(1);
                break;
            case R.id.btn_cancel_set:
                ((ControlActivity) getActivity()).changePage(1);
                break;

        }
    }
}
