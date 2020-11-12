package com.abcd.wifimanager.routerlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.abcd.wifimanager.routerlogin.network.Host;
import com.abcd.wifimanager.routerlogin.network.Wireless;
import com.abcd.wifimanager.routerlogin.db.DatabaseHandler;
import java.io.IOException;

public class LanHostActivity extends BaseActivity {
    static final  boolean $assertionsDisabled = false;
    private Button block_wifi_button;
    private TextView brand;
    private Button button_id;
    private TextView deviceName;
    private TextView deviceOS;
    private ImageView device_logo;
    private TextView header;
    private Host host;
    private TextView ipAddress;
    private TextView macAddress;
    private DatabaseHandler macdb;
    private ImageView rename_action;
    String vendor = null;
    private Wireless wifi;

    class C04702 implements OnClickListener {
        C04702() {
        }

        public void onClick(View view) {
            if (LanHostActivity.this.host.getIp().equals(LanHostActivity.this.wifi.getGetWay()) || LanHostActivity.this.host.getIp().equals(LanHostActivity.this.wifi.getInternalMobileIpAddress())) {
                Toast.makeText(LanHostActivity.this, "", 0).show();
                return;
            }
            WhoUseWifiActivity.checkNotify = true;
            if (LanHostActivity.this.macdb.checkMacId(LanHostActivity.this.host.getMac())) {
                LanHostActivity.this.macdb.deleteMACId(LanHostActivity.this.host.getMac());
                LanHostActivity.this.button_id.setBackground(LanHostActivity.this.getResources().getDrawable(R.drawable.button_background));
                LanHostActivity.this.button_id.setText("Stranger");
                return;
            }
            LanHostActivity.this.macdb.addMACId(LanHostActivity.this.host.getMac());
            LanHostActivity.this.button_id.setBackground(LanHostActivity.this.getResources().getDrawable(R.drawable.known_button_background));
            LanHostActivity.this.button_id.setText("Known");
        }
    }

    class C04713 implements OnClickListener {
        C04713() {
        }

        public void onClick(View view) {
            LanHostActivity.this.startActivity(new Intent(LanHostActivity.this.getApplicationContext(), Router_Page.class));
        }
    }

    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            setContentView(R.layout.activity_lanhost);
            getBundle();
            setupToolBar();
            openDatabase();
            bindView();
            getVendorName();
            setupView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void openDatabase() {
        this.macdb = new DatabaseHandler(this);
    }

    private void bindView() {
        this.rename_action = (ImageView) findViewById(R.id.rename_icon);
        this.device_logo = (ImageView) findViewById(R.id.device_icon);
        this.header = (TextView) findViewById(R.id.header);
        this.deviceName = (TextView) findViewById(R.id.deviceName);
        this.deviceOS = (TextView) findViewById(R.id.deviceOS);
        this.brand = (TextView) findViewById(R.id.brand);
        this.ipAddress = (TextView) findViewById(R.id.ipAddress);
        this.macAddress = (TextView) findViewById(R.id.macAddress);
        this.button_id = (Button) findViewById(R.id.button_id);
        this.block_wifi_button = (Button) findViewById(R.id.block_wifi_button);
    }

    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.wifi = new Wireless(getApplicationContext());
            this.host = (Host) extras.get("HOST");
            if (this.host != null) {
            }
        }
    }

    private void getVendorName() {
        try {
            this.vendor = Host.getMacVendor(this.host.getMac(), this);
        } catch (Exception e) {
        }
    }

    private void setupView() {
        this.brand.setText(this.vendor);
        this.macAddress.setText(this.host.getMac());
        this.ipAddress.setText(this.host.getIp());
        if (this.host.getIp().equals(this.wifi.getGetWay()) || this.host.getIp().equals(this.wifi.getInternalMobileIpAddress()) || this.macdb.checkMacId(this.host.getMac())) {
            this.button_id.setBackground(getResources().getDrawable(R.drawable.known_button_background));
            this.button_id.setText("Known");
        } else {
            this.button_id.setBackground(getResources().getDrawable(R.drawable.button_background));
            this.button_id.setText("Stranger");
        }
        if (this.host.getIp().equals(new Wireless(getApplicationContext()).getGetWay())) {
            this.header.setText(getString(R.string.router));
            this.deviceName.setText(getString(R.string.router));
            this.toolbarTextView.setText(getString(R.string.router));
            this.device_logo.setImageResource(R.drawable.ic_router);
        } else {
            if (this.host.getIp().equals(new Wireless(getApplicationContext()).getInternalMobileIpAddress())) {
                this.header.setText(getString(R.string.your_device));
                this.deviceName.setText(getString(R.string.your_device));
                this.toolbarTextView.setText(getString(R.string.your_device));
            } else {
                if (this.vendor.length() > 15) {
                    this.vendor = this.vendor.substring(0, 20).concat("...");
                } else {
                    this.vendor = this.vendor;
                }
                this.header.setText(this.vendor);
                this.deviceName.setText(this.host.getHostname());
                this.toolbarTextView.setText(this.vendor);
            }
            try {
                this.device_logo.setImageResource(Host.getIconFromDevice(this.host.getMac(), this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final String str = this.vendor;
        this.rename_action.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    LanHostActivity.this.renameDialog(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.button_id.setOnClickListener(new C04702());
        this.block_wifi_button.setOnClickListener(new C04713());
    }

    private void renameDialog(final String str) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pdf_name_prompt_menu, null);
        final EditText editText = (EditText) inflate.findViewById(R.id.inputpdfname);
        editText.setHint(str);
        Builder builder = new Builder(this);
        builder.setView(inflate);
        builder.setCancelable(false);
        builder.setPositiveButton((CharSequence) "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ((InputMethodManager) LanHostActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(LanHostActivity.this.getApplicationContext(), "Name cannot be blank", 1).show();
                    return;
                }
                try {
                    Host.renameVendor(str, editText.getText().toString(), LanHostActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).setNeutralButton((CharSequence) "Cancel", null);
        builder.show();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
