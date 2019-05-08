package com.clj.blesample.comm;


public class BleUtils {

    public static String RESET = "0x30"; //复位	电机初始位置
    public static String HEAD_UP = "0x31";//1号电机升	头部电机
    public static String HEAD_DOWN = "0x32";//1号电机降	头部电机
    public static String LEG_UP = "0x33";//2号电机升	腿部电机
    public static String LEG_DOWN = "0x34";//2号电机降	腿部电机
    public static String MACHINE_THREE_UP = "0x35";//3号电机升	预留电机
    public static String MACHINE_THREE_DOWN = "0x36";//3号电机降	预留电机
    public static String WORK_FOUR_UP = "0x37";//4号电机升	预留电机
    public static String WORK_FOUR_DOWN = "0x38";//4号电机降	预留电机
    public static String HEAD_AND_LEG_UP = "0x39";//1、2号电机同时升	头部、腿部电机
    public static String HEAD_AND_LEG_DOWN = "0x3A";//1、2号电机同时降	头部、腿部电机
    public static String BACK_COMBINE_ONE = "0x3B";//*、号电机同时升______降	预留组合
    public static String BACK_COMBINE_TWO = "0x3C";//■电机同时升______降	预留组合
    public static String BACK_COMBINE_THREE = "0x3D";//*、号电机同时升降	预留组合
    public static String BACK_ONE = "0x3E";//	预留
    public static String BACK_TWO = "0x3F";//	预留
    public static String MEMORY_POSITION_ONE = "0x41";//记忆位置1
    public static String MEMORY_POSITION_TWO = "0x42";//记忆位置2
    public static String MEMORY_POSITION_THREE = "0x43";//记忆位置3
    public static String MEMORY_POSITION_FOUR = "0x44";//记忆位置4	预留
    public static String SET_MEMORY_POSITION_ONE = "0x45";//设置记忆位置1	设置键+记忆位置1
    public static String SET_MEMORY_POSITION_TWO = "0x46";//设置记忆位置2	设置键+记忆位置2
    public static String SET_MEMORY_POSITION_THREE = "0x47";//设置记忆位置3	设置键+记忆位置3
    public static String SET_MEMORY_POSITION_FOUR = "0x48";//设置记忆位置4	预留
    public static String LIGHT = "0x08";//照明灯
    public static String CHECK_CODE = "0x09";//对码
    public static String RELEASE_CODE = "0x1A";//按键释放
    public static String SET_CODE = "0x0A";//设置键


}
