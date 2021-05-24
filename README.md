
# flutter_uniapp


**flutter 集成uni_app! unisdk:V3.1.13 **
可以给个star🐴，混口饭吃

邮箱：yldebug@gmail.com

*注意*：此插件仍处于开发阶段，某些API可能尚未推出 目前只集成了基础包!。


### 安装依赖：
```yaml
dependencies:
  flutter:
    sdk: flutter
  # 添加依赖
  flutter_uniapp: 
    git:
      url: git://github.com/yldebug/flutter_uniapp.git
      ref: master

```


### 配置

##### Android:
在 `/android/app/build.gradle` 中添加下列代码：

```groovy
def mfph = [
      //包名
      "apk.applicationId" : "替换成自己应用 ID",
]
android: {
  defaultConfig {
    minSdkVersion 21
    multiDexEnabled true
    manifestPlaceholders = mfph
    ndk {
        //选择要添加的对应 cpu 类型的 .so 库。
        abiFilters 'x86','armeabi-v7a',"arm64-v8a" //不支持armeabi
    }
  }
  aaptOptions {
      additionalParameters '--auto-add-overlay'
      ignoreAssetsPattern "!.svn:!.git:.*:!CVS:!thumbs.db:!picasa.ini:!*.scc:*~"
  }  
}
```
在`res/values` 新建文件strings.xml：
```xml
<resources>
    <string name="app_name">UnimpDemo</string>
</resources>
```
##### Ios:
* IOS 导入官方SDK Core/Libs&& Core/Resources 以及Apps 放wgt包 *
![示例](ios-config.png)

[SDK参考](https://nativesupport.dcloud.net.cn/UniMPDocs/SDKDownload/ios)


### 方法说明
* 头文件引入
```
import 'package:flutter_uniapp/flutter_uniapp.dart';
```
* 初始化

```
 /**
   * 初始化
   * result//true 为初始化成功，其他为失败
   */
var result = await FlutterUniapp.init();
```

* 唤起UniApp，本地包方式
```
 /**
   * appId:目标 appId
   * redirectPath:目标路径
   * return: //true 为初始化成功，其他为失败
   */
 await FlutterUniapp.startApp(
      appId: '__UNI__04E3A11',
      redirectPath: '',
  );
```