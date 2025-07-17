package cli;

import java.util.*;
import models.Vehicle;
import modules.DeliveryManager;
import modules.DriverManager;
import modules.MaintenanceManager;
import modules.VehicleManager;
import utils.InputValidator;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final VehicleManager vehicleManager = new VehicleManager();
    private final DriverManager driverManager = new DriverManager();
    private final DeliveryManager deliveryManager = new DeliveryManager(driverManager, vehicleManager);
    private final MaintenanceManager maintenanceManager = new MaintenanceManager(vehicleManager);
    private boolean running = true;

    public void launch() {
        InputValidator.showInfo("Welcome to Adom Logistics Management System!");

        while (running) {
            showMainMenu();
            int choice = InputValidator.getValidMenuChoice("Enter your choice (1-6): ", 1, 6);

            if (choice == -1) {
                // User typed 'back' on main menu, ask if they want to exit
                if (InputValidator.askContinueOrBack("You are at the main menu. Do you want to exit the system?")) {
                    continue; // Stay in main menu
                } else {
                    exitSystem();
                    break;
                }
            }

            switch (choice) {
                case 1 ->
                    manageVehicles();
                case 2 ->
                    manageDrivers();
                case 3 ->
                    trackDeliveries();
                case 4 ->
                    scheduleMaintenance();
                case 5 ->
                    generateReports();
                case 6 ->
                    exitSystem();
            }

            if (running) {
                InputValidator.waitForEnter();
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Manage Vehicles");
        System.out.println("2. Manage Drivers");
        System.out.println("3. Track Deliveries");
        System.out.println("4. Maintenance Scheduling");
        System.out.println("5. Generate Reports");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
    }

    private void manageVehicles() {
        boolean inVehicleMenu = true;

        while (inVehicleMenu) {
            System.out.println("\n--- Vehicle Management ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. View All Vehicles");
            System.out.println("3. Search Vehicle by Reg No");
            System.out.println("4. View Vehicles Sorted by Mileage");
            System.out.println("5. Search Vehicles by Mileage");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice (1-6): ");

            switch (scanner.nextLine().trim()) {
                case "1" ->
                    vehicleManager.addVehicle();
                case "2" ->
                    vehicleManager.listVehicles();
                case "3" ->
                    vehicleManager.searchVehicle();
                case "4" ->
                    vehicleManager.listVehiclesSortedByMileage();
                case "5" ->
                    vehicleManager.searchByMileage();
                case "6" ->
                    inVehicleMenu = false;
                default ->
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void manageDrivers() {
        boolean inDriverMenu = true;

        while (inDriverMenu) {
            System.out.println("\n--- Driver Management ---");
            System.out.println("1. Add New Driver");
            System.out.println("2. View All Drivers");
            System.out.println("3. Search Driver by ID");
            System.out.println("4. Assign Next Available Driver");
            System.out.println("5. Show Available Drivers Queue");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice (1-6): ");

            switch (scanner.nextLine().trim()) {
                case "1" ->
                    driverManager.addDriver();
                case "2" ->
                    driverManager.listDrivers();
                case "3" ->
                    driverManager.searchDriverById();
                case "4" ->
                    driverManager.assignDriver();
                case "5" ->
                    driverManager.showAvailableDrivers();
                case "6" ->
                    inDriverMenu = false;
                default ->
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void trackDeliveries() {
        boolean inDeliveryMenu = true;

        while (inDeliveryMenu) {
            System.out.println("\n--- Delivery Tracking ---");
            System.out.println("1. Add New Delivery");
            System.out.println("2. View All Deliveries");
            System.out.println("3. Search Delivery by Package ID");
            System.out.println("4. Update Delivery Status");
            System.out.println("5. Reroute Delivery");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice (1-6): ");

            switch (scanner.nextLine().trim()) {
                case "1" ->
                    deliveryManager.addDelivery();
                case "2" ->
                    deliveryManager.listDeliveries();
                case "3" ->
                    deliveryManager.searchByPackageId();
                case "4" ->
                    deliveryManager.updateDeliveryStatus();
                case "5" ->
                    deliveryManager.rerouteDelivery();
                case "6" ->
                    inDeliveryMenu = false;
                default ->
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void scheduleMaintenance() {
        boolean inMaintenanceMenu = true;

        while (inMaintenanceMenu) {
            System.out.println("\n--- Maintenance Scheduling ---");
            System.out.println("1. Schedule Maintenance Now");
            System.out.println("2. View Maintenance History for a Vehicle");
            System.out.println("3. View Next Vehicle Due for Maintenance");
            System.out.println("4. View Maintenance Queue");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice (1-5): ");

            switch (scanner.nextLine().trim()) {
                case "1" ->
                    maintenanceManager.scheduleMaintenance();
                case "2" ->
                    maintenanceManager.viewMaintenanceHistory();
                case "3" ->
                    maintenanceManager.viewNextVehicleDue();
                case "4" ->
                    maintenanceManager.showMaintenanceQueue();
                case "5" ->
                    inMaintenanceMenu = false;
                default ->
                    System.out.println("Invalid input. Try again.");
            }
        }
    }

    private void generateReports() {
        System.out.println("\n--- Fuel Efficiency Report ---");

        List<Vehicle> vehicles = vehicleManager.getAllVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available to generate report.");
            return;
        }

        quickSort(vehicles, 0, vehicles.size() - 1);

        double totalEfficiency = 0;
        int validVehicles = 0;

        for (Vehicle v : vehicles) {
            if (v.getMileage() > 0) {
                totalEfficiency += v.getFuelUsage() / (double) v.getMileage();
                validVehicles++;
            }
        }

        if (validVehicles == 0) {
            System.out.println("No valid vehicle data for fuel efficiency analysis.");
            return;
        }

        double avgEfficiency = totalEfficiency / validVehicles;

        System.out.printf("\nAverage Fuel Usage per km: %.5f liters/km\n", avgEfficiency);
        System.out.println("\n--- Vehicles Sorted by Fuel Efficiency ---");

        for (Vehicle v : vehicles) {
            double eff = v.getMileage() == 0 ? 0 : v.getFuelUsage() / (double) v.getMileage();
            System.out.printf("• %s | Type: %s | %.5f L/km", v.getRegistrationNumber(), v.getType(), eff);
            if (eff > avgEfficiency * 1.2) {
                System.out.print(" Inefficient");
            }
            if (eff < avgEfficiency * 0.8) {
                System.out.print(" Efficient");
            }
            System.out.println();
        }

        System.out.println("\n--- Efficiency Comparison by Vehicle Type ---");
        Map<String, List<Vehicle>> typeMap = new HashMap<>();

        for (Vehicle v : vehicles) {
            typeMap.computeIfAbsent(v.getType().toLowerCase(), k -> new ArrayList<>()).add(v);
        }

        for (Map.Entry<String, List<Vehicle>> entry : typeMap.entrySet()) {
            String type = entry.getKey();
            List<Vehicle> group = entry.getValue();

            double groupTotal = 0;
            int count = 0;

            for (Vehicle v : group) {
                if (v.getMileage() > 0) {
                    groupTotal += v.getFuelUsage() / (double) v.getMileage();
                    count++;
                }
            }

            if (count == 0) {
                continue;
            }

            double avg = groupTotal / count;
            System.out.printf("• %-10s → Average Efficiency: %.5f L/km\n", type, avg);
        }
    }

    private void quickSort(List<Vehicle> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    private int partition(List<Vehicle> list, int low, int high) {
        double pivot = getEfficiency(list.get(high));
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (getEfficiency(list.get(j)) < pivot) {
                i++;
                Collections.swap(list, i, j);
            }
        }

        Collections.swap(list, i + 1, high);
        return i + 1;
    }

    private double getEfficiency(Vehicle v) {
        return v.getMileage() == 0 ? Double.MAX_VALUE : v.getFuelUsage() / (double) v.getMileage();
    }

    private void exitSystem() {
        System.out.println("\nExiting system. Thank you!");
        running = false;
    }
}
