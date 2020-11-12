package com.abcd.wifimanager.routerlogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.abcd.wifimanager.routerlogin.adapter.RouterPasswordAdapter;
import com.abcd.wifimanager.routerlogin.utils.DataModel;

import java.util.ArrayList;

public class Router_password extends BaseActivity {
    private static ArrayList<DataModel> data;
    RouterPasswordAdapter adapter;
    ArrayList<DataModel> arrayList;
    EditText brandSearch;
    Context con;
    private LayoutManager layoutManager;
    RecyclerView recyclerview;
    String textBrand = "";
    String textType = "";
    EditText typeSearch;

    class C04842 implements TextWatcher {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C04842() {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Router_password.this.textBrand = charSequence.toString().trim();
            Router_password.this.adapter.filter(Router_password.this.textBrand, Router_password.this.textType);
        }
    }

    class C04853 implements TextWatcher {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C04853() {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Router_password.this.textType = charSequence.toString().trim();
            Router_password.this.adapter.filter(Router_password.this.textBrand, Router_password.this.textType);
        }
    }

    protected void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            setContentView(R.layout.activity_router_password);
            this.toolbarTextView.setText(R.string.main_screen_router_password);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.notice_msg);
            relativeLayout.setOnClickListener(new OnClickListener() {
                @SuppressLint("WrongConstant")
                public void onClick(View view) {
                    relativeLayout.setVisibility(8);
                }
            });
            this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
            this.recyclerview.setHasFixedSize(true);
            this.layoutManager = new LinearLayoutManager(this.con);
            this.recyclerview.setLayoutManager(this.layoutManager);
            this.recyclerview.setItemAnimator(new DefaultItemAnimator());
            data = new ArrayList();
            this.arrayList = new ArrayList();
            TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.routersData);
            int length = obtainTypedArray.length();
            String[][] strArr = new String[length][];
            for (int i = 0; i < length; i++) {
                int resourceId = obtainTypedArray.getResourceId(i, 0);
                if (resourceId > 0) {
                    strArr[i] = getResources().getStringArray(resourceId);
                    String str = "";
                    String str2 = "";
                    if (!TextUtils.isEmpty(strArr[i][2])) {
                        str = strArr[i][2].trim();
                    }
                    if (!TextUtils.isEmpty(strArr[i][3])) {
                        str2 = strArr[i][3].trim();
                    }
                    this.arrayList.add(new DataModel(strArr[i][0], strArr[i][1], str, str2));
                }
            }
            this.adapter = new RouterPasswordAdapter(this.con, this.arrayList);
            this.recyclerview.setAdapter(this.adapter);
            this.brandSearch = (EditText) findViewById(R.id.simpleSearchView);
            this.typeSearch = (EditText) findViewById(R.id.simpleSearchView1);
            this.brandSearch.addTextChangedListener(new C04842());
            this.typeSearch.addTextChangedListener(new C04853());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
