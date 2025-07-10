package utils;

import models.Vehicle;
import models.Driver;
import models.Delivery;
import models.Maintenance;

import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String VEHICLE_FILE = "data/vehicles.txt";
    private static final String DRIVER_FILE = "data/drivers.txt";
    private static final String DELIVERY_FILE = "data/deliveries.txt";
    private static final String MAINTENANCE_FILE = "data/maintenance.txt";

    // === VEHICLES ===

    public static void saveVehicles(List<Vehicle> vehicles) {
        try {
            ensureDataDirectory();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(VEHICLE_FILE))) {
                for (Vehicle v : vehicles) {
                    writer.write(formatVehicle(v));
                    writer.newLine();
                }
                System.out.println("Vehicles saved to " + VEHICLE_FILE);
            }
        } catch (IOException e) {
            System.out.println("Error saving vehicles: " + e.getMessage());
        }
    }

    public static List<Vehicle> loadVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        File file = new File(VEHICLE_FILE);
        if (!file.exists()) {
            System.out.println("No existing vehicle file found. Starting fresh.");
            return vehicles;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Vehicle vehicle = parseVehicle(line);
                if (vehicle != null) vehicles.add(vehicle);
            }
        } catch (IOException e) {
            System.out.println("Error loading vehicles: " + e.getMessage());
        }

        return vehicles;
    }

    private static String formatVehicle(Vehicle v) {
        return String.join(",",
                v.getRegistrationNumber(),
                v.getType(),
                String.valueOf(v.getMileage()),
                String.valueOf(v.getFuelUsage()),
                v.getDriverId()
        );
    }

    private static Vehicle parseVehicle(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) return null;

        try {
            String regNo = parts[0];
            String type = parts[1];
            int mileage = Integer.parseInt(parts[2]);
            double fuelUsage = Double.parseDouble(parts[3]);
            String driverId = parts[4];

            return new Vehicle(regNo, type, mileage, fuelUsage, driverId);
        } catch (Exception e) {
            return null;
        }
    }

    // === DRIVERS ===

    public static void saveDrivers(List<Driver> drivers) {
        try {
            ensureDataDirectory();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(DRIVER_FILE))) {
                for (Driver d : drivers) {
                    writer.write(formatDriver(d));
                    writer.newLine();
                }
                System.out.println("Drivers saved to " + DRIVER_FILE);
            }
        } catch (IOException e) {
            System.out.println("Error saving drivers: " + e.getMessage());
        }
    }

    public static List<Driver> loadDrivers() {
        List<Driver> drivers = new ArrayList<>();
        File file = new File(DRIVER_FILE);
        if (!file.exists()) {
            System.out.println("No existing driver file found. Starting fresh.");
            return drivers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Driver driver = parseDriver(line);
                if (driver != null) drivers.add(driver);
            }
        } catch (IOException e) {
            System.out.println("Error loading drivers: " + e.getMessage());
        }

        return drivers;
    }

    private static String formatDriver(Driver d) {
        return String.join(",",
                d.getDriverId(),
                d.getName(),
                String.valueOf(d.getYearsOfExperience()),
                String.valueOf(d.getDelays()),
                String.valueOf(d.getInfractions())
        );
    }

    private static Driver parseDriver(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) return null;

        try {
            String id = parts[0];
            String name = parts[1];
            int exp = Integer.parseInt(parts[2]);
            int delays = Integer.parseInt(parts[3]);
            int infractions = Integer.parseInt(parts[4]);

            Driver d = new Driver(id, name, exp);
            for (int i = 0; i < delays; i++) d.addDelay();
            for (int i = 0; i < infractions; i++) d.addInfraction();

            return d;
        } catch (Exception e) {
            return null;
        }
    }

    // === DELIVERIES ===

    public static void saveDeliveries(List<Delivery> deliveries) {
        try {
            ensureDataDirectory();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(DELIVERY_FILE))) {
                for (Delivery d : deliveries) {
                    writer.write(formatDelivery(d));
                    writer.newLine();
                }
                System.out.println("Deliveries saved to " + DELIVERY_FILE);
            }
        } catch (IOException e) {
            System.out.println("Error saving deliveries: " + e.getMessage());
        }
    }

    public static List<Delivery> loadDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        File file = new File(DELIVERY_FILE);
        if (!file.exists()) {
            System.out.println("No existing deliveries file found. Starting fresh.");
            return deliveries;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Delivery d = parseDelivery(line);
                if (d != null) deliveries.add(d);
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading deliveries: " + e.getMessage());
        }

        return deliveries;
    }

    private static String formatDelivery(Delivery d) {
        return String.join(",",
                d.getPackageId(),
                d.getOrigin(),
                d.getDestination(),
                d.getEta(),
                d.getVehicleRegNo(),
                d.getDriverId(),
                d.getStatus()
        );
    }

    private static Delivery parseDelivery(String line) {
        String[] parts = line.split(",");
        if (parts.length != 7) return null;

        try {
            return new Delivery(
                    parts[0], parts[1], parts[2], parts[3],
                    parts[4], parts[5], parts[6]
            );
        } catch (Exception e) {
            return null;
        }
    }

    // === MAINTENANCE (Flat List API) ===

    public static void saveMaintenance(List<Maintenance> records) {
        try {
            ensureDataDirectory();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(MAINTENANCE_FILE))) {
                for (Maintenance m : records) {
                    writer.write(formatMaintenance(m));
                    writer.newLine();
                }
                System.out.println("Maintenance records saved to " + MAINTENANCE_FILE);
            }
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

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Maintenance m = parseMaintenance(line);
                if (m != null) records.add(m);
            }
        } catch (IOException e) {
            System.out.println("Error loading maintenance: " + e.getMessage());
        }

        return records;
    }

    // === MAINTENANCE (Nested Map API) ===

    public static void saveMaintenanceRecords(Map<String, List<Maintenance>> map) {
        List<Maintenance> all = new ArrayList<>();
        for (List<Maintenance> list : map.values()) {
            all.addAll(list);
        }
        saveMaintenance(all);
    }

    public static Map<String, List<Maintenance>> loadMaintenanceRecords() {
        Map<String, List<Maintenance>> map = new HashMap<>();
        for (Maintenance m : loadMaintenance()) {
            map.computeIfAbsent(m.getVehicleRegNo(), k -> new ArrayList<>()).add(m);
        }
        return map;
    }

    // === MAINTENANCE Utility ===

    private static String formatMaintenance(Maintenance m) {
        return String.join(",",
                m.getVehicleRegNo(),
                m.getDate(),
                m.getDescription(),
                String.join("|", m.getPartsReplaced()),
                String.valueOf(m.getCost())
        );
    }

    private static Maintenance parseMaintenance(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 5) return null;

        try {
            String regNo = parts[0];
            String date = parts[1];
            String desc = parts[2];
            List<String> partsList = List.of(parts[3].split("\\|"));
            double cost = Double.parseDouble(parts[4]);

            return new Maintenance(regNo, date, desc, partsList, cost);
        } catch (Exception e) {
            return null;
        }
    }

    // === Utility ===

    private static void ensureDataDirectory() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
