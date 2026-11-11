package com.uet.construction.fleetops;

import java.util.List;

/**
 * Unit-style tests for the FleetOps domain classes (no external library needed).
 * Run: right-click this file in NetBeans -> Run File,
 * or: mvn test-compile exec:java -Dexec.classpathScope=test
 *         -Dexec.mainClass=com.uet.construction.fleetops.FleetOpsTest
 */
public class FleetOpsTest {

    private static void check(String name, boolean ok) {
        TestUtil.check(name, ok);
    }

    public static void main(String[] args) {
        run();
        TestUtil.summary();
    }

    /** Integration-style tests across several classes. */
    public static void run() {
        TestUtil.section("FleetOpsTest (integration)");
        testDriverValidation();
        testEngineCopyConstructor();
        testMaintenanceLogCopyConstructor();
        testVehicleCompositionEngine();
        testVehicleMaintenanceHistory();
        testDepotAggregation();
        testDriverAggregation();
    }

    private static void testDriverValidation() {
        boolean nullRejected = false, blankRejected = false;
        try { new Driver(null, "A", "L"); } catch (IllegalArgumentException e) { nullRejected = true; }
        try { new Driver("D1", "   ", "L"); } catch (IllegalArgumentException e) { blankRejected = true; }
        check("Driver rejects null input", nullRejected);
        check("Driver rejects blank input", blankRejected);
        Driver d = new Driver("D1", "Ali", "L1");
        d.setFullName("Ali Khan");
        check("Driver setter/getter work", "Ali Khan".equals(d.getFullName()));
        check("Driver toString contains name", d.toString().contains("Ali Khan"));
    }

    private static void testEngineCopyConstructor() {
        Engine a = new Engine("E1", 1500, "Petrol");
        Engine b = new Engine(a);
        b.setFuelType("Diesel");
        check("Engine copy is a different object", a != b);
        check("Changing the copy does not change the original", "Petrol".equals(a.getFuelType()));
        check("Engine copy has the same initial state", b.getEngineNumber().equals("E1") && b.getDisplacementCC() == 1500);
    }

    private static void testMaintenanceLogCopyConstructor() {
        MaintenanceLog a = new MaintenanceLog("L1", "Oil", 100.0);
        MaintenanceLog b = new MaintenanceLog(a);
        b.setServiceCost(5.0);
        check("MaintenanceLog copy is independent", a.getServiceCost() == 100.0 && a != b);
    }

    private static void testVehicleCompositionEngine() {
        Vehicle v = new Vehicle("V1", "Civic", "E9", 1800, "Petrol");
        Engine leaked = v.getEngine();
        leaked.setEngineNumber("HACK");
        check("getEngine() returns a copy (no representation exposure)", "E9".equals(v.getEngine().getEngineNumber()));
        check("getEngine() gives a new object each call", v.getEngine() != v.getEngine());
    }

    private static void testVehicleMaintenanceHistory() {
        Vehicle v = new Vehicle("V2", "Corolla", "E8", 1300, "Petrol");
        v.addMaintenanceRecord("L1", "Brakes", 200.0);
        List<MaintenanceLog> h = v.getMaintenanceHistory();
        boolean blocked = false;
        try { h.clear(); } catch (UnsupportedOperationException e) { blocked = true; }
        check("History list is unmodifiable", blocked);
        h.get(0).setServiceDescription("TAMPERED");
        check("History entries are clones", "Brakes".equals(v.getMaintenanceHistory().get(0).getServiceDescription()));
        check("addMaintenanceRecord adds a record", v.getMaintenanceCount() == 1);
    }

    private static void testDepotAggregation() {
        Vehicle v = new Vehicle("V3", "Hilux", "E7", 2400, "Diesel");
        FleetDepot depot = new FleetDepot("DEP", "North");
        depot.assignVehicle(v);
        check("assignVehicle stores the vehicle", depot.getAssignedVehicles().size() == 1);
        boolean blocked = false;
        try { depot.getAssignedVehicles().add(v); } catch (UnsupportedOperationException e) { blocked = true; }
        check("getAssignedVehicles is unmodifiable", blocked);
        check("releaseVehicle unlinks the vehicle", depot.releaseVehicle(v) && depot.getAssignedVehicles().isEmpty());
        check("Released vehicle still exists with its state", "V3".equals(v.getVin()));
        depot = null;
        check("Vehicle survives depot = null", v.getModel().equals("Hilux"));
    }

    private static void testDriverAggregation() {
        Driver d = new Driver("D2", "Sara", "L2");
        Vehicle v = new Vehicle("V4", "Bolan", "E6", 800, "Petrol");
        v.assignDriver(d);
        check("Vehicle references the same Driver object", v.getDriver() == d);
        v.removeDriver();
        check("Driver survives removal from the vehicle", "Sara".equals(d.getFullName()) && v.getDriver() == null);
    }
}
