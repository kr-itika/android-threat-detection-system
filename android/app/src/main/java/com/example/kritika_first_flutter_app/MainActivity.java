package com.example.kritika_first_flutter_app;

import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FlutterActivity {

    private static final String CHANNEL = "installed_apps";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        new MethodChannel(
                flutterEngine.getDartExecutor().getBinaryMessenger(),
                CHANNEL
        ).setMethodCallHandler(
                (call, result) -> {

                    if (call.method.equals("getInstalledApps")) {

                        PackageManager pm = getPackageManager();

                        List<PackageInfo> packages =
                                pm.getInstalledPackages(0);

                        List<Map<String, String>> appList =
                                new ArrayList<>();

                        for (PackageInfo packageInfo : packages) {

                            Map<String, String> appData =
                                    new HashMap<>();

                            String appName =
                                    packageInfo.applicationInfo
                                            .loadLabel(pm)
                                            .toString();

                            String packageName =
                                    packageInfo.packageName;

                            appData.put("appName", appName);
                            appData.put("packageName", packageName);

                            appList.add(appData);
                        }

                        result.success(appList);

                    } else {
                        result.notImplemented();
                    }
                }
        );
    }
}