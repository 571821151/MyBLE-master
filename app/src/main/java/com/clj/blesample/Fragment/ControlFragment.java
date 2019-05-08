package com.clj.blesample.Fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.clj.blesample.ControlActivity;
import com.clj.blesample.R;
import com.clj.blesample.View.MainBoard;
import com.clj.blesample.comm.BleUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ControlFragment extends Fragment implements View.OnTouchListener {
    private boolean isTouched = false;

    private final String TAG = getClass().getName();
    private int degree_left = 0;
    private int degree_right = 0;

    private ControlActivity controlActivity;


    private Button btn_left_up;
    private Button btn_left_down;

    private Button btn_mid_up;
    private Button btn_mid_down;


    private Button btn_right_up;
    private Button btn_right_down;


    private MainBoard main_board;
    private ScheduledExecutorService scheduledExecutor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_control, null);
        initView(v);
        controlActivity = (ControlActivity) getActivity();
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }


    private View initView(View v) {
        main_board = (getActivity()).findViewById(R.id.img_main);


        //left
        btn_left_up = v.findViewById(R.id.btn_left_up);
        btn_left_up.setOnTouchListener(this);

        btn_left_down = v.findViewById(R.id.btn_left_down);
        btn_left_down.setOnTouchListener(this);

        //mid
        btn_mid_up = v.findViewById(R.id.btn_mid_up);
        btn_mid_up.setOnTouchListener(this);

        btn_mid_down = v.findViewById(R.id.btn_mid_down);
        btn_mid_down.setOnTouchListener(this);

        //right
        btn_right_up = v.findViewById(R.id.btn_right_up);
        btn_right_up.setOnTouchListener(this);

        btn_right_down = v.findViewById(R.id.btn_right_down);
        btn_right_down.setOnTouchListener(this);

        return v;
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouched)
                return false;
            updateAddOrSubtract(view.getId());    //手指按下时触发不停的发送消息
            isTouched = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            stopAddOrSubtract();    //手指抬起时停止发送
            for (int i = 0; i < 5; i++) writeBleCode(BleUtils.RELEASE_CODE);


            isTouched = false;
        }
        return true;

    }

    private void updateAddOrSubtract(int viewId) {
        final int vid = viewId;
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = vid;
                handler.sendMessage(msg);
            }
        }, 0, 130, TimeUnit.MILLISECONDS);    //每间隔130ms发送Message
    }

    private void stopAddOrSubtract() {
        if (scheduledExecutor != null) {
            scheduledExecutor.shutdownNow();
            scheduledExecutor = null;
        }
    }

    private void writeBleCode(String ble_code) {
        if (controlActivity == null)
            Log.e(TAG, "writeBleCode: " + "no controlActivity");
        else
            controlActivity.writeBleMessage(ble_code);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int viewId = msg.what;
            switch (viewId) {
                case R.id.btn_left_up:
                    setDegreeForLeft(true);
                    writeBleCode(BleUtils.HEAD_UP);
                    break;
                case R.id.btn_left_down:
                    setDegreeForLeft(false);

                    writeBleCode(BleUtils.HEAD_DOWN);
                    break;
                case R.id.btn_mid_up:
                    setDegreeForMid(true);

                    writeBleCode(BleUtils.HEAD_AND_LEG_UP);
                    break;
                case R.id.btn_mid_down:

                    setDegreeForMid(false);

                    writeBleCode(BleUtils.HEAD_AND_LEG_DOWN);
                    break;
                case R.id.btn_right_up:
                    setDegreeForRight(true);

                    writeBleCode(BleUtils.LEG_UP);
                    break;
                case R.id.btn_right_down:
                    writeBleCode(BleUtils.LEG_DOWN);

                    setDegreeForRight(false);
                    break;
            }
        }


        private void setDegreeForLeft(boolean is_up) {

            if (is_up)
                degree_left += 4;
            else
                degree_left -= 4;
            if (degree_left > 60)
                degree_left = 60;
            if (degree_left < 0)
                degree_left = 0;
            main_board.SetLeftDegree(degree_left);
        }

        private void setDegreeForMid(boolean is_up) {

            if (is_up) {
                degree_left += 4;
                degree_right -= 4;
            } else {
                degree_left -= 4;
                degree_right += 4;
            }
            if (degree_left > 60)
                degree_left = 60;
            if (degree_left < 0)
                degree_left = 0;
            if (degree_right > 0)
                degree_right = 0;
            if (degree_right < -60)
                degree_right = -60;

            main_board.SetLeftDegree(degree_left);
            main_board.SetRightDegree(degree_right);
        }

        private void setDegreeForRight(boolean is_up) {

            if (is_up)
                degree_right -= 4;
            else
                degree_right += 4;
            if (degree_right > 0)
                degree_right = 0;
            if (degree_right < -60)
                degree_right = -60;
            main_board.SetRightDegree(degree_right);

        }
    };


}