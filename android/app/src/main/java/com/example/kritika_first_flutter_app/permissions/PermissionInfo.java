package com.example.kritika_first_flutter_app.permissions;

/**
 * Model class for storing information about a dangerous permission.
 *
 * This class stores:
 * - permission name
 * - granted status
 * - risk level
 * - dangerous status
 */
public class PermissionInfo {

    // Name of the permission
    // Example: android.permission.CAMERA
    private String permissionName;

    // True if permission is granted
    private boolean isGranted;

    // Risk level of the permission
    // Example: High, Medium, Low
    private String riskLevel;

    // True if permission is considered dangerous
    private boolean isDangerous;

    /**
     * Default constructor.
     */
    public PermissionInfo() {
    }

    /**
     * Parameterized constructor.
     *
     * @param permissionName Name of the permission
     * @param isGranted True if granted
     * @param riskLevel Risk level of permission
     * @param isDangerous True if dangerous
     */
    public PermissionInfo(
            String permissionName,
            boolean isGranted,
            String riskLevel,
            boolean isDangerous
    ) {
        this.permissionName = permissionName;
        this.isGranted = isGranted;
        this.riskLevel = riskLevel;
        this.isDangerous = isDangerous;
    }

    /**
     * Returns permission name.
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * Sets permission name.
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * Returns granted status.
     */
    public boolean isGranted() {
        return isGranted;
    }

    /**
     * Sets granted status.
     */
    public void setGranted(boolean granted) {
        isGranted = granted;
    }

    /**
     * Returns risk level.
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * Sets risk level.
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * Returns dangerous status.
     */
    public boolean isDangerous() {
        return isDangerous;
    }

    /**
     * Sets dangerous status.
     */
    public void setDangerous(boolean dangerous) {
        isDangerous = dangerous;
    }

    /**
     * Converts object into readable string.
     */
    @Override
    public String toString() {
        return "PermissionInfo{" +
                "permissionName='" + permissionName + '\'' +
                ", isGranted=" + isGranted +
                ", riskLevel='" + riskLevel + '\'' +
                ", isDangerous=" + isDangerous +
                '}';
    }
}