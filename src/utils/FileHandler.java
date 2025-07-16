package utils;

import models.Vehicle;
import models.Driver;
import models.Delivery;
import models.Maintenance;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String VEHICLE_FILE = "data/vehicles.txt";
    private static final String DRIVER_FILE = "data/drivers.txt";
    private static final String DELIVERY_FILE = "data/deliveries.txt";
    private static final String MAINTENANCE_FILE = "data/maintenance.txt";

    // === VEHICLES ===

    public static void saveVehicles(List<Vehicle> vehicles) {
        try {
            ensureDataDirectory();
            BufferedWriter writer = new BufferedWriter(new FileWriter(VEHICLE_FILE));
            for (Vehicle v : vehicles) {
                writer.write(v.getRegistrationNumber() + "," +
                             v.getType() + "," +
                             v.getMileage() + "," +
                             v.getFuelUsage() + "," +
                             v.getDriverId());
                writer.newLine();
            }
            writer.close();
            System.out.println("Vehicles saved to " + VEHICLE_FILE);
        } catch (IOException e) {
            System.out.println("Error saving vehicles: " + e.getMessage());
        }
    }

    public static List<Vehicle> loadVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        File file = new File(VEHICLE_FILE);
        if (!file.exists()) {
            System.out.println("No existing vehicle file found.");
            return vehicles;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String reg = parts[0];
                    String type = parts[1];
                    int mileage = Integer.parseInt(parts[2]);
                    double fuel = Double.parseDouble(parts[3]);
                    String driverId = parts[4];
                    vehicles.add(new Vehicle(reg, type, mileage, fuel, driverId));
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading vehicles: " + e.getMessage());
        }

        return vehicles;
    }

    // === DRIVERS ===

    public static void saveDrivers(List<Driver> drivers) {
        try {
            ensureDataDirectory();
            BufferedWriter writer = new BufferedWriter(new FileWriter(DRIVER_FILE));
            for (Driver d : drivers) {
                writer.write(d.getDriverId() + "," +
                             d.getName() + "," +
                             d.getYearsOfExperience() + "," +
                             d.getDelays() + "," +
                             d.getInfractions());
                writer.newLine();
            }
            writer.close();
            System.out.println("Drivers saved to " + DRIVER_FILE);
        } catch (IOException e) {
            System.out.println("Error saving drivers: " + e.getMessage());
        }
    }

    public static List<Driver> loadDrivers() {
        List<Driver> drivers = new ArrayList<>();
        File file = new File(DRIVER_FILE);
        if (!file.exists()) {
            System.out.println("No existing driver file found.");
            return drivers;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    String name = parts[1];
                    int exp = Integer.parseInt(parts[2]);
                    int delays = Integer.parseInt(parts[3]);
                    int infractions = Integer.parseInt(parts[4]);
                    Driver d = new Driver(id, name, exp);
                    for (int i = 0; i < delays; i++) d.addDelay();
                    for (int i = 0; i < infractions; i++) d.addInfraction();
                    drivers.add(d);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading drivers: " + e.getMessage());
        }

        return drivers;
    }

    // === DELIVERIES ===

    public static void saveDeliveries(List<Delivery> deliveries) {
        try {
            ensureDataDirectory();
            BufferedWriter writer = new BufferedWriter(new FileWriter(DELIVERY_FILE));
            for (Delivery d : deliveries) {
                writer.write(d.getPackageId() + "," +
                             d.getOrigin() + "," +
                             d.getDestination() + "," +
                             d.getEta() + "," +
                             d.getVehicleRegNo() + "," +
                             d.getDriverId() + "," +
                             d.getStatus());
                writer.newLine();
            }
            writer.close();
            System.out.println("Deliveries saved to " + DELIVERY_FILE);
        } catch (IOException e) {
            System.out.println("Error saving deliveries: " + e.getMessage());
        }
    }

    public static List<Delivery> loadDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        File file = new File(DELIVERY_FILE);
        if (!file.exists()) {
            System.out.println("No existing deliveries file found.");
            return deliveries;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    deliveries.add(new Delivery(
                        parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6]
                    ));
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading deliveries: " + e.getMessage());
        }

        return deliveries;
    }

    // === MAINTENANCE ===

    public static void saveMaintenance(List<Maintenance> records) {
        try {
            ensureDataDirectory();
            BufferedWriter writer = new BufferedWriter(new FileWriter(MAINTENANCE_FILE));
            for (Maintenance m : records) {
                writer.write(m.getRegNo() + "," +
                             m.getServiceType() + "," +
                             m.getServiceDate() + "," +
                             m.getMileageAtService() + "," +
                             m.getPartsReplaced() + "," +
                             m.getCost() + "," +
                             m.getNextServiceDate());
                writer.newLine();
            }
            writer.close();
            System.out.println("Maintenance records saved to " + MAINTENANCE_FILE);
        } catch (IOException e) {
            System.out.println("Error saving maintenance: " + e.getMessage());
        }
    }

    public static List<Maintenance> loadMaintenance() {
        List<Maintenance> records = new ArrayList<>();
        File file = new File(MAINTENANCE_FILE);
        if (!file.exists()) {
            System.out.println("No existing maintenance file found.");
            return records;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 7) {
                    String reg = parts[0];
                    String type = parts[1];
                    String date = parts[2];
                    int mileage = Integer.parseInt(parts[3]);
                    String partsReplaced = parts[4];
                    double cost = Double.parseDouble(parts[5]);
                    String nextDate = parts[6];
                    records.add(new Maintenance(reg, type, date, mileage, partsReplaced, cost, nextDate));
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading maintenance: " + e.getMessage());
        }

        return records;
    }

    // === Utility ===

    private static void ensureDataDirectory() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
