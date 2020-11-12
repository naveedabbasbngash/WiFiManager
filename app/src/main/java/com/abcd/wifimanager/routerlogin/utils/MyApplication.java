package com.abcd.wifimanager.routerlogin.utils;

import android.app.Application;
import com.abcd.wifimanager.routerlogin.utils.ConnectivityReceiver.ConnectivityReceiverListener;

public class MyApplication extends Application {
    private static MyApplication mInstance;

    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        MyApplication myApplication;
        synchronized (MyApplication.class) {
            myApplication = mInstance;
        }
        return myApplication;
    }

    public void setConnectivityListener(ConnectivityReceiverListener connectivityReceiverListener) {
        ConnectivityReceiver.connectivityReceiverListener = connectivityReceiverListener;
    }
}
