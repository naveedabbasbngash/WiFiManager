package com.abcd.wifimanager.routerlogin.response;

interface ErrorAsyncResponse {
    <T extends Throwable> void processFinish(T t);
}
