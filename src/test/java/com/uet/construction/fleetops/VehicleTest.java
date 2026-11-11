package com.uet.construction.fleetops;

import static com.uet.construction.fleetops.TestUtil.check;
import java.util.List;

/** Tests for Vehicle (composition, defensive copies, driver aggregation). */
public class VehicleTest {

    public static void run() {
        TestUtil.section("VehicleTest");
        Vehicle v = new Vehicle("VIN-1", "Civic", "E9", 1800, "Petrol");
        check("getVin", "VIN-1".equals(v.getVin()));
        check("getModel", "Civic".equals(v.getModel()));
        check("engine built from raw parameters", "E9".equals(v.getEngine().getEngineNumber())
                && v.getEngine().getDisplacementCC() == 1800);

        // Composition: engine
        Engine leaked = v.getEngine();
        check("getEngine() returns a new object each call", leaked != v.getEngine());
        leaked.setEngineNumber("HACK");
        leaked.setFuelType("Water");
        check("changing leakedEngine does not change the vehicle's engine",
                "E9".equals(v.getEngine().getEngineNumber()) && "Petrol".equals(v.getEngine().getFuelType()));

        // Composition: maintenance logs
        v.addMaintenanceRecord("L1", "Brakes", 200.0);
        v.addMaintenanceRecord("L2", "Tyres", 800.0);
        check("addMaintenanceRecord adds records", v.getMaintenanceCount() == 2);

        List<MaintenanceLog> history = v.getMaintenanceHistory();
        boolean clearBlocked = false, addBlocked = false;
        try { history.clear(); } catch (UnsupportedOperationException e) { clearBlocked = true; }
        try { history.add(new MaintenanceLog("X", "Fake", 1)); } catch (UnsupportedOperationException e) { addBlocked = true; }
        check("history.clear() is blocked", clearBlocked);
        check("history.add() is blocked", addBlocked);

        history.get(0).setServiceDescription("TAMPERED");
        check("history entries are clones", "Brakes".equals(v.getMaintenanceHistory().get(0).getServiceDescription()));
        check("internal history still has 2 records", v.getMaintenanceCount() == 2);

        // Aggregation: driver
        Driver d = new Driver("D1", "Sara", "L-1");
        v.assignDriver(d);
        check("assignDriver stores the same Driver object", v.getDriver() == d);
        v.removeDriver();
        check("driver is independent after removal", v.getDriver() == null && "Sara".equals(d.getFullName()));
    }

    public static void main(String[] args) {
        run();
        TestUtil.summary();
    }
}
