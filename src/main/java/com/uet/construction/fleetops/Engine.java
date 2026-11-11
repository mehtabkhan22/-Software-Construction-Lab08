package com.uet.construction.fleetops;

/**
 * The engine of a Vehicle.
 *
 * COMPOSITION: an Engine only exists as part of one Vehicle. Only Vehicle
 * creates Engine objects for its own use; outside code only ever receives
 * copies (see the copy constructor).
 */
public class Engine {

    private String engineNumber;
    private int displacementCC;
    private String fuelType;

    public Engine(String engineNumber, int displacementCC, String fuelType) {
        this.engineNumber = engineNumber;
        this.displacementCC = displacementCC;
        this.fuelType = fuelType;
    }

    /** Copy constructor used for defensive (deep) copying. */
    public Engine(Engine other) {
        this.engineNumber = other.engineNumber;
        this.displacementCC = other.displacementCC;
        this.fuelType = other.fuelType;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public int getDisplacementCC() {
        return displacementCC;
    }

    public void setDisplacementCC(int displacementCC) {
        this.displacementCC = displacementCC;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return "Engine[no=" + engineNumber + ", " + displacementCC
                + "cc, fuel=" + fuelType + "]";
    }
}
