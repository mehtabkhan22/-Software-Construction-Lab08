package com.uet.construction.fleetops;

import java.util.List;

/**
 * Verification driver for Lab 08.
 *
 * Test Case A : Aggregation lifecycle independence.
 * Test Case B : Composition lifetime binding and encapsulation
 *               (defensive copying / no representation exposure).
 *
 * Run it: right-click the file in NetBeans -> Run File (Shift+F6).
 */
public class FleetVerificationDriver {

    private static int passed = 0;
    private static int failed = 0;

    /** Prints one test assertion (works even when "java -ea" is not used). */
    private static void check(String description, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("  [PASS] " + description);
        } else {
            failed++;
            System.out.println("  [FAIL] " + description);
        }
    }

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println(" FleetOps Verification Driver");
        System.out.println("==============================================");

        testCaseA();
        System.out.println();
        testCaseB();

        System.out.println();
        System.out.println("==============================================");
        System.out.println(" RESULT: " + passed + " passed, " + failed + " failed");
        System.out.println("==============================================");
    }

    // ------------------------------------------------------------------
    // Test Case A: Aggregation Lifecycle Independence
    // ------------------------------------------------------------------
    private static void testCaseA() {
        System.out.println("TEST CASE A: Aggregation Lifecycle Independence");
        System.out.println("----------------------------------------------");

        // 1. Two Driver objects and two Vehicle objects, created independently
        Driver driver1 = new Driver("D-001", "Ali Khan", "LIC-12345");
        Driver driver2 = new Driver("D-002", "Sara Ahmed", "LIC-67890");
        Vehicle vehicle1 = new Vehicle("VIN-1001", "Toyota Hilux", "ENG-A1", 2400, "Diesel");
        Vehicle vehicle2 = new Vehicle("VIN-1002", "Suzuki Bolan", "ENG-B2", 800, "Petrol");

        vehicle1.assignDriver(driver1);
        vehicle2.assignDriver(driver2);

        // 2. A FleetDepot, and the vehicles assigned to it
        FleetDepot depot = new FleetDepot("DEP-01", "Abbottabad Central");
        depot.assignVehicle(vehicle1);
        depot.assignVehicle(vehicle2);
        System.out.println("Before closing depot : " + depot);
        check("Depot holds 2 assigned vehicles", depot.getAssignedVehicles().size() == 2);

        // 3. Explicitly nullify the depot reference
        depot = null;
        System.gc(); // only a hint, the objects below are still strongly referenced
        System.out.println("Depot reference set to null (depot closed).");

        // 4. Prove vehicles and drivers still exist and keep their state
        System.out.println("Vehicle 1 still alive: " + vehicle1);
        System.out.println("Vehicle 2 still alive: " + vehicle2);
        System.out.println("Driver  1 still alive: " + driver1);
        System.out.println("Driver  2 still alive: " + driver2);

        check("depot reference is null", depot == null);
        check("Vehicle 1 retains its VIN", "VIN-1001".equals(vehicle1.getVin()));
        check("Vehicle 2 retains its VIN", "VIN-1002".equals(vehicle2.getVin()));
        check("Vehicle 1 retains its Driver", vehicle1.getDriver() == driver1);
        check("Vehicle 2 retains its Driver", vehicle2.getDriver() == driver2);
        check("Driver 1 retains its state", "Ali Khan".equals(driver1.getFullName()));
        check("Driver 2 retains its state", "Sara Ahmed".equals(driver2.getFullName()));

        // Vehicles can be reassigned to another depot
        FleetDepot newDepot = new FleetDepot("DEP-02", "Havelian North");
        newDepot.assignVehicle(vehicle1);
        newDepot.assignVehicle(vehicle2);
        System.out.println("Reassigned to        : " + newDepot);
        check("Vehicles were reassigned to a new depot",
                newDepot.getAssignedVehicles().size() == 2);

        // releaseVehicle() unlinks without destroying the vehicle
        boolean released = newDepot.releaseVehicle(vehicle2);
        check("releaseVehicle() removed vehicle 2 from the depot",
                released && newDepot.getAssignedVehicles().size() == 1);
        check("Released vehicle 2 is still intact", "VIN-1002".equals(vehicle2.getVin()));

        // A driver leaving the fleet does not remove the vehicle
        vehicle1.removeDriver();
        driver1 = null;
        check("Vehicle 1 survives after its driver is gone", vehicle1.getVin() != null);
        check("Driver 2 is independent of the vehicle (still usable)",
                vehicle2.getDriver() != null && vehicle2.getDriver().getFullName() != null);

        // The depot's list is an unmodifiable view
        try {
            newDepot.getAssignedVehicles().clear();
            check("getAssignedVehicles() is unmodifiable", false);
        } catch (UnsupportedOperationException e) {
            check("getAssignedVehicles() is unmodifiable "
                    + "(UnsupportedOperationException thrown)", true);
        }
    }

    // ------------------------------------------------------------------
    // Test Case B: Composition Lifetime Binding & Encapsulation
    // ------------------------------------------------------------------
    private static void testCaseB() {
        System.out.println("TEST CASE B: Composition Lifetime Binding & Encapsulation");
        System.out.println("----------------------------------------------");

        // 1. A new Vehicle, which builds its own Engine internally
        Vehicle vehicle = new Vehicle("VIN-2001", "Honda City", "ENG-C3", 1500, "Petrol");
        vehicle.addMaintenanceRecord("LOG-1", "Oil change", 4500.0);
        vehicle.addMaintenanceRecord("LOG-2", "Brake pad replacement", 12000.0);
        System.out.println("Created: " + vehicle);

        // 2. Store the reference returned by getEngine()
        Engine leakedEngine = vehicle.getEngine();
        Engine secondCall = vehicle.getEngine();

        System.out.println("leakedEngine (before) : " + leakedEngine
                + " hash=" + System.identityHashCode(leakedEngine));
        System.out.println("vehicle's engine      : " + vehicle.getEngine()
                + " hash=" + System.identityHashCode(vehicle.getEngine()));
        check("getEngine() returns a new object on every call (not the internal one)",
                leakedEngine != secondCall);

        // 3. Attempt to mutate the returned engine
        leakedEngine.setEngineNumber("HACKED-999");
        leakedEngine.setDisplacementCC(9999);
        leakedEngine.setFuelType("Water");
        System.out.println("leakedEngine (after)  : " + leakedEngine);

        // 4. Verify the vehicle's real engine did not change
        Engine real = vehicle.getEngine();
        System.out.println("vehicle's engine now  : " + real);
        check("Engine number unchanged", "ENG-C3".equals(real.getEngineNumber()));
        check("Engine displacement unchanged", real.getDisplacementCC() == 1500);
        check("Engine fuel type unchanged", "Petrol".equals(real.getFuelType()));

        // 5. Fetch the history and try to clear / mutate it
        List<MaintenanceLog> history = vehicle.getMaintenanceHistory();
        System.out.println("History fetched      : " + history.size() + " entries");

        try {
            history.clear();
            check("history.clear() was blocked", false);
        } catch (UnsupportedOperationException e) {
            check("history.clear() was blocked (UnsupportedOperationException)", true);
        }
        try {
            history.add(new MaintenanceLog("FAKE", "Injected record", 1.0));
            check("history.add() was blocked", false);
        } catch (UnsupportedOperationException e) {
            check("history.add() was blocked (UnsupportedOperationException)", true);
        }

        // Mutating a returned log entry must not touch the internal one
        history.get(0).setServiceDescription("TAMPERED");
        history.get(0).setServiceCost(0.0);
        List<MaintenanceLog> fresh = vehicle.getMaintenanceHistory();
        System.out.println("History after attack : " + fresh);
        check("Internal history still has 2 records",
                vehicle.getMaintenanceCount() == 2 && fresh.size() == 2);
        check("Log 1 description unchanged", "Oil change".equals(fresh.get(0).getServiceDescription()));
        check("Log 1 cost unchanged", fresh.get(0).getServiceCost() == 4500.0);
        check("Each history call returns fresh clones",
                vehicle.getMaintenanceHistory().get(0) != vehicle.getMaintenanceHistory().get(0));
    }
}
