package com.example.kritika_first_flutter_app.permissions;

/**
 * Utility class for storing:
 * - dangerous Android permissions
 * - permission risk levels
 * - readable permission names
 *
 * These permissions are commonly abused by:
 * - spyware
 * - malware
 * - banking trojans
 * - hidden surveillance apps
 */
public class DangerousPermissionList {

    /**
     * Array containing commonly dangerous Android permissions.
     */
    @SuppressWarnings("InlinedApi")
    public static final String[] DANGEROUS_PERMISSIONS = {

            // Camera access
            android.Manifest.permission.CAMERA,

            // Microphone access
            android.Manifest.permission.RECORD_AUDIO,

            // SMS reading
            android.Manifest.permission.READ_SMS,

            // SMS receiving
            android.Manifest.permission.RECEIVE_SMS,

            // SMS sending
            android.Manifest.permission.SEND_SMS,

            // Contact access
            android.Manifest.permission.READ_CONTACTS,

            // Call logs
            android.Manifest.permission.READ_CALL_LOG,

            // Phone state access
            android.Manifest.permission.READ_PHONE_STATE,

            // Location access
            android.Manifest.permission.ACCESS_FINE_LOCATION,

            // Background location
            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,

            // External storage access
            android.Manifest.permission.READ_EXTERNAL_STORAGE,

            // Notification access
            android.Manifest.permission.POST_NOTIFICATIONS
    };

    /**
     * Checks whether a permission is dangerous.
     *
     * @param permissionName Permission name to check
     * @return true if dangerous, false otherwise
     */
    public static boolean isDangerousPermission(
            String permissionName
    ) {

        // Null safety check
        if (permissionName == null) {
            return false;
        }

        // Loop through dangerous permission list
        for (String dangerousPermission
                : DANGEROUS_PERMISSIONS) {

            // Compare permission names
            if (permissionName.equals(
                    dangerousPermission)) {

                return true;
            }
        }

        return false;
    }

    /**
     * Returns readable risk level
     * for a permission.
     *
     * @param permissionName Permission name
     * @return High / Medium / Low
     */
    public static String getRiskLevel(
            String permissionName
    ) {

        if (permissionName == null) {
            return "Low";
        }

        // High-risk permissions
        if (
                permissionName.equals(
                        android.Manifest.permission.RECORD_AUDIO
                )
                        ||
                        permissionName.equals(
                                android.Manifest.permission.CAMERA
                        )
                        ||
                        permissionName.equals(
                                android.Manifest.permission.READ_SMS
                        )
                        ||
                        permissionName.equals(
                                android.Manifest.permission.RECEIVE_SMS
                        )
        ) {

            return "High";
        }

        // Medium-risk permissions
        if (
                permissionName.equals(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                        ||
                        permissionName.equals(
                                android.Manifest.permission.READ_CONTACTS
                        )
                        ||
                        permissionName.equals(
                                android.Manifest.permission.READ_CALL_LOG
                        )
        ) {

            return "Medium";
        }

        // Default risk level
        return "Low";
    }

    /**
     * Converts technical Android permission names
     * into simple user-friendly names.
     *
     * Example:
     * CAMERA → Camera Access
     * READ_SMS → Read SMS Messages
     *
     * This improves readability for
     * non-technical users.
     *
     * @param permissionName Android permission
     * @return readable permission name
     */
    public static String getReadablePermissionName(
            String permissionName
    ) {

        switch (permissionName) {

            case "android.permission.CAMERA":
                return "Camera Access";

            case "android.permission.RECORD_AUDIO":
                return "Microphone Access";

            case "android.permission.READ_CONTACTS":
                return "Contacts Access";

            case "android.permission.READ_SMS":
                return "Read SMS Messages";

            case "android.permission.RECEIVE_SMS":
                return "Receive SMS Messages";

            case "android.permission.SEND_SMS":
                return "Send SMS Messages";

            case "android.permission.ACCESS_FINE_LOCATION":
                return "Precise Location Access";

            case "android.permission.ACCESS_BACKGROUND_LOCATION":
                return "Background Location Access";

            case "android.permission.ACCESS_COARSE_LOCATION":
                return "Approximate Location Access";

            case "android.permission.READ_CALL_LOG":
                return "Call History Access";

            case "android.permission.READ_PHONE_STATE":
                return "Phone State Access";

            case "android.permission.READ_EXTERNAL_STORAGE":
                return "Storage Access";

            case "android.permission.POST_NOTIFICATIONS":
                return "Notification Access";

            default:

                // Fallback conversion
                return permissionName
                        .replace(
                                "android.permission.",
                                ""
                        )
                        .replace("_", " ");
        }
    }
}