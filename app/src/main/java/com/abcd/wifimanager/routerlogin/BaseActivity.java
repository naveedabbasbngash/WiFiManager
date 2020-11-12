package com.abcd.wifimanager.routerlogin;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.abcd.wifimanager.AdViewController;
import com.abcd.wifimanager.routerlogin.adapter.DrawerItemCustomAdapter;
import com.abcd.wifimanager.routerlogin.adapter.ObjectDrawerItem;
import com.abcd.wifimanager.routerlogin.utils.Ad_Global;
import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    DrawerLayout leftDrawer;
    String playStoreUrl;
    Toolbar toolbar;
    TextView toolbarTextView;
    AdViewController controller;

    public boolean useToolbar() {
        return true;
    }
;
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        controller = new AdViewController(getApplicationContext());
    }

    public void setContentView(int i) {
        this.leftDrawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = (FrameLayout) this.leftDrawer.findViewById(R.id.main_frame);
        final RelativeLayout relativeLayout = (RelativeLayout) this.leftDrawer.findViewById(R.id.leftDrawerLayout);
        this.toolbarTextView = (TextView) this.leftDrawer.findViewById(R.id.toolbarTextView);
        this.toolbar = (Toolbar) this.leftDrawer.findViewById(R.id.toolbar);
        this.toolbar.setTitle((CharSequence) "");
        setSupportActionBar(this.toolbar);
        this.toolbarTextView.setText(R.string.app_name);
        if (!useToolbar()) {
            this.toolbar.setVisibility(View.GONE);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://play.google.com/store/apps/details?id=");
        stringBuilder.append(getPackageName());
        this.playStoreUrl = stringBuilder.toString();
        Ad_Global.loadBannerAd(this, (RelativeLayout) this.leftDrawer.findViewById(R.id.ad_View));
        List arrayList = new ArrayList();
        arrayList.add(new ObjectDrawerItem("Tools", 17170445));
        arrayList.add(new ObjectDrawerItem(getString(R.string.nav_wifi_list), R.drawable.wifi_list));
        arrayList.add(new ObjectDrawerItem(getString(R.string.nav_ping), R.drawable.ping));
        arrayList.add(new ObjectDrawerItem(getString(R.string.nav_whois), R.drawable.whois));
        ListAdapter drawerItemCustomAdapter = new DrawerItemCustomAdapter(this, R.layout.listview_drawer_item_row, arrayList);
        ListView listView = (ListView) this.leftDrawer.findViewById(R.id.upperLeftDrawerList);
        listView.setAdapter(drawerItemCustomAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                switch (i) {
                    case 1:
                        BaseActivity.this.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
                        break;
                    case 2:
                        BaseActivity.this.startActivity(new Intent(BaseActivity.this.getApplicationContext(), PingActivity.class));
                        if (controller != null) {
                            controller.showFullScreenAd();
                        }
                        break;
                    case 3:
                        BaseActivity.this.startActivity(new Intent(BaseActivity.this.getApplicationContext(), WhoisActivity.class));
                        if (controller != null) {
                            controller.showFullScreenAd();
                        }
                        break;
                    default:
                        break;
                }
                BaseActivity.this.leftDrawer.closeDrawer(relativeLayout);
            }
        });
        getLayoutInflater().inflate(i, frameLayout, true);
        super.setContentView(this.leftDrawer);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }

    @SuppressLint("WrongConstant")
    public void uriparse(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.addFlags(1208483840);
        try {
            startActivityForResult(intent, 9);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
