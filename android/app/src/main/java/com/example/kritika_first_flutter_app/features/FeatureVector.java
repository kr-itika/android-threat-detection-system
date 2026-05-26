package com.example.kritika_first_flutter_app.features;

/**
 * Main ML feature container.
 * This object stores ALL telemetry features
 * that will later be used for ML prediction.
 */
public class FeatureVector {

    // =========================
    // STATIC FEATURES
    // =========================

    public String packageName;

    public String installerSource;

    public boolean isSystemApp;

    public boolean isHiddenApp;

    public boolean isSideloaded;

    // =========================
    // PERMISSION FEATURES
    // =========================

    public boolean hasOverlay;

    public boolean hasAccessibility;

    public boolean hasSMS;

    public boolean hasContacts;

    public boolean hasCallLogs;

    public boolean hasCamera;

    public boolean hasMicrophone;

    public boolean hasUsageStats;

    public boolean hasNotificationAccess;

    public boolean hasBootReceiver;

    public boolean hasDeviceAdmin;

    public boolean hasPackageInstall;

    public boolean hasBatteryIgnore;

    // =========================
    // USAGE FEATURES
    // =========================

    public long dailyUsageTime;

    public long foregroundTime;

    public long backgroundTime;

    public int sessionCount;

    public double avgSessionLength;

    public long nightUsageTime;

    public int launchFrequency;

    public int rapidSwitchCount;

    // =========================
    // BEHAVIOR FEATURES
    // =========================

    public int backgroundWakeupCount;

    public int overlayTriggerCount;

    public int accessibilityEventCount;

    public int foregroundServiceUsage;

    public int persistentRestartCount;

    // =========================
    // CORRELATION FEATURES
    // =========================

    public boolean hiddenPlusOverlay;

    public boolean hiddenPlusAccessibility;

    public boolean smsPlusBackground;

    public boolean bankingPlusOverlay;

    public boolean bankingPlusHidden;

    public boolean overlayAfterBanking;

    public boolean micPlusNightActivity;

    // =========================
    // TEMPORAL FEATURES
    // =========================

    public boolean activeAfterMidnight;

    public double weekendActivityPattern;

    public long timeBetweenLaunches;

    // =========================
    // TRUST FEATURES
    // =========================

    public boolean playStoreInstalled;

    public boolean sideloaded;

    public boolean unknownSignature;

    public double packageEntropy;

    public double trustedVendorScore;

    // =========================
    // ANOMALY FEATURES
    // =========================

    public double behaviorDeviation;

    public double suddenUsageSpike;

    public double newPermissionSpike;

    public double abnormalBackgroundRatio;
}