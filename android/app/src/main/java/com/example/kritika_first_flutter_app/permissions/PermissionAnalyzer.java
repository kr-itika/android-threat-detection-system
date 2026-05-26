package com.example.kritika_first_flutter_app.permissions;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * PermissionAnalyzer is responsible for:
 * - fetching permissions used by installed apps
 * - checking granted permissions
 * - detecting dangerous permissions
 * - generating PermissionInfo objects
 *
 * IMPORTANT:
 * This analyzer now focuses ONLY on
 * GRANTED permissions because ungranted
 * permissions cannot actively harm users.
 */
public class PermissionAnalyzer {

    // Tag used for Logcat debugging
    private static final String TAG = "PermissionAnalyzer";

    // Application context
    private Context context;

    /**
     * Constructor
     *
     * @param context Application context
     */
    public PermissionAnalyzer(Context context) {
        this.context = context;
    }

    /**
     * Analyze permissions used by installed apps
     *
     * @return List of PermissionInfo objects
     */
    public List<PermissionInfo> analyzePermissions() {

        // Final list storing analyzed permission information
        List<PermissionInfo> permissionInfoList =
                new ArrayList<>();

        try {

            // Get PackageManager instance
            PackageManager pm =
                    context.getPackageManager();

            // Null safety check
            if (pm == null) {

                Log.d(TAG,
                        "PackageManager is null.");

                return permissionInfoList;
            }

            // Fetch installed packages with permissions
            List<PackageInfo> packages =
                    pm.getInstalledPackages(
                            PackageManager.GET_PERMISSIONS
                    );

            Log.d(
                    TAG,
                    "Installed packages found: "
                            + packages.size()
            );

            // Loop through installed packages
            for (PackageInfo packageInfo : packages) {

                // Skip invalid package data
                if (packageInfo == null
                        || packageInfo.requestedPermissions == null) {

                    continue;
                }

                // Get package name
                String packageName =
                        packageInfo.packageName;

                Log.d(
                        TAG,
                        "Analyzing package: "
                                + packageName
                );

                // Loop through requested permissions
                for (int i = 0;
                     i < packageInfo.requestedPermissions.length;
                     i++) {

                    // Get permission name
                    String permissionName =
                            packageInfo.requestedPermissions[i];

                    // Stores granted status
                    boolean isGranted = false;

                    // Stores dangerous status
                    boolean isDangerous;

                    // Default risk level
                    String riskLevel = "Safe";

                    /**
                     * Check whether permission is granted
                     */
                    if (packageInfo.requestedPermissionsFlags != null
                            && i < packageInfo.requestedPermissionsFlags.length) {

                        isGranted =
                                (packageInfo.requestedPermissionsFlags[i]
                                        & PackageInfo.REQUESTED_PERMISSION_GRANTED)
                                        != 0;
                    }

                    /**
                     * IMPORTANT:
                     * Ignore permissions that are NOT granted.
                     *
                     * This massively improves output quality because
                     * apps cannot abuse permissions that users denied.
                     */
                    if (!isGranted) {
                        continue;
                    }

                    Log.d(
                            TAG,
                            "Granted Permission: "
                                    + permissionName
                    );

                    /**
                     * Check whether permission is dangerous
                     */
                    isDangerous =
                            DangerousPermissionList
                                    .isDangerousPermission(permissionName);

                    /**
                     * If permission is dangerous,
                     * fetch its risk level.
                     */
                    if (isDangerous) {

                        Log.d(
                                TAG,
                                "Dangerous permission detected: "
                                        + permissionName
                        );

                        riskLevel =
                                DangerousPermissionList
                                        .getRiskLevel(permissionName);
                    }

                    /**
                     * Create PermissionInfo object
                     */
                    PermissionInfo permissionInfo =
                            new PermissionInfo(
                                    permissionName,
                                    isGranted,
                                    riskLevel,
                                    isDangerous
                            );

                    // Add permission info to final list
                    permissionInfoList.add(permissionInfo);
                }
            }

        } catch (Exception e) {

            // Handle errors safely
            Log.d(
                    TAG,
                    "Error analyzing permissions: "
                            + e.getMessage()
            );
        }

        Log.d(
                TAG,
                "Total granted permissions analyzed: "
                        + permissionInfoList.size()
        );

        return permissionInfoList;
    }

    /**
     * Analyze permissions for ONE specific app
     *
     * @param packageName Package name of app
     * @return List of PermissionInfo objects
     */
    public List<PermissionInfo> analyzePermissionsForApp(
            String packageName
    ) {

        List<PermissionInfo> permissionInfoList =
                new ArrayList<>();

        try {

            PackageManager pm =
                    context.getPackageManager();

            if (pm == null) {

                Log.d(
                        TAG,
                        "PackageManager is null."
                );

                return permissionInfoList;
            }

            PackageInfo packageInfo =
                    pm.getPackageInfo(
                            packageName,
                            PackageManager.GET_PERMISSIONS
                    );

            if (packageInfo == null
                    || packageInfo.requestedPermissions == null) {

                return permissionInfoList;
            }

            // Loop through permissions
            for (int i = 0;
                 i < packageInfo.requestedPermissions.length;
                 i++) {

                String permissionName =
                        packageInfo.requestedPermissions[i];

                boolean isGranted = false;

                boolean isDangerous;

                String riskLevel = "Safe";

                // Check granted status
                if (packageInfo.requestedPermissionsFlags != null
                        && i < packageInfo.requestedPermissionsFlags.length) {

                    isGranted =
                            (packageInfo.requestedPermissionsFlags[i]
                                    & PackageInfo.REQUESTED_PERMISSION_GRANTED)
                                    != 0;
                }

                /**
                 * Ignore ungranted permissions
                 */
                if (!isGranted) {
                    continue;
                }

                // Check dangerous permission
                isDangerous =
                        DangerousPermissionList
                                .isDangerousPermission(permissionName);

                // Get risk level
                if (isDangerous) {

                    riskLevel =
                            DangerousPermissionList
                                    .getRiskLevel(permissionName);
                }

                // Create permission object
                PermissionInfo permissionInfo =
                        new PermissionInfo(
                                permissionName,
                                isGranted,
                                riskLevel,
                                isDangerous
                        );

                permissionInfoList.add(permissionInfo);
            }

        } catch (Exception e) {

            Log.d(
                    TAG,
                    "Error analyzing app permissions: "
                            + e.getMessage()
            );
        }

        return permissionInfoList;
    }
}