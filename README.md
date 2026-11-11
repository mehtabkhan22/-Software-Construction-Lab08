# Lab 08 - Object-Oriented Programming (OOP) in Java

**Course:** Software Construction & Development
**Department:** Software Engineering - 5th Semester
**Student:** Muhammad Mehtab (24ABSWE0018)
**Instructor:** Engr. Rizwan Shah
**University:** University of Engineering and Technology, Abbottabad Campus

## Lab title
Aggregation, Composition and Representation Exposure - the **FleetOps** Fleet & Vehicle Management System.

## Project details
| Item | Value |
|------|-------|
| Project name | `Lab04_ObjectRelationships_Student` |
| Group Id | `com.uet.construction` |
| Package | `com.uet.construction.fleetops` |
| Build tool | Maven (Java 17 or newer) |
| IDE | Apache NetBeans |

## Task descriptions
1. **Task 1 - Environment & scaffolding:** Maven project, metadata above, `.gitignore` ignoring `target/` and `.nb-cache/`.
2. **Task 2 - Aggregation:** `Driver`, `Vehicle` and `FleetDepot`. A depot only *references* vehicles
   (`assignVehicle`, `releaseVehicle`, unmodifiable `getAssignedVehicles`); drivers and vehicles live independently.
3. **Task 3 - Composition & representation exposure:** `Engine`, `MaintenanceLog` and `Vehicle`.
   The vehicle builds its own engine from raw parameters, creates its own log entries, and returns only
   defensive copies through `getEngine()` and `getMaintenanceHistory()` (copy constructors).
4. **Tests:** one simple test file per class in `src/test/java/...` (`DriverTest`, `EngineTest`, `MaintenanceLogTest`, `VehicleTest`, `FleetDepotTest`, `FleetOpsTest`) plus `AllTests`, which runs them all.
5. **Task 4 - Verification driver:** `FleetVerificationDriver` runs Test Case A (aggregation lifecycle
   independence) and Test Case B (composition lifetime binding & encapsulation) and prints PASS/FAIL results.

## Project structure
```
Lab04_ObjectRelationships_Student
|-- pom.xml
|-- .gitignore
|-- README.md
`-- src/main/java/com/uet/construction/fleetops
    |-- Driver.java
    |-- Engine.java
    |-- MaintenanceLog.java
    |-- Vehicle.java
    |-- FleetDepot.java
    `-- FleetVerificationDriver.java
src/test/java/com/uet/construction/fleetops
    |-- TestUtil.java            (shared check helper)
    |-- DriverTest.java
    |-- EngineTest.java
    |-- MaintenanceLogTest.java
    |-- VehicleTest.java
    |-- FleetDepotTest.java
    |-- FleetOpsTest.java        (integration-style tests)
    `-- AllTests.java            (runs all of the above)
```

## How to run

### In Apache NetBeans
1. `File -> Open Project...` and select the `Lab04_ObjectRelationships_Student` folder.
2. Right-click `FleetVerificationDriver.java` -> **Run File** (Shift+F6).
3. The results appear in the **Output** window (24 checks, all `[PASS]`).

### From the command line
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass=com.uet.construction.fleetops.FleetVerificationDriver
```

### Run the test files
NetBeans: open **Test Packages**, right-click any `*Test.java` (or `AllTests.java` to run everything) -> **Run File**.
Command line: `mvn test-compile exec:java -Dexec.classpathScope=test -Dexec.mainClass=com.uet.construction.fleetops.AllTests`
