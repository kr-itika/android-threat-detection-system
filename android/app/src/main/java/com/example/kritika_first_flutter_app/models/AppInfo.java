package com.example.kritika_first_flutter_app.models;

import java.util.HashMap;
import java.util.Map;

/** * Model class to store information about an installed app. */
public class AppInfo {
    // The display name of the app
    private String appName;
    // The package name of the app
    private String packageName;
    // True if the app is a system app, false otherwise
    private boolean isSystemApp;
    // True if the app is hidden, false otherwise
    private boolean isHiddenApp;

    /**     * Default constructor.     */
    public AppInfo() {
    }

    /**     * Parameterized constructor to initialize all fields.     * @param appName The display name of the app     * @param packageName The package name of the app     * @param isSystemApp Whether the app is a system app     * @param isHiddenApp Whether the app is hidden     */
    public AppInfo(String appName, String packageName, boolean isSystemApp, boolean isHiddenApp) {
        this.appName = appName;
        this.packageName = packageName;
        this.isSystemApp = isSystemApp;
        this.isHiddenApp = isHiddenApp;
    }

    // Getter and setter for appName
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }

    // Getter and setter for packageName
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    // Getter and setter for isSystemApp
    public boolean isSystemApp() {
        return isSystemApp;
    }
    public void setSystemApp(boolean systemApp) {
        isSystemApp = systemApp;
    }

    // Getter and setter for isHiddenApp
    public boolean isHiddenApp() {
        return isHiddenApp;
    }
    public void setHiddenApp(boolean hiddenApp) {
        isHiddenApp = hiddenApp;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> appMap = new HashMap<>();
        appMap.put("appName", appName);
        appMap.put("packageName", packageName);
        appMap.put("isSystemApp", isSystemApp);
        appMap.put("isHiddenApp", isHiddenApp);
        return appMap;
    }

    /**     * Returns a string representation of the AppInfo object.     */
    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", isSystemApp=" + isSystemApp +
                ", isHiddenApp=" + isHiddenApp +
                '}';
    }
}
