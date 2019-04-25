/*
 * Copyright (cly) 2019.
 */

package com.clj.blesample.operation;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleMtuChangedCallback;
import com.clj.fastble.callback.BleRssiCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OperateActivity extends AppCompatActivity implements View.OnClickListener {

    //TAG and others
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;


    //ui
    private SimpleDeviceAdapter matchedAdapter;
    private Button btn_link;
    private Button btn_search;
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
        BleManager.getInstance().init(getApplication());
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setConnectOverTime(20000)
                .setOperateTimeout(5000);

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
        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);

        listView_device.setAdapter(matchedAdapter);


        progressDialog = new ProgressDialog(this);
        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showConnectedDevice();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_connect_xy:
                Intent intent = new Intent(getApplicationContext(), ControlActivity.class);
//                    intent.putExtra(OperationActivity.KEY_DATA, bleDevice);
                startActivity(intent);
                if (device != null) {
                    // connect(device);

                } else
                    Toast.makeText(OperateActivity.this, R.string.connect_fail, Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_search:
                try {
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
//                List<BleDevice> deviceList = BleManager.getInstance().getAllConnectedDevice();
//
//
//                matchedAdapter.clearConnectedDevice();
//                for (BleDevice bleDevice : deviceList) {
//                    matchedAdapter.addDevice(bleDevice);
//                }
//                matchedAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(OperateActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void showConnectedDevice() {
        List<BleDevice> deviceList = BleManager.getInstance().getAllConnectedDevice();
        matchedAdapter.clearConnectedDevice();
        for (BleDevice bleDevice : deviceList) {
            matchedAdapter.addDevice(bleDevice);
        }
        matchedAdapter.notifyDataSetChanged();
    }

    private void setScanRule() {
//        String[] uuids;
//        String str_uuid = et_uuid.getText().toString();
//        if (TextUtils.isEmpty(str_uuid)) {
//            uuids = null;
//        } else {
//            uuids = str_uuid.split(",");
//        }
//        UUID[] serviceUuids = null;
//        if (uuids != null && uuids.length > 0) {
//            serviceUuids = new UUID[uuids.length];
//            for (int i = 0; i < uuids.length; i++) {
//                String name = uuids[i];
//                String[] components = name.split("-");
//                if (components.length != 5) {
//                    serviceUuids[i] = null;
//                } else {
//                    serviceUuids[i] = UUID.fromString(uuids[i]);
//                }
//            }
//        }
//
//        String[] names;
//        String str_name = et_name.getText().toString();
//        if (TextUtils.isEmpty(str_name)) {
//            names = null;
//        } else {
//            names = str_name.split(",");
//        }
//
//        String mac = et_mac.getText().toString();
//
//        boolean isAutoConnect = sw_auto.isChecked();

        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                //  .setServiceUuids(serviceUuids)      // 只扫描指定的服务的设备，可选
                //   .setDeviceName(true, names)   // 只扫描指定广播名的设备，可选
                //  .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
                //  .setAutoConnect(isAutoConnect)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }

    private void startScan() {
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                matchedAdapter.clearScanDevice();
                matchedAdapter.notifyDataSetChanged();
//                img_loading.startAnimation(operatingAnim);
//                img_loading.setVisibility(View.VISIBLE);
//                btn_scan.setText(getString(R.string.stop_scan));
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                matchedAdapter.addDevice(bleDevice);
                matchedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
//                img_loading.clearAnimation();
//                img_loading.setVisibility(View.INVISIBLE);
//                btn_scan.setText(getString(R.string.start_scan));
            }
        });
    }

    private void connect(final BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                progressDialog.show();
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
//                img_loading.clearAnimation();
//                img_loading.setVisibility(View.INVISIBLE);
//                btn_scan.setText(getString(R.string.start_scan));
                progressDialog.dismiss();
                Toast.makeText(OperateActivity.this, getString(R.string.connect_fail), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                progressDialog.dismiss();
                matchedAdapter.addDevice(bleDevice);
                matchedAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getApplicationContext(), ControlActivity.class);
                intent.putExtra(ControlActivity.KEY_DATA, bleDevice);
                startActivity(intent);
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                progressDialog.dismiss();
                matchedAdapter.removeDevice(bleDevice);
                matchedAdapter.notifyDataSetChanged();

                if (isActiveDisConnected) {
                    Toast.makeText(OperateActivity.this, getString(R.string.active_disconnected), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OperateActivity.this, getString(R.string.disconnected), Toast.LENGTH_LONG).show();
                    ObserverManager.getInstance().notifyObserver(bleDevice);
                }

            }
        });
    }

    private void readRssi(BleDevice bleDevice) {
        BleManager.getInstance().readRssi(bleDevice, new BleRssiCallback() {
            @Override
            public void onRssiFailure(BleException exception) {
                Log.i(TAG, "onRssiFailure" + exception.toString());
            }

            @Override
            public void onRssiSuccess(int rssi) {
                Log.i(TAG, "onRssiSuccess: " + rssi);
            }
        });
    }

    private void setMtu(BleDevice bleDevice, int mtu) {
        BleManager.getInstance().setMtu(bleDevice, mtu, new BleMtuChangedCallback() {
            @Override
            public void onSetMTUFailure(BleException exception) {
                Log.i(TAG, "onsetMTUFailure" + exception.toString());
            }

            @Override
            public void onMtuChanged(int mtu) {
                Log.i(TAG, "onMtuChanged: " + mtu);
            }
        });
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode,
                                                 @NonNull String[] permissions,
                                                 @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            onPermissionGranted(permissions[i]);
                        }
                    }
                }
                break;
        }
    }

    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, getString(R.string.please_open_blue), Toast.LENGTH_LONG).show();
            return;
        }

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(this, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }

    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.notifyTitle)
                            .setMessage(R.string.gpsNotifyMsg)
                            .setNegativeButton(R.string.cancel,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                            .setPositiveButton(R.string.setting,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                                        }
                                    })
                            .setCancelable(false)
                            .show();
                } else {
                    setScanRule();
                    startScan();
                }
                break;
        }
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_GPS) {
            if (checkGPSIsOpen()) {
                setScanRule();
                startScan();
            }
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

                holder.txt_name = convertView.findViewById(R.id.txt_matched);

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
