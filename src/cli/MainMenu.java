package cli;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
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
        System.out.println("\n--- Vehicle Management Module ---");
        // TODO: Call VehicleManager logic here
        System.out.println("This will manage vehicles (add/search/remove).");
    }

    private void manageDrivers() {
        System.out.println("\n--- Driver Management Module ---");
        // TODO: Call DriverManager logic here
        System.out.println("This will manage drivers and assignments.");
    }

    private void trackDeliveries() {
        System.out.println("\n--- Delivery Tracking Module ---");
        // TODO: Call DeliveryManager logic here
        System.out.println("This will handle delivery tracking and rerouting.");
    }

    private void scheduleMaintenance() {
        System.out.println("\n--- Maintenance Scheduling Module ---");
        // TODO: Call MaintenanceManager logic here
        System.out.println("This will schedule and log vehicle maintenance.");
    }

    private void generateReports() {
        System.out.println("\n--- Fuel Efficiency and System Reports ---");
        // TODO: Call ReportGenerator logic here
        System.out.println("This will generate reports on fuel and usage.");
    }

    private void exitSystem() {
        System.out.println("\n✅ Exiting system. Thank you!");
        running = false;
    }
}
