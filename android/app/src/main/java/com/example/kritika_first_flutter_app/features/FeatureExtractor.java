package com.example.kritika_first_flutter_app.features;

import android.content.Context;

import com.example.kritika_first_flutter_app.models.AppInfo;

/**
 * Extracts ML features from app telemetry.
 */
public class FeatureExtractor {

    private final Context context;

    public FeatureExtractor(Context context) {
        this.context = context;
    }

    /**
     * Build complete ML feature vector.
     */
    public FeatureVector extractFeatures(AppInfo appInfo) {

        FeatureVector vector =
                new FeatureVector();

        // =========================
        // STATIC FEATURES
        // =========================

        vector.packageName =
                appInfo.getPackageName();

        vector.isSystemApp =
                appInfo.isSystemApp();

        vector.isHiddenApp =
                appInfo.isHidden();

        // TODO:
        // Add installer source detection

        // =========================
        // PERMISSION FEATURES
        // =========================

        // TODO:
        // Populate permission features

        // =========================
        // USAGE FEATURES
        // =========================

        // TODO:
        // Populate usage telemetry

        // =========================
        // CORRELATION FEATURES
        // =========================

        // TODO:
        // Populate behavior correlations

        return vector;
    }
}