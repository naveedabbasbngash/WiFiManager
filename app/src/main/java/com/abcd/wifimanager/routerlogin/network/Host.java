package com.abcd.wifimanager.routerlogin.network;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.abcd.wifimanager.routerlogin.R;
import com.abcd.wifimanager.routerlogin.db.Database;

import java.io.IOException;
import java.io.Serializable;

public class Host implements Serializable {
    private String hostname;
    private String f47ip;
    private String mac;

    public Host(String str, String str2) {
        this(null, str, str2);
    }

    public Host(String str, String str2, String str3) {
        this.hostname = str;
        this.f47ip = str2;
        this.mac = str3;
    }

    public String getHostname() {
        return this.hostname;
    }

    public Host setHostname(String str) {
        this.hostname = str;
        return this;
    }

    public static void renameVendor(String str, String str2, Context context) throws IOException, SQLiteException {
        parseMacId(str);
        new Database(context).openDatabase("vendors");
    }

    public String getIp() {
        return this.f47ip;
    }

    public String getMac() {
        return this.mac;
    }

    public static String getMacVendor(String str, Context context) throws IOException, SQLiteException {
        str = parseMacId(str);
        Database database = new Database(context);
        database.openDatabase("vendors");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getMacVendor: ");
        stringBuilder.append(str);
        Log.i("DATABAse", stringBuilder.toString());
        Cursor queryDatabase = database.queryDatabase("SELECT vendor FROM macvendor WHERE mac LIKE ?", new String[]{str});
        stringBuilder = new StringBuilder();
        stringBuilder.append("getMacVendor: ");
        stringBuilder.append(queryDatabase.getCount());
        Log.i("DATABAse", stringBuilder.toString());
        String string = queryDatabase.moveToFirst() ? queryDatabase.getString(queryDatabase.getColumnIndex("vendor")) : "Vendor not in database";
        queryDatabase.close();
        database.close();
        return string;
    }

    public static String getIconStringFromMac(String str, Context context) throws IOException, SQLiteException {
        str = parseMacId(str);
        String str2 = "device";
        Database database = new Database(context);
        database.openDatabase("vendors");
        Cursor queryDatabase = database.queryDatabase("SELECT type FROM macvendor WHERE mac LIKE ?", new String[]{str});
        if (queryDatabase.moveToFirst()) {
            str2 = queryDatabase.getString(queryDatabase.getColumnIndex("type"));
        }
        queryDatabase.close();
        database.close();
        return str2;
    }


    public static int getIconFromDevice(String arg2, Context arg3) throws IOException {
        int v3_1 = 0;
        arg2 = Host.getIconStringFromMac(arg2, arg3);
        Log.i("", "getIconFromDevice: " + arg2);
        boolean v3 = arg2.equals("computer");
        if (arg2.equals("apple")) {
            v3_1 = 4;
        }

        if (arg2.equals("phone")) {
            v3_1 = 0;
        }

        if (arg2.equals("playstation")) {
            v3_1 = 3;
        }

        if (arg2.equals("nintendo")) {
            v3_1 = 2;
        }

        if (arg2.equals("samsung")) {
            v3_1 = 5;
        }

        if (arg2.equals("device")) {
            v3_1 = 6;
        }

        switch (((int) v3_1)) {
            case 0: {
                return R.drawable.ic_smartphone;
            }
            case 1: {
                return R.drawable.ic_desktop;
            }
            case 2: {
                return R.drawable.ic_game_controller;
            }
            case 3: {
                return R.drawable.ic_play_station;
            }
            case 4: {
                return R.drawable.ic_apple;
            }
            case 5: {
                return R.drawable.ic_samsung;
            }
            case 6: {
                return R.drawable.ic_user;
            }
            default:
                return R.drawable.ic_user;
        }

    }

    public static String parseMacId(String str) {
        return str.substring(0, 8);
    }

    public static void updateDevicename(String str, Context context, String str2) throws IOException, SQLiteException {
        try {
            str = parseMacId(str);
            Database database = new Database(context);
            database.openDatabase("vendors");
            ContentValues contentValues = new ContentValues();
            contentValues.put("device_name", str2);
            database.updateQuery(contentValues, new String[]{str});
            database.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLiteException e2) {
            e2.printStackTrace();
        }
    }

    public static void getData(Context context, String str) throws IOException, SQLiteException {
        str = parseMacId(str);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getData:1 ");
        stringBuilder.append(str);
        Log.i("Data", stringBuilder.toString());
        Database database = new Database(context);
        database.openDatabase("vendors");
        Cursor queryDatabase = database.queryDatabase("SELECT * FROM macvendor", null);
        while (queryDatabase.moveToNext()) {
            String string = queryDatabase.getString(queryDatabase.getColumnIndex("mac"));
            String string2 = queryDatabase.getString(queryDatabase.getColumnIndex("device_name"));
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("getData: ");
            stringBuilder2.append(str);
            stringBuilder2.append("-->");
            stringBuilder2.append(string);
            stringBuilder2.append(string2);
            Log.i("Data", stringBuilder2.toString());
        }
        queryDatabase.close();
        database.close();
    }
}
