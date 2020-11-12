package com.abcd.wifimanager.routerlogin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DeviceManager";
    private static final int DATABASE_VERSION = 2;
    private static final String KEY_ID = "id";
    private static final String KEY_MAC = "macid";
    private static final String KEY_NAME = "device_name";
    private static final String TABLE_NAME = "RouterAdminDeviceId";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS RouterAdminDeviceId(id INTEGER PRIMARY KEY AUTOINCREMENT,macid TEXT,device_name TEXT)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i2 > i) {
            Log.i("SIze", "onUpgrade: ");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS RouterAdminDeviceId");
        }
        onCreate(sQLiteDatabase);
    }

    public void deleteTable() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (writableDatabase == null || !writableDatabase.isOpen()) {
            writableDatabase = getWritableDatabase();
        }
        writableDatabase.execSQL("delete from RouterAdminDeviceId");
    }

    public void addMACId(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Log.i("SIze", "addMACId: ");
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MAC, str);
        writableDatabase.insert(TABLE_NAME, null, contentValues);
        writableDatabase.close();
    }

    public void deleteMACId(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(TABLE_NAME, "macid = ?", new String[]{String.valueOf(str)});
        writableDatabase.close();
    }

    public boolean checkMacId(String str) {
        Exception e;
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery;
        try {
            String[] strArr = new String[1];
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("");
            strArr[0] = stringBuilder.toString();
            rawQuery = readableDatabase.rawQuery("SELECT * FROM RouterAdminDeviceId WHERE macid=?", strArr);
            try {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("checkMacId: ");
                stringBuilder2.append(rawQuery.getCount());
                Log.i("SIze", stringBuilder2.toString());
                while (rawQuery.moveToNext()) {
                    String string = rawQuery.getString(rawQuery.getColumnIndex(KEY_MAC));
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("checkMacId: ");
                    stringBuilder3.append(string);
                    Log.i("SIze", stringBuilder3.toString());
                }
                if (rawQuery.getCount() <= 0) {
                    return false;
                }
                rawQuery.moveToFirst();
                return true;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                rawQuery.close();
                return false;
            }
        } catch (Exception e3) {
            e = e3;
            rawQuery = null;
            e.printStackTrace();
            rawQuery.close();
            return false;
        }
    }


    public boolean checkDevice(String str) {
        Exception e;
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] strArr = new String[1];
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("");
            strArr[0] = stringBuilder.toString();
            Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM RouterAdminDeviceId WHERE device_name=?", strArr);
            try {
                if (rawQuery.getCount() <= 0) {
                    return false;
                }
                rawQuery.moveToFirst();
                rawQuery.getString(rawQuery.getColumnIndex(KEY_MAC));
                return true;
            } catch (Exception e2) {
                e = e2;
                cursor = rawQuery;
                e.printStackTrace();
                cursor.close();
                return false;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            cursor.close();
            return false;
        }
    }

}
