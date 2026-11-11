package com.uet.construction.fleetops;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A depot that houses vehicles.
 *
 * AGGREGATION: vehicles are created OUTSIDE the depot and only referenced
 * here. If the depot disappears, the vehicles keep existing.
 */
public class FleetDepot {

    private final String depotId;
    private final String locationName;
    private final List<Vehicle> assignedVehicles = new ArrayList<>();

    public FleetDepot(String depotId, String locationName) {
        this.depotId = depotId;
        this.locationName = locationName;
    }

    public String getDepotId() {
        return depotId;
    }

    public String getLocationName() {
        return locationName;
    }

    /** Accepts a pre-existing vehicle created outside the depot. */
    public void assignVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicle must not be null");
        }
        if (!assignedVehicles.contains(vehicle)) {
            assignedVehicles.add(vehicle);
        }
    }

    /**
     * Unlinks the vehicle from this depot. The Vehicle object itself is NOT
     * destroyed - it stays alive and can be assigned to another depot.
     *
     * @return true if the vehicle was assigned here and has been removed
     */
    public boolean releaseVehicle(Vehicle vehicle) {
        return assignedVehicles.remove(vehicle);
    }

    /** Unmodifiable view: callers cannot bypass assignVehicle/releaseVehicle. */
    public List<Vehicle> getAssignedVehicles() {
        return Collections.unmodifiableList(assignedVehicles);
    }

    @Override
    public String toString() {
        return "FleetDepot[id=" + depotId + ", location=" + locationName
                + ", vehicles=" + assignedVehicles.size() + "]";
    }
}
