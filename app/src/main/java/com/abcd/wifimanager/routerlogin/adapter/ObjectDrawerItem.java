package com.abcd.wifimanager.routerlogin.adapter;

public class ObjectDrawerItem {
    String ItemName;
    int imgResID;

    public ObjectDrawerItem(String str, int i) {
        this.ItemName = str;
        this.imgResID = i;
    }

    public String getItemName() {
        return this.ItemName;
    }

    public void setItemName(String str) {
        this.ItemName = str;
    }

    public int getImgResID() {
        return this.imgResID;
    }

    public void setImgResID(int i) {
        this.imgResID = i;
    }
}
