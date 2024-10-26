
import 'flutter_native_sms_platform_interface.dart';

class FlutterNativeSms {
  Future<dynamic> send({required String phone,required String smsBody,String sim = '1',bool reportByToast = false}) {
    return FlutterNativeSmsPlatform.instance.send(phone:phone,smsBody: smsBody,sim: sim,reportByToast : reportByToast);
  }
}
