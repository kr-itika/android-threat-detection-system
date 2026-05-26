package com.example.kritika_first_flutter_app.permissions;

import android.util.Log;

import java.util.List;

/**
 * PermissionRiskEvaluator is responsible for:
 * - calculating app risk score
 * - generating user-friendly threat levels
 * - keeping scores normalized between 0–100
 *
 * IMPORTANT:
 * Permissions alone DO NOT prove malware.
 * This evaluator only estimates potential risk.
 *
 * Future modules like:
 * - background monitoring
 * - accessibility abuse detection
 * - overlay detection
 * - behavioral analysis
 *
 * will improve threat accuracy.
 */
public class PermissionRiskEvaluator {

    // Tag used for Logcat debugging
    private static final String TAG =
            "PermissionRiskEvaluator";

    /**
     * Calculate risk score for an app
     *
     * @param permissions List of app permissions
     * @return normalized risk score (0–100)
     */
    public int calculateRiskScore(
            List<PermissionInfo> permissions
    ) {

        int riskScore = 0;

        // Null safety check
        if (permissions == null) {

            Log.d(
                    TAG,
                    "Permission list is null."
            );

            return 0;
        }

        // Loop through app permissions
        for (PermissionInfo permission : permissions) {

            // Skip invalid permission objects
            if (permission == null) {
                continue;
            }

            /**
             * Only evaluate:
             * - dangerous permissions
             * - granted permissions
             */
            if (permission.isDangerous()
                    && permission.isGranted()) {

                int score =
                        getPermissionScore(
                                permission.getPermissionName()
                        );

                // Add permission score
                riskScore += score;

                Log.d(
                        TAG,
                        "Permission: "
                                + permission.getPermissionName()
                                + " | Score: +"
                                + score
                );
            }
        }

        /**
         * IMPORTANT:
         * Normalize score to 0–100 range
         */
        riskScore =
                Math.min(riskScore, 100);
        // Additional risk for suspicious combinations

        boolean hasMicrophone = false;
        boolean hasContacts = false;
        boolean hasSms = false;
        boolean hasLocation = false;

        for (PermissionInfo permission : permissions) {

            String name =
                    permission.getPermissionName();

            if (name.equals(
                    "android.permission.RECORD_AUDIO")) {

                hasMicrophone = true;
            }

            if (name.equals(
                    "android.permission.READ_CONTACTS")) {

                hasContacts = true;
            }

            if (name.equals(
                    "android.permission.READ_SMS")
                    ||
                    name.equals(
                            "android.permission.RECEIVE_SMS")) {

                hasSms = true;
            }

            if (name.equals(
                    "android.permission.ACCESS_FINE_LOCATION")) {

                hasLocation = true;
            }
        }

// Suspicious combinations

        if (hasMicrophone && hasContacts) {

            riskScore += 10;
        }

        if (hasSms && hasContacts) {

            riskScore += 15;
        }

        if (hasMicrophone && hasLocation) {

            riskScore += 8;
        }

        Log.d(
                TAG,
                "Final Normalized Risk Score: "
                        + riskScore
        );

        return riskScore;
    }

    /**
     * Generate readable threat level
     * based on risk score.
     *
     * @param riskScore Final risk score
     * @return readable threat level
     */
    public String getThreatLevel(int riskScore) {

        if (riskScore <= 20) {
            return "Safe";
        }

        if (riskScore <= 40) {
            return "Low Risk";
        }

        if (riskScore <= 60) {
            return "Medium Risk";
        }

        if (riskScore <= 80) {
            return "High Risk";
        }

        return "Critical Risk";
    }

    /**
     * Returns score for each permission.
     *
     * IMPORTANT:
     * Scores are intentionally conservative.
     *
     * Permissions alone should NOT
     * automatically label apps as malware.
     *
     * @param permissionName Android permission
     * @return risk score
     */
    private int getPermissionScore(
            String permissionName
    ) {

        switch (permissionName) {

            // SMS permissions
            case "android.permission.READ_SMS":

            case "android.permission.RECEIVE_SMS":
                return 10;

            // Microphone access
            case "android.permission.RECORD_AUDIO":
                return 8;

            // Camera access
            case "android.permission.CAMERA":
                return 6;

            // Location access
            case "android.permission.ACCESS_FINE_LOCATION":
                return 5;

            // Contact access
            case "android.permission.READ_CONTACTS":
                return 4;

            /**
             * Default low score
             * for less sensitive permissions
             */
            default:
                return 1;
        }
    }
}