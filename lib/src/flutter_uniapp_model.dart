


class AppVersionInfoModel {
  String? name;
  String? code;

  AppVersionInfoModel(this.name, this.code);

  AppVersionInfoModel.fromJson(Map<String, dynamic> map) {
    this.name = map['name'];
    this.code = map['code'];
  }
}

class MenuButtonClickModel {
  String? appId;
  String? id;

  MenuButtonClickModel(this.appId, this.id);

  MenuButtonClickModel.fromJson(Map<String, dynamic> map) {
    this.appId = map['appId'];
    this.id = map['id'];
  }
}

class EventCallbackModel {
  String? event;
  Object? data;

  EventCallbackModel(this.event, this.data);

  EventCallbackModel.fromJson(Map<String, dynamic> map) {
    this.event = map['event'];
    this.data = map['data'];
  }
}