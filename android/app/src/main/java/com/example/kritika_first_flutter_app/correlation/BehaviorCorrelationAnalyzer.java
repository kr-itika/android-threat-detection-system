package com.example.kritika_first_flutter_app.correlation;

import com.example.kritika_first_flutter_app.permissions.PermissionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Detects suspicious behavior combinations.
 */
public class BehaviorCorrelationAnalyzer {

    public CorrelationResult analyzeBehavior(

            List<PermissionInfo> permissions,

            boolean isHiddenApp,

            long foregroundTime,

            int sessionCount
    ) {

        int extraRisk = 0;

        List<String> behaviors =
                new ArrayList<>();

        boolean hasOverlay = false;

        boolean hasAccessibility = false;

        boolean hasSMS = false;

        boolean hasMic = false;

        for (PermissionInfo permission
                : permissions) {

            String name =
                    permission.getPermissionName();

            if (name.equals(
                    "android.permission.SYSTEM_ALERT_WINDOW")) {

                hasOverlay = true;
            }

            if (name.equals(
                    "android.permission.BIND_ACCESSIBILITY_SERVICE")) {

                hasAccessibility = true;
            }

            if (name.equals(
                    "android.permission.READ_SMS")) {

                hasSMS = true;
            }

            if (name.equals(
                    "android.permission.RECORD_AUDIO")) {

                hasMic = true;
            }
        }

        // Hidden + Overlay
        if (isHiddenApp && hasOverlay) {

            extraRisk += 25;

            behaviors.add(
                    "Hidden app using overlay access"
            );
        }

        // Accessibility + Heavy usage
        if (hasAccessibility
                &&
                foregroundTime >
                        4 * 60 * 60 * 1000L) {

            extraRisk += 20;

            behaviors.add(
                    "Accessibility service with heavy activity"
            );
        }

        // SMS + High sessions
        if (hasSMS && sessionCount > 40) {

            extraRisk += 15;

            behaviors.add(
                    "SMS access with excessive sessions"
            );
        }

        // Mic + Excessive usage
        if (hasMic
                &&
                foregroundTime >
                        6 * 60 * 60 * 1000L) {

            extraRisk += 20;

            behaviors.add(
                    "Microphone access with long activity"
            );
        }

        return new CorrelationResult(
                extraRisk,
                behaviors
        );
    }
}