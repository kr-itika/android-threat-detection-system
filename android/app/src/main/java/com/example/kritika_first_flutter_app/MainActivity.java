package com.example.kritika_first_flutter_app;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.kritika_first_flutter_app.models.AppInfo;
import com.example.kritika_first_flutter_app.permissions.PermissionAnalyzer;
import com.example.kritika_first_flutter_app.permissions.PermissionInfo;
import com.example.kritika_first_flutter_app.evaluation.CombinedRiskEvaluator;
import com.example.kritika_first_flutter_app.scanners.InstalledAppsScanner;
import com.example.kritika_first_flutter_app.permissions.DangerousPermissionList;

import com.example.kritika_first_flutter_app.monitoring.UsageStatsCollector;
import com.example.kritika_first_flutter_app.monitoring.AppUsageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

/**
 * MainActivity acts as bridge between:
 *
 * Flutter Frontend
 *        ↕
 * Native Android Backend
 *
 * Responsibilities:
 * - installed app scanning
 * - permission analysis
 * - app risk evaluation
 * - sending data to Flutter
 */
public class MainActivity extends FlutterActivity {

    // MethodChannel name
    private static final String CHANNEL =
            "installed_apps";

    // Logcat tag
    private static final String TAG =
            "MainActivity";

    @Override
    public void configureFlutterEngine(
            @NonNull FlutterEngine flutterEngine
    ) {

        super.configureFlutterEngine(flutterEngine);

        //Register MethodChannel
        new MethodChannel(

                flutterEngine
                        .getDartExecutor()
                        .getBinaryMessenger(),

                CHANNEL

        ).setMethodCallHandler(

                (call, result) -> {

                    /**
                     * =====================================
                     * GET INSTALLED APPS
                     * =====================================
                     */
                    if (call.method.equals(
                            "getInstalledApps")) {

                        Log.d(
                                TAG,
                                "Received getInstalledApps call"
                        );

                        try {

                            InstalledAppsScanner scanner =
                                    new InstalledAppsScanner(
                                            getApplicationContext()
                                    );

                            List<AppInfo> appInfoList =
                                    scanner.scanInstalledApps();

                            // Final app list for Flutter
                            List<Map<String, Object>> appList =
                                    new ArrayList<>();

                            for (AppInfo appInfo : appInfoList) {

                                Map<String, Object> appMap =
                                        new HashMap<>();

                                appMap.put(
                                        "appName",
                                        appInfo.getAppName()
                                );

                                appMap.put(
                                        "packageName",
                                        appInfo.getPackageName()
                                );

                                appMap.put(
                                        "isSystemApp",
                                        appInfo.isSystemApp()
                                );

                                appMap.put(
                                        "isHiddenApp",
                                        appInfo.isHiddenApp()
                                );

                                appList.add(appMap);
                            }

                            Log.d(
                                    TAG,
                                    "Returning "
                                            + appList.size()
                                            + " installed apps"
                            );

                            result.success(appList);

                        } catch (Exception e) {

                            Log.d(
                                    TAG,
                                    "Error fetching installed apps: "
                                            + e.getMessage()
                            );

                            result.error(
                                    "ERROR",
                                    "Failed to fetch installed apps",
                                    null
                            );
                        }

                        /**
                         * =====================================
                         * GET PERMISSION ANALYSIS
                         * =====================================
                         */
                    } else if (call.method.equals(
                            "getPermissionAnalysis")) {

                        Log.d(
                                TAG,
                                "Received getPermissionAnalysis call"
                        );

                        try {

                            //Scan installed apps
                            InstalledAppsScanner scanner =
                                    new InstalledAppsScanner(
                                            getApplicationContext()
                                    );

                            List<AppInfo> appInfoList =
                                    scanner.scanInstalledApps();

                            //Initialize analyzers
                            PermissionAnalyzer analyzer =
                                    new PermissionAnalyzer(
                                            getApplicationContext()
                                    );

                            CombinedRiskEvaluator evaluator =
                                    new CombinedRiskEvaluator();

                            //Final app risk profile list
                            List<Map<String, Object>>
                                    appRiskProfileList =
                                    new ArrayList<>();

                            //Analyze each app separately
                            for (AppInfo appInfo : appInfoList) {

                                // Skip system apps
                                if (appInfo.isSystemApp()) {
                                    continue;
                                }

                                //Analyze permissions for this app
                                List<PermissionInfo> permissions =
                                        analyzer.analyzePermissionsForApp(appInfo.getPackageName());

                                //Fetch usage statistics for this app

                                UsageStatsCollector usageCollector =
                                        new UsageStatsCollector(
                                                getApplicationContext()
                                        );

                                AppUsageInfo usageInfo =
                                        usageCollector.getUsageStatsForPackage(
                                                appInfo.getPackageName()
                                        );

                                long foregroundTime = 0;

                                int launchCount = 0;

                                if (usageInfo != null) {

                                    foregroundTime =
                                            usageInfo
                                                    .getTotalTimeInForeground();

                                    launchCount =
                                            usageInfo
                                                    .getLaunchCount();
                                }

                            //Calculate app risk score

                                int riskScore =
                                        evaluator.calculateFinalRiskScore(

                                                permissions,

                                                appInfo.isHiddenApp(),

                                                foregroundTime,

                                                launchCount
                                        );

                                //Generate threat level
                                String threatLevel =
                                        evaluator.getThreatLevel(
                                                riskScore
                                        );

                                //Convert permissions into Flutter-friendly maps
                                List<Map<String, Object>>
                                        permissionList =
                                        new ArrayList<>();

                                for (PermissionInfo permissionInfo
                                        : permissions) {

                                    // Only show dangerous permissions in UI
                                    if (!permissionInfo.isDangerous()) {
                                        continue;
                                    }

                                    Map<String, Object>
                                            permissionMap =
                                            new HashMap<>();

                                    //Permission name
                                    permissionMap.put("permissionName", DangerousPermissionList.getReadablePermissionName(permissionInfo.getPermissionName()));

                                    //Risk level only(simpler for users)

                                    permissionMap.put(
                                            "riskLevel",
                                            permissionInfo
                                                    .getRiskLevel()
                                    );

                                    permissionList.add(
                                            permissionMap
                                    );
                                }
                                // Skip system apps
                                if (appInfo.isSystemApp()) {
                                    continue;
                                }

                                //Build final app profile
                                Map<String, Object>
                                        appProfileMap =
                                        new HashMap<>();

                                appProfileMap.put(
                                        "appName",
                                        appInfo.getAppName()
                                );

                                appProfileMap.put(
                                        "packageName",
                                        appInfo.getPackageName()
                                );

                                appProfileMap.put(
                                        "isSystemApp",
                                        appInfo.isSystemApp()
                                );

                                appProfileMap.put(
                                        "isHiddenApp",
                                        appInfo.isHiddenApp()
                                );

                                appProfileMap.put(
                                        "permissions",
                                        permissionList
                                );

                                appProfileMap.put(
                                        "riskScore",
                                        riskScore
                                );

                                appProfileMap.put(
                                        "foregroundTime",
                                        foregroundTime
                                );
                                Log.d(
                                        "USAGE_STATS",
                                        "App: "
                                                + appInfo.getAppName()
                                                + " | Foreground: "
                                                + foregroundTime
                                );

                                appProfileMap.put(
                                        "launchCount",
                                        launchCount
                                );

                                appProfileMap.put(
                                        "threatLevel",
                                        threatLevel
                                );

                                //Add app profile to final list
                                appRiskProfileList.add(
                                        appProfileMap
                                );

                                Log.d(
                                        TAG,
                                        "App: "
                                                + appInfo.getAppName()
                                                + " | RiskScore: "
                                                + riskScore
                                                + " | ThreatLevel: "
                                                + threatLevel
                                );
                            }

                            //Return final result to Flutter

                            Log.d(
                                    TAG,
                                    "Returning "
                                            + appRiskProfileList.size()
                                            + " app risk profiles"
                            );

                            appRiskProfileList.sort((a, b) -> Integer.compare((int) b.get("riskScore"), (int) a.get("riskScore"))
                            );
                            result.success(appRiskProfileList);

                        } catch (Exception e) {

                            Log.d(
                                    TAG,
                                    "Error analyzing permissions: "
                                            + e.getMessage()
                            );

                            result.error(
                                    "ERROR",
                                    "Failed to analyze permissions",
                                    null
                            );
                        }

                    }
                    /**
                     * =====================================
                     * GET USAGE STATS
                     * =====================================
                     */
                    else if(call.method.equals("getUsageStats")) {

                        UsageStatsCollector collector =
                                new UsageStatsCollector(this);

                        List<AppUsageInfo> usageStats =
                                collector.getUsageStats();

                        List<Map<String, Object>> resultList =
                                new ArrayList<>();

                        for (AppUsageInfo app : usageStats) {

                            Map<String, Object> map =
                                    new HashMap<>();

                            map.put(
                                    "appName",
                                    app.getAppName()
                            );

                            map.put(
                                    "packageName",
                                    app.getPackageName()
                            );

                            map.put(
                                    "foregroundTime",
                                    app.getTotalTimeInForeground()
                            );

                            map.put(
                                    "lastUsed",
                                    app.getLastTimeUsed()
                            );

                            map.put(
                                    "launchCount",
                                    app.getLaunchCount()
                            );

                            map.put(
                                    "riskLevel",
                                    app.getUsageRiskLevel()
                            );

                            map.put(
                                    "riskScore",
                                    app.getUsageRiskScore()
                            );

                            resultList.add(map);
                        }

                        result.success(resultList);
                    }

                    else {

                        //Unknown method
                        result.notImplemented();
                    }
                }
        );
    }
}