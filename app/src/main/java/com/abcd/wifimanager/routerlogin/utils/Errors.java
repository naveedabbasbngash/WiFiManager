package com.abcd.wifimanager.routerlogin.utils;

import android.content.Context;
import android.widget.Toast;

public class Errors {
    public static void showError(Context context, String str) {
        Toast.makeText(context, str, 0).show();
    }
}
