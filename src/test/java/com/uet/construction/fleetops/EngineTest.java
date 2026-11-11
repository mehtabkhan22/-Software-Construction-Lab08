package com.uet.construction.fleetops;

import static com.uet.construction.fleetops.TestUtil.check;

/** Tests for Engine (constructor and copy constructor). */
public class EngineTest {

    public static void run() {
        TestUtil.section("EngineTest");
        Engine a = new Engine("E1", 1500, "Petrol");
        check("getEngineNumber", "E1".equals(a.getEngineNumber()));
        check("getDisplacementCC", a.getDisplacementCC() == 1500);
        check("getFuelType", "Petrol".equals(a.getFuelType()));

        Engine b = new Engine(a);
        check("copy is a different object", a != b);
        check("copy has the same state", "E1".equals(b.getEngineNumber())
                && b.getDisplacementCC() == 1500 && "Petrol".equals(b.getFuelType()));

        b.setFuelType("Diesel");
        b.setDisplacementCC(9999);
        check("changing the copy does not change the original",
                "Petrol".equals(a.getFuelType()) && a.getDisplacementCC() == 1500);
    }

    public static void main(String[] args) {
        run();
        TestUtil.summary();
    }
}
