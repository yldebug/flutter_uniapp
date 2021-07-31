


import 'flutter_uniapp_model.dart';

class FlutterUniappConstants{

}

typedef CommonCallback = void Function(Map<String, Object> map);

typedef AppIdCallback = void Function(String appId);

typedef BoolCallback = void Function(bool res);

typedef EventCallback = void Function(EventCallbackModel callbackModel);

typedef MenuButtonCallback = void Function(MenuButtonClickModel clickModel);

enum CallBackType {
  UniappWgtToRunPath,
  MPOnCloseCallBack,
  InitializeCallBack,
  MenuButtonClickCallBack,
  UniMPEventCallBack
}

T enumFromString<T>(Iterable<T> values, String value) {
  return values.firstWhere((type) => type.toString().split('.').last == value, orElse: () => null as T);
}