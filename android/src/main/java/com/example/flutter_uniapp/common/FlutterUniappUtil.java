package com.example.flutter_uniapp.common;

import io.dcloud.feature.sdk.DCSDKInitConfig;
import io.flutter.plugin.common.MethodCall;

public class FlutterUniappUtil {


    public static DCSDKInitConfig initConfig(MethodCall call){

        String fontSize = call.argument("fontSize");
        if (fontSize != null){
            FlutterUniappConstants.fontSize = fontSize;
        }

        String fontColor = call.argument("fontColor");
        if (fontColor != null){
            FlutterUniappConstants.fontColor = fontColor;
        }

        String fontWeight = call.argument("fontWeight");
        if (fontSize != null){
            FlutterUniappConstants.fontWeight = fontWeight;
        }

        String enableBackground = call.argument("enableBackground");
        if (enableBackground != null){
            FlutterUniappConstants.enableBackground = Boolean.parseBoolean(enableBackground);
        }

        String capsule = call.argument("capsule");
        if (capsule != null){
            FlutterUniappConstants.capsule = Boolean.parseBoolean(capsule);
        }

        return new DCSDKInitConfig.Builder()
                .setCapsule(FlutterUniappConstants.capsule)
                .setMenuDefFontSize(FlutterUniappConstants.fontSize)
                .setMenuDefFontColor(FlutterUniappConstants.fontColor)
                .setMenuDefFontWeight(FlutterUniappConstants.fontWeight)
                .setEnableBackground(FlutterUniappConstants.enableBackground)
                .build();
    }
}
