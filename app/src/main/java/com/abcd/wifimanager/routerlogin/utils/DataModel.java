package com.abcd.wifimanager.routerlogin.utils;

public class DataModel {
    private String brand;
    private String password;
    private String type;
    private String username;

    public DataModel(String str, String str2, String str3, String str4) {
        this.brand = str;
        this.type = str2;
        this.username = str3;
        this.password = str4;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String str) {
        this.brand = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String str) {
        this.password = str;
    }
}
