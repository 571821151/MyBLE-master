package com.clj.blesample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clj.blesample.comm.Utils;


public class ControlFragment extends Fragment implements View.OnClickListener {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_control, null);
        initView(v);
        return v;
    }
    private View initView(View v) {

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left_up:
                Utils.Toast(getContext(), "hello");
                break;
            case R.id.btn_left_down:

                break;
            case R.id.btn_mid_up:

                break;
            case R.id.btn_mid_down:

                break;
            case R.id.btn_right_up:

                break;
            case R.id.btn_right_down:

                break;

        }
    }


}
