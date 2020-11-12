package com.abcd.wifimanager.routerlogin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.abcd.wifimanager.routerlogin.adapter.HostAdapter;
import com.abcd.wifimanager.routerlogin.network.Discovery;
import com.abcd.wifimanager.routerlogin.network.Host;
import com.abcd.wifimanager.routerlogin.network.Wireless;
import com.abcd.wifimanager.routerlogin.db.DatabaseHandler;
import com.abcd.wifimanager.routerlogin.response.MainAsyncResponse;
import com.abcd.wifimanager.routerlogin.utils.DividerItemDecoration;
import com.abcd.wifimanager.routerlogin.utils.Errors;
import com.daimajia.numberprogressbar.NumberProgressBar;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTPReply;

public class WhoUseWifiActivity extends BaseActivity implements MainAsyncResponse {
    public static boolean checkNotify = false;
    boolean buttonenable = true;
    private DatabaseHandler f134db;
    private TextView getway_name_tv;
    Host host;
    private HostAdapter hostAdapter;
    private int hostSocketTimeout = FTPReply.FILE_STATUS_OK;
    private List<Host> knownhost = new ArrayList();
    private HostAdapter knownhostAdapter;
    private RecyclerView knownhostList;
    private TextView no_of_host;
    private NumberProgressBar progressbar;
    private Handler scanHandler;
    private Button scanhost;
    private Handler signalHandler = new Handler();
    private List<Host> strangerhost = new ArrayList();
    private RecyclerView strangerhostList;
    private TextView textview_known;
    private TextView textview_stranger;
    private Wireless wifi;

    class C04861 implements OnClickListener {
        C04861() {
        }

        @SuppressLint("WrongConstant")
        public void onClick(View view) {
            if (WhoUseWifiActivity.this.buttonenable) {
                WhoUseWifiActivity.this.scanHost();
            } else {
                Toast.makeText(WhoUseWifiActivity.this.getApplicationContext(), "Scanning is in progress...", 0).show();
            }
        }
    }

