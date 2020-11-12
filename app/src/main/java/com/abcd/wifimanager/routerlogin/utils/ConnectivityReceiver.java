package com.abcd.wifimanager.routerlogin.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityReceiver extends BroadcastReceiver {
    public static ConnectivityReceiverListener connectivityReceiverListener;

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean z);
    }

    public void onReceive(Context context, Intent intent) {
        Log.i("ContentValues", "onReceive: ");
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        boolean z = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        String connectivityStatusString = NetworkUtil.getConnectivityStatusString(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onReceive: ");
        stringBuilder.append(connectivityStatusString);
        Log.i("ContentValues", stringBuilder.toString());
        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(z);
        }
    }

    public static boolean isConnected() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
