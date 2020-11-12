package com.abcd.wifimanager.routerlogin.runnable;

import com.abcd.wifimanager.routerlogin.response.MainAsyncResponse;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class ScanHostsRunnable implements Runnable {
    private final WeakReference<MainAsyncResponse> delegate;
    private int start;
    private int stop;
    private int timeout;

    public ScanHostsRunnable(int i, int i2, int i3, WeakReference<MainAsyncResponse> weakReference) {
        this.start = i;
        this.stop = i2;
        this.timeout = i3;
        this.delegate = weakReference;
    }

    public void run() {
        MainAsyncResponse mainAsyncResponse;
        int i = this.start;
        while (i <= this.stop) {
            Socket socket = new Socket();
            try {
                socket.setTcpNoDelay(true);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            try {
                socket.connect(new InetSocketAddress(InetAddress.getByAddress(BigInteger.valueOf((long) i).toByteArray()), 7), this.timeout);
            } catch (IOException e) {
                e.printStackTrace();
            }
                    mainAsyncResponse = (MainAsyncResponse) this.delegate.get();
                    if (mainAsyncResponse == null) {
                        i++;
                    }
                    mainAsyncResponse.processFinish(1);
                    i++;
        }
    }
}
