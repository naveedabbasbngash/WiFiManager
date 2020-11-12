package com.abcd.wifimanager.routerlogin.utils;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public abstract class MyUtility {
    public static boolean addWhois(Activity activity, String str) {
        String stringFromPreferences = getStringFromPreferences(activity, null, "whois");
        if (stringFromPreferences != null && !stringFromPreferences.isEmpty() && stringFromPreferences.contains(str)) {
            return false;
        }
        if (stringFromPreferences != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(stringFromPreferences);
            stringBuilder.append(",");
            stringBuilder.append(str);
            str = stringBuilder.toString();
        }
        return putStringInPreferences(activity, str, "whois");
    }

    public static String[] getWhois(Activity activity) {
        try {
            String stringFromPreferences = getStringFromPreferences(activity, null, "whois");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getWhois: ");
            stringBuilder.append(stringFromPreferences);
            Log.i("TAG", stringBuilder.toString());
            if (stringFromPreferences == null || stringFromPreferences.isEmpty()) {
                Log.i("TAG", "getWhois: ELSE");
                return null;
            }
            Log.i("TAG", "getWhois: If");
            return convertStringToArray(stringFromPreferences);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addping(Activity activity, String str) {
        String stringFromPreferences = getStringFromPreferences(activity, null, "ping");
        if (stringFromPreferences != null && !stringFromPreferences.isEmpty() && stringFromPreferences.contains(str)) {
            return false;
        }
        if (stringFromPreferences != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(stringFromPreferences);
            stringBuilder.append(",");
            stringBuilder.append(str);
            str = stringBuilder.toString();
        }
        return putStringInPreferences(activity, str, "ping");
    }

    public static String[] getping(Activity activity) {
        try {
            String stringFromPreferences = getStringFromPreferences(activity, null, "ping");
            if (stringFromPreferences == null || stringFromPreferences.isEmpty()) {
                return null;
            }
            return convertStringToArray(stringFromPreferences);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addport(Activity activity, String str) {
        String stringFromPreferences = getStringFromPreferences(activity, null, "port");
        if (stringFromPreferences != null && !stringFromPreferences.isEmpty() && stringFromPreferences.contains(str)) {
            return false;
        }
        if (stringFromPreferences != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(stringFromPreferences);
            stringBuilder.append(",");
            stringBuilder.append(str);
            str = stringBuilder.toString();
        }
        return putStringInPreferences(activity, str, "port");
    }

    public static String[] getport(Activity activity) {
        try {
            String stringFromPreferences = getStringFromPreferences(activity, null, "port");
            if (stringFromPreferences == null || stringFromPreferences.isEmpty()) {
                return null;
            }
            return convertStringToArray(stringFromPreferences);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addtrace(Activity activity, String str) {
        String stringFromPreferences = getStringFromPreferences(activity, null, "trace");
        if (stringFromPreferences != null && !stringFromPreferences.isEmpty() && stringFromPreferences.contains(str)) {
            return false;
        }
        if (stringFromPreferences != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(stringFromPreferences);
            stringBuilder.append(",");
            stringBuilder.append(str);
            str = stringBuilder.toString();
        }
        return putStringInPreferences(activity, str, "trace");
    }

    public static String[] gettrace(Activity activity) {
        try {
            String stringFromPreferences = getStringFromPreferences(activity, null, "trace");
            if (stringFromPreferences == null || stringFromPreferences.isEmpty()) {
                return null;
            }
            return convertStringToArray(stringFromPreferences);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean adddns(Activity activity, String str) {
        String stringFromPreferences = getStringFromPreferences(activity, null, "dns");
        if (stringFromPreferences != null && !stringFromPreferences.isEmpty() && stringFromPreferences.contains(str)) {
            return false;
        }
        if (stringFromPreferences != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(stringFromPreferences);
            stringBuilder.append(",");
            stringBuilder.append(str);
            str = stringBuilder.toString();
        }
        return putStringInPreferences(activity, str, "dns");
    }

    public static String[] getdns(Activity activity) {
        try {
            String stringFromPreferences = getStringFromPreferences(activity, null, "dns");
            if (stringFromPreferences == null || stringFromPreferences.isEmpty()) {
                return null;
            }
            return convertStringToArray(stringFromPreferences);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addipconverter(Activity activity, String str) {
        String stringFromPreferences = getStringFromPreferences(activity, null, "ipconverter");
        if (stringFromPreferences != null && !stringFromPreferences.isEmpty() && stringFromPreferences.contains(str)) {
            return false;
        }
        if (stringFromPreferences != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(stringFromPreferences);
            stringBuilder.append(",");
            stringBuilder.append(str);
            str = stringBuilder.toString();
        }
        return putStringInPreferences(activity, str, "ipconverter");
    }

    public static String[] getipconverter(Activity activity) {
        try {
            String stringFromPreferences = getStringFromPreferences(activity, null, "ipconverter");
            if (stringFromPreferences == null || stringFromPreferences.isEmpty()) {
                return null;
            }
            return convertStringToArray(stringFromPreferences);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean putStringInPreferences(Activity activity, String str, String str2) {
        Editor edit = activity.getPreferences(0).edit();
        edit.putString(str2, str);
        edit.commit();
        return true;
    }

    private static String getStringFromPreferences(Activity activity, String str, String str2) {
        String string = activity.getPreferences(0).getString(str2, str);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getStringFromPreferences: ");
        stringBuilder.append(string);
        Log.i("TAG", stringBuilder.toString());
        return string;
    }

    private static String[] convertStringToArray(String str) {
        try {
            return str.split(",");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
