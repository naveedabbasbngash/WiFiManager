package com.abcd.wifimanager.routerlogin;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abcd.wifimanager.routerlogin.utils.ConnectivityReceiver;
import com.abcd.wifimanager.routerlogin.utils.MyUtility;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import org.apache.commons.net.whois.WhoisClient;

public class WhoisActivity extends BaseActivity implements OnItemSelectedListener {
    ArrayAdapter<String> adapter;
    Button btn;
    boolean checkclick = false;
    Spinner dns;
    String host;
    AsyncTask initdb;
    ProgressBar progressBar;
    SharedPreferences f135s;
    AutoCompleteTextView f136tv;
    TextView tvResult;
    String typeSpinner;
    String[] whoisserver = new String[]{"Use default", "whois.nic.domain", "whois.eu", "whois.je", "whois.ax", "whois.ua", "whois.cat", "whois.aero", "whois.co.nl", "whois.dns.pt", "whois.ati.tn", "whois.ja.net", "whois.tld.ee", "whois.dns.be", "whois.dns.pl", "whois.dns.hr", "whois.pir.org", "whois.isi.edu", "whois.cira.ca", "whois.jprs.jp", "whois.vrx.net", "whois.iana.org", "whois.ripe.net", "whois.cctld.by", "whois.denic.de", "whois.rotld.ro", "whois.arnes.si", "whois.norid.no", "whois.isnic.is", "whois.tonic.to", "whois.cctld.uz", "whois.domain.kg", "whois.amnic.net", "whois.aunic.net", "whois.thnic.net", "whois.domerg.lt", "whois.sk-nic.sk", "whois.tcinet.ru", "whois.nic-se.se", "whois.ficora.fi", "whois.website.ws", "whois.domains.tl", "whois.iam.net.ma", "whois.srs.net.nz", "whois.restena.lu", "whois.registry.hm", "whois.kenic.or.ke", "whois.tznic.or.tz", "whois.isoc.org.il", "whois.aeda.net.ae", "whois.register.bg", WhoisClient.DEFAULT_HOST, "whois.afilias.info", "www.hknic.net.hk", "whois.mynic.net.my", "whois.netnames.net", "whois.educause.net", "whois.twnic.net.tw", "whois.audns.net.au", "whois.cnnic.net.cn", "whois.neulevel.biz", "whois.belizenic.bz", "whois.lydomains.com", "whois.adamsnames.tc", "whois.centralnic.com", "whois.inregistrypro.pro", "whois.verisign-grs.com", "whois2.afilias-grs.net", "whois.dk-hostmaster.dk", "whois.domainregistry.ie", "whois.domain-registry.nl", "whois.dotmobiregistry.net"};

    class C04901 implements OnKeyListener {
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            return false;
        }

        C04901() {
        }
    }

    class C04912 implements OnClickListener {
        C04912() {
        }

        @SuppressLint("ResourceType")
        public void onClick(View view) {
            try {
                WhoisActivity.this.checkclick = true;
                if (ConnectivityReceiver.isConnected()) {
                    ((InputMethodManager) WhoisActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(WhoisActivity.this.f136tv.getWindowToken(), 0);
                    WhoisActivity.this.host = WhoisActivity.this.f136tv.getText().toString();
                    if (Patterns.WEB_URL.matcher(WhoisActivity.this.host).matches()) {
                        try {
                            WhoisActivity.this.initdb = new initDb();
                            if (MyUtility.addWhois(WhoisActivity.this, WhoisActivity.this.host) && WhoisActivity.this.host != null) {
                                if (WhoisActivity.this.adapter != null) {
                                    WhoisActivity.this.adapter.add(WhoisActivity.this.host);
                                    WhoisActivity.this.adapter.notifyDataSetChanged();
                                } else {
                                    String[] whois = MyUtility.getWhois(WhoisActivity.this);
                                    if (whois != null) {
                                        WhoisActivity.this.adapter = new ArrayAdapter(WhoisActivity.this, 17367050, whois);
                                        WhoisActivity.this.f136tv.setAdapter(WhoisActivity.this.adapter);
                                    }
                                }
                            }
                            if (VERSION.SDK_INT >= 11) {
                                WhoisActivity.this.initdb.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
                                return;
                            } else {
                                WhoisActivity.this.initdb.execute(new Object[0]);
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    Toast.makeText(WhoisActivity.this, "Invalid URL or Host", 0).show();
                    return;
                }
                Toast.makeText(WhoisActivity.this, "No Internet Connection", 0).show();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class initDb extends AsyncTask<Object, Object, String> {
        ArrayList<String> finalList;
        String whoi;

        protected void onPreExecute() {
            try {
                WhoisActivity.this.progressBar.setVisibility(View.VISIBLE);
                WhoisActivity.this.progressBar.setIndeterminate(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(Object... objArr) {
            try {
                this.whoi = WhoisActivity.this.getWhois(WhoisActivity.this.host, WhoisActivity.this.typeSpinner);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.whoi;
        }

        protected void onPostExecute(String str) {
            try {
                WhoisActivity.this.progressBar.setVisibility(View.GONE);
                WhoisActivity.this.tvResult.setText(this.whoi);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @SuppressLint("ResourceType")
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_whois);
        try {
            this.toolbarTextView.setText("Whois");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.f136tv = (AutoCompleteTextView) findViewById(R.id.hostTxt);
            this.f136tv.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Limerick-Regular.ttf"));
            this.f136tv.setOnKeyListener(new C04901());
            this.progressBar = (ProgressBar) findViewById(R.id.progress);
            String[] whois = MyUtility.getWhois(this);
            if (whois != null) {
                this.adapter = new ArrayAdapter(this, 17367050, whois);
                this.f136tv.setAdapter(this.adapter);
            }
            this.tvResult = (TextView) findViewById(R.id.tvResult);
            this.btn = (Button) findViewById(R.id.btn);
            this.dns = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item, this.whoisserver);
            arrayAdapter.setDropDownViewResource(17367049);
            this.dns.setAdapter(arrayAdapter);
            this.dns.setOnItemSelectedListener(this);
            this.host = this.f136tv.getText().toString();
            this.btn.setOnClickListener(new C04912());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        try {
            this.typeSpinner = adapterView.getItemAtPosition(i).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWhois(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder("");
        WhoisClient whoisClient = new WhoisClient();
        try {
            if (VERSION.SDK_INT > 9) {
                StrictMode.setThreadPolicy(new Builder().permitAll().build());
                if (str2.equals("Use default")) {
                    str2 = WhoisClient.DEFAULT_HOST;
                }
                whoisClient.connect(str2);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("=");
                stringBuilder2.append(str);
                stringBuilder.append(whoisClient.query(stringBuilder2.toString()));
                whoisClient.disconnect();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void onStop() {
        super.onStop();
        try {
            this.progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
