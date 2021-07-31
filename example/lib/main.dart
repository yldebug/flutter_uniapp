import 'package:flutter/material.dart';
import 'package:flutter_uniapp/flutter_uniapp.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  String _info = "";

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              Container(
                child: Text('info: $_info'),
              ),
              TextButton(
                child: Text("初始化小程序引擎"),
                onPressed: () async {
                  await FlutterUniapp.init(
                      callback: (re){
                        print('初始化小程序引擎 $re');
                      },
                      mPOnCloseCallBack: (String appId){
                        print(appId + '被关闭了---Flutter端');
                      },

                  );
                },
              ),
              TextButton(
                child: Text("检测是否初始化引擎"),
                onPressed: () async {
                 bool res =  await FlutterUniapp.isInitialize;
                 print('检测是否初始化引擎 : $res');
                },
              ),
              TextButton(
                child: Text("启动本地小程序"),
                onPressed: () async {
                  FlutterUniapp.startApp(appId: '__UNI__04E3A11',redirectPath: '',isSplash: true);
                },
              ),
              TextButton(
                child: Text("释放wgt小程序"),
                onPressed: () async {
                  //下载完成后 再释放小程序
                  await FlutterUniapp.wgtToRunPath(
                    appId: '__UNI__ADA930E',
                    wgtPath: '/data/user/0/com.example.flutter_uniapp_example/files/wgt/__UNI__ADA930E.wgt',
                    callback: (res)=>{
                      print('释放wgt小程序 : $res')
                    }
                  );
                },
              ),
              TextButton(
                child: Text("获取小程序版本"),
                onPressed: () async {
                 final AppVersionInfoModel? res = await FlutterUniapp.getAppVersionInfo(appId: '__UNI__04E3A11');
                 if (res != null){
                   print('小程序name: ${res.name}' );
                   print('小程序code: ${res.code}' );
                 }
                },
              ),
              TextButton(
                child: Text("关闭当前小程序"),
                onPressed: () async {
                  await FlutterUniapp.closeCurrentApp();
                },
              ),
              TextButton(
                child: Text("检测小程序是否存在"),
                onPressed: () async {
                  bool res = await FlutterUniapp.isExistsApp(appId: "");
                  print('小程序是否存在：$res');
                },
              ),
              TextButton(
                child: Text("获取当前运行小程序ID"),
                onPressed: () async {
                  String? res = await FlutterUniapp.getRuningAppid;
                  print('获取当前运行小程序ID：$res');
                },
              ),
              TextButton(
                child: Text('获取小程序运行路径'),
                onPressed: () async {
                  String res = await FlutterUniapp.getAppBasePath;
                  print('获取小程序运行路径：$res');
                },
              ),
            ],
          ),
        )
      ),
    );
  }
}
