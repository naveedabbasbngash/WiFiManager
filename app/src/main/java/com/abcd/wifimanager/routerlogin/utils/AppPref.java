package com.abcd.wifimanager.routerlogin.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class AppPref {
    static final String IS_TERMS_ACCEPT = "IS_TERMS_ACCEPT";
    static final String MyPref = "userPref";

    public static boolean IsTermsAccept(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getBoolean(IS_TERMS_ACCEPT, false);
    }

    public static void setIsTermsAccept(Context context, boolean z) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putBoolean(IS_TERMS_ACCEPT, z);
        edit.commit();
    }
}
