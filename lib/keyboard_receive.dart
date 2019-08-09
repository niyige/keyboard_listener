import 'package:flutter/services.dart';

class KeyBoardReceive {
  static const EventChannel eventChannel =
      EventChannel("mq://oyy/keyboardListener");

  static void getKeyBoardStatus(Function(bool) onChange) {

    eventChannel.receiveBroadcastStream().listen((onData) {

      bool isKeyboardVisible = onData;
      onChange(isKeyboardVisible);
    });
  }
}
