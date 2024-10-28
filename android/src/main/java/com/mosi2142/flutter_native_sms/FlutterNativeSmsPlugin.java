package com.mosi2142.flutter_native_sms;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** FlutterNativeSmsPlugin */
public class FlutterNativeSmsPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_native_sms");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
  }

  // this method is copied from
  // https://github.com/akshaydoshi2/flutter_sim_data
  public JSONArray simData(){
    SubscriptionManager subscriptionManager = null;
    JSONArray array = new JSONArray();
    subscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    List<SubscriptionInfo> infoList = subscriptionManager != null ? subscriptionManager.getActiveSubscriptionInfoList() : null;
    for(int i = 0; i < Objects.requireNonNull(infoList).size(); i++){
      JSONObject map = new JSONObject();
      try{
        map.put("COUNTRY_CODE", infoList.get(i).getCountryIso());
//          Log.i("COUNTRY_CODE", infoList.get(i).getCountryIso());

//        Log.i("ICC_ID", infoList.get(i).getIccId());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//          Log.i("MCC_STRING", Objects.requireNonNull(infoList.get(i).getMccString()));
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//          Log.i("MNC_STRING", Objects.requireNonNull(infoList.get(i).getMncString()));
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            Log.i("GET_PHONE_NUMBER", subscriptionManager.getPhoneNumber(infoList.get(i).getSubscriptionId()));
          map.put("PHONE_NUMBER", subscriptionManager.getPhoneNumber(infoList.get(i).getSubscriptionId()));
        }else{
//            Log.i("GET_NUMBER", infoList.get(i).getNumber());
          map.put("PHONE_NUMBER", infoList.get(i).getNumber());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            Log.i("CARD_ID", String.valueOf(infoList.get(i).getCardId()));
          map.put("CARD_ID", infoList.get(i).getCardId());
        }else{
          map.put("CARD_ID", null);
        }

//          Log.i("CARRIER_NAME", (String) infoList.get(i).getCarrierName());
        map.put("CARRIER_NAME", infoList.get(i).getCarrierName());

//          Log.i("DISPLAY_NAME", (String) infoList.get(i).getDisplayName());
        map.put("DISPLAY_NAME", infoList.get(i).getDisplayName());

//          Log.i("SIM_SLOT_INDEX", String.valueOf(infoList.get(i).getSimSlotIndex()));
        map.put("SIM_SLOT_INDEX", infoList.get(i).getSimSlotIndex());

//          Log.i("SUBSCRIPTION_ID", String.valueOf(infoList.get(i).getSubscriptionId()));
        map.put("SUBSCRIPTION_ID", infoList.get(i).getSubscriptionId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            Log.i("IS_EMBEDDED", String.valueOf(infoList.get(i).isEmbedded()));
          map.put("IS_EMBEDDED", infoList.get(i).isEmbedded());
        }else{
          map.put("IS_EMBEDDED", false);
        }
        array.put(map);
      }catch(JSONException e){
        e.printStackTrace();
        array = null;
        break;
      }
    }
    return array;
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    if(call.method.equals("send")){
      try{
        List arguments = (List) call.arguments;

        String phoneNumber = String.valueOf(arguments.get(0));
        String textBody = String.valueOf(arguments.get(1));
        int sim = Integer.parseInt(String.valueOf(arguments.get(2)));

        JSONArray simdatas = this.simData();

        int subId = simdatas.getJSONObject(sim).getInt("SUBSCRIPTION_ID");

        String sent = "SMS_SENT";
        String delivered = "SMS_DELIVERED";
        PendingIntent sendPendingIntent;
        PendingIntent deliveryPendingIntent;

        SmsManager smsManager = SmsManager.getSmsManagerForSubscriptionId(subId);

        sendPendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                new Intent(sent),
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );
        deliveryPendingIntent = PendingIntent.getBroadcast(
                context,
                2,
                new Intent(delivered),
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        smsManager.sendTextMessage(phoneNumber, null, textBody, sendPendingIntent, deliveryPendingIntent);

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
