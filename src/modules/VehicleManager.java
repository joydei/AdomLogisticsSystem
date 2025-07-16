package modules;

import java.util.List;
import models.Vehicle;
import structures.bst.BST;
import structures.hash.HashTable;
import utils.FileHandler;
import utils.InputValidator;

public class VehicleManager {

    private final HashTable vehicleTable = new HashTable(); // for reg number
    private final BST vehicleTree = new BST();              // for mileage

    public VehicleManager() {
        // Load vehicles from file on initialization
        List<Vehicle> loadedVehicles = FileHandler.loadVehicles();
        for (Vehicle vehicle : loadedVehicles) {
            vehicleTable.put(vehicle.getRegistrationNumber(), vehicle);
            vehicleTree.insert(vehicle);
        }
        if (!loadedVehicles.isEmpty()) {
            System.out.println("Loaded " + loadedVehicles.size() + " vehicles from file.");
        }
    }

    // Add a new vehicle
    public void addVehicle() {
        System.out.println("\n--- Add New Vehicle ---");

        while (true) {
            // Get registration number
            String regNo = InputValidator.getValidString("Enter Registration Number: ", 3, 20);
            if (regNo.equals("BACK")) {
                return;
            }

            if (vehicleTable.containsKey(regNo)) {
                if (!InputValidator.handleErrorAndAskRetry("Vehicle with this registration number already exists!")) {
                    return;
                }
                continue;
            }

            // Get vehicle type with validation
            String[] allowedTypes = {"Truck", "Van"};
            String type = InputValidator.getValidChoice("Enter Vehicle Type (Truck/Van): ", allowedTypes, false);
            if (type.equals("BACK")) {
                return;
            }

            // Get mileage with validation
            int mileage = InputValidator.getValidInteger("Enter Mileage (km): ", 0, 1000000);
            if (mileage == -999) {
                return;
            }

            // Get fuel usage with validation
            double fuelUsage = InputValidator.getValidDouble("Enter Fuel Usage (liters per 100km): ", 0.1, 100.0);
            if (fuelUsage == -999.0) {
                return;
            }

            // Get driver ID
            String driverId = InputValidator.getValidString("Enter Driver ID: ", 2, 15);
            if (driverId.equals("BACK")) {
                return;
            }

            try {
                Vehicle vehicle = new Vehicle(regNo, type, mileage, fuelUsage, driverId);

                // Add to in-memory structures
                vehicleTable.put(regNo, vehicle);
                vehicleTree.insert(vehicle);

                // Save updated list to file
                FileHandler.saveVehicles(vehicleTable.toList());

                InputValidator.showSuccess("Vehicle added and saved successfully!");
                return;

            } catch (Exception e) {
                if (!InputValidator.handleErrorAndAskRetry("Error creating vehicle: " + e.getMessage())) {
                    return;
                }
            }
        }
    }

    // Display all vehicles (unsorted, via HashTable)
    public void listVehicles() {
        System.out.println("\n--- All Vehicles ---");
        vehicleTable.printAll();
    }

    // Display vehicles sorted by mileage (using BST)
    public void listVehiclesSortedByMileage() {
        vehicleTree.printInOrder();
    }

    // Search for a vehicle by registration number
    public void searchVehicle() {
        System.out.println("\n--- Search Vehicle by Reg No ---");

        while (true) {
            String regNo = InputValidator.getValidString("Enter Registration Number: ", 1, 20);
            if (regNo.equals("BACK")) {
                return;
            }

            Vehicle found = vehicleTable.get(regNo);
            if (found != null) {
                System.out.println("Vehicle Found:");
                System.out.println(found);
                return;
            } else {
                if (!InputValidator.handleErrorAndAskRetry("Vehicle not found.")) {
                    return;
                }
            }
        }
    }

    // Search for vehicles by mileage (can have duplicates)
    public void searchByMileage() {
        System.out.println("\n--- Search Vehicles by Mileage ---");

        while (true) {
            int mileage = InputValidator.getValidInteger("Enter mileage to search: ", 0, 1000000);
            if (mileage == -999) {
                return;
            }

            vehicleTree.searchByMileage(mileage);
            return;
        }
    }

    // Optional: Expose vehicle lookup by regNo
    public Vehicle getVehicleByRegNo(String regNo) {
        return vehicleTable.get(regNo);
    }

    // ** New method to get any available vehicle (example implementation) **
    public Vehicle getAvailableVehicle() {
        // Simple example: return the first vehicle found in the hash table
        List<Vehicle> allVehicles = vehicleTable.toList();
        if (!allVehicles.isEmpty()) {
            return allVehicles.get(0);
        }
        return null; // no vehicle available
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleTable.toList();
    }
}
