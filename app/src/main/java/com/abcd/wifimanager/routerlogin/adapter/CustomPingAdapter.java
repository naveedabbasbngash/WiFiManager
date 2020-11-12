package com.abcd.wifimanager.routerlogin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.abcd.wifimanager.routerlogin.R;
import java.util.ArrayList;

public class CustomPingAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    ArrayList<String> time;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public CustomPingAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.time = arrayList;
        this.inflter = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.time == null ? 0 : this.time.size();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.inflter.inflate(R.layout.port_scanner_item, null);
        TextView textView = (TextView) view.findViewById(R.id.tv1);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("#");
            stringBuilder.append(getCount() - i);
            stringBuilder.append(": ");
            stringBuilder.append(String.valueOf(this.time.get(i)));
            textView.setText(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
