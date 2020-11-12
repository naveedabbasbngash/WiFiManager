package com.abcd.wifimanager.routerlogin;

import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.TextView;
import com.abcd.wifimanager.routerlogin.network.Wireless;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import org.jsoup.Jsoup;

public class WifiInfoActivity extends BaseActivity {
    TextView broadcat_adrs;
    TextView channel;
    TextView device_ip;
    TextView device_mac;
    TextView dns_adrs1;
    TextView dns_adrs2;
    TextView frequency;
    TextView gatway;
    TextView host;
    JsoupAsyncTask jsoupAsyncTask;
    TextView mac_adrs;
    TextView subnetmask;

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Boolean> {
        String f45ip;

        private JsoupAsyncTask() {
            this.f45ip = "";
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = 19)
        protected Boolean doInBackground(Void... voidArr) {
            try {
                this.f45ip = new OkHttpClient().newCall(new Builder().url("https://api.ipify.org/").build()).execute().body().string().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
            } catch (Exception unused) {
                try {
                    this.f45ip = Jsoup.connect("http://checkip.amazonaws.com/").ignoreContentType(true).get().select("body").text().trim();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            return null;  //DT Add return null
        }

        protected void onPostExecute(Boolean bool) {
            try {
                WifiInfoActivity.this.host.setText(this.f45ip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_wifi_info);
        this.toolbarTextView.setText("WiFi Router Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.host = (TextView) findViewById(R.id.host);
        this.gatway = (TextView) findViewById(R.id.getway);
        this.subnetmask = (TextView) findViewById(R.id.subnet_mask);
        this.mac_adrs = (TextView) findViewById(R.id.mac_adrs);
        this.dns_adrs1 = (TextView) findViewById(R.id.dns_adrs1);
        this.dns_adrs2 = (TextView) findViewById(R.id.dns_adrs2);
        this.broadcat_adrs = (TextView) findViewById(R.id.brodcast_adrs);
        this.frequency = (TextView) findViewById(R.id.frequency);
        this.channel = (TextView) findViewById(R.id.channel);
        this.device_ip = (TextView) findViewById(R.id.device_ip);
        this.device_mac = (TextView) findViewById(R.id.device_mac_adrs);
        this.jsoupAsyncTask = new JsoupAsyncTask();
        if (VERSION.SDK_INT >= 11) {
            this.jsoupAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        } else {
            this.jsoupAsyncTask.execute(new Void[0]);
        }
        setDetails();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setDetails() {
        Wireless wireless = new Wireless(this);
        if (wireless.isEnabled() && wireless.isConnectedWifi()) {
            try {
                this.host.setText("fetching....");
                String valueOf = String.valueOf(wireless.getWifiInfo().getFrequency());
                this.gatway.setText(wireless.getGetWay());
                this.subnetmask.setText(String.valueOf(intToIp(wireless.getWifiManager().getDhcpInfo().netmask)));
                this.mac_adrs.setText(String.valueOf(wireless.getBSSID()));
                this.dns_adrs1.setText(String.valueOf(intToIp(wireless.getWifiManager().getDhcpInfo().dns1)));
                this.dns_adrs2.setText(String.valueOf(intToIp(wireless.getWifiManager().getDhcpInfo().dns2)));
                this.broadcat_adrs.setText(String.valueOf(getBroadcastAddress()));
                this.frequency.setText(valueOf.concat("MHz"));
                this.channel.setText(frequencyToChannel(valueOf));
                this.device_ip.setText(wireless.getInternalMobileIpAddress());
                this.device_mac.setText(wireless.getMacAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String intToIp(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i & 255);
        stringBuilder.append(".");
        stringBuilder.append((i >> 8) & 255);
        stringBuilder.append(".");
        stringBuilder.append((i >> 16) & 255);
        stringBuilder.append(".");
        stringBuilder.append((i >> 24) & 255);
        return stringBuilder.toString();
    }

    public InetAddress getBroadcastAddress() throws UnknownHostException {
        DhcpInfo dhcpInfo = ((WifiManager) getApplicationContext().getSystemService("wifi")).getDhcpInfo();
        if (dhcpInfo != null) {
            int i = (dhcpInfo.netmask ^ -1) | (dhcpInfo.ipAddress & dhcpInfo.netmask);
            byte[] bArr = new byte[4];
            for (int i2 = 0; i2 < 4; i2++) {
                bArr[i2] = (byte) ((i >> (i2 * 8)) & 255);
            }
            return InetAddress.getByAddress(bArr);
        }
        throw new UnknownHostException();
    }

    String frequencyToChannel(String str) {
        int obj;
        switch (str.hashCode()) {
            case 1541091:
                if (str.equals("2412")) {
                    obj = 0;
                    break;
                }
            case 1541096:
                if (str.equals("2417")) {
                    obj = 1;
                    break;
                }
            case 1541122:
                if (str.equals("2422")) {
                    obj = 2;
                    break;
                }
            case 1541127:
                if (str.equals("2427")) {
                    obj = 3;
                    break;
                }
            case 1541153:
                if (str.equals("2432")) {
                    obj = 4;
                    break;
                }
            case 1541158:
                if (str.equals("2437")) {
                    obj = 5;
                    break;
                }
            case 1541184:
                if (str.equals("2442")) {
                    obj = 6;
                    break;
                }
            case 1541189:
                if (str.equals("2447")) {
                    obj = 7;
                    break;
                }
            case 1541215:
                if (str.equals("2452")) {
                    obj = 8;
                    break;
                }
            case 1541220:
                if (str.equals("2457")) {
                    obj = 9;
                    break;
                }
            case 1541246:
                if (str.equals("2462")) {
                    obj = 10;
                    break;
                }
            case 1541251:
                if (str.equals("2467")) {
                    obj = 11;
                    break;
                }
            case 1541277:
                if (str.equals("2472")) {
                    obj = 12;
                    break;
                }
            case 1541310:
                if (str.equals("2484")) {
                    obj = 13;
                    break;
                }
            case 1605481:
                if (str.equals("4915")) {
                    obj = 49;
                    break;
                }
            case 1605507:
                if (str.equals("4920")) {
                    obj = 50;
                    break;
                }
            case 1605512:
                if (str.equals("4925")) {
                    obj = 51;
                    break;
                }
            case 1605543:
                if (str.equals("4935")) {
                    obj = 52;
                    break;
                }
            case 1605569:
                if (str.equals("4940")) {
                    obj = 53;
                    break;
                }
            case 1605574:
                if (str.equals("4945")) {
                    obj = 54;
                    break;
                }
            case 1605631:
                if (str.equals("4960")) {
                    obj = 55;
                    break;
                }
            case 1605693:
                if (str.equals("4980")) {
                    obj = 56;
                    break;
                }
            case 1626685:
                if (str.equals("5035")) {
                    obj = 14;
                    break;
                }
            case 1626711:
                if (str.equals("5040")) {
                    obj = 15;
                    break;
                }
            case 1626716:
                if (str.equals("5045")) {
                    obj = 16;
                    break;
                }
            case 1626747:
                if (str.equals("5055")) {
                    obj = 17;
                    break;
                }
            case 1626773:
                if (str.equals("5060")) {
                    obj = 18;
                    break;
                }
            case 1626835:
                if (str.equals("5080")) {
                    obj = 19;
                    break;
                }
            case 1627765:
                if (str.equals("5170")) {
                    obj = 20;
                    break;
                }
            case 1627796:
                if (str.equals("5180")) {
                    obj = 21;
                    break;
                }
            case 1627827:
                if (str.equals("5190")) {
                    obj = 22;
                    break;
                }
            case 1628509:
                if (str.equals("5200")) {
                    obj = 23;
                    break;
                }
            case 1628540:
                if (str.equals("5210")) {
                    obj = 24;
                    break;
                }
            case 1628571:
                if (str.equals("5220")) {
                    obj = 25;
                    break;
                }
            case 1628602:
                if (str.equals("5230")) {
                    obj = 26;
                    break;
                }
            case 1628633:
                if (str.equals("5240")) {
                    obj = 27;
                    break;
                }
            case 1628695:
                if (str.equals("5260")) {
                    obj = 28;
                    break;
                }
            case 1628757:
                if (str.equals("5280")) {
                    obj = 29;
                    break;
                }
            case 1629470:
                if (str.equals("5300")) {
                    obj = 30;
                    break;
                }
            case 1629532:
                if (str.equals("5320")) {
                    obj = 31;
                    break;
                }
            case 1631392:
                if (str.equals("5500")) {
                    obj = 32;
                    break;
                }
            case 1631454:
                if (str.equals("5520")) {
                    obj = 33;
                    break;
                }
            case 1631516:
                if (str.equals("5540")) {
                    obj = 34;
                    break;
                }
            case 1631578:
                if (str.equals("5560")) {
                    obj = 35;
                    break;
                }
            case 1631640:
                if (str.equals("5580")) {
                    obj = 36;
                    break;
                }
            case 1632353:
                if (str.equals("5600")) {
                    obj = 37;
                    break;
                }
            case 1632415:
                if (str.equals("5620")) {
                    obj = 38;
                    break;
                }
            case 1632477:
                if (str.equals("5640")) {
                    obj = 39;
                    break;
                }
            case 1632539:
                if (str.equals("5660")) {
                    obj = 40;
                    break;
                }
            case 1632601:
                if (str.equals("5680")) {
                    obj = 41;
                    break;
                }
            case 1633314:
                if (str.equals("5700")) {
                    obj = 42;
                    break;
                }
            case 1633376:
                if (str.equals("5720")) {
                    obj = 43;
                    break;
                }
            case 1633443:
                if (str.equals("5745")) {
                    obj = 44;
                    break;
                }
            case 1633505:
                if (str.equals("5765")) {
                    obj = 45;
                    break;
                }
            case 1633567:
                if (str.equals("5785")) {
                    obj = 46;
                    break;
                }
            case 1634280:
                if (str.equals("5805")) {
                    obj = 47;
                    break;
                }
            case 1634342:
                if (str.equals("5825")) {
                    obj = 48;
                    break;
                }
            default:
                obj = -1;
                break;
        }
        switch (obj) {
            case 0:
                return "ch 1 - 2.4ghz";
            case 1:
                return "ch 2 - 2.4ghz";
            case 2:
                return "ch 3 - 2.4ghz";
            case 3:
                return "ch 4 - 2.4ghz";
            case 4:
                return "ch 5 - 2.4ghz";
            case 5:
                return "ch 6 - 2.4ghz";
            case 6:
                return "ch 7 - 2.4ghz";
            case 7:
                return "ch 8 - 2.4ghz";
            case 8:
                return "ch 9 - 2.4ghz";
            case 9:
                return "ch 10 - 2.4ghz";
            case 10:
                return "ch 11 - 2.4ghz";
            case 11:
                return "ch 12 - 2.4ghz";
            case 12:
                return "ch 13 - 2.4ghz";
            case 13:
                return "ch 14 - 2.4ghz";
            case 14:
                return "ch 7 - 5.0ghz";
            case 15:
                return "ch 8 - 5.0ghz";
            case 16:
                return "ch 9 - 5.0ghz";
            case 17:
                return "ch 11 - 5.0ghz";
            case 18:
                return "ch 12 - 5.0ghz";
            case 19:
                return "ch 16 - 5.0ghz";
            case 20:
                return "ch 34 - 5.0ghz";
            case 21:
                return "ch 36 - 5.0ghz";
            case 22:
                return "ch 38 - 5.0ghz";
            case 23:
                return "ch 40 - 5.0ghz";
            case 24:
                return "ch 42 - 5.0ghz";
            case 25:
                return "ch 44 - 5.0ghz";
            case 26:
                return "ch 46 - 5.0ghz";
            case 27:
                return "ch 48 - 5.0ghz";
            case 28:
                return "ch 52 - 5.0ghz";
            case 29:
                return "ch 56 - 5.0ghz";
            case 30:
                return "ch 60 - 5.0ghz";
            case 31:
                return "ch 64 - 5.0ghz";
            case 32:
                return "ch 100 - 5.0ghz";
            case 33:
                return "ch 104 - 5.0ghz";
            case 34:
                return "ch 108 - 5.0ghz";
            case 35:
                return "ch 112 - 5.0ghz";
            case 36:
                return "ch 116 - 5.0ghz";
            case 37:
                return "ch 120 - 5.0ghz";
            case 38:
                return "ch 124 - 5.0ghz";
            case 39:
                return "ch 128 - 5.0ghz";
            case 40:
                return "ch 132 - 5.0ghz";
            case 41:
                return "ch 136 - 5.0ghz";
            case 42:
                return "ch 140 - 5.0ghz";
            case 43:
                return "ch 144 - 5.0ghz";
            case 44:
                return "ch 149 - 5.0ghz";
            case 45:
                return "ch 153 - 5.0ghz";
            case 46:
                return "ch 157 - 5.0ghz";
            case 47:
                return "ch 161 - 5.0ghz";
            case 48:
                return "ch 165 - 5.0ghz";
            case 49:
                return "ch 183 - 5.0ghz";
            case 50:
                return "ch 184 - 5.0ghz";
            case 51:
                return "ch 185 - 5.0ghz";
            case 52:
                return "ch 187 - 5.0ghz";
            case 53:
                return "ch 188 - 5.0ghz";
            case 54:
                return "ch 189 - 5.0ghz";
            case 55:
                return "ch 192 - 5.0ghz";
            case 56:
                return "ch 196 - 5.0ghz";
            default:
                return "No channel";
        }
    }
}
