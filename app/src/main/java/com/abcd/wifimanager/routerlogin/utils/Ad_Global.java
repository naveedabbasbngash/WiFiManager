package com.abcd.wifimanager.routerlogin.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;

public class Ad_Global {
    public static String AD_Banner = "YOUR_PLACEMENT_ID";
    public static String AD_Full = "YOUR_PLACEMENT_ID";
    public static String AD_Full_SPLASH = "YOUR_PLACEMENT_ID";

    @SuppressLint("WrongConstant")
    public static void loadBannerAd(Context context, final RelativeLayout relativeLayout) {
        if (isNetworkAvailable(context)) {
            relativeLayout.setVisibility(8);
            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, Ad_Global.AD_Banner, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            relativeLayout.addView(adView);
            adView.loadAd();
            adView.setAdListener(new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    relativeLayout.setVisibility(8);
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    relativeLayout.setVisibility(0);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    relativeLayout.setVisibility(0);
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            });
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo != null) {
                for (NetworkInfo state : allNetworkInfo) {
                    if (state.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
