/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample.operation;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.blesample.ControlActivity;
import com.clj.blesample.MainActivity;
import com.clj.blesample.R;
import com.clj.blesample.adapter.MatchedAdapter;
import com.clj.blesample.comm.Observer;
import com.clj.blesample.comm.ObserverManager;
import com.clj.blesample.comm.Utils;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

import java.util.ArrayList;
import java.util.List;

public class OperateActivity extends AppCompatActivity implements View.OnClickListener, Observer {
    //ui
    private SimpleDeviceAdapter matchedAdapter;
    private Button btn_link;
    private ListView listView_device;
    private ProgressDialog progressDialog;

    //var
    private BleDevice device;


    private int cur_pos;//当前行

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_page);
        initView();


    }

    //初始化View
    private void initView() {
        matchedAdapter = new SimpleDeviceAdapter(this);
        listView_device = findViewById(R.id.mainList);
        listView_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                cur_pos = position;// 更新当前行
            }
        });
        btn_link = findViewById(R.id.btn_connect_xy);
        btn_link.setOnClickListener(this);
        listView_device.setAdapter(matchedAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_connect_xy:
                Utils.Toast(this, getApplicationContext().toString());


                break;
            case R.id.btn_search:
                BleManager.getInstance().scan(new BleScanCallback() {
                    @Override
                    public void onScanFinished(List<BleDevice> scanResultList) {

                    }

                    @Override
                    public void onScanStarted(boolean success) {

                    }

                    @Override
                    public void onScanning(BleDevice bleDevice) {
                        matchedAdapter.addDevice(bleDevice);
                        matchedAdapter.notifyDataSetChanged();
                    }
                });
                List<BleDevice> deviceList = BleManager.getInstance().getAllConnectedDevice();


                matchedAdapter.clearConnectedDevice();
                for (BleDevice bleDevice : deviceList) {
                    matchedAdapter.addDevice(bleDevice);
                }
                matchedAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void connect(final BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                progressDialog.show();
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), getString(R.string.connect_fail), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                progressDialog.dismiss();
                matchedAdapter.addDevice(bleDevice);
                matchedAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getApplicationContext(), ControlActivity.class);
                intent.putExtra(OperationActivity.KEY_DATA, bleDevice);
                startActivity(intent);
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                progressDialog.dismiss();

                matchedAdapter.removeDevice(bleDevice);
                matchedAdapter.notifyDataSetChanged();

                if (isActiveDisConnected) {
                    ObserverManager.getInstance().notifyObserver(bleDevice);
                }

            }
        });
    }

    @Override
    public void disConnected(BleDevice bleDevice) {
        if (device != null && bleDevice != null && device.getKey().equals(bleDevice.getKey())) {
            finish();
        }
    }

    private class SimpleDeviceAdapter extends BaseAdapter {

        private Context context;
        private List<BleDevice> bleDeviceList;

        public SimpleDeviceAdapter(Context context) {
            this.context = context;
            bleDeviceList = new ArrayList<>();
        }

        public void addDevice(BleDevice bleDevice) {
            removeDevice(bleDevice);
            bleDeviceList.add(bleDevice);
        }

        public void removeDevice(BleDevice bleDevice) {
            for (int i = 0; i < bleDeviceList.size(); i++) {
                BleDevice device = bleDeviceList.get(i);
                if (bleDevice.getKey().equals(device.getKey())) {
                    bleDeviceList.remove(i);
                }
            }
        }

        public void clearConnectedDevice() {
            for (int i = 0; i < bleDeviceList.size(); i++) {
                BleDevice device = bleDeviceList.get(i);
                if (BleManager.getInstance().isConnected(device)) {
                    bleDeviceList.remove(i);
                }
            }
        }

        public void clearScanDevice() {
            for (int i = 0; i < bleDeviceList.size(); i++) {
                BleDevice device = bleDeviceList.get(i);
                if (!BleManager.getInstance().isConnected(device)) {
                    bleDeviceList.remove(i);
                }
            }
        }

        public void clear() {
            clearConnectedDevice();
            clearScanDevice();
        }

        @Override
        public int getCount() {
            return bleDeviceList.size();
        }

        @Override
        public BleDevice getItem(int position) {
            if (position > bleDeviceList.size())
                return null;
            return bleDeviceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = View.inflate(context, R.layout.adapter_matched, null);
                holder = new ViewHolder();
                convertView.setTag(holder);

                holder.txt_name = convertView.findViewById(R.id.txt_name);

            }

            final BleDevice bleDevice = getItem(position);
            if (bleDevice != null) {
                boolean isConnected = BleManager.getInstance().isConnected(bleDevice);
                String name = bleDevice.getName();
                holder.txt_name.setText(name);

                if (isConnected) {
                    holder.txt_name.setTextColor(0xFF1DE9B6);
                } else {
                    holder.txt_name.setTextColor(0xFF000000);

                }
            }
            if (position == cur_pos) {// 如果当前的行就是ListView中选中的一行，就更改显示样式
                convertView.setBackgroundColor(getResources().getColor(R.color.lineColor));// 更改整行的背景色
                device = bleDevice;
            }
            return convertView;
        }

        class ViewHolder {
            TextView txt_name;
        }


    }


}
