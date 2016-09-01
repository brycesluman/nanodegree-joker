package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.view.View;
import android.view.ViewStub;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by bryce on 8/26/16.
 */
public class AdProvider extends BaseAdProvider {
    private String mJoke;
    private static AdProvider mInstance;
    private static InterstitialAd mInterstitialAd;

    private AdProvider(Activity context) {
        if (context instanceof NextViewListener) {
            mListener = (NextViewListener) context;
        }
    }
    public static AdProvider getInstance(Activity context) {
        if(mInstance!=null) {
            return mInstance;
        }
        mInstance = new AdProvider(context);
        return mInstance;
    }

    public void loadAd(View root) {
        ViewStub stub = (ViewStub) root.findViewById(R.id.stub);
        AdView mAdView = (AdView) stub.inflate();
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        if (mAdView != null) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
        }
    }

    public void loadInterstitial(Activity activity) {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mListener.onRenderNextView(mJoke);
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();
    }

    public void showInterstitial(String joke) {
        mJoke = joke;
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}
