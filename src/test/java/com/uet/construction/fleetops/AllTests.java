package com.uet.construction.fleetops;

/**
 * Runs every test file in one go.
 * NetBeans: right-click AllTests.java -> Run File.
 */
public class AllTests {

    public static void main(String[] args) {
        DriverTest.run();
        EngineTest.run();
        MaintenanceLogTest.run();
        VehicleTest.run();
        FleetDepotTest.run();
        FleetOpsTest.run();
        System.out.println();
        TestUtil.summary();
        if (TestUtil.getFailed() > 0) System.exit(1);
    }
}
