package com.uet.construction.fleetops;

import static com.uet.construction.fleetops.TestUtil.check;

/** Tests for MaintenanceLog (constructor and copy constructor). */
public class MaintenanceLogTest {

    public static void run() {
        TestUtil.section("MaintenanceLogTest");
        MaintenanceLog a = new MaintenanceLog("L1", "Oil change", 4500.0);
        check("getLogId", "L1".equals(a.getLogId()));
        check("getServiceDescription", "Oil change".equals(a.getServiceDescription()));
        check("getServiceCost", a.getServiceCost() == 4500.0);

        MaintenanceLog b = new MaintenanceLog(a);
        check("copy is a different object", a != b);
        check("copy has the same state", "L1".equals(b.getLogId()) && b.getServiceCost() == 4500.0);

        b.setServiceDescription("TAMPERED");
        b.setServiceCost(0.0);
        check("changing the copy does not change the original",
                "Oil change".equals(a.getServiceDescription()) && a.getServiceCost() == 4500.0);
    }

    public static void main(String[] args) {
        run();
        TestUtil.summary();
    }
}
