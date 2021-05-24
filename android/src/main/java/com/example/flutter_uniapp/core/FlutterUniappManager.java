package com.example.flutter_uniapp.core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.flutter_uniapp.common.ResultHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.feature.sdk.DCSDKInitConfig;
import io.dcloud.feature.sdk.DCUniMPSDK;
import io.dcloud.feature.sdk.MenuActionSheetItem;
import io.flutter.plugin.common.MethodCall;

public class FlutterUniappManager {

    private static final String TAG = "FlutterUniappManager";

    private Context context;

    private DCUniMPSDK sdk;


    public FlutterUniappManager(Context context){
        this.context = context;
        this.sdk = DCUniMPSDK.getInstance();
    }

    public void init( final ResultHandler resultHandler){
        DCSDKInitConfig config = new DCSDKInitConfig.Builder()
                .setCapsule(true)
                .setMenuDefFontSize("16px")
                .setMenuDefFontColor("#ff00ff")
                .setMenuDefFontWeight("normal")
                .setEnableBackground(false)
                .build();
        sdk.initialize(context, config, new DCUniMPSDK.IDCUNIMPPreInitCallback() {
            @Override
            public void onInitFinished(boolean isSuccess) {
                Log.d(TAG, "初始化-----------"+isSuccess);
                Map<String, Object> map = new HashMap<>();
                map.put("result", isSuccess);
                resultHandler.reply(map);
            }
        });
    }

    /// 启动小程序
    public void startApp(MethodCall call){
        if (!isInitialize()){
            Toast.makeText(context, "引擎未初始化", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "引擎未初始化-----------");
            return;
        }

        final String appId = call.argument("appId");

        if (appId == null){
            Toast.makeText(context, "小程序未找到", Toast.LENGTH_SHORT).show();
            return;
        }

        final String redirectPath = call.argument("redirectPath");

        // 启动小程序
        try {
            if (redirectPath != null && !redirectPath.equals("")){
                sdk.startApp(context,appId,redirectPath);
            }else{
                sdk.startApp(context,appId);
            }
            sdk.setUniMPOnCloseCallBack(new DCUniMPSDK.IUniMPOnCloseCallBack() {
                @Override
                public void onClose(String appid) {
                    close();
                }
            });

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }
    }


    /// 是否初始化
    private boolean isInitialize(){
        return sdk.isInitialize();
    }

    /// 强制关闭当前小程序
    public void close(){
        Log.d(TAG,"关闭小程序");
        sdk.closeCurrentApp();
    }

}
