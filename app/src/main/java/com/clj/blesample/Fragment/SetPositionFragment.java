package com.clj.blesample.Fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.clj.blesample.ControlActivity;
import com.clj.blesample.R;
import com.clj.blesample.comm.BleOrder;
import com.clj.fastble.data.BleDevice;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetPositionFragment extends Fragment implements View.OnClickListener {

    private BleDevice bleDevice;
    private Bitmap bitmap_one_b;
    private Bitmap bitmap_one_w;
    private Bitmap bitmap_two_b;
    private Bitmap bitmap_two_w;
    private Bitmap bitmap_three_w;
    private Bitmap bitmap_three_b;

    private ImageButton btn_one;
    private ImageButton btn_two;
    private ImageButton btn_three;
    private Button btn_set;

    private Button btn_cancel_set;

    public SetPositionFragment() {
//        // Required empty public constructor
//        bitmap_one_b = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_p1_b);
//        bitmap_one_w = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_p1);
//        bitmap_two_b = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_p2);
//        bitmap_two_w = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_p2_w);
//        bitmap_three_w = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_p2_w);
//        bitmap_three_b = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_p1_b);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
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

        return v;

    }

    @Override
    public void onClick(View view) {
        int data = 0;
        btn_one.setBackgroundResource(R.mipmap.icon_p1_b);
        btn_two.setBackgroundResource(R.mipmap.icon_p2);
        btn_three.setBackgroundResource(R.mipmap.icon_p3);

        switch (view.getId()) {
            case R.id.btn_set_one:
                btn_one.setBackgroundResource(R.mipmap.icon_p1);

                break;
            case R.id.btn_set_two:
                btn_two.setBackgroundResource(R.mipmap.icon_p2_w);
                break;
            case R.id.btn_set_three:
                btn_three.setBackgroundResource(R.mipmap.icon_p3_w);

                break;
            case R.id.btn_set:
                BleOrder.PostDateForBle(bleDevice, data);

                ((ControlActivity) getActivity()).changePage(1);
                break;
            case R.id.btn_cancel_set:
                ((ControlActivity) getActivity()).changePage(1);
                break;

        }
    }
}
