# flutter_native_sms
A Flutter plugin for sending sms and also retrieving your sim's info , and you can also choosing from which sim to send the sms.
Note : this plugin works only for android. Ios is not implemented yet.

## Getting Started
```dart
  import 'package:flutter_native_sms/flutter_native_sms.dart';
```
To use this package import it first

## Usage

First u need to add required permissions :
```xml
    <uses-permission android:name="android.permission.SEND_SMS" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_NUMBERS" />
```
you may also need to request sms permission from user with [`permission_handler`][1] package, or any other package.
```dart
   await Permission.sms.request();
   await Permission.phone.request();

```

Sending sms
```dart

 FlutterNativeSms sms =  new FlutterNativeSms();
 sms.send(
      phone:'phone number your want to send the sms',
      smsBody:'A sms text',
      sim:'0', // choose which sim you want to send the sms from, the default is 0 which means first sim, if u have dual sim you can change the value to 1 which means second sim
 );
```

retrieving info of your current sim's of your phone

```dart
  FlutterNativeSms sms =  new FlutterNativeSms();
  print(sms.simInfo());
// prints : 
// [
//   {
//     COUNTRY_CODE: your_country_code,
//     PHONE_NUMBER: your_phone_number,
//     CARD_ID: card_id,
//     CARRIER_NAME: you_operator_name,
//     DISPLAY_NAME: your_custom_sim_name_that_you_defined_in_your_phone,
//     SIM_SLOT_INDEX: slot_of_your_sim_number, 
//     SUBSCRIPTION_ID: subscription_id, 
//   },
// ]
```

[1]: https://pub.dev/packages/permission_handler
    