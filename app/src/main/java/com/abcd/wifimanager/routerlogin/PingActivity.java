package com.abcd.wifimanager.routerlogin;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.abcd.wifimanager.routerlogin.adapter.CustomPingAdapter;
import com.abcd.wifimanager.routerlogin.utils.ConnectivityReceiver;
import com.abcd.wifimanager.routerlogin.utils.MyUtility;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PingActivity extends BaseActivity {
    ArrayAdapter<String> adapter;
    com.facebook.ads.InterstitialAd fb_interstitial;
    AsyncTask asyncTask;
    boolean checkclick = false;
    int count = 5;
    int delay = 5;
    EditText edtcount;
    AutoCompleteTextView edthost;
    EditText edttime;
    Boolean flag = Boolean.valueOf(false);
    String host;
    /* renamed from: ms */
    ArrayList<String> f133ms;
    ListView pingListview;
    Process proc;
    ProgressBar progressBar;
    Button startBtn;
    TextView tvpingcount;
    TextView tvpinging;

    class C04731 implements OnClickListener {
        C04731() {
        }

        @SuppressLint({"WrongConstant", "ResourceType"})
        public void onClick(View view) {
            try {
                PingActivity.this.f133ms = new ArrayList();
                PingActivity.this.pingListview.setAdapter(null);
                if (ConnectivityReceiver.isConnected()) {
                    ((InputMethodManager) PingActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(PingActivity.this.edthost.getWindowToken(), 0);
                    PingActivity.this.host = PingActivity.this.edthost.getText().toString();
                    boolean matches = Patterns.WEB_URL.matcher(PingActivity.this.host).matches();
                    if (!matches) {
                        PingActivity.this.progressBar.setVisibility(8);
                        Toast.makeText(PingActivity.this, "Invalid URL or Host", 0).show();
                    }
                    String obj = PingActivity.this.edtcount.getText().toString();
                    String obj2 = PingActivity.this.edttime.getText().toString();
                    if (obj != null) {
                        try {
                            if (!obj.matches("")) {
                                PingActivity.this.count = Integer.parseInt(obj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!(obj2 == null || obj2.matches(""))) {
                        PingActivity.this.delay = Integer.parseInt(obj2);
                    }
                    TextView textView = PingActivity.this.tvpinging;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Pinging - ");
                    stringBuilder.append(PingActivity.this.host);
                    textView.setText(stringBuilder.toString());
                    textView = PingActivity.this.tvpingcount;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Count - ");
                    stringBuilder.append(PingActivity.this.count);
                    textView.setText(stringBuilder.toString());
                    PingActivity.this.asyncTask = new initDb();
                    if (PingActivity.this.host != null && matches) {
                        PingActivity.this.checkclick = true;
                        Log.i("host", "onClick: dss");
                        if (MyUtility.addping(PingActivity.this, PingActivity.this.host) && PingActivity.this.host != null) {
                            if (PingActivity.this.adapter != null) {
                                PingActivity.this.adapter.add(PingActivity.this.host);
                                PingActivity.this.adapter.notifyDataSetChanged();
                            } else {
                                String[] strArr = MyUtility.getping(PingActivity.this);
                                if (strArr != null) {
                                    PingActivity.this.adapter = new ArrayAdapter(PingActivity.this, 17367050, strArr);
                                    PingActivity.this.edthost.setAdapter(PingActivity.this.adapter);
                                }
                            }
                        }
                    }
                    if (VERSION.SDK_INT >= 11) {
                        PingActivity.this.asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
                        return;
                    } else {
                        PingActivity.this.asyncTask.execute(new Object[0]);
                        return;
                    }
                }
                Toast.makeText(PingActivity.this, "No Internet Connection", 0).show();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class initDb extends AsyncTask<Object, Object, Void> {
        ArrayList<String> finalList;

        protected void onPreExecute() {
            try {
                PingActivity.this.progressBar.setVisibility(0);
                PingActivity.this.progressBar.setIndeterminate(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @RequiresApi(api = 19)
        protected Void doInBackground(Object... objArr) {
            try {
                isCancelled();
                try {
                    PingActivity.this.doPing(PingActivity.this.count, PingActivity.this.delay, PingActivity.this.host);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        }

        @SuppressLint("WrongConstant")
        protected void onPostExecute(Void voidR) {
            try {
                PingActivity.this.progressBar.setVisibility(8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ping);
        this.edthost = (AutoCompleteTextView) findViewById(R.id.host);
        this.edthost.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Limerick-Regular.ttf"));
        this.edttime = (EditText) findViewById(R.id.edttime);
        this.edtcount = (EditText) findViewById(R.id.edtcount);
        this.pingListview = (ListView) findViewById(R.id.pingListview);
        this.startBtn = (Button) findViewById(R.id.startBtn);
        this.tvpinging = (TextView) findViewById(R.id.tvpinging);
        this.tvpingcount = (TextView) findViewById(R.id.tvpingcount);
        String[] strArr = MyUtility.getping(this);
        if (strArr != null) {
            this.adapter = new ArrayAdapter(this, 17367050, strArr);
            this.edthost.setAdapter(this.adapter);
        }
        this.toolbarTextView.setText("Ping");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.progressBar = (ProgressBar) findViewById(R.id.progress);
        this.startBtn.setOnClickListener(new C04731());
    }

    @RequiresApi(api = 19)
    public int doPing(int i, int i2, String str) {
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            try {
                if (getflag()) {
                    break;
                }
                try {
                    Thread.sleep((long) i2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (pingHost(str, i2) == 0) {
                    Log.i("TAG", "doPing: ");
                    i4++;
                }
                i3++;
            } catch (Exception e2) {
                Thread.currentThread().interrupt();
                e2.printStackTrace();
            }
        }
        return i4;
    }

    @RequiresApi(api = 19)
    public int pingHost(String str, int i) {
        Runtime runtime = Runtime.getRuntime();

        str = "ping -c 1 -W " + i / 1000 + " " + str;
        try {
            this.proc = runtime.exec(str);
        } catch (IOException v4) {
            v4.printStackTrace();
        }

        try {

            BufferedReader v4_1 = new BufferedReader(new InputStreamReader(this.proc.getInputStream()));
            StringBuilder v5 = new StringBuilder();
            while (true) {
                String v0_1 = null;
                try {
                    v0_1 = v4_1.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v("PINGGGi3", "pingHost_readLine::" + e.getMessage());
                }
                if (v0_1 == null) {
                    break;
                }

                v5.append(v0_1);
                v5.append('\n');
            }
            final String[] split = v5.toString().split("\\n");
            if (split.length > 1) {
                try {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            PingActivity.this.f133ms.add(0, split[1].toString());
                            CustomPingAdapter customPingAdapter = new CustomPingAdapter(PingActivity.this, PingActivity.this.f133ms);
                            PingActivity.this.pingListview.setAdapter(customPingAdapter);
                            customPingAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (/*IO*/Exception e3) {
            e3.printStackTrace();
        }
        try {
            this.proc.waitFor();
        } catch (InterruptedException e4) {
            e4.printStackTrace();
        }
        return this.proc.exitValue();
    }

    public void onBackPressed() {
        setflag();
        super.onBackPressed();
    }

    public synchronized void setflag() {
        this.flag = Boolean.valueOf(true);
    }

    public synchronized boolean getflag() {
        return this.flag.booleanValue();
    }
}
