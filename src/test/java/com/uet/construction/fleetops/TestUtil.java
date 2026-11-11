package com.uet.construction.fleetops;

/** Tiny helper shared by all test files (no external library needed). */
public class TestUtil {

    private static int passed = 0;
    private static int failed = 0;

    public static void check(String name, boolean ok) {
        if (ok) { passed++; System.out.println("  [PASS] " + name); }
        else    { failed++; System.out.println("  [FAIL] " + name); }
    }

    public static void section(String title) {
        System.out.println(title);
    }

    public static int getPassed() { return passed; }
    public static int getFailed() { return failed; }

    public static void summary() {
        System.out.println("RESULT: " + passed + " passed, " + failed + " failed");
    }
}
