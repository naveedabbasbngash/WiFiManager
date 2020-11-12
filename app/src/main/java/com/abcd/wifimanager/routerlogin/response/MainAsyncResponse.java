package com.abcd.wifimanager.routerlogin.response;

import com.abcd.wifimanager.routerlogin.network.Host;

public interface MainAsyncResponse extends ErrorAsyncResponse {
    void processFinish(int i);

    void processFinish(Host host);

    void processFinish(String str);

    void processFinish(boolean z);
}
