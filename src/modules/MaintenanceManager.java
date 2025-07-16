package modules;

import models.Maintenance;
import models.Vehicle;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaintenanceManager {
    private final List<Maintenance> allMaintenanceRecords = FileHandler.loadMaintenance(); // flat list
    private final List<Vehicle> sortedVehicleList = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public MaintenanceManager(VehicleManager vehicleManager) {
        // Load vehicles and sort manually by mileage
        List<Vehicle> allVehicles = vehicleManager.getAllVehicles();
        sortedVehicleList.addAll(allVehicles);
        sortVehiclesByMileage();
        System.out.println("Loaded " + allMaintenanceRecords.size() + " maintenance records.");
    }

    private void sortVehiclesByMileage() {
        for (int i = 0; i < sortedVehicleList.size(); i++) {
            for (int j = i + 1; j < sortedVehicleList.size(); j++) {
                if (sortedVehicleList.get(j).getMileage() < sortedVehicleList.get(i).getMileage()) {
                    Vehicle temp = sortedVehicleList.get(i);
                    sortedVehicleList.set(i, sortedVehicleList.get(j));
                    sortedVehicleList.set(j, temp);
                }
            }
        }
    }

    // === CORE LOGIC ===

    public void scheduleNextMaintenance() {
        if (sortedVehicleList.isEmpty()) {
            System.out.println("No vehicles currently need maintenance.");
            return;
        }

        Vehicle next = sortedVehicleList.remove(0); // simulate priority
        System.out.println("\nVehicle Due for Maintenance:");
        System.out.println(next);

        System.out.print("Enter Service Type (e.g. Oil Change): ");
        String type = scanner.nextLine().trim();

        System.out.print("Enter Service Date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        System.out.print("Enter Parts Replaced: ");
        String parts = scanner.nextLine().trim();

        System.out.print("Enter Cost: ");
        double cost = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Enter Next Scheduled Service Date (optional): ");
        String nextServiceDate = scanner.nextLine().trim();

        Maintenance m = new Maintenance(
                next.getRegistrationNumber(),
                type, date, next.getMileage(),
                parts, cost, nextServiceDate
        );

        allMaintenanceRecords.add(m);
        FileHandler.saveMaintenance(allMaintenanceRecords);
        System.out.println("Maintenance logged successfully.");
    }

    public void viewHistory() {
        System.out.print("Enter Vehicle Reg No: ");
        String regNo = scanner.nextLine().trim();

        boolean found = false;
        System.out.println("\n--- Maintenance History for " + regNo + " ---");
        for (Maintenance m : allMaintenanceRecords) {
            if (m.getRegNo().equalsIgnoreCase(regNo)) {
                System.out.println(m);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No maintenance records found for this vehicle.");
        }
    }

    public void showMaintenanceQueue() {
        System.out.println("\n--- Maintenance Priority Queue (by mileage) ---");
        for (Vehicle v : sortedVehicleList) {
            System.out.printf("• RegNo: %s | Mileage: %d km\n", v.getRegistrationNumber(), v.getMileage());
        }
    }

    // === ALIASES FOR MainMenu.java compatibility ===

    public void scheduleMaintenance() {
        scheduleNextMaintenance();
    }

    public void viewMaintenanceHistory() {
        viewHistory();
    }

    public void viewNextVehicleDue() {
        if (sortedVehicleList.isEmpty()) {
            System.out.println("No vehicles currently due for maintenance.");
        } else {
            Vehicle next = sortedVehicleList.get(0);
            System.out.println("\nNext Vehicle Due for Maintenance:");
            System.out.printf("• RegNo: %s | Mileage: %d km\n", next.getRegistrationNumber(), next.getMileage());
        }
    }
}
