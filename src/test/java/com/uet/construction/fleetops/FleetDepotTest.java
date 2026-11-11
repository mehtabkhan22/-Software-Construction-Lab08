package com.uet.construction.fleetops;

import static com.uet.construction.fleetops.TestUtil.check;

/** Tests for FleetDepot (aggregation of vehicles). */
public class FleetDepotTest {

    public static void run() {
        TestUtil.section("FleetDepotTest");
        FleetDepot depot = new FleetDepot("DEP-1", "North");
        check("getDepotId", "DEP-1".equals(depot.getDepotId()));
        check("getLocationName", "North".equals(depot.getLocationName()));

        Vehicle v1 = new Vehicle("V1", "Hilux", "E1", 2400, "Diesel");
        Vehicle v2 = new Vehicle("V2", "Bolan", "E2", 800, "Petrol");
        depot.assignVehicle(v1);
        depot.assignVehicle(v2);
        depot.assignVehicle(v2); // duplicate must be ignored
        check("assignVehicle stores references (no duplicates)", depot.getAssignedVehicles().size() == 2);
        check("depot holds the same object, not a copy", depot.getAssignedVehicles().get(0) == v1);

        boolean nullRejected = false;
        try { depot.assignVehicle(null); } catch (IllegalArgumentException e) { nullRejected = true; }
        check("assignVehicle rejects null", nullRejected);

        boolean blocked = false;
        try { depot.getAssignedVehicles().clear(); } catch (UnsupportedOperationException e) { blocked = true; }
        check("getAssignedVehicles is unmodifiable", blocked);

        check("releaseVehicle returns true for an assigned vehicle", depot.releaseVehicle(v2));
        check("releaseVehicle unlinks the vehicle", depot.getAssignedVehicles().size() == 1);
        check("released vehicle is NOT destroyed", "V2".equals(v2.getVin()));
        check("releaseVehicle returns false for an unknown vehicle", !depot.releaseVehicle(v2));

        // Lifecycle independence
        depot = null;
        check("vehicle survives depot = null", "V1".equals(v1.getVin()) && "Hilux".equals(v1.getModel()));
        FleetDepot other = new FleetDepot("DEP-2", "South");
        other.assignVehicle(v1);
        check("vehicle can be reassigned to another depot", other.getAssignedVehicles().size() == 1);
    }

    public static void main(String[] args) {
        run();
        TestUtil.summary();
    }
}
