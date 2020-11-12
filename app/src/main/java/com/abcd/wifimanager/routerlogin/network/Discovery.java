package com.abcd.wifimanager.routerlogin.network;

import com.abcd.wifimanager.routerlogin.async.ScanHostsAsyncTask;
import com.abcd.wifimanager.routerlogin.response.MainAsyncResponse;

public class Discovery {
    public static void scanHosts(int i, int i2, int i3, MainAsyncResponse mainAsyncResponse) {
        new ScanHostsAsyncTask(mainAsyncResponse).execute(i, i2, i3);
    }
}
