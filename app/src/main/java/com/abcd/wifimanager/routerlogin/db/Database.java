package com.abcd.wifimanager.routerlogin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Database extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase db;

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public Database(Context context) {
        super(context, "vendors", null, 1);
        this.context = context;
    }

    private boolean checkDatabase(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.context.getApplicationInfo().dataDir);
        stringBuilder.append("/");
        stringBuilder.append(str);
        return new File(stringBuilder.toString()).exists();
    }

    private void copyDatabase(String str) throws IOException {
        InputStream open = this.context.getAssets().open(str);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.context.getApplicationInfo().dataDir);
        stringBuilder.append("/");
        stringBuilder.append(str);
        OutputStream fileOutputStream = new FileOutputStream(stringBuilder.toString());
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = open.read(bArr);
                if (read <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable th) {
                fileOutputStream.close();
                open.close();
            }
        }
        fileOutputStream.close();
        open.close();
    }


    public void openDatabase(String str) throws IOException, SQLiteException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("openDatabase: ");
        stringBuilder.append(checkDatabase(str));
        Log.i("DATABAse", stringBuilder.toString());
        if (!checkDatabase(str)) {
            copyDatabase(str);
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.context.getApplicationInfo().dataDir);
        stringBuilder2.append("/");
        stringBuilder2.append(str);
        this.db = SQLiteDatabase.openDatabase(stringBuilder2.toString(), null, 0);
    }

    public void updateQuery(ContentValues contentValues, String[] strArr) {
        this.db.update("macvendor", contentValues, "device_name = ?", strArr);
    }

    public Cursor queryDatabase(String str, String[] strArr) {
        return this.db.rawQuery(str, strArr);
    }

    public void close() {
        this.db.close();
    }
}
