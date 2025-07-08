package models;

public class Driver {
    private String driverId;
    private String name;
    private int yearsOfExperience;
    private int delays;
    private int infractions;

    public Driver(String driverId, String name, int yearsOfExperience) {
        this.driverId = driverId;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.delays = 0;
        this.infractions = 0;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getName() {
        return name;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public int getDelays() {
        return delays;
    }

    public int getInfractions() {
        return infractions;
    }

    public void addDelay() {
        delays++;
    }

    public void addInfraction() {
        infractions++;
    }

    @Override
    public String toString() {
        return "Driver ID: " + driverId +
               ", Name: " + name +
               ", Experience: " + yearsOfExperience + " yrs" +
               ", Delays: " + delays +
               ", Infractions: " + infractions;
    }
}
