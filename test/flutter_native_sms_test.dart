import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_native_sms/flutter_native_sms.dart';
import 'package:flutter_native_sms/flutter_native_sms_platform_interface.dart';
import 'package:flutter_native_sms/flutter_native_sms_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterNativeSmsPlatform
    with MockPlatformInterfaceMixin
    implements FlutterNativeSmsPlatform {

  @override

  Future<dynamic> send({required String phone,required String smsBody,String sim = '1'}) => Future.value(true);
  Future<List<Map>> simInfo() => Future.value([{'success':true}]);
}

void main() {
  final FlutterNativeSmsPlatform initialPlatform = FlutterNativeSmsPlatform.instance;

  test('$MethodChannelFlutterNativeSms is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterNativeSms>());
  });

  test('send', () async {
    FlutterNativeSms flutterNativeSmsPlugin = FlutterNativeSms();
    MockFlutterNativeSmsPlatform fakePlatform = MockFlutterNativeSmsPlatform();
    FlutterNativeSmsPlatform.instance = fakePlatform;

    expect(await flutterNativeSmsPlugin.send(phone: '123', smsBody: 'test'), 'failed');
  });
  test('simInfo', () async {
    FlutterNativeSms flutterNativeSmsPlugin = FlutterNativeSms();
    MockFlutterNativeSmsPlatform fakePlatform = MockFlutterNativeSmsPlatform();
    FlutterNativeSmsPlatform.instance = fakePlatform;

    expect(await flutterNativeSmsPlugin.smInfo(), 'nothing');
  });
}
