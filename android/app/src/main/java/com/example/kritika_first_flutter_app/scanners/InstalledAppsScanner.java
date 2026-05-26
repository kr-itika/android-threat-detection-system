
package com.example.kritika_first_flutter_app.scanners;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.kritika_first_flutter_app.models.AppInfo;

import java.util.ArrayList;
import java.util.List;

/** * Scanner class for fetching and analyzing installed Android applications. */
public class InstalledAppsScanner {

    private static final String TAG = "InstalledAppsScanner";
    private Context context;

    /**     * Constructor that takes a Context for accessing system services.     * @param context Application or Activity context     */
    public InstalledAppsScanner(Context context) {
        this.context = context;
    }

    /**     * Scans and returns a list of installed apps as AppInfo objects.     * @return List of AppInfo representing installed applications     */
    public List<AppInfo> scanInstalledApps() {
        List<AppInfo> appInfoList = new ArrayList<>();

        try {
            // Get the PackageManager instance
            PackageManager pm = context.getPackageManager();
            if (pm == null) {
                Log.d(TAG, "PackageManager is null. Cannot scan apps.");
                return appInfoList;
            }

            // Get the list of installed packages
            List<PackageInfo> packages = pm.getInstalledPackages(0);
            if (packages == null) {
                Log.d(TAG, "No installed packages found.");
                return appInfoList;
            }

            Log.d(TAG, "Found " + packages.size() + " installed packages.");

            // Loop through each package and extract app info
            for (PackageInfo packageInfo : packages) {
                if (packageInfo == null || packageInfo.applicationInfo == null) {
                    Log.d(TAG, "Skipping null package or applicationInfo.");
                    continue;
                }

                // Get app name (label)
                String appName = pm.getApplicationLabel(packageInfo.applicationInfo).toString();
                // Get package name
                String packageName = packageInfo.packageName;
                // Check if it's a system app
                boolean isSystemApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;

                // Create AppInfo object
                AppInfo appInfo = new AppInfo(appName, packageName, isSystemApp, false); // Hidden app status not detected here

                // Add to the list
                appInfoList.add(appInfo);

                Log.d(TAG, "App scanned: " + appInfo.toString());
            }
        } catch (Exception e) {
            // Handle any errors during scanning
            Log.d(TAG, "Error scanning installed apps: " + e.getMessage());
        }

        Log.d(TAG, "Total apps scanned: " + appInfoList.size());
        return appInfoList;
    }
}