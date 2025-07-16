package modules;

import models.Delivery;
import structures.list.LinkedList;
import utils.FileHandler;
import utils.InputValidator;

import java.util.Scanner;

public class DeliveryManager {

    private final LinkedList<Delivery> deliveryQueue = new LinkedList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final DriverManager driverManager;
    private final VehicleManager vehicleManager;

    public DeliveryManager(DriverManager driverManager, VehicleManager vehicleManager) {
        this.driverManager = driverManager;
        this.vehicleManager = vehicleManager;

        var loaded = FileHandler.loadDeliveries();
        for (Delivery d : loaded) {
            deliveryQueue.add(d);
        }

        if (!loaded.isEmpty()) {
            System.out.println("Loaded " + loaded.size() + " deliveries from file.");
        }
    }

    public void addDelivery() {
        System.out.println("\n--- Schedule New Delivery ---");

        while (true) {
            // Get package ID
            String packageId = InputValidator.getValidString("Enter Package ID: ", 3, 20);
            if (packageId.equals("BACK")) {
                return;
            }

            // Check if package ID already exists
            Delivery existing = deliveryQueue.find(d -> d.getPackageId().equals(packageId));
            if (existing != null) {
                if (!InputValidator.handleErrorAndAskRetry("Package ID already exists!")) {
                    return;
                }
                continue;
            }

            // Get origin
            String origin = InputValidator.getValidString("Enter Origin: ", 2, 50);
            if (origin.equals("BACK")) {
                return;
            }

            // Get destination
            String destination = InputValidator.getValidString("Enter Destination: ", 2, 50);
            if (destination.equals("BACK")) {
                return;
            }

            // Get ETA with proper date validation
            String eta = InputValidator.getValidDateTime("Enter ETA");
            if (eta.equals("BACK")) {
                return;
            }

            try {
                var driver = driverManager.assignDriver();
                if (driver == null) {
                    if (!InputValidator.handleErrorAndAskRetry("No available driver. Cannot schedule delivery.")) {
                        return;
                    }
                    continue;
                }

                var vehicle = vehicleManager.getAvailableVehicle();
                if (vehicle == null) {
                    if (!InputValidator.handleErrorAndAskRetry("No available vehicle. Cannot schedule delivery.")) {
                        return;
                    }
                    continue;
                }

                Delivery delivery = new Delivery(
                        packageId, origin, destination, eta,
                        vehicle.getRegistrationNumber(),
                        driver.getDriverId(),
                        "Pending"
                );

                deliveryQueue.add(delivery);
                FileHandler.saveDeliveries(deliveryQueue.toList());

                InputValidator.showSuccess("Delivery scheduled!");
                return;

            } catch (Exception e) {
                if (!InputValidator.handleErrorAndAskRetry("Error creating delivery: " + e.getMessage())) {
                    return;
                }
            }
        }
    }

    public void listDeliveries() {
        System.out.println("\n--- All Deliveries ---");
        deliveryQueue.printAll();
    }

    public void updateDeliveryStatus() {
        System.out.println("\n--- Update Delivery Status ---");

        while (true) {
            String packageId = InputValidator.getValidString("Enter Package ID to update status: ", 1, 20);
            if (packageId.equals("BACK")) {
                return;
            }

            var delivery = deliveryQueue.find(d -> d.getPackageId().equalsIgnoreCase(packageId));
            if (delivery == null) {
                if (!InputValidator.handleErrorAndAskRetry("Delivery not found.")) {
                    return;
                }
                continue;
            }

            String[] allowedStatuses = {"Pending", "In Transit", "Delivered", "Cancelled"};
            String status = InputValidator.getValidChoice("Enter new status (Pending/In Transit/Delivered/Cancelled): ", allowedStatuses, false);
            if (status.equals("BACK")) {
                return;
            }

            try {
                delivery.setStatus(status);
                FileHandler.saveDeliveries(deliveryQueue.toList());
                InputValidator.showSuccess("Delivery status updated.");
                return;
            } catch (Exception e) {
                if (!InputValidator.handleErrorAndAskRetry("Error updating delivery status: " + e.getMessage())) {
                    return;
                }
            }
        }
    }

    public void rerouteDelivery() {
        System.out.println("\n--- Reroute Delivery ---");

        while (true) {
            String packageId = InputValidator.getValidString("Enter Package ID to reroute: ", 1, 20);
            if (packageId.equals("BACK")) {
                return;
            }

            var delivery = deliveryQueue.find(d -> d.getPackageId().equalsIgnoreCase(packageId));
            if (delivery == null) {
                if (!InputValidator.handleErrorAndAskRetry("Delivery not found.")) {
                    return;
                }
                continue;
            }

            String newDest = InputValidator.getValidString("Enter new destination: ", 2, 50);
            if (newDest.equals("BACK")) {
                return;
            }

            try {
                delivery.setDestination(newDest);
                FileHandler.saveDeliveries(deliveryQueue.toList());
                InputValidator.showSuccess("Delivery rerouted.");
                return;
            } catch (Exception e) {
                if (!InputValidator.handleErrorAndAskRetry("Error rerouting delivery: " + e.getMessage())) {
                    return;
                }
            }
        }
    }

    public void removeDelivery() {
        System.out.print("Enter Package ID to remove: ");
        String packageId = scanner.nextLine().trim();

        boolean removed = deliveryQueue.removeIf(d -> d.getPackageId().equalsIgnoreCase(packageId));
        if (removed) {
            FileHandler.saveDeliveries(deliveryQueue.toList());
            System.out.println("Delivery removed.");
        } else {
            System.out.println("Package not found.");
        }
    }

    // Optional: Search deliveries by Package ID
    public void searchByPackageId() {
        System.out.println("\n--- Search Delivery by Package ID ---");

        while (true) {
            String packageId = InputValidator.getValidString("Enter Package ID to search: ", 1, 20);
            if (packageId.equals("BACK")) {
                return;
            }

            var delivery = deliveryQueue.find(d -> d.getPackageId().equalsIgnoreCase(packageId));
            if (delivery != null) {
                System.out.println("Delivery Found:");
                System.out.println(delivery);
                return;
            } else {
                if (!InputValidator.handleErrorAndAskRetry("Delivery not found.")) {
                    return;
                }
            }
        }
    }
}
