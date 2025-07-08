package utils;

import models.Vehicle;
import models.Driver;
import models.Delivery;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String VEHICLE_FILE = "data/vehicles.txt";
    private static final String DRIVER_FILE = "data/drivers.txt";
    private static final String DELIVERY_FILE = "data/deliveries.txt";

    // === VEHICLES ===

    public static void saveVehicles(List<Vehicle> vehicles) {
        try {
            ensureDataDirectory();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(VEHICLE_FILE))) {
                for (Vehicle v : vehicles) {
                    writer.write(formatVehicle(v));
                    writer.newLine();
                }
                System.out.println("✅ Vehicles saved to " + VEHICLE_FILE);
            }

        } catch (IOException e) {
            System.out.println("❌ Error saving vehicles: " + e.getMessage());
        }
    }

    public static List<Vehicle> loadVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        File file = new File(VEHICLE_FILE);

        if (!file.exists()) {
            System.out.println("📂 No existing vehicle file found. Starting fresh.");
            return vehicles;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Vehicle vehicle = parseVehicle(line);
                if (vehicle != null) {
                    vehicles.add(vehicle);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading vehicles: " + e.getMessage());
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
                System.out.println("✅ Drivers saved to " + DRIVER_FILE);
            }

        } catch (IOException e) {
            System.out.println("❌ Error saving drivers: " + e.getMessage());
        }
    }

    public static List<Driver> loadDrivers() {
        List<Driver> drivers = new ArrayList<>();
        File file = new File(DRIVER_FILE);

        if (!file.exists()) {
            System.out.println("📂 No existing driver file found. Starting fresh.");
            return drivers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Driver driver = parseDriver(line);
                if (driver != null) {
                    drivers.add(driver);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading drivers: " + e.getMessage());
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
                System.out.println("✅ Deliveries saved to " + DELIVERY_FILE);
            }

        } catch (IOException e) {
            System.out.println("❌ Error saving deliveries: " + e.getMessage());
        }
    }

    public static List<Delivery> loadDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        File file = new File(DELIVERY_FILE);

        if (!file.exists()) {
            System.out.println("📂 No existing deliveries file found. Starting fresh.");
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
                    parts[0], // packageId
                    parts[1], // origin
                    parts[2], // destination
                    parts[3], // eta
                    parts[4], // vehicle
                    parts[5], // driver
                    parts[6]  // status
            );
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
