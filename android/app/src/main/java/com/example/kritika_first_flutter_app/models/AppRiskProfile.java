package com.example.kritika_first_flutter_app.models;

import java.util.List;

import com.example.kritika_first_flutter_app.permissions.PermissionInfo;

/**
 * Stores complete risk analysis for one app.
 */
public class AppRiskProfile {

    private String appName;

    private String packageName;

    private boolean isSystemApp;

    private boolean isHiddenApp;

    private List<PermissionInfo> permissions;

    private int riskScore;

    private String riskLevel;

    public AppRiskProfile(
            String appName,
            String packageName,
            boolean isSystemApp,
            boolean isHiddenApp,
            List<PermissionInfo> permissions,
            int riskScore,
            String riskLevel
    ) {

        this.appName = appName;
        this.packageName = packageName;
        this.isSystemApp = isSystemApp;
        this.isHiddenApp = isHiddenApp;
        this.permissions = permissions;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public boolean isHiddenApp() {
        return isHiddenApp;
    }

    public List<PermissionInfo> getPermissions() {
        return permissions;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }
}