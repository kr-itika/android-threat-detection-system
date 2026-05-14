import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  static const platform =
  MethodChannel('installed_apps');

  List apps = [];

  Future<void> getInstalledApps() async {

    final result =
    await platform.invokeMethod(
        'getInstalledApps');

    setState(() {
      apps = result;
    });
  }

  @override
  void initState() {
    super.initState();
    getInstalledApps();
  }

  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text(
              "Installed Apps"),
        ),

        body: ListView.builder(

          itemCount: apps.length,

          itemBuilder: (context, index) {

            return ListTile(

              title: Text(
                  apps[index]['appName']
              ),

              subtitle: Text(
                  apps[index]['packageName']
              ),
            );
          },
        ),
      ),
    );
  }
}