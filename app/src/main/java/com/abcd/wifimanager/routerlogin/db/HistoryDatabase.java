package com.abcd.wifimanager.routerlogin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "historyManager";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_GATEWAY_NAME = "gateway_name";
    private static final String KEY_ID = "id";
    private static final String KEY_IP = "ip";
    private static final String KEY_MAC = "mac";
    private static final String KEY_NO_OF_HOST = "no_of_host";
    private static final String KEY_NO_OF_UNNNOWN_HOST = "no_known_host";
    private static final String KEY_REF_KEY = "ref_id";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_VENDOR = "vendor";
    private static final String LOG = "DatabaseHelper";
    private static final String TABLE_Gateway = "gateway";
    private static final String TABLE_Host = "host";
    String CREATE_GATWAY_TABLE = "CREATE TABLE IF NOT EXISTS gateway(id INTEGER PRIMARY KEY AUTOINCREMENT,gateway_nameTEXT,timestamp TEXT,no_of_host INTEGER,no_known_host INTEGER)";
    String CREATE_HOST_TABLE = "CREATE TABLE IF NOT EXISTS host(id INTEGER PRIMARY KEY AUTOINCREMENT,ref_id INTEGER,ipTEXT,mac TEXT,vendor TEXT)";

    public HistoryDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(this.CREATE_GATWAY_TABLE);
        sQLiteDatabase.execSQL(this.CREATE_HOST_TABLE);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i2 > i) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS host");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS gateway");
        }
        onCreate(sQLiteDatabase);
    }

    public void insertGetwayData(String str, String str2, int i, int i2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_GATEWAY_NAME, str);
        contentValues.put(KEY_TIMESTAMP, str2);
        contentValues.put(KEY_NO_OF_HOST, Integer.valueOf(i));
        contentValues.put(KEY_NO_OF_UNNNOWN_HOST, Integer.valueOf(i2));
        writableDatabase.insert(TABLE_Gateway, null, contentValues);
        writableDatabase.close();
    }

    public void insertHostData(int i, String str, String str2, String str3) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_REF_KEY, Integer.valueOf(i));
        contentValues.put(KEY_IP, str);
        contentValues.put(KEY_MAC, str2);
        contentValues.put(KEY_VENDOR, str3);
        writableDatabase.insert(TABLE_Host, null, contentValues);
        writableDatabase.close();
    }
}
