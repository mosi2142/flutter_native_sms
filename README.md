# send_sms_natively
A Flutter plugin for sending native sms. for android only

## Getting Started
```dart
  import 'package:flutter_native_sms/flutter_native_sms.dart';
```
To use this package import it first

## Usage

First u need to add required permissions :
```xml
    <uses-permission android:name="android.permission.SEND_SMS" />
```
also you may also need to request sms permission from user with [`permission_handler`][1]  package
```dart
   await Permission.sms.request();
```
```dart
  // Returns either true on successful send or throws exception
 FlutterNativeSms sms =  new FlutterNativeSms();
 sms.send(phone:'phone number your want to send the sms', smsBody:'A sms text',sim:'sim1');
```
note : sim is not implemented yet

[1]: https://pub.dev/packages/permission_handler
    