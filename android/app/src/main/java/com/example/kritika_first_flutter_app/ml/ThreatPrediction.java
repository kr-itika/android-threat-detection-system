package com.example.kritika_first_flutter_app.ml;

import java.util.List;

/**
 * Stores ML prediction result.
 */
public class ThreatPrediction {

    private final double riskScore;

    private final String riskLevel;

    private final double confidence;

    private final List<String> threatReasons;

    public ThreatPrediction(
            double riskScore,
            String riskLevel,
            double confidence,
            List<String> threatReasons
    ) {

        this.riskScore = riskScore;

        this.riskLevel = riskLevel;

        this.confidence = confidence;

        this.threatReasons = threatReasons;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public double getConfidence() {
        return confidence;
    }

    public List<String> getThreatReasons() {
        return threatReasons;
    }
}