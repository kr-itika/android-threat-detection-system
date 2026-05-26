package com.example.kritika_first_flutter_app.monitoring;

/**
 * Evaluates behavioral usage risk.
 */
public class UsageRiskEvaluator {

    /**
     * Returns usage risk score.
     */
    public static int calculateUsageRisk(

            long foregroundTime,
            int launchCount
    ) {

        int score = 0;

        // Extremely excessive usage
        if (foregroundTime > 8 * 60 * 60 * 1000L) {

            score += 25;
        }

        // Heavy usage
        else if (foregroundTime > 4 * 60 * 60 * 1000L) {

            score += 15;
        }

        // Frequent launches
        if (launchCount > 80) {

            score += 20;
        }

        else if (launchCount > 40) {

            score += 10;
        }

        return Math.min(score, 100);
    }

    /**
     * Returns readable risk level.
     */
    public static String getRiskLevel(int score) {

        if (score >= 70) {
            return "Critical Risk";
        }

        if (score >= 50) {
            return "High Risk";
        }

        if (score >= 30) {
            return "Medium Risk";
        }

        if (score >= 10) {
            return "Low Risk";
        }

        return "Safe";
    }
}