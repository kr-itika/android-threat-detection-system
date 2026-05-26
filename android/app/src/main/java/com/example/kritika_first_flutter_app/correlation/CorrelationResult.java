package com.example.kritika_first_flutter_app.correlation;

import java.util.List;

/**
 * Stores suspicious behavior correlations.
 */
public class CorrelationResult {

    private final int correlationRisk;

    private final List<String> detectedBehaviors;

    public CorrelationResult(
            int correlationRisk,
            List<String> detectedBehaviors
    ) {

        this.correlationRisk =
                correlationRisk;

        this.detectedBehaviors =
                detectedBehaviors;
    }

    public int getCorrelationRisk() {
        return correlationRisk;
    }

    public List<String> getDetectedBehaviors() {
        return detectedBehaviors;
    }
}