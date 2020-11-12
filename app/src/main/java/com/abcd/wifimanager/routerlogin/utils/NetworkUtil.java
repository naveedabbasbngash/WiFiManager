package com.abcd.wifimanager.routerlogin.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtil {
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    public static int TYPE_WIFI = 1;

    public static int getConnectivityStatus(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() == 1) {
                return TYPE_WIFI;
            }
            if (activeNetworkInfo.getType() == 0) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int connectivityStatus = getConnectivityStatus(context);
        if (connectivityStatus == TYPE_WIFI) {
            return "wifi_enabled";
        }
        if (connectivityStatus == TYPE_MOBILE) {
            return "mobile_enabled";
        }
        return connectivityStatus == TYPE_NOT_CONNECTED ? "no_connection" : null;
    }

    public static List<String> getConnectivityInfo(Context context) {
        List<String> arrayList = new ArrayList();
        ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        String connectivityStatusString = getConnectivityStatusString(context);
        if (connectivityStatusString == "wifi_enabled") {
            arrayList.add("wifi_enabled");
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            double calculateSignalLevel = (double) WifiManager.calculateSignalLevel(connectionInfo.getRssi(), 10);
            Double.isNaN(calculateSignalLevel);
            int i = (int) ((calculateSignalLevel / 9.0d) * 100.0d);
            arrayList.add(connectionInfo.getSSID());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toString(i));
            stringBuilder.append(" %");
            arrayList.add(stringBuilder.toString());
            return arrayList;
        } else if (connectivityStatusString.equals("mobile_enabled")) {
            arrayList.add("mobile_enabled");
            arrayList.add(((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName());
            return arrayList;
        } else {
            arrayList.add("no_connection");
            return arrayList;
        }
    }
}
