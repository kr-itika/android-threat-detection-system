package com.example.kritika_first_flutter_app.evaluation;
import com.example.kritika_first_flutter_app.correlation.BehaviorCorrelationAnalyzer;
import com.example.kritika_first_flutter_app.correlation.CorrelationResult;
import com.example.kritika_first_flutter_app.permissions.PermissionInfo;

import java.util.List;

/**
 * Calculates final combined app risk.
 *
 * Combines:
 * - dangerous permissions
 * - hidden app behavior
 * - usage statistics
 * - behavioral patterns
 */
public class CombinedRiskEvaluator {

    /**
     * FINAL RISK SCORE
     */
    public int calculateFinalRiskScore(

            List<PermissionInfo> permissions,

            boolean isHiddenApp,

            long foregroundTime,

            int launchCount
    ) {

        int score = 0;

        /**
         * ============================
         * PERMISSION ANALYSIS
         * ============================
         */

        for (PermissionInfo permission : permissions) {

            String permissionName =
                    permission.getPermissionName();

            switch (permissionName) {

                case "android.permission.SYSTEM_ALERT_WINDOW":
                    score += 12;
                    break;

                case "android.permission.BIND_ACCESSIBILITY_SERVICE":
                    score += 15;
                    break;

                case "android.permission.RECORD_AUDIO":
                    score += 8;
                    break;

                case "android.permission.CAMERA":
                    score += 6;
                    break;

                case "android.permission.READ_SMS":
                    score += 12;
                    break;

                case "android.permission.RECEIVE_SMS":
                    score += 10;
                    break;

                case "android.permission.REQUEST_INSTALL_PACKAGES":
                    score += 15;
                    break;

                case "android.permission.PACKAGE_USAGE_STATS":
                    score += 6;
                    break;
            }
        }

        /**
         * ============================
         * HIDDEN APP BONUS RISK
         * ============================
         */

        if (isHiddenApp) {

            score += 15;
        }

        /**
         * ============================
         * USAGE ANALYSIS
         * ============================
         */

        // Very excessive usage
        if (foregroundTime >
                8 * 60 * 60 * 1000L) {

            score += 15;
        }

        // Heavy usage3
        else if (foregroundTime >
                4 * 60 * 60 * 1000L) {

            score += 8;
        }

        // Too many launches
        if (launchCount > 80) {

            score += 12;
        }

        else if (launchCount > 40) {

            score += 6;
        }

        /**
         * ============================
         * LIMIT SCORE
         * ============================
         */

        return Math.min(score, 100);
    }

    /**
     * FINAL THREAT LEVEL
     */
    public String getThreatLevel(int score) {

        if (score >= 70) {
            return "Critical Risk";
        }

        if (score >= 50) {
            return "High Risk";
        }

        if (score >= 30) {
            return "Medium Risk";
        }

        if (score >= 15) {
            return "Low Risk";
        }

        return "Safe";
    }
}