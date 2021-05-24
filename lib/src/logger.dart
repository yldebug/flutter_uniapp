class Logger {
  static bool showLog = true;

  void log(Object? msg) {
    if (!showLog) {
      return;
    }

    if (msg == null) {
      print("null");
      return;
    }

    print(msg.toString());
  }
}
