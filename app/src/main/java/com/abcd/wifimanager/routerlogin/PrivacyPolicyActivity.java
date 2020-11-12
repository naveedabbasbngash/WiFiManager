package com.abcd.wifimanager.routerlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_web);
        dialog = new ProgressDialog(PrivacyPolicyActivity.this);
        this.webView = (WebView) findViewById(R.id.wvPrivacyPolicy);
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                PrivacyPolicyActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(PrivacyPolicyActivity.this, description, 0).show();
            }
        });
        dialog.setMessage("Loading...Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        this.webView.loadUrl(Glob.privacy_link);
    }

    public void onBackPressed() {
        finish();
    }
}
