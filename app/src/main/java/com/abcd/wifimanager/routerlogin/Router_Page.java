package com.abcd.wifimanager.routerlogin;

import android.content.DialogInterface;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.abcd.wifimanager.routerlogin.network.Wireless;
import com.abcd.wifimanager.routerlogin.utils.Ad_Global;

public class Router_Page extends AppCompatActivity {
    String gateway;
    private String mCurrentUrl;
    ProgressBar progressBar;
    ImageView refresh;
    WebView webView;
    private Wireless wifi;

    class C04801 implements OnClickListener {
        C04801() {
        }

        public void onClick(View view) {
            Router_Page.this.loadUrl();
        }
    }

    private class CustomWebViewClient extends WebViewClient {
        public void onReceivedError(WebView webView, int i, String str, String str2) {
        }

        private CustomWebViewClient() {
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (Router_Page.this.mCurrentUrl == null || str == null || !str.equals(Router_Page.this.mCurrentUrl)) {
                webView.loadUrl(str);
                Router_Page.this.mCurrentUrl = str;
                return true;
            }
            Router_Page.this.webView.goBack();
            return true;
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            Router_Page.this.setTitle(webView.getTitle());
            Router_Page.this.progressBar.setVisibility(8);
            Router_Page.this.refresh.setVisibility(0);
        }

        public void onLoadResource(WebView webView, String str) {
            super.onLoadResource(webView, str);
        }
    }

    private class MyCustomizedWebClient extends WebViewClient {
        MyCustomizedWebClient(Router_Page router_Page, C04801 c04801) {
            this();
        }

        private MyCustomizedWebClient() {
        }

        public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
            Router_Page.this.showHttpAuthDialog(httpAuthHandler, str, str2, null, null, null);
        }

        public void onPageFinished(WebView webView, String str) {
            Router_Page.this.progressBar.setVisibility(8);
            super.onPageFinished(webView, str);
        }
    }

    class backInWB implements OnKeyListener {
        backInWB() {
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == 0) {
                WebView webView = (WebView) view;
                if (i == 4 && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
            }
            return false;
        }
    }

    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            setContentView((int) R.layout.activity_router_page);
            Ad_Global.loadBannerAd(this, (RelativeLayout) findViewById(R.id.ad_View));
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.wifi = new Wireless(getApplicationContext());
            this.progressBar = (ProgressBar) findViewById(R.id.progress);
            this.refresh = (ImageView) findViewById(R.id.refresh);
            this.webView = (WebView) findViewById(R.id.webview);
            loadUrl();
            this.refresh.setOnClickListener(new C04801());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadUrl() {
        try {
            ((ConnectivityManager) getApplicationContext().getSystemService("connectivity")).getNetworkInfo(1).isConnectedOrConnecting();
            if (!this.wifi.isConnectedWifi()) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.notConnectedWifi), 0).show();
                this.progressBar.setVisibility(8);
                this.refresh.setVisibility(0);
            } else if (this.wifi.isEnabled()) {
                try {
                    this.gateway = String.valueOf(Formatter.formatIpAddress(((WifiManager) getApplicationContext().getSystemService("wifi")).getDhcpInfo().gateway));
                    WebSettings settings = this.webView.getSettings();
                    settings.setJavaScriptEnabled(true);
                    settings.setLoadWithOverviewMode(true);
                    settings.setUseWideViewPort(true);
                    this.progressBar.setVisibility(0);
                    settings.setBuiltInZoomControls(true);
                    settings.setDisplayZoomControls(false);
                    settings.setJavaScriptCanOpenWindowsAutomatically(true);
                    this.webView.setOnKeyListener(new backInWB());
                    this.webView.setWebViewClient(new MyCustomizedWebClient(this, null));
                    WebView webView = this.webView;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("http://");
                    stringBuilder.append(this.gateway);
                    webView.loadUrl(stringBuilder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.wifiDisabled), 0).show();
                this.progressBar.setVisibility(8);
                this.refresh.setVisibility(0);
            }
        } catch (NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private void showHttpAuthDialog(final HttpAuthHandler httpAuthHandler, String str, String str2, String str3, String str4, String str5) {
        try {
            Builder builder = new Builder(this);
            View inflate = getLayoutInflater().inflate(R.layout.login_dialog, null);
            final EditText editText = (EditText) inflate.findViewById(R.id.login_user_name);
            final EditText editText2 = (EditText) inflate.findViewById(R.id.login_password);
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
            inputMethodManager.showSoftInput(editText, 1);
            inputMethodManager.showSoftInput(editText2, 1);
            final String str6 = str;
            final String str7 = str2;
            final String str8 = str4;
            final String str9 = str5;
            final HttpAuthHandler httpAuthHandler2 = httpAuthHandler;
            HttpAuthHandler httpAuthHandler3 = httpAuthHandler;
            builder.setView(inflate).setCancelable(false).setPositiveButton((CharSequence) "ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    String obj = editText.getText().toString();
                    String obj2 = editText2.getText().toString();
                    Router_Page.this.webView.setHttpAuthUsernamePassword(str6, str7, str8, str9);
                    httpAuthHandler2.proceed(obj, obj2);
                    Router_Page.this.progressBar.setVisibility(0);
                }
            }).setNegativeButton((CharSequence) "cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    httpAuthHandler.cancel();
                }
            }).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        try {
            if (this.webView == null) {
                return;
            }
            if (this.webView.canGoBack()) {
                this.webView.goBack();
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
