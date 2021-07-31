
import 'dart:async';

import 'package:flutter/services.dart';

import 'flutter_uniapp_constants.dart';
import 'flutter_uniapp_model.dart';


class FlutterUniapp {
  static final MethodChannel _channel = const MethodChannel("flutter_uniapp")
    ..setMethodCallHandler(_platformCallHandler);

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Map<CallBackType, Function?> _callBackMaps = {
    CallBackType.UniappWgtToRunPath : null,
    CallBackType.MPOnCloseCallBack : null,
    CallBackType.InitializeCallBack : null,
    CallBackType.UniMPEventCallBack : null,
    CallBackType.MenuButtonClickCallBack : null,
  };
  static Future<void> _platformCallHandler(MethodCall call) async {
    var arguments = call.arguments;
    print('接收数据: $arguments');
    print('接收方法: ${call.method}');
    CallBackType type = enumFromString(CallBackType.values, call.method);
    switch (type) {
      case CallBackType.InitializeCallBack:
      case CallBackType.MPOnCloseCallBack:
      case CallBackType.UniappWgtToRunPath:
        break;
      case CallBackType.MenuButtonClickCallBack:
        arguments = MenuButtonClickModel(arguments['appId'],arguments['id']);
        break;
      case CallBackType.UniMPEventCallBack:
        arguments = EventCallbackModel(arguments['event'],arguments['data']);
        break;
      default:
        print(arguments);
        print("unsupport method handler");
        return;
    }
    Function? f = _callBackMaps[type];
    if (f != null) {
      f(arguments);
      _callBackMaps[type] = null;
    }
  }

  static Future<void> init({
    String? fontSize,
    String? fontColor,
    String? fontWeight,
    bool? enableBackground,
    bool? capsule,
    BoolCallback? callback,
    MenuButtonCallback? menuButtonClickCallBack,///设置menu点击事件回调接口
    AppIdCallback? mPOnCloseCallBack,///小程序被关闭事件监听
    EventCallback? uniMPEventCallBack,///监听小程序发送给宿主的事件
  }) async {
     _channel.invokeMethod('init',{
      'fontSize':fontSize,
      'fontColor':fontColor,
      'fontWeight':fontWeight,
      'enableBackground':enableBackground,
      'capsule':capsule,
      'menuButtonClickCallBack': menuButtonClickCallBack != null,
      'mPOnCloseCallBack': mPOnCloseCallBack != null,
      'uniMPEventCallBack': uniMPEventCallBack != null,
    });
    _callBackMaps[CallBackType.InitializeCallBack] = callback;
    _callBackMaps[CallBackType.MPOnCloseCallBack] = mPOnCloseCallBack;
    _callBackMaps[CallBackType.UniMPEventCallBack] = uniMPEventCallBack;
    _callBackMaps[CallBackType.MenuButtonClickCallBack] = menuButtonClickCallBack;
  }


  static Future<bool> startApp({
    String? appId,
    String? redirectPath,
    String? wgtPath,
    Map<String,Object>? arguments,
    bool isSplash = false,
  }) async {
    final bool res = await _channel.invokeMethod('startApp',{
      'appId':appId,
      'redirectPath':redirectPath,
      'wgtPath':wgtPath,
      'arguments':arguments?.toString(),
      'isSplash':isSplash,
    });
    return res;
  }

  static Future<void> wgtToRunPath({
    required String appId,
    required String wgtPath,
    bool isStartApp = false,
    BoolCallback? callback
  }) async{
    _channel.invokeMethod('wgtToRunPath',{'appId':appId, 'wgtPath':wgtPath, 'isStartApp':isStartApp});
    _callBackMaps[CallBackType.UniappWgtToRunPath] = callback;
  }


  static Future<AppVersionInfoModel?> getAppVersionInfo({required String appId}) async{
    final Map? res = await _channel.invokeMethod('getAppVersionInfo',{'appId':appId});
    if (res != null){
      return AppVersionInfoModel.fromJson(res.cast());
    }
  }

  static Future<bool> isExistsApp({required String appId}) async{
    final bool res = await _channel.invokeMethod('isExistsApp',{'appId':appId});
    return res;
  }

  static Future<void> closeCurrentApp() async{
    await _channel.invokeMethod('closeCurrentApp');
  }


  static Future<String?> get getRuningAppid async{
    final String? res = await _channel.invokeMethod('getRuningAppid');
    return res;
  }

  static Future<String> get getAppBasePath async{
    final String res = await _channel.invokeMethod('getAppBasePath');
    return res;
  }

  static Future<bool> get isInitialize async{
    final bool res = await _channel.invokeMethod('isInitialize');
    return res;
  }
}
