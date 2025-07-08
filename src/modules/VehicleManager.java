package modules;

import models.Vehicle;
import structures.hash.HashTable;
import structures.bst.BST;
import utils.FileHandler;

import java.util.List;
import java.util.Scanner;

public class VehicleManager {
    private final HashTable vehicleTable = new HashTable(); // for reg number
    private final BST vehicleTree = new BST();              // for mileage
    private final Scanner scanner = new Scanner(System.in);

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

        System.out.print("Enter Registration Number: ");
        String regNo = scanner.nextLine().trim();

        if (vehicleTable.containsKey(regNo)) {
            System.out.println("❌ Vehicle with this registration number already exists!");
            return;
        }

        System.out.print("Enter Vehicle Type (Truck/Van): ");
        String type = scanner.nextLine().trim();

        System.out.print("Enter Mileage (km): ");
        int mileage = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter Fuel Usage (liters per 100km): ");
        double fuelUsage = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Enter Driver ID: ");
        String driverId = scanner.nextLine().trim();

        Vehicle vehicle = new Vehicle(regNo, type, mileage, fuelUsage, driverId);

        // Add to in-memory structures
        vehicleTable.put(regNo, vehicle);
        vehicleTree.insert(vehicle);

        // Save updated list to file
        FileHandler.saveVehicles(vehicleTable.toList());

        System.out.println("Vehicle added and saved successfully!");
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
        System.out.print("Enter Registration Number: ");
        String regNo = scanner.nextLine().trim();

        Vehicle found = vehicleTable.get(regNo);
        if (found != null) {
            System.out.println("Vehicle Found:");
            System.out.println(found);
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    // Search for vehicles by mileage (can have duplicates)
    public void searchByMileage() {
        System.out.println("\n--- Search Vehicles by Mileage ---");
        System.out.print("Enter mileage to search: ");
        int mileage = Integer.parseInt(scanner.nextLine().trim());

        vehicleTree.searchByMileage(mileage);
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
}