    public void processFinish(String str) {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            setContentView(R.layout.activity_who_use_wifi);
            this.toolbarTextView.setText(R.string.main_screen_whois);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.f134db = new DatabaseHandler(getApplicationContext());
            this.strangerhostList = (RecyclerView) findViewById(R.id.unknown_list_recyclerview);
            this.knownhostList = (RecyclerView) findViewById(R.id.known_list_recyclerview);
            this.no_of_host = (TextView) findViewById(R.id.no_of_host);
            this.getway_name_tv = (TextView) findViewById(R.id.getway_name);
            this.textview_stranger = (TextView) findViewById(R.id.textview_stranger);
            this.textview_known = (TextView) findViewById(R.id.textview_known);
            this.scanhost = (Button) findViewById(R.id.scanhost);
            this.wifi = new Wireless(getApplicationContext());
            this.scanHandler = new Handler(Looper.getMainLooper());
            setupHostsAdapter();
            setupHostDiscovery();
            scanHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupHostsAdapter() {
        try {
            TextView textView = this.no_of_host;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.strangerhost.size() + this.knownhost.size());
            stringBuilder.append(" Devices on this WIFI");
            textView.setText(stringBuilder.toString());
            this.strangerhostList.setLayoutManager(new LinearLayoutManager(this));
            this.strangerhostList.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decorator)));
            this.strangerhostList.setNestedScrollingEnabled(false);
            this.strangerhostList.setHasFixedSize(false);
            this.knownhostList.setLayoutManager(new LinearLayoutManager(this));
            this.knownhostList.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decorator)));
            this.knownhostList.setNestedScrollingEnabled(false);
            this.knownhostList.setHasFixedSize(false);
            this.hostAdapter = new HostAdapter(this, this.strangerhost);
            this.knownhostAdapter = new HostAdapter(this, this.knownhost);
            this.strangerhostList.setAdapter(this.hostAdapter);
            this.knownhostList.setAdapter(this.knownhostAdapter);
            setVisiblity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    private void setVisiblity() {
        int size = this.strangerhost.size() + this.knownhost.size();
        TextView textView = this.no_of_host;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(size);
        stringBuilder.append(" Devices on this WIFI");
        textView.setText(stringBuilder.toString());
        if (this.knownhost.size() == 0) {
            this.textview_known.setVisibility(8);
        } else {
            this.textview_known.setVisibility(0);
        }
        if (this.strangerhost.size() == 0) {
            this.textview_stranger.setVisibility(8);
        } else {
            this.textview_stranger.setVisibility(0);
        }
    }

    private void setupHostDiscovery() {
        this.scanhost.setOnClickListener(new C04861());
    }

    private void scanHost() {
        this.buttonenable = false;
        if (!this.wifi.isEnabled()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.wifiDisabled), 0).show();
        } else if (this.wifi.isConnectedWifi()) {
            this.strangerhost.clear();
            this.knownhost.clear();
            this.hostAdapter.notifyDataSetChanged();
            this.knownhostAdapter.notifyDataSetChanged();
            this.progressbar = (NumberProgressBar) findViewById(R.id.progressbar);
            this.progressbar.setMax(this.wifi.getNumberOfHostsInWifiSubnet());
            this.progressbar.setProgress(0);
            this.getway_name_tv.setText(this.wifi.getSSID());
            try {
                Discovery.scanHosts(((Integer) this.wifi.getInternalWifiIpAddress(Integer.class)).intValue(), this.wifi.getInternalWifiSubnet(), this.hostSocketTimeout, this);
            } catch (UnknownHostException unused) {
                Errors.showError(getApplicationContext(), getResources().getString(R.string.notConnectedWifi));
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.notConnectedWifi), 0).show();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.signalHandler.removeCallbacksAndMessages(null);
    }

    public void onRestart() {
        super.onRestart();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public <T extends Throwable> void processFinish(final T t) {
        this.scanHandler.post(new Runnable() {
            public void run() {
                Errors.showError(WhoUseWifiActivity.this.getApplicationContext(), t.getLocalizedMessage());
            }
        });
    }

    public void processFinish(final Host host) {
        try {
            this.scanHandler.post(new Runnable() {
                public void run() {
                    try {
                        if (host.getIp().equalsIgnoreCase(WhoUseWifiActivity.this.wifi.getGetWay())) {
                            Wireless wireless = new Wireless(WhoUseWifiActivity.this.getApplicationContext());
                            WhoUseWifiActivity.this.host = new Host(wireless.getInternalMobileIpAddress(), wireless.getMacAddress());
                            WhoUseWifiActivity.this.knownhost.add(WhoUseWifiActivity.this.host);
                            WhoUseWifiActivity.this.knownhost.add(host);
                        } else if (WhoUseWifiActivity.this.f134db.checkMacId(host.getMac())) {
                            Log.i("sizeadapter", "run: ");
                            WhoUseWifiActivity.this.knownhost.add(host);
                        } else {
                            WhoUseWifiActivity.this.strangerhost.add(host);
                        }
                        int size = WhoUseWifiActivity.this.strangerhost.size() + WhoUseWifiActivity.this.knownhost.size();
                        WhoUseWifiActivity.this.setVisiblity();
                        TextView access$600 = WhoUseWifiActivity.this.no_of_host;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(size);
                        stringBuilder.append(" Devices on this WIFI");
                        access$600.setText(stringBuilder.toString());
                        WhoUseWifiActivity.this.hostAdapter.notifyDataSetChanged();
                        WhoUseWifiActivity.this.knownhostAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processFinish(final int i) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (WhoUseWifiActivity.this.progressbar != null) {
                    WhoUseWifiActivity.this.progressbar.incrementProgressBy(i);
                }
            }
        });
    }

    public void processFinish(boolean z) {
        this.buttonenable = true;
    }

    protected void onResume() {
        super.onResume();
        if (checkNotify) {
            if (!(this.hostAdapter == null || this.knownhostAdapter == null)) {
                this.hostAdapter.notifyDataSetChanged();
                this.knownhostAdapter.notifyDataSetChanged();
            }
            checkNotify = false;
        }
    }

    protected void onPause() {
        super.onPause();
        checkNotify = false;
    }
}
