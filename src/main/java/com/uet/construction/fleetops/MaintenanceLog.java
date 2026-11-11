package com.uet.construction.fleetops;

/**
 * One maintenance entry of a Vehicle.
 *
 * COMPOSITION: a log entry has no meaning outside its parent Vehicle, so
 * only Vehicle creates them (through addMaintenanceRecord).
 */
public class MaintenanceLog {

    private String logId;
    private String serviceDescription;
    private double serviceCost;

    public MaintenanceLog(String logId, String serviceDescription, double serviceCost) {
        this.logId = logId;
        this.serviceDescription = serviceDescription;
        this.serviceCost = serviceCost;
    }

    /** Copy constructor used for defensive duplication. */
    public MaintenanceLog(MaintenanceLog other) {
        this.logId = other.logId;
        this.serviceDescription = other.serviceDescription;
        this.serviceCost = other.serviceCost;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    @Override
    public String toString() {
        return "Log[id=" + logId + ", " + serviceDescription
                + ", cost=" + serviceCost + "]";
    }
}
