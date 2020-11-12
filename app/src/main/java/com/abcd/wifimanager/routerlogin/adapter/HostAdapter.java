package com.abcd.wifimanager.routerlogin.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.abcd.wifimanager.routerlogin.R;
import com.abcd.wifimanager.routerlogin.LanHostActivity;
import com.abcd.wifimanager.routerlogin.network.Host;
import com.abcd.wifimanager.routerlogin.network.Wireless;
import com.abcd.wifimanager.routerlogin.db.DatabaseHandler;
import java.util.List;

public class HostAdapter extends Adapter<HostAdapter.HostViewHolder> {
    boolean check;
    Context context;
    private final List<Host> data;
    DatabaseHandler f122db;

    public static class HostViewHolder extends ViewHolder implements OnClickListener {
        private Button button_id;
        private ImageView device_icon;
        private TextView hostIp;
        private TextView hostMac;
        private TextView hostMacVendor;
        private RelativeLayout root;

        public void onClick(View view) {
        }

        public HostViewHolder(View view) {
            super(view);
            this.device_icon = (ImageView) view.findViewById(R.id.device_icon);
            this.root = (RelativeLayout) view.findViewById(R.id.root);
            this.hostIp = (TextView) view.findViewById(R.id.hostIp);
            this.hostMac = (TextView) view.findViewById(R.id.hostMac);
            this.hostMacVendor = (TextView) view.findViewById(R.id.hostMacVendor);
            this.button_id = (Button) view.findViewById(R.id.button_id);
        }
    }

    public HostAdapter(Context context, List<Host> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HostAdapter: ");
        stringBuilder.append(list.size());
        Log.i("Size", stringBuilder.toString());
        this.data = list;
        this.context = context;
        this.f122db = new DatabaseHandler(context);
    }

    public HostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new HostViewHolder(LayoutInflater.from(this.context).inflate(R.layout.host_list_item, null));
    }

    public void onBindViewHolder(final HostViewHolder hostViewHolder, int i) {
        try {
            final Host host = (Host) this.data.get(i);
            TextView access$000 = hostViewHolder.hostIp;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IP : ");
            stringBuilder.append(host.getIp());
            access$000.setText(stringBuilder.toString());
            access$000 = hostViewHolder.hostMac;
            stringBuilder = new StringBuilder();
            stringBuilder.append("MAC : ");
            stringBuilder.append(host.getMac());
            access$000.setText(stringBuilder.toString());
            if (host.getIp().equals(new Wireless(this.context).getGetWay())) {
                hostViewHolder.hostMacVendor.setText(this.context.getString(R.string.router));
                hostViewHolder.device_icon.setImageResource(R.drawable.ic_router);
            } else {
                try {
                    if (host.getIp().equals(new Wireless(this.context).getInternalMobileIpAddress())) {
                        hostViewHolder.hostMacVendor.setText(this.context.getString(R.string.your_device));
                    } else {
                        hostViewHolder.hostMacVendor.setText(Host.getMacVendor(host.getMac(), this.context));
                    }
                    hostViewHolder.device_icon.setImageResource(Host.getIconFromDevice(host.getMac(), this.context));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (host.getIp().equals(new Wireless(this.context).getGetWay()) || host.getIp().equals(new Wireless(this.context).getInternalMobileIpAddress()) || this.f122db.checkMacId(host.getMac())) {
                hostViewHolder.button_id.setBackground(this.context.getResources().getDrawable(R.drawable.known_button_background));
                hostViewHolder.button_id.setText("Known");
            } else {
                hostViewHolder.button_id.setBackground(this.context.getResources().getDrawable(R.drawable.button_background));
                hostViewHolder.button_id.setText("Stranger");
            }
            hostViewHolder.root.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(HostAdapter.this.context, LanHostActivity.class);
                    intent.putExtra("HOST", host);
                    HostAdapter.this.context.startActivity(intent);
                }
            });
            hostViewHolder.button_id.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!host.getIp().equals(new Wireless(HostAdapter.this.context).getGetWay()) && !host.getIp().equals(new Wireless(HostAdapter.this.context).getInternalMobileIpAddress())) {
                        if (HostAdapter.this.f122db.checkMacId(host.getMac())) {
                            HostAdapter.this.f122db.deleteMACId(host.getMac());
                            hostViewHolder.button_id.setBackground(HostAdapter.this.context.getResources().getDrawable(R.drawable.button_background));
                            hostViewHolder.button_id.setText("Stranger");
                            return;
                        }
                        HostAdapter.this.f122db.addMACId(host.getMac());
                        hostViewHolder.button_id.setBackground(HostAdapter.this.context.getResources().getDrawable(R.drawable.known_button_background));
                        hostViewHolder.button_id.setText("Known");
                    }
                }
            });
        } catch (NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.data.size();
    }
}
