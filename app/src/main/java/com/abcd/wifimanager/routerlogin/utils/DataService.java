package com.abcd.wifimanager.routerlogin.utils;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.util.Log;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.json.JSONObject;

public class DataService extends Service {
    public static final String MONTH_DATA = "monthdata";
    public static final String TODAY_DATA = "todaydata";
    static int f48k = 0;
    static NotificationManager notificationManager = null;
    public static boolean notification_status = true;
    public static boolean service_status = false;
    Context context;
    Thread dataThread;

    final class MyThreadClass implements Runnable {
        int service_id;

        MyThreadClass(int i) {
            this.service_id = i;
        }

        public void run() {
            synchronized (this) {
                while (DataService.this.dataThread.getName() == "showNotification") {
                    DataService.this.getData();
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        SharedPreferences sharedPreferences = getSharedPreferences(TODAY_DATA, 0);
        if (!sharedPreferences.contains("today_date")) {
            Editor edit = sharedPreferences.edit();
            edit.putString("today_date", new SimpleDateFormat("MMM dd, yyyy").format(Calendar.getInstance().getTime()));
            edit.apply();
        }
        if (!service_status) {
            service_status = true;
            this.dataThread = new Thread(new MyThreadClass(i2));
            this.dataThread.setName("showNotification");
            this.dataThread.start();
            if (!StoredData.isSetData) {
                StoredData.setZero();
            }
        }
        return 1;
    }

    public void onDestroy() {
        super.onDestroy();
        service_status = false;
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void getData() {
        long j;
        String connectivityStatusString = NetworkUtil.getConnectivityStatusString(getApplicationContext());
        List findData = RetrieveData.findData();
        Long l = (Long) findData.get(0);
        Long l2 = (Long) findData.get(1);
        long longValue = l.longValue() + l2.longValue();
        storedData(l, l2);
        if (connectivityStatusString.equals("wifi_enabled")) {
            j = longValue;
            longValue = 0;
        } else if (connectivityStatusString.equals("mobile_enabled")) {
            j = 0;
        } else {
            j = 0;
            longValue = j;
        }
        String format = new SimpleDateFormat("MMM dd, yyyy").format(Calendar.getInstance().getTime());
        SharedPreferences sharedPreferences = getSharedPreferences(TODAY_DATA, 0);
        String string = sharedPreferences.getString("today_date", "empty");
        if (string.equals(format)) {
            long j2 = sharedPreferences.getLong("MOBILE_DATA", 0);
            long j3 = sharedPreferences.getLong("WIFI_DATA", 0);
            Editor edit = sharedPreferences.edit();
            edit.putLong("MOBILE_DATA", longValue + j2);
            edit.putLong("WIFI_DATA", j + j3);
            edit.apply();
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("WIFI_DATA", sharedPreferences.getLong("WIFI_DATA", 0));
            jSONObject.put("MOBILE_DATA", sharedPreferences.getLong("MOBILE_DATA", 0));
            Editor edit2 = getSharedPreferences(MONTH_DATA, 0).edit();
            edit2.putString(string, jSONObject.toString());
            edit2.apply();
            Editor edit3 = sharedPreferences.edit();
            edit3.clear();
            edit3.putString("today_date", format);
            edit3.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWifiMobileData() {
        String stringBuilder;
        String stringBuilder2;
        SharedPreferences sharedPreferences = getSharedPreferences(TODAY_DATA, 0);
        long j = sharedPreferences.getLong("MOBILE_DATA", 0);
        long j2 = sharedPreferences.getLong("WIFI_DATA", 0);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double d = (double) j2;
        Double.isNaN(d);
        d /= 1048576.0d;
        double d2 = (double) j;
        Double.isNaN(d2);
        d2 /= 1048576.0d;
        StringBuilder stringBuilder3;
        if (d < 1024.0d) {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Wifi: ");
            stringBuilder3.append(decimalFormat.format(d));
            stringBuilder3.append("MB  ");
            stringBuilder = stringBuilder3.toString();
        } else {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Wifi: ");
            stringBuilder3.append(decimalFormat.format(d / 1024.0d));
            stringBuilder3.append("GB  ");
            stringBuilder = stringBuilder3.toString();
        }
        StringBuilder stringBuilder4;
        if (d2 < 1024.0d) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(" Mobile: ");
            stringBuilder4.append(decimalFormat.format(d2));
            stringBuilder4.append("MB");
            stringBuilder2 = stringBuilder4.toString();
        } else {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(" Mobile: ");
            stringBuilder4.append(decimalFormat.format(d2 / 1024.0d));
            stringBuilder4.append("GB");
            stringBuilder2 = stringBuilder4.toString();
        }
        StringBuilder stringBuilder5 = new StringBuilder();
        stringBuilder5.append(stringBuilder);
        stringBuilder5.append(stringBuilder2);
        return stringBuilder5.toString();
    }

    public void storedData(Long l, Long l2) {
        StoredData.downloadSpeed = l;
        StoredData.uploadSpeed = l2;
        if (StoredData.isSetData) {
            StoredData.downloadList.remove(0);
            StoredData.uploadList.remove(0);
            StoredData.downloadList.add(l);
            StoredData.uploadList.add(l2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("test ");
        toString();
        stringBuilder.append(String.valueOf(StoredData.downloadList.size()));
        Log.e("storeddata", stringBuilder.toString());
    }
}
