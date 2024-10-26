import 'package:flutter/material.dart';

import 'package:flutter_native_sms/flutter_native_sms.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _sendSmsNativelyPlugin = FlutterNativeSms();

  @override
  void initState() {
    super.initState();
  }

  TextEditingController _phoneController = TextEditingController();
  TextEditingController _smsBodyController = TextEditingController();
  String sim = '0';

  _sendSms() async {
    var result = await _sendSmsNativelyPlugin.send(
        phone: _phoneController.value.text,
        smsBody: _smsBodyController.value.text,
        sim: sim,
        reportByToast: true
    );
    debugPrint(result.toString());
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin sms app'),
        ),
        body: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Column(
            children: [
              TextField(
                controller: _phoneController,
                decoration: const InputDecoration(label: Text('Phone number')),
              ),
              TextField(
                controller: _smsBodyController,
                decoration: const InputDecoration(label: Text('Sms body')),
                maxLines: 4,
              ),
              Padding(
                padding: const EdgeInsets.only(top:15.0),
                child: DropdownButtonFormField(
                    value: sim,
                    items: const [
                      DropdownMenuItem(
                        value: '0',
                        child: Text('Sim 1'),
                      ),
                      DropdownMenuItem(
                        value: '1',
                        child: Text('Sim 2'),
                      ),
                    ],
                    onChanged: (value) {
                      debugPrint(value);
                      setState(() {
                        sim = value!;
                      });
                    }),
              ),
              Center(
                child: ElevatedButton(
                    onPressed: _sendSms, child: const Text('Send')),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
