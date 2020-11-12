package com.abcd.wifimanager.routerlogin;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.abcd.wifimanager.routerlogin.network.Wireless;
import com.abcd.wifimanager.routerlogin.utils.DataService;
import com.abcd.wifimanager.routerlogin.utils.StoredData;
import com.onesignal.OneSignal;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class HomeActivity extends BaseActivity implements OnClickListener {
    Button btn_browse1;
    Button btn_browse2;
    private long mBackPressed = 0;
    private TextView dSpeed;
    private Thread dataUpdate;
    DecimalFormat f132df = new DecimalFormat("#.##");
    private IntentFilter intentFilter = new IntentFilter();
    protected ArrayList<Float> mDownload;
    protected ArrayList<Float> mUpload;
    private BroadcastReceiver receiver = new C04651();
    CardView roter_setting_card;
    private TextView router_coonected_msg;
    Button router_info_btn;
    private TextView router_name;
    CardView router_password_card;
    ScrollView scrollView;
    private TextView uSpeed;
    private Handler vHandler = new Handler();
    CardView who_is_my_wifi_card;
    Wireless wifi;
    ImageView wifi_menu;

    class C04651 extends BroadcastReceiver {
        C04651() {
        }

        public void onReceive(Context context, Intent intent) {
            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
            if (networkInfo != null) {
                HomeActivity.this.getNetworkInfo(networkInfo);
            }
        }
    }

    class C04662 implements OnClickListener {
        C04662() {
        }

        public void onClick(View view) {
            HomeActivity.this.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
        }
    }

    class C04683 implements Runnable {

        class C04671 implements Runnable {
            C04671() {
            }

            public void run() {
                HomeActivity.this.setSpeed();
            }
        }

        C04683() {
        }

        public void run() {
            while (!HomeActivity.this.dataUpdate.getName().equals("stopped")) {
                HomeActivity.this.vHandler.post(new C04671());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getNetworkInfo(NetworkInfo networkInfo) {
        this.wifi = new Wireless(getApplicationContext());
        try {
            if (!this.wifi.isEnabled()) {
                this.router_name.setText(R.string.wifiDisabled);
                this.router_coonected_msg.setVisibility(8);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (networkInfo.isConnected()) {
            try {
                CharSequence ssid = this.wifi.getSSID();
                this.router_coonected_msg.setVisibility(0);
                this.router_name.setText(ssid);
                return;
            } catch (Exception unused) {
                return;
            }
        }
        this.router_name.setText(R.string.noWifiConnection);
        this.router_coonected_msg.setVisibility(8);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_home);
        this.scrollView = (ScrollView) findViewById(R.id.scrollView);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_action_menu);
        if (!DataService.service_status) {
            startService(new Intent(this, DataService.class));
        }
        this.router_info_btn = (Button) findViewById(R.id.router_info_btn);
        this.router_name = (TextView) findViewById(R.id.router_name);
        this.router_coonected_msg = (TextView) findViewById(R.id.router_connected_msg);
        this.dSpeed = (TextView) findViewById(R.id.text_download);
        this.uSpeed = (TextView) findViewById(R.id.text_upload);
        this.btn_browse1 = (Button) findViewById(R.id.btn_browse1);
        this.btn_browse2 = (Button) findViewById(R.id.btn_browse2);
        this.dSpeed.setText(" ");
        this.uSpeed.setText(" ");
        this.mDownload = new ArrayList();
        this.mUpload = new ArrayList();
        liveData();
        this.who_is_my_wifi_card = (CardView) findViewById(R.id.who_is_my_wifi);
        this.roter_setting_card = (CardView) findViewById(R.id.roter_setting);
        this.router_password_card = (CardView) findViewById(R.id.router_password);
        this.who_is_my_wifi_card.setOnClickListener(this);
        this.roter_setting_card.setOnClickListener(this);
        this.router_password_card.setOnClickListener(this);
        this.router_info_btn.setOnClickListener(this);
        this.btn_browse1.setOnClickListener(this);
        this.btn_browse2.setOnClickListener(this);
        this.leftDrawer.setDrawerListener(new ActionBarDrawerToggle(this, this.leftDrawer, this.toolbar, 0, 0));
        this.intentFilter.addAction("android.net.wifi.STATE_CHANGE");

        //Fabric
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

    }

    @SuppressLint("RestrictedApi")
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        this.wifi_menu = (ImageView) ((LinearLayout) menu.findItem(R.id.action_wifi).getActionView()).findViewById(R.id.action_wifi_icon);
        this.wifi_menu.setOnClickListener(new C04662());

        try {
            if (menu instanceof MenuBuilder) {
                MenuBuilder m = (MenuBuilder) menu;
                m.setOptionalIconsVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.action_wifi:

                startActivity(new Intent("android.settings.WIFI_SETTINGS"));

            case R.id.share:
                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setType("text/*");
                shareIntent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.app_name) + " Ip Tools - Network Analyzer\\nWhat is my IP, Whois, Network Tools, Network Analyzer, Network Utilities, Ping, LAN Scanner, Port Scanner, DNS Lookup, Port Scanner, IP Calculator, WiFi Analyzer etc." + "https://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(shareIntent, "Share App"));
                break;
            case R.id.rate:
                if (Glob.isOnline(HomeActivity.this)) {


                    try {
                        Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                        startActivity(marketIntent);
                    } catch (ActivityNotFoundException e) {
                        Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                        startActivity(marketIntent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.more:
                if (!Glob.isOnline(HomeActivity.this)) {
                    Toast.makeText(HomeActivity.this, "No Internet Connection..", 0).show();
                    break;
                }
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Glob.acc_link)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "You don't have Google Play installed", 1).show();
                }
                break;
            case R.id.privacy_policy:
                if (!Glob.isOnline(HomeActivity.this)) {
                    Toast.makeText(HomeActivity.this, "No Internet Connection..", 0).show();
                    break;
                }
                startActivity(new Intent(getApplicationContext(), PrivacyPolicyActivity.class));
                break;
        }
        return true;
//        return super.onOptionsItemSelected(menuItem);
    }

    public void liveData() {
        this.dataUpdate = new Thread(new C04683());
        this.dataUpdate.setName("started");
        this.dataUpdate.start();
    }

    public void setSpeed() {
        CharSequence charSequence = " ";
        CharSequence charSequence2 = " ";
        Long valueOf = Long.valueOf(StoredData.downloadSpeed);
        Long valueOf2 = Long.valueOf(StoredData.uploadSpeed);
        StringBuilder stringBuilder;
        if (valueOf.longValue() < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(valueOf);
            stringBuilder.append("\nB/s");
            charSequence = stringBuilder.toString();
        } else if (valueOf.longValue() < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.f132df.format(valueOf.longValue() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
            stringBuilder.append("\nKB/s");
            charSequence = stringBuilder.toString();
        } else if (valueOf.longValue() >= PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            stringBuilder = new StringBuilder();
            DecimalFormat decimalFormat = this.f132df;
            double longValue = (double) valueOf.longValue();
            Double.isNaN(longValue);
            stringBuilder.append(decimalFormat.format(longValue / 1048576.0d));
            stringBuilder.append("\nMB/s");
            charSequence = stringBuilder.toString();
        }
        StringBuilder stringBuilder2;
        if (valueOf2.longValue() < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(valueOf2);
            stringBuilder2.append("\nB/s");
            charSequence2 = stringBuilder2.toString();
        } else if (valueOf2.longValue() < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.f132df.format(valueOf2.longValue() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
            stringBuilder2.append("\nKB/s");
            charSequence2 = stringBuilder2.toString();
        } else if (valueOf2.longValue() >= PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            stringBuilder2 = new StringBuilder();
            DecimalFormat decimalFormat2 = this.f132df;
            double longValue2 = (double) valueOf2.longValue();
            Double.isNaN(longValue2);
            stringBuilder2.append(decimalFormat2.format(longValue2 / 1048576.0d));
            stringBuilder2.append("\nMB/s");
            charSequence2 = stringBuilder2.toString();
        }
        this.dSpeed.setText(charSequence);
        this.uSpeed.setText(charSequence2);
        this.dSpeed.setTextSize(18.0f);
        this.uSpeed.setTextSize(18.0f);
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(this.receiver);
        this.dataUpdate.setName("stopped");
    }

    public void onResume() {
        super.onResume();
        registerReceiver(this.receiver, this.intentFilter);
        DataService.notification_status = true;
        this.dataUpdate.setName("started");
        if (!this.dataUpdate.isAlive()) {
            liveData();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_browse1:
                uriparse("https://play.google.com/store/apps/details?id=com.whois.dnslookup.networkiptools");
                return;
            case R.id.btn_browse2:
                uriparse("https://play.google.com/store/apps/details?id=com.freewifi.freewifiinternet");
                return;
            case R.id.roter_setting:
                startActivity(new Intent(this, Router_Page.class));
                if (controller != null) {
                    controller.showFullScreenAd();
                }
                return;
            case R.id.router_info_btn:
                startActivity(new Intent(this, WifiInfoActivity.class));
                if (controller != null) {
                    controller.showFullScreenAd();
                }
                return;
            case R.id.router_password:
                startActivity(new Intent(this, Router_password.class));
                if (controller != null) {
                    controller.showFullScreenAd();
                }
                return;
            case R.id.who_is_my_wifi:
                startActivity(new Intent(this, WhoUseWifiActivity.class));
                if (controller != null) {
                    controller.showFullScreenAd();
                }
                return;
            default:
                return;
        }
    }

    @Override
    public void onBackPressed() {
        if (this.mBackPressed + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Snackbar.make(btn_browse1, "Press Again To Exit", -1).show();
        }
        this.mBackPressed = System.currentTimeMillis();
    }
}
