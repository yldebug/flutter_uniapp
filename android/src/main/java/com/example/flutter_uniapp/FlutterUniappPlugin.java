package com.example.flutter_uniapp;

import androidx.annotation.NonNull;

import com.example.flutter_uniapp.handle.FlutterUniappHandle;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** FlutterUniappPlugin */
public class FlutterUniappPlugin implements FlutterPlugin, MethodCallHandler {

  private FlutterUniappHandle handle;
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_uniapp");
    channel.setMethodCallHandler(this);
    handle = new FlutterUniappHandle(flutterPluginBinding.getApplicationContext(),channel);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("init")) {
      handle.initialize(call,result);
    } else if (call.method.equals("startApp")) {
      handle.startApp(call);
    } else if (call.method.equals("wgtToRunPath")) {
      handle.releaseWgtToRunPathFromePath(call);
    } else if (call.method.equals("getAppVersionInfo")) {
      result.success(handle.getAppVersionInfo(call.<String>argument("appId")));
    } else if (call.method.equals("closeCurrentApp")) {
      handle.closeCurrentApp();
    } else if (call.method.equals("getRuningAppid")) {
      result.success(handle.getRuningAppid());
    } else if (call.method.equals("getAppBasePath")) {
      result.success(handle.getAppBasePath());
    } else if (call.method.equals("isInitialize")) {
      result.success(handle.isInitialize());
    }  else if (call.method.equals("isExistsApp")) {
      result.success(handle.isExistsApp(call.<String>argument("appId")));
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    handle.disposed();
  }
}
