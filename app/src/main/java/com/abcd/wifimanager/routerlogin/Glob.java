package com.abcd.wifimanager.routerlogin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Glob {

    public static String privacy_link = "Policy Link";
    public static String acc_link = "https://play.google.com/store/apps/developer?id=Your+Account+Name";

    public static boolean isOnline(Context ctx) {
        NetworkInfo netInfo = ((ConnectivityManager) ctx.getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected()) {
            return false;
        }
        return true;
    }
}
