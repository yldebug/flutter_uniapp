import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:async';

import 'logger.dart';

class FlutterUniapp {
  static const MethodChannel _channel = const MethodChannel('flutter_uniapp');
  static final logger = Logger();

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> init() async {
    final result = await _channel.invokeMethod("init");
    if (result == null) {
      return false;
    }
    if (result["result"] == true) {
      return true;
    } else {
      logger.log("error: ${result["error"]}");
      return false;
    }
  }

  static Future<bool> startApp({required String appId, String? redirectPath}) async {
    final result = await _channel.invokeMethod(
        "startApp", {"appId": appId, "redirectPath": redirectPath});
    if (result == null) {
      return false;
    }
    if (result["result"] == true) {
      return true;
    } else {
      logger.log("error: ${result["error"]}");
      return false;
    }
  }
}
