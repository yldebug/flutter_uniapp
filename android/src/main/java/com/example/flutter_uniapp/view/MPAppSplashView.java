package com.example.flutter_uniapp.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.flutter_uniapp.R;

import io.dcloud.feature.sdk.Interface.IDCUniMPAppSplashView;

public class MPAppSplashView implements IDCUniMPAppSplashView {

    View splashView;

    @Override
    public View getSplashView(Context context, String appId) {

        splashView = LayoutInflater.from(context).inflate(R.layout.mp_splash,new FrameLayout(context));
        
        
//        splashView = new FrameLayout(context);
        splashView.setBackgroundColor(Color.WHITE);

        return splashView;
    }

    @Override
    public void onCloseSplash(ViewGroup rootView) {
        if(rootView != null){
            rootView.removeView(splashView);
        }
    }
}
