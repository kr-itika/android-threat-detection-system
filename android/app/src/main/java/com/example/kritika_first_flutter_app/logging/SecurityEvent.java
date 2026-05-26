package com.example.kritika_first_flutter_app.logging;

/**
 * Represents a security-related event.
 */
public class SecurityEvent {

    private final String eventType;

    private final String packageName;

    private final long timestamp;

    private final String severity;

    private final String metadata;

    public SecurityEvent(
            String eventType,
            String packageName,
            long timestamp,
            String severity,
            String metadata
    ) {

        this.eventType = eventType;

        this.packageName = packageName;

        this.timestamp = timestamp;

        this.severity = severity;

        this.metadata = metadata;
    }

    public String getEventType() {
        return eventType;
    }

    public String getPackageName() {
        return packageName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSeverity() {
        return severity;
    }

    public String getMetadata() {
        return metadata;
    }
}