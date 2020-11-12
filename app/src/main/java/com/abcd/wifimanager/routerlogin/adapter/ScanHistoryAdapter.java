package com.abcd.wifimanager.routerlogin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

public class ScanHistoryAdapter extends Adapter<ScanHistoryAdapter.ScanHistoryViewHolder> {
    Context mContext;

    public class ScanHistoryViewHolder extends ViewHolder {
        public ScanHistoryViewHolder(View view) {
            super(view);
        }
    }

    public int getItemCount() {
        return 0;
    }

    public void onBindViewHolder(ScanHistoryViewHolder scanHistoryViewHolder, int i) {
    }

    public ScanHistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    public ScanHistoryAdapter(Context context) {
        this.mContext = context;
    }
}
