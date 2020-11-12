package com.abcd.wifimanager;

import android.content.Context;
import android.util.Log;

import com.abcd.wifimanager.routerlogin.utils.Ad_Global;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;

public class AdViewController {
    private Context context;
    private com.facebook.ads.InterstitialAd fbInterstitialAd;

    public AdViewController(Context context) {
        this.context = context;
        loadInterstitialAd();
    }

    public void loadInterstitialAd() {

        fbInterstitialAd = new com.facebook.ads.InterstitialAd(context, Ad_Global.AD_Full);
        fbInterstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                fbInterstitialAd.loadAd();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.v("FBADDS", "FBADSS" + adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        });
        fbInterstitialAd.loadAd();
    }

    public void showFullScreenAd() {
        if (fbInterstitialAd != null) {
            if (fbInterstitialAd.isAdLoaded()) {
                fbInterstitialAd.show();
            }
        }
        Log.d("TAG", "The interstitial wasn't loaded yet.");
    }
}
