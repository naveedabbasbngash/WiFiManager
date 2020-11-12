package com.abcd.wifimanager.routerlogin.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DataReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (!DataService.service_status) {
            context.startService(new Intent(context, DataService.class));
        }
    }
}
