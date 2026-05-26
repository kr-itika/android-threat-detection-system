package com.example.kritika_first_flutter_app.monitoring;

import android.app.usage.UsageStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Collects app usage statistics.
 */
public class UsageStatsCollector {

    private static final String TAG =
            "UsageStatsCollector";

    private final Context context;

    public UsageStatsCollector(Context context) {

        this.context = context;
    }

    /**
     * Fetch usage statistics.
     */
    public List<AppUsageInfo> getUsageStats() {

        List<AppUsageInfo> usageInfoList =
                new ArrayList<>();

        try {

            UsageStatsManager usageStatsManager =
                    (UsageStatsManager)
                            context.getSystemService(
                                    Context.USAGE_STATS_SERVICE
                            );

            if (usageStatsManager == null) {

                Log.d(TAG,
                        "UsageStatsManager is null");

                return usageInfoList;
            }

            long endTime =
                    System.currentTimeMillis();

            long startTime =
                    endTime - TimeUnit.DAYS.toMillis(1);

            List<UsageStats> stats =

                    usageStatsManager.queryUsageStats(

                            UsageStatsManager.INTERVAL_DAILY,

                            startTime,

                            endTime
                    );

            PackageManager packageManager =
                    context.getPackageManager();

            for (UsageStats usageStats : stats) {

                if (usageStats == null) {
                    continue;
                }

                String packageName =
                        usageStats.getPackageName();

                String appName =
                        packageName;

                try {

                    ApplicationInfo appInfo =
                            packageManager
                                    .getApplicationInfo(
                                            packageName,
                                            0
                                    );

                    appName =
                            packageManager
                                    .getApplicationLabel(
                                            appInfo
                                    ).toString();

                } catch (Exception ignored) {
                }

                long foregroundTime =
                        usageStats
                                .getTotalTimeInForeground();

                long lastUsed =
                        usageStats.getLastTimeUsed();

                int launchCount = 0;

                int usageRiskScore =
                        UsageRiskEvaluator
                                .calculateUsageRisk(
                                        foregroundTime,
                                        launchCount
                                );

                String usageRiskLevel =
                        UsageRiskEvaluator
                                .getRiskLevel(
                                        usageRiskScore
                                );

                AppUsageInfo info =
                        new AppUsageInfo(

                                appName,
                                packageName,
                                foregroundTime,
                                lastUsed,
                                launchCount,
                                usageRiskLevel,
                                usageRiskScore
                        );

                usageInfoList.add(info);
            }

        } catch (Exception e) {

            Log.d(
                    TAG,
                    "Error: " + e.getMessage()
            );
        }

        return usageInfoList;
    }

    public AppUsageInfo getUsageStatsForPackage(
            String packageName
    ) {

        try {

            UsageStatsManager usageStatsManager =
                    (UsageStatsManager)
                            context.getSystemService(
                                    Context.USAGE_STATS_SERVICE
                            );

            if (usageStatsManager == null) {
                return null;
            }

            long endTime =
                    System.currentTimeMillis();

            long startTime =
                    endTime - (24L * 60 * 60 * 1000);

            List<UsageStats> stats =
                    usageStatsManager.queryUsageStats(

                            UsageStatsManager.INTERVAL_DAILY,

                            startTime,

                            endTime
                    );

            if (stats == null) {
                return null;
            }

            PackageManager packageManager =
                    context.getPackageManager();

            for (UsageStats usageStats : stats) {

                if (usageStats == null) {
                    continue;
                }

                if (!usageStats
                        .getPackageName()
                        .equals(packageName)) {

                    continue;
                }

                String appName =
                        packageName;

                try {

                    ApplicationInfo appInfo =
                            packageManager
                                    .getApplicationInfo(
                                            packageName,
                                            0
                                    );

                    appName =
                            packageManager
                                    .getApplicationLabel(
                                            appInfo
                                    ).toString();

                } catch (Exception ignored) {
                }

                long foregroundTime =
                        usageStats
                                .getTotalTimeInForeground();

                long lastUsed =
                        usageStats.getLastTimeUsed();

                int launchCount = 0;

                UsageEvents usageEvents =
                        usageStatsManager.queryEvents(
                                startTime,
                                endTime
                        );

                UsageEvents.Event event =
                        new UsageEvents.Event();

                while (usageEvents.hasNextEvent()) {

                    usageEvents.getNextEvent(event);

                    if (event.getPackageName() == null) {
                        continue;
                    }

                    // Count app launches
                    long lastSessionTime = 0;

                    while (usageEvents.hasNextEvent()) {

                        usageEvents.getNextEvent(event);

                        if (event.getPackageName() == null) {
                            continue;
                        }

                        if (event.getPackageName().equals(packageName)
                                &&
                                event.getEventType()
                                        == UsageEvents.Event.ACTIVITY_RESUMED) {

                            long currentTime =
                                    event.getTimeStamp();

                            // Ignore noisy rapid transitions
                            if (currentTime - lastSessionTime
                                    > 15000) {

                                launchCount++;

                                lastSessionTime =
                                        currentTime;
                            }
                        }
                    }
                }
                int riskScore = 0;

                String riskLevel = "Safe";

                return new AppUsageInfo(

                        appName,

                        packageName,

                        foregroundTime,

                        lastUsed,

                        launchCount,

                        riskLevel,

                        riskScore
                );
            }

        } catch (Exception ignored) {
        }

        return null;
    }
}