package modules;

import models.Maintenance;
import models.Vehicle;
import structures.heap.MinHeap;
import utils.FileHandler;

import java.util.*;

public class MaintenanceManager {
    private final Map<String, List<Maintenance>> maintenanceHistory = new HashMap<>();
    private final MinHeap<Vehicle> maintenanceQueue = new MinHeap<>((v1, v2) -> v1.getMileage() - v2.getMileage());
    private final Scanner scanner = new Scanner(System.in);

    public MaintenanceManager(VehicleManager vehicleManager) {
        // Load history
        Map<String, List<Maintenance>> loaded = FileHandler.loadMaintenanceRecords();
        if (loaded != null) {
            maintenanceHistory.putAll(loaded);
            System.out.println("📂 Loaded maintenance history for " + loaded.size() + " vehicles.");
        }

        // Populate priority queue
        for (Vehicle v : vehicleManager.getAllVehicles()) {
            maintenanceQueue.add(v);
        }
    }

    // === CORE LOGIC ===

    public void scheduleNextMaintenance() {
        if (maintenanceQueue.isEmpty()) {
            System.out.println("No vehicles currently need maintenance.");
            return;
        }

        Vehicle next = maintenanceQueue.poll();
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

        maintenanceHistory
                .computeIfAbsent(next.getRegistrationNumber(), k -> new ArrayList<>())
                .add(m);

        FileHandler.saveMaintenanceRecords(maintenanceHistory);
        System.out.println("Maintenance logged successfully.");
    }

    public void viewHistory() {
        System.out.print("Enter Vehicle Reg No: ");
        String regNo = scanner.nextLine().trim();

        List<Maintenance> history = maintenanceHistory.get(regNo);
        if (history == null || history.isEmpty()) {
            System.out.println("No maintenance records found for this vehicle.");
            return;
        }

        System.out.println("\n--- Maintenance History for " + regNo + " ---");
        for (Maintenance m : history) {
            System.out.println(m);
        }
    }

    public void showMaintenanceQueue() {
        System.out.println("\n--- Maintenance Priority Queue (by mileage) ---");
        maintenanceQueue.printAll();
    }

    // === ALIASES FOR MainMenu.java compatibility ===

    public void scheduleMaintenance() {
        scheduleNextMaintenance();
    }

    public void viewMaintenanceHistory() {
        viewHistory();
    }

    public void viewNextVehicleDue() {
        if (maintenanceQueue.isEmpty()) {
            System.out.println("No vehicles currently due for maintenance.");
        } else {
            Vehicle next = maintenanceQueue.peek();
            System.out.println("\nNext Vehicle Due for Maintenance:");
            System.out.printf("• RegNo: %s | Mileage: %d km\n", next.getRegistrationNumber(), next.getMileage());
        }
    }
}
