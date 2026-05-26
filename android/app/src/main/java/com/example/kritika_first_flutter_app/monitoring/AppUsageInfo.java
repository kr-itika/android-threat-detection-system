package com.example.kritika_first_flutter_app.monitoring;

/**
 * Model class for storing app usage information.
 */
public class AppUsageInfo {

    private String appName;

    private String packageName;

    private long totalTimeInForeground;

    private long lastTimeUsed;

    private int launchCount;

    private String usageRiskLevel;

    private int usageRiskScore;

    public AppUsageInfo(
            String appName,
            String packageName,
            long totalTimeInForeground,
            long lastTimeUsed,
            int launchCount,
            String usageRiskLevel,
            int usageRiskScore
    ) {

        this.appName = appName;
        this.packageName = packageName;
        this.totalTimeInForeground = totalTimeInForeground;
        this.lastTimeUsed = lastTimeUsed;
        this.launchCount = launchCount;
        this.usageRiskLevel = usageRiskLevel;
        this.usageRiskScore = usageRiskScore;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public long getTotalTimeInForeground() {
        return totalTimeInForeground;
    }

    public long getLastTimeUsed() {
        return lastTimeUsed;
    }

    public int getLaunchCount() {
        return launchCount;
    }

    public String getUsageRiskLevel() {
        return usageRiskLevel;
    }

    public int getUsageRiskScore() {
        return usageRiskScore;
    }
}