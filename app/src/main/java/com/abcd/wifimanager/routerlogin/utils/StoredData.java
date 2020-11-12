package com.abcd.wifimanager.routerlogin.utils;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public class StoredData {
    public static List<Long> downloadList = new ArrayList();
    public static long downloadSpeed = 0;
    public static boolean isSetData = false;
    public static List<Long> uploadList = new ArrayList();
    public static long uploadSpeed = 0;

    public static void setZero() {
        isSetData = true;
        for (int i = 0; i < HttpServletResponse.SC_MULTIPLE_CHOICES; i++) {
            downloadList.add(Long.valueOf(0));
            uploadList.add(Long.valueOf(0));
        }
    }
}
