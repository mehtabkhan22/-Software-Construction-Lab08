package com.uet.construction.fleetops;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A fleet vehicle.
 *
 * COMPOSITION (strong ownership, same lifetime):
 *   - Engine           : created INSIDE the constructor from raw parameters.
 *   - MaintenanceLog   : created INSIDE addMaintenanceRecord().
 *
 * AGGREGATION (independent lifetime, shared reference):
 *   - Driver           : created outside and only referenced here.
 *
 * REPRESENTATION EXPOSURE is prevented because getEngine() and
 * getMaintenanceHistory() return defensive copies, never internal references.
 */
public class Vehicle {

    private final String vin;
    private final String model;

    private final Engine engine;                      // composition
    private final List<MaintenanceLog> maintenanceLogs; // composition
    private Driver driver;                            // aggregation (optional)

    /**
     * Restricted instantiation: no Engine object is accepted from outside.
     * The Vehicle builds its own Engine from raw parameters.
     */
    public Vehicle(String vin, String model,
                   String engineNumber, int displacementCC, String fuelType) {
        this.vin = vin;
        this.model = model;
        this.engine = new Engine(engineNumber, displacementCC, fuelType);
        this.maintenanceLogs = new ArrayList<>();
    }

    public String getVin() {
        return vin;
    }

    public String getModel() {
        return model;
    }

    // ---------------- Composition: Engine ----------------

    /** Returns a deep defensive copy - NEVER this.engine. */
    public Engine getEngine() {
        return new Engine(this.engine);
    }

    // ---------------- Composition: MaintenanceLog ----------------

    /** The only way to add a log; the MaintenanceLog is created internally. */
    public void addMaintenanceRecord(String logId, String description, double cost) {
        maintenanceLogs.add(new MaintenanceLog(logId, description, cost));
    }

    /**
     * Returns an unmodifiable list made of deep clones of every log entry.
     * Changing the list or any returned entry cannot change the real history.
     */
    public List<MaintenanceLog> getMaintenanceHistory() {
        List<MaintenanceLog> copy = new ArrayList<>();
        for (MaintenanceLog log : maintenanceLogs) {
            copy.add(new MaintenanceLog(log));
        }
        return Collections.unmodifiableList(copy);
    }

    public int getMaintenanceCount() {
        return maintenanceLogs.size();
    }

    // ---------------- Aggregation: Driver ----------------

    public void assignDriver(Driver driver) {
        this.driver = driver;
    }

    public void removeDriver() {
        this.driver = null;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return "Vehicle[vin=" + vin + ", model=" + model + ", " + engine
                + ", logs=" + maintenanceLogs.size()
                + ", driver=" + (driver == null ? "none" : driver.getFullName()) + "]";
    }
}
