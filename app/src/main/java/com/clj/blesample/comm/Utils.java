package com.clj.blesample.comm;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static void Toast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

    }
}
