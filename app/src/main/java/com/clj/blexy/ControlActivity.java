/*
 * Copyright (cly) 2019.
 */

package com.clj.blexy;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.blexy.Fragment.ControlFragment;
import com.clj.blexy.Fragment.SetPositionFragment;
import com.clj.blexy.Fragment.SettingFragment;
import com.clj.blexy.comm.Observer;
import com.clj.blexy.comm.ObserverManager;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.utils.HexUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener, Observer {
    private static final String TAG = ControlActivity.class.getSimpleName();

    public static final String KEY_DATA = "key_data";

    private BleDevice bleDevice;

    private List<Fragment> fragments = new ArrayList<>();
    private int currentPage = 0;
    private LinearLayout btn_left;
    private LinearLayout btn_right;
    private ImageView imageView_left;
    private ImageView imageView_right;
    private TextView tv_back;
    private LinearLayout layout_bottom;


    private BluetoothGattCharacteristic characteristicWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        btn_left = findViewById(R.id.btn_control_left);
        btn_left.setOnClickListener(this);
        btn_right = findViewById(R.id.btn_control_right);
        btn_right.setOnClickListener(this);
        imageView_left = findViewById(R.id.view_left);
        imageView_right = findViewById(R.id.view_right);
        layout_bottom = findViewById(R.id.layout_bottom);
        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        ObserverManager.getInstance().addObserver(this);
        initPage();
    }


    private void initPage() {

        prepareFragment();
        changePage(0);
        bleDevice = getIntent().getParcelableExtra(KEY_DATA);
        if (bleDevice != null) {
            BluetoothGatt gatt = BleManager.getInstance().getBluetoothGatt(bleDevice);
            if (gatt == null) return;
            for (BluetoothGattService service : gatt.getServices()) {
                UUID uuid = service.getUuid();
                for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                    int charaProp = characteristic.getProperties();

                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0) {
                        characteristicWrite = characteristic;

                    }
                    writeBleMessage("0x05393ef9");
                    break;

                }

                break;
            }

        }


    }

    private byte[] get_hex(String hex) {
        Integer code = Integer.parseInt(hex);
        code = code + 0x05;

        return HexUtil.hexStringToBytes(hex);
    }

    public void writeBleMessage(String hex) {
        if (characteristicWrite == null)
            return;
        else {
            BleManager.getInstance().write(
                    bleDevice,
                    characteristicWrite.getService().getUuid().toString(),
                    characteristicWrite.getUuid().toString(),
                    HexUtil.hexStringToBytes(hex),
                    new BleWriteCallback() {

                        @Override
                        public void onWriteSuccess(final int current, final int total, final byte[] justWrite) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ControlActivity.this, "" + HexUtil.formatHexString(justWrite, true), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onWriteFailure(final BleException exception) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ControlActivity.this, "" + "write failure", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ObserverManager.getInstance().deleteObserver(this);
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
                if (i == 2)
                    layout_bottom.setVisibility(View.INVISIBLE);
                else
                    layout_bottom.setVisibility(View.VISIBLE);
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
            case R.id.tv_back:

                Intent intent = new Intent();

                //把返回数据存入Intent

                intent.putExtra("result", "My name is ChenLingYun");

                //设置返回数据

                this.setResult(RESULT_OK, intent);

                //关闭Activity

                this.finish();
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

    @Override
    public void disConnected(BleDevice bleDevice) {
        Intent intent = new Intent();

        //把返回数据存入Intent

        intent.putExtra("result", "My name is linjiqin");

        //设置返回数据

        this.setResult(RESULT_OK, intent);

        //关闭Activity

        this.finish();
    }
}
