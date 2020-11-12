package com.abcd.wifimanager.routerlogin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.abcd.wifimanager.routerlogin.R;
import com.abcd.wifimanager.routerlogin.utils.DataModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class RouterPasswordAdapter extends Adapter<RouterPasswordAdapter.ViewHolder> implements Filterable {
    private ArrayList<DataModel> arraylist = new ArrayList();
    Context f123c;
    private ArrayList<DataModel> dataSet;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public View layout;
        public TextView txtBrand;
        public TextView txtPassword;
        public TextView txtType;
        public TextView txtUsername;

        public ViewHolder(View view) {
            super(view);
            this.layout = view;
            this.txtBrand = (TextView) view.findViewById(R.id.txtBrand);
            this.txtType = (TextView) view.findViewById(R.id.txtType);
            this.txtUsername = (TextView) view.findViewById(R.id.txtUsername);
            this.txtPassword = (TextView) view.findViewById(R.id.txtPassword);
        }
    }

    public Filter getFilter() {
        return null;
    }

    public RouterPasswordAdapter(Context context, ArrayList<DataModel> arrayList) {
        this.f123c = context;
        this.dataSet = arrayList;
        this.arraylist.addAll(this.dataSet);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.router_password_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.txtBrand.setText(((DataModel) this.dataSet.get(i)).getBrand());
        viewHolder.txtPassword.setText(((DataModel) this.dataSet.get(i)).getPassword());
        viewHolder.txtType.setText(((DataModel) this.dataSet.get(i)).getType());
        viewHolder.txtUsername.setText(((DataModel) this.dataSet.get(i)).getUsername());
    }

    public void filter(String str, String str2) {
        try {
            this.dataSet.clear();
            Iterator it;
            DataModel dataModel;
            if (!str.isEmpty() && !str2.isEmpty()) {
                Iterator it2 = this.arraylist.iterator();
                while (it2.hasNext()) {
                    DataModel dataModel2 = (DataModel) it2.next();
                    if (dataModel2.getType() != null) {
                        Log.i("ContentValues", "filter BOTH: CALLED");
                        if (dataModel2.getType().toLowerCase(Locale.getDefault()).contains(str2.toLowerCase()) && dataModel2.getBrand().toLowerCase(Locale.getDefault()).contains(str.toLowerCase())) {
                            this.dataSet.add(dataModel2);
                        }
                    }
                }
            } else if (!str2.isEmpty()) {
                Log.i("ContentValues", "filter: 11111");
                it = this.arraylist.iterator();
                while (it.hasNext()) {
                    dataModel = (DataModel) it.next();
                    if (dataModel.getType() != null && dataModel.getType().toLowerCase(Locale.getDefault()).contains(str2.toLowerCase())) {
                        this.dataSet.add(dataModel);
                    }
                }
            } else if (!str.isEmpty()) {
                Log.i("ContentValues", "filter: 222222");
                Iterator it3 = this.arraylist.iterator();
                while (it3.hasNext()) {
                    dataModel = (DataModel) it3.next();
                    if (dataModel.getBrand().toLowerCase(Locale.getDefault()).contains(str.toLowerCase())) {
                        this.dataSet.add(dataModel);
                    }
                }
            } else if (str.isEmpty()) {
                it = this.arraylist.iterator();
                while (it.hasNext()) {
                    this.dataSet.add((DataModel) it.next());
                }
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.dataSet.size();
    }
}
