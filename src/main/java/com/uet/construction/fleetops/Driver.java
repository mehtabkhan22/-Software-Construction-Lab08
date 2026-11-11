package com.uet.construction.fleetops;

/**
 * A fleet driver.
 *
 * AGGREGATION: a Driver exists independently of any Vehicle. A Vehicle only
 * holds a reference to a Driver; it never creates or destroys one.
 */
public class Driver {

    private String driverId;
    private String fullName;
    private String licenseNumber;

    public Driver(String driverId, String fullName, String licenseNumber) {
        this.driverId = requireText(driverId, "driverId");
        this.fullName = requireText(fullName, "fullName");
        this.licenseNumber = requireText(licenseNumber, "licenseNumber");
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = requireText(driverId, "driverId");
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = requireText(fullName, "fullName");
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = requireText(licenseNumber, "licenseNumber");
    }

    /** Rejects null, empty and whitespace-only values. */
    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be null or blank");
        }
        return value.trim();
    }

    @Override
    public String toString() {
        return "Driver[id=" + driverId + ", name=" + fullName
                + ", license=" + licenseNumber + "]";
    }
}
