import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_native_sms_method_channel.dart';

abstract class FlutterNativeSmsPlatform extends PlatformInterface {
  /// Constructs a FlutterNativeSmsPlatform.
  FlutterNativeSmsPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterNativeSmsPlatform _instance = MethodChannelFlutterNativeSms();

  /// The default instance of [FlutterNativeSmsPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterNativeSms].
  static FlutterNativeSmsPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterNativeSmsPlatform] when
  /// they register themselves.
  static set instance(FlutterNativeSmsPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<dynamic> send({required String phone,required String smsBody,String sim = '1'}) {
    throw UnimplementedError('send() has not been implemented.');
  }
  Future<List> simInfo() {
    throw UnimplementedError('simInfo() has not been implemented.');
  }
}
