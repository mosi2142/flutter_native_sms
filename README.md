# flutter_native_sms
A Flutter plugin for sending native sms, and also choosing from which sim to send the sms.
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
```dart

 FlutterNativeSms sms =  new FlutterNativeSms();
 sms.send(
      phone:'phone number your want to send the sms',
      smsBody:'A sms text',
      sim:'0', // choose which sim you want to send the sms from, the default is 0 which means first sim, if u have dual sim you can change the value to 1 which means second sim
 );
```

[1]: https://pub.dev/packages/permission_handler
    