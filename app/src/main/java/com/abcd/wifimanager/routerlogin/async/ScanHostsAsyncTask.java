package com.abcd.wifimanager.routerlogin.async;

import android.os.AsyncTask;

import com.abcd.wifimanager.routerlogin.network.Host;
import com.abcd.wifimanager.routerlogin.response.MainAsyncResponse;
import com.abcd.wifimanager.routerlogin.runnable.ScanHostsRunnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import jcifs.netbios.NbtAddress;

public class ScanHostsAsyncTask extends AsyncTask<Integer, Void, Void> {
    private static final String ARP_INACTIVE = "00:00:00:00:00:00";
    private static final String ARP_INCOMPLETE = "0x0";
    private static final String ARP_TABLE = "/proc/net/arp";
    private static final int NETBIOS_FILE_SERVER = 32;
    private final WeakReference<MainAsyncResponse> delegate;

    public ScanHostsAsyncTask(MainAsyncResponse mainAsyncResponse) {
        this.delegate = new WeakReference(mainAsyncResponse);
    }

    protected Void doInBackground(Integer... numArr) {
        int intValue = numArr[0];
        int intValue2 = numArr[1];
        int intValue3 = numArr[2];
        MainAsyncResponse mainAsyncResponse = (MainAsyncResponse) this.delegate.get();
        File file = new File(ARP_TABLE);
        if (file.exists() && file.canRead()) {
            ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
            double d = (double) intValue2;
            Double.isNaN(d);
            double d2 = 32.0d - d;
            intValue2 = 32 - intValue2;
            intValue = (intValue & ((-1 >> intValue2) << intValue2)) + 1;
            intValue2 = (int) d2;
            d2 = (double) (((int) Math.pow(2.0d, d2)) - 2);
            d = (double) intValue2;
            Double.isNaN(d2);
            Double.isNaN(d);
            int ceil = (int) Math.ceil(d2 / d);
            int i = (ceil - 2) + intValue;
            int i2 = intValue;
            for (intValue = 0; intValue < intValue2; intValue++) {
                newCachedThreadPool.execute(new ScanHostsRunnable(i2, i, intValue3, this.delegate));
                i2 = i + 1;
                i = (ceil - 1) + i2;
            }
            newCachedThreadPool.shutdown();
            try {
                newCachedThreadPool.awaitTermination(5, TimeUnit.MINUTES);
                newCachedThreadPool.shutdownNow();
                publishProgress(new Void[0]);
                return null;
            } catch (Throwable e) {
                mainAsyncResponse.processFinish(e);
                return null;
            }
        }
        mainAsyncResponse.processFinish(new FileNotFoundException("Unable to access device ARP table"));
        mainAsyncResponse.processFinish(false);
        return null;
    }

    protected final void onProgressUpdate(Void... voidArr) {
        Throwable e;
        Throwable th;
        MainAsyncResponse mainAsyncResponse = (MainAsyncResponse) this.delegate.get();
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(ARP_TABLE));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            bufferedReader.readLine();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        while (true) {
            String readLine = null;
            try {
                readLine = bufferedReader.readLine();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (readLine == null) {
                break;
            }
            String[] split = readLine.split("\\s+");
            final String str = split[0];
            String obj = split[2];
            readLine = split[3];
            if (!(ARP_INCOMPLETE.equals(obj) || ARP_INACTIVE.equals(readLine))) {
                final String finalReadLine = readLine;
                newCachedThreadPool.execute(new Runnable() {
                    public void run() {
                        Host host = new Host(str, finalReadLine);
                        MainAsyncResponse mainAsyncResponse = (MainAsyncResponse) ScanHostsAsyncTask.this.delegate.get();
                        try {
                            host.setHostname(InetAddress.getByName(str).getCanonicalHostName());
                        } catch (UnknownHostException e1) {
                            e1.printStackTrace();
                        }
                        if (mainAsyncResponse != null) {
                                mainAsyncResponse.processFinish(host);
                            }
                        try {
                            for (NbtAddress nbtAddress : NbtAddress.getAllByAddress(str)) {
                                if (nbtAddress.getNameType() == 32) {
                                    host.setHostname(nbtAddress.getHostName());
                                    return;
                                }
                            }
                        } catch (UnknownHostException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        }
        newCachedThreadPool.shutdown();
        if (mainAsyncResponse != null) {
            mainAsyncResponse.processFinish(true);
        }
        try {
            bufferedReader.close();
        } catch (IOException unused) {
        }
    }
}
