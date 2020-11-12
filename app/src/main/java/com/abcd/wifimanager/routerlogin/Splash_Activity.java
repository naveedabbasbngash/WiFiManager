package com.abcd.wifimanager.routerlogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.abcd.wifimanager.routerlogin.utils.Ad_Global;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.lucem.anb.cardslide2.SliderAnimate;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_Activity extends Activity {
    Context context;
    private com.facebook.ads.InterstitialAd interstitialAd;
    Splash_Activity splash_activity;
    SliderAnimate splashicon;
    Timer waitTimer;
    Boolean interstitialCanceled = false;

    class C08841 implements Runnable {
        C08841() {
        }

        public void run() {
            startActivity(new Intent(Splash_Activity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashicon = (SliderAnimate) findViewById(R.id.slider_animate);
        splashicon.startSlide();
        splashicon.setStartPoint(SliderAnimate.TOP_LEFT);
        this.splash_activity = this;
        this.context = this;

        waitTimer = new Timer();
        waitTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                interstitialCanceled = true;
                Splash_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Splash_Activity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }, 15000);

        this.interstitialAd = new com.facebook.ads.InterstitialAd(this, Ad_Global.AD_Full_SPLASH);
        this.interstitialAd.loadAd();
        this.interstitialAd.setAdListener(new com.facebook.ads.InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

                new Handler().postDelayed(new C08841(), 3000);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Intent intent = new Intent(Splash_Activity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onPause() {
        waitTimer.cancel();
        interstitialCanceled = true;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}
