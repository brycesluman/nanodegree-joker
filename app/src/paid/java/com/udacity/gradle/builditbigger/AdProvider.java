package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.view.View;

/**
 * Created by bryce on 8/26/16.
 */
public class AdProvider extends BaseAdProvider {
    private static AdProvider mInstance;
    private NextViewListener mListener;


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
        //method stub
    }

    @Override
    public void loadInterstitial(Activity activity) {}

    @Override
    public void showInterstitial(String joke) {
        mListener.onRenderNextView(joke);
    }

    @Override
    public void requestNewInterstitial() {}

}
