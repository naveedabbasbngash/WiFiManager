package com.abcd.wifimanager.routerlogin.utils;

import android.net.TrafficStats;
import java.util.ArrayList;
import java.util.List;

public class RetrieveData {
    static long totalDownload;
    static long totalDownload_n;
    static long totalUpload;
    static long totalUpload_n;

    public static List<Long> findData() {
        List<Long> arrayList = new ArrayList();
        if (totalDownload == 0) {
            totalDownload = TrafficStats.getTotalRxBytes();
        }
        if (totalUpload == 0) {
            totalUpload = TrafficStats.getTotalTxBytes();
        }
        long totalRxBytes = TrafficStats.getTotalRxBytes();
        long j = totalRxBytes - totalDownload;
        long totalTxBytes = TrafficStats.getTotalTxBytes();
        long j2 = totalTxBytes - totalUpload;
        totalDownload = totalRxBytes;
        totalUpload = totalTxBytes;
        arrayList.add(Long.valueOf(j));
        arrayList.add(Long.valueOf(j2));
        return arrayList;
    }

    public static long getNotificationData() {
        if (totalDownload_n == 0) {
            totalDownload_n = TrafficStats.getTotalRxBytes();
        }
        if (totalUpload_n == 0) {
            totalUpload_n = TrafficStats.getTotalTxBytes();
        }
        long totalRxBytes = TrafficStats.getTotalRxBytes();
        long j = totalRxBytes - totalDownload_n;
        long totalTxBytes = TrafficStats.getTotalTxBytes();
        long j2 = totalTxBytes - totalUpload_n;
        totalDownload_n = totalRxBytes;
        totalUpload_n = totalTxBytes;
        return j + j2;
    }
}
