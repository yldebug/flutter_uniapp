import 'dart:io';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_uniapp/flutter_uniapp.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            FlatButton(
                child: Text('初始化'),
                onPressed: ()async{
                  await FlutterUniapp.init();
                }
            ),
            FlatButton(
                child: Text('启动小程序'),
                onPressed: ()async{
                  await FlutterUniapp.startApp(
                    appId: Platform.isIOS?'__UNI__11E9B73':'__UNI__04E3A11',
                    redirectPath: '',
                  );
                }
            )
          ],
        ),
      ),
    );
  }
}
