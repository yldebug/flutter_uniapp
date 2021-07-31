package com.example.flutter_uniapp.handle;

import android.content.Context;
import android.util.Log;

import com.example.flutter_uniapp.common.FlutterUniappUtil;
import com.example.flutter_uniapp.view.MPAppSplashView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.feature.sdk.DCSDKInitConfig;
import io.dcloud.feature.sdk.DCUniMPJSCallback;
import io.dcloud.feature.sdk.DCUniMPSDK;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class FlutterUniappHandle {

    private Context context;
    private DCUniMPSDK uniMPSDK;
    private MethodChannel channel;


    public FlutterUniappHandle(Context context,MethodChannel channel){
        this.context = context;
        this.channel = channel;
        this.uniMPSDK = DCUniMPSDK.getInstance();
    }
    public void disposed(){
        this.context = null;
        this.channel = null;
        this.uniMPSDK = null;
    }

    public void initialize(MethodCall call,final MethodChannel.Result result){
        DCSDKInitConfig config = FlutterUniappUtil.initConfig(call);
        uniMPSDK.initialize(context, config, new DCUniMPSDK.IDCUNIMPPreInitCallback() {
            @Override
            public void onInitFinished(boolean isSuccess) {
                channel.invokeMethod("InitializeCallBack",isSuccess);
            }
        });

        if (call.<Boolean>argument("mPOnCloseCallBack")){
            setUniMPOnCloseCallBack();
        }
        if (call.<Boolean>argument("menuButtonClickCallBack")){
            setDefMenuButtonClickCallBack();
        }
        if (call.<Boolean>argument("uniMPEventCallBack")){
            setOnUniMPEventCallBack();
        }

    }

    public void startApp(final MethodCall call){
        final String appId = call.argument("appId");
        final String redirectPath = call.argument("redirectPath");
        final String wgtPath = call.argument("wgtPath");
        final String arguments = call.argument("arguments");
        final boolean isSplash = call.argument("isSplash");

        if (!isExistsApp(appId)){
            uniMPSDK.releaseWgtToRunPathFromePath(appId,wgtPath,new ICallBack(){
                @Override
                public Object onCallBack(int code, Object o) {
                    if (code == 1){
                        startUniApp(appId,redirectPath,arguments,isSplash);
                    }
                    return null;
                }
            });
            return;
        }
        startUniApp(appId,redirectPath,arguments,isSplash);
    }


    private void startUniApp(String appId,String redirectPath,String arguments,boolean isSplash){
        if(!isInitialize()){
            return;
        }
        // 启动小程序
        try {
            JSONObject argumentsJson = new JSONObject();
            if (arguments != null){
                argumentsJson = argumentsJson.getJSONObject(arguments);
            }
            if (isSplash){
                uniMPSDK.startApp(context,appId, MPAppSplashView.class,redirectPath,argumentsJson);
            }else{
                uniMPSDK.startApp(context,appId,null,redirectPath,argumentsJson);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void releaseWgtToRunPathFromePath(final MethodCall call){

        final String appId = call.argument("appId");
        final String wgtPath = call.argument("wgtPath");

        final boolean isStartApp = call.argument("isStartApp");

        uniMPSDK.releaseWgtToRunPathFromePath(appId,wgtPath,new ICallBack(){

            @Override
            public Object onCallBack(int code, Object o) {
                channel.invokeMethod("UniappWgtToRunPath",code == 1);
                if (code == 1 && isStartApp){
                    startUniApp(appId,"","",true);
                }
                return null;
            }
        });
    }

    /// 获取已运行过的小程序应用版本信息,没有运行过的小程序是无法正常获取到版本信息的。返回值需要判空处理!!!
    public Map<String,String> getAppVersionInfo(String appId){
        JSONObject versionInfo = uniMPSDK.getAppVersionInfo(appId);
        if (versionInfo != null){
            try {
                Map<String, String> map = new HashMap<>();
                map.put("code", versionInfo.getString("name"));
                map.put("name", versionInfo.getString("code"));
                return map;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /// 检查当前appid小程序是否已释放wgt资源 可用来检查当前appid资源是否存在
    public boolean isExistsApp(String appId){
        return uniMPSDK.isExistsApp(appId);
    }

    ///关闭当前正在运行的uni小程序
    public void closeCurrentApp(){
        uniMPSDK.closeCurrentApp();
    }

    ///正在运行的uni小程序appid 返回null表示未启动应用或应用未初始化完毕。
    public String getRuningAppid(){
        return uniMPSDK.getRuningAppid();
    }

    ///uni小程序运行路径 路径格式： "/xxx/xxx/宿主包名/files/apps/"
    public String getAppBasePath(){
        return uniMPSDK.getAppBasePath(context);
    }

    /// true表示初始化成功 false表示失败
    public boolean isInitialize(){
        return uniMPSDK.isInitialize();
    }


    private void setOnUniMPEventCallBack(){
        uniMPSDK.setOnUniMPEventCallBack(new DCUniMPSDK.IOnUniMPEventCallBack() {
            @Override
            public void onUniMPEventReceive(String event, Object data, DCUniMPJSCallback callback) {
                Map<String, Object> map = new HashMap<>();
                map.put("event", event);
                map.put("data", data);
                channel.invokeMethod("UniMPEventCallBack",map);
            }
        });
    }

    private void setDefMenuButtonClickCallBack(){
        uniMPSDK.setDefMenuButtonClickCallBack(new DCUniMPSDK.IMenuButtonClickCallBack() {
            @Override
            public void onClick(String appId, String id) {
                Map<String, String> map = new HashMap<>();
                map.put("appId", appId);
                map.put("id", id);
                channel.invokeMethod("MenuButtonClickCallBack",map);
            }
        });
    }

    private void setUniMPOnCloseCallBack(){
        uniMPSDK.setUniMPOnCloseCallBack(new DCUniMPSDK.IUniMPOnCloseCallBack() {
            @Override
            public void onClose(String appId) {
                Log.e("setUniMPOnCloseCallBack", appId+"被关闭了");
                channel.invokeMethod("MPOnCloseCallBack",appId);
            }
        });
    }

}
