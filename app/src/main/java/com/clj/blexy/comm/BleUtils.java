package com.clj.blexy.comm;


import android.util.Log;

import com.clj.blexy.ControlActivity;
import com.clj.fastble.utils.HexUtil;

import java.math.BigInteger;

public class BleUtils {
    public static final String TAG = "BleUtils";

    public static String RESET = "30"; //复位	电机初始位置
    public static String HEAD_UP = "31";//1号电机升	头部电机
    public static String HEAD_DOWN = "32";//1号电机降	头部电机
    public static String LEG_UP = "33";//2号电机升	腿部电机
    public static String LEG_DOWN = "34";//2号电机降	腿部电机
    public static String MACHINE_THREE_UP = "35";//3号电机升	预留电机
    public static String MACHINE_THREE_DOWN = "36";//3号电机降	预留电机
    public static String WORK_FOUR_UP = "37";//4号电机升	预留电机
    public static String WORK_FOUR_DOWN = "38";//4号电机降	预留电机
    public static String HEAD_AND_LEG_UP = "39";//1、2号电机同时升	头部、腿部电机
    public static String HEAD_AND_LEG_DOWN = "3A";//1、2号电机同时降	头部、腿部电机
    public static String BACK_COMBINE_ONE = "3B";//*、号电机同时升______降	预留组合
    public static String BACK_COMBINE_TWO = "3C";//■电机同时升______降	预留组合
    public static String BACK_COMBINE_THREE = "3D";//*、号电机同时升降	预留组合
    public static String BACK_ONE = "3E";//	预留
    public static String BACK_TWO = "3F";//	预留
    public static String MEMORY_POSITION_ONE = "41";//记忆位置1
    public static String MEMORY_POSITION_TWO = "42";//记忆位置2
    public static String MEMORY_POSITION_THREE = "43";//记忆位置3
    public static String MEMORY_POSITION_FOUR = "44";//记忆位置4	预留
    public static String SET_MEMORY_POSITION_ONE = "45";//设置记忆位置1	设置键+记忆位置1
    public static String SET_MEMORY_POSITION_TWO = "46";//设置记忆位置2	设置键+记忆位置2
    public static String SET_MEMORY_POSITION_THREE = "47";//设置记忆位置3	设置键+记忆位置3
    public static String SET_MEMORY_POSITION_FOUR = "48";//设置记忆位置4	预留
    public static String LIGHT = "08";//照明灯
    public static String CHECK_CODE = "09";//对码
    public static String RELEASE_CODE = "1B";//按键释放
    public static String SET_CODE = "0A";//设置键
    public static String SEND_CODE = "05";//发送命令
    public static String END_CODE = "F9";//发送命令
    public static String READ_LEFT = "81";//左侧回显
    public static String READ_RIGHT= "82";//右侧回显
    public static String LENGTH_DETECT  = "0B";//长度检测


    public static void writeBleCode(ControlActivity controlActivity, String ble_code) {
        if (controlActivity == null)
            Log.e(TAG, "writeBleCode: " + "no controlActivity");
        else {
            Integer final_int = Integer.parseInt(new BigInteger(ble_code, 16).toString()) + Integer.parseInt(new BigInteger(SEND_CODE, 16).toString());
            ble_code = SEND_CODE + ble_code + HexUtil.numToHex8(final_int) + END_CODE;

            Log.d(TAG, "writeBleCode: Just write ble message " + ble_code);
            controlActivity.writeBleMessage(ble_code);
        }
    }

}
