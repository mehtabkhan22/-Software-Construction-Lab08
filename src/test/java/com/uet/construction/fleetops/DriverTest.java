package com.uet.construction.fleetops;

import static com.uet.construction.fleetops.TestUtil.check;

/** Tests for Driver. Run File to execute. */
public class DriverTest {

    public static void run() {
        TestUtil.section("DriverTest");
        boolean nullRejected = false, blankRejected = false;
        try { new Driver(null, "Ali", "L1"); } catch (IllegalArgumentException e) { nullRejected = true; }
        try { new Driver("D1", "   ", "L1"); } catch (IllegalArgumentException e) { blankRejected = true; }
        check("constructor rejects null", nullRejected);
        check("constructor rejects blank", blankRejected);

        Driver d = new Driver("D1", "Ali Khan", "LIC-1");
        check("getDriverId", "D1".equals(d.getDriverId()));
        check("getFullName", "Ali Khan".equals(d.getFullName()));
        check("getLicenseNumber", "LIC-1".equals(d.getLicenseNumber()));

        d.setFullName("Ali Raza");
        check("setFullName", "Ali Raza".equals(d.getFullName()));
        boolean setterRejected = false;
        try { d.setLicenseNumber(""); } catch (IllegalArgumentException e) { setterRejected = true; }
        check("setter rejects blank", setterRejected);
        check("toString contains name", d.toString().contains("Ali Raza"));
    }

    public static void main(String[] args) {
        run();
        TestUtil.summary();
    }
}
