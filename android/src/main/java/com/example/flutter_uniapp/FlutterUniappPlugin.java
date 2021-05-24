package com.example.flutter_uniapp;

import androidx.annotation.NonNull;

import com.example.flutter_uniapp.common.ResultHandler;
import com.example.flutter_uniapp.core.FlutterUniappManager;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterUniappPlugin */
public class FlutterUniappPlugin implements FlutterPlugin, MethodCallHandler {

  private FlutterUniappManager manager;

  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_uniapp");

    FlutterUniappManager manager = new FlutterUniappManager(flutterPluginBinding.getApplicationContext());
    FlutterUniappPlugin plugin = new FlutterUniappPlugin();
    plugin.manager = manager;
    channel.setMethodCallHandler(plugin);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    ResultHandler handler = new ResultHandler(result);
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }else if (call.method.equals("init")) {
      manager.init(handler);
    } else if ("startApp".equals(call.method)) {
      manager.startApp(call);
    } else if ("close".equals(call.method)) {
      manager.close();
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
