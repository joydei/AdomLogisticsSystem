package cli;

import modules.VehicleManager;
import modules.DriverManager;
import modules.DeliveryManager;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final VehicleManager vehicleManager = new VehicleManager();
    private final DriverManager driverManager = new DriverManager();
    private final DeliveryManager deliveryManager = new DeliveryManager(driverManager, vehicleManager);
    private boolean running = true;

    public void launch() {
        while (running) {
            showMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1 -> manageVehicles();
                case 2 -> manageDrivers();
                case 3 -> trackDeliveries();
                case 4 -> scheduleMaintenance();
                case 5 -> generateReports();
                case 6 -> exitSystem();
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }

            System.out.println("\nPress Enter to return to the menu...");
            scanner.nextLine(); // Pause
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

    private int getUserChoice() {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            // handled in switch-case
        }
        return choice;
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

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> vehicleManager.addVehicle();
                case "2" -> vehicleManager.listVehicles();
                case "3" -> vehicleManager.searchVehicle();
                case "4" -> vehicleManager.listVehiclesSortedByMileage();
                case "5" -> vehicleManager.searchByMileage();
                case "6" -> inVehicleMenu = false;
                default -> System.out.println("Invalid input. Please try again.");
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

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> driverManager.addDriver();
                case "2" -> driverManager.listDrivers();
                case "3" -> driverManager.searchDriverById();
                case "4" -> driverManager.assignDriver();
                case "5" -> driverManager.showAvailableDrivers();
                case "6" -> inDriverMenu = false;
                default -> System.out.println("Invalid input. Please try again.");
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

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> deliveryManager.addDelivery();
                case "2" -> deliveryManager.listDeliveries();
                case "3" -> deliveryManager.searchByPackageId();
                case "4" -> deliveryManager.updateDeliveryStatus();
                case "5" -> deliveryManager.rerouteDelivery();
                case "6" -> inDeliveryMenu = false;
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void scheduleMaintenance() {
        System.out.println("\n--- Maintenance Scheduling Module ---");
        System.out.println("This will schedule and log vehicle maintenance.");
    }

    private void generateReports() {
        System.out.println("\n--- Fuel Efficiency and System Reports ---");
        System.out.println("This will generate reports on fuel and usage.");
    }

    private void exitSystem() {
        System.out.println("\nExiting system. Thank you!");
        running = false;
    }
}
