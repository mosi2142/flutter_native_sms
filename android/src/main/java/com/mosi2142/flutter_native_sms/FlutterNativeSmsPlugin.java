package com.mosi2142.flutter_native_sms;

import androidx.annotation.NonNull;

import java.util.List;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import android.telephony.SmsManager;

/** FlutterNativeSmsPlugin */
public class FlutterNativeSmsPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_native_sms");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    if(call.method.equals("send")){
      try{
        List arguments = (List) call.arguments;

        String phoneNumber = String.valueOf(arguments.get(0));
        String textBody = String.valueOf(arguments.get(1));
        String sim = String.valueOf(arguments.get(2));

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, textBody, null, null);

        result.success(true);
      } catch (Exception ex) {
        ex.printStackTrace();
        result.error("Something went wrong", "Send sms exception : "+ ex.getMessage(), null);
      }

    }else{
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
