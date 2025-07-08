package utils;

import models.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String VEHICLE_FILE = "data/vehicles.txt";

    // Save vehicles to file
    public static void saveVehicles(List<Vehicle> vehicles) {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs(); // Create data directory if it doesn't exist
            }

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

    // Load vehicles from file
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
                if (vehicle != null) {
                    vehicles.add(vehicle);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading vehicles: " + e.getMessage());
        }

        return vehicles;
    }

    // Format a Vehicle object into CSV line
    private static String formatVehicle(Vehicle v) {
        return String.join(",",
                v.getRegistrationNumber(),
                v.getType(),
                String.valueOf(v.getMileage()),
                String.valueOf(v.getFuelUsage()),
                v.getDriverId()
        );
    }

    // Convert CSV line to a Vehicle object
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
}
