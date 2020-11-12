package com.abcd.wifimanager.routerlogin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.abcd.wifimanager.routerlogin.R;
import java.util.List;

public class DrawerItemCustomAdapter extends ArrayAdapter<ObjectDrawerItem> {
    Context context;
    List<ObjectDrawerItem> drawerItemList;
    int layoutResID;
    Context mContext;
    ObjectDrawerItem[] mData = null;
    int mLayoutResourceId;

    private static class DrawerItemHolder {
        TextView ItemName;
        View divider;
        TextView header;
        ImageView icon;

        private DrawerItemHolder() {
        }
    }

    public DrawerItemCustomAdapter(Context context, int i, List<ObjectDrawerItem> list) {
        super(context, i, list);
        this.context = context;
        this.drawerItemList = list;
        this.layoutResID = i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            try {
                View inflate;
                LayoutInflater layoutInflater = ((Activity) this.context).getLayoutInflater();
                DrawerItemHolder drawerItemHolder = new DrawerItemHolder();
                ObjectDrawerItem objectDrawerItem = (ObjectDrawerItem) this.drawerItemList.get(i);
                if (i == 4 || i == 0) {
                    inflate = layoutInflater.inflate(R.layout.section_header, null);
                    drawerItemHolder.header = (TextView) inflate.findViewById(R.id.section_header);
                    drawerItemHolder.divider = inflate.findViewById(R.id.divider);
                    drawerItemHolder.header.setText(objectDrawerItem.getItemName());
                    drawerItemHolder.divider.setBackgroundColor(objectDrawerItem.getImgResID());
                } else {
                    inflate = layoutInflater.inflate(this.layoutResID, viewGroup, false);
                    drawerItemHolder.ItemName = (TextView) inflate.findViewById(R.id.drawer_itemName);
                    drawerItemHolder.icon = (ImageView) inflate.findViewById(R.id.drawer_icon);
                    drawerItemHolder.icon.setImageDrawable(inflate.getResources().getDrawable(objectDrawerItem.getImgResID()));
                    drawerItemHolder.ItemName.setText(objectDrawerItem.getItemName());
                }
                view = inflate;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        DrawerItemHolder drawerItemHolder2 = (DrawerItemHolder) view.getTag();
        return view;
    }
}
