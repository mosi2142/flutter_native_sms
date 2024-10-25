import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_native_sms/flutter_native_sms.dart';
import 'package:flutter_native_sms/flutter_native_sms_platform_interface.dart';
import 'package:flutter_native_sms/flutter_native_sms_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterNativeSmsPlatform
    with MockPlatformInterfaceMixin
    implements FlutterNativeSmsPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterNativeSmsPlatform initialPlatform = FlutterNativeSmsPlatform.instance;

  test('$MethodChannelFlutterNativeSms is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterNativeSms>());
  });

  test('getPlatformVersion', () async {
    FlutterNativeSms flutterNativeSmsPlugin = FlutterNativeSms();
    MockFlutterNativeSmsPlatform fakePlatform = MockFlutterNativeSmsPlatform();
    FlutterNativeSmsPlatform.instance = fakePlatform;

    expect(await flutterNativeSmsPlugin.getPlatformVersion(), '42');
  });
}
