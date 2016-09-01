package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.view.View;

/**
 * Created by bryce on 8/31/16.
 */
public abstract class BaseAdProvider {
    NextViewListener mListener;

    abstract void loadAd(View root);

    abstract void loadInterstitial(Activity activity);

    abstract void showInterstitial(String joke);

    abstract void requestNewInterstitial();

    public interface NextViewListener {
        void onRenderNextView(String joke);
    }
}
