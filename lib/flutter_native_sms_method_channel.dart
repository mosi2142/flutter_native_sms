import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_native_sms_platform_interface.dart';

/// An implementation of [FlutterNativeSmsPlatform] that uses method channels.
class MethodChannelFlutterNativeSms extends FlutterNativeSmsPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_native_sms');

  @override
  Future<dynamic> send({required String phone,required String smsBody,String sim = '1'}) async {
    final result = await methodChannel.invokeMethod<dynamic>('send',[
      phone,
      smsBody,
      sim,
    ]);
    return result;
  }
}
