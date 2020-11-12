package com.abcd.wifimanager.routerlogin.response;

import android.util.SparseArray;

interface LanHostAsyncResponse {
    void processFinish(int i);

    void processFinish(SparseArray<String> sparseArray);

    void processFinish(boolean z);
}
