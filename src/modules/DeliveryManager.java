package modules;

import models.Delivery;
import structures.list.LinkedList;
import utils.FileHandler;

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

        System.out.print("Enter Package ID: ");
        String packageId = scanner.nextLine().trim();

        System.out.print("Enter Origin: ");
        String origin = scanner.nextLine().trim();

        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine().trim();

        System.out.print("Enter ETA (e.g. 2025-07-02 16:00): ");
        String eta = scanner.nextLine().trim();

        var driver = driverManager.assignDriver();
        if (driver == null) {
            System.out.println("No available driver. Aborting delivery.");
            return;
        }

        var vehicle = vehicleManager.getAvailableVehicle();
        if (vehicle == null) {
            System.out.println("No available vehicle. Aborting delivery.");
            return;
        }

        Delivery delivery = new Delivery(
                packageId, origin, destination, eta,
                vehicle.getRegistrationNumber(),
                driver.getDriverId(),
                "Pending"
        );

        deliveryQueue.add(delivery);
        FileHandler.saveDeliveries(deliveryQueue.toList());

        System.out.println("Delivery scheduled!");
    }

    public void listDeliveries() {
        System.out.println("\n--- All Deliveries ---");
        deliveryQueue.printAll();
    }

    public void updateDeliveryStatus() {
        System.out.print("Enter Package ID to update status: ");
        String packageId = scanner.nextLine().trim();

        var delivery = deliveryQueue.find(d -> d.getPackageId().equalsIgnoreCase(packageId));
        if (delivery == null) {
            System.out.println("Delivery not found.");
            return;
        }

        System.out.print("Enter new status (Pending/In Transit/Delivered/Cancelled): ");
        String status = scanner.nextLine().trim();

        delivery.setStatus(status);
        FileHandler.saveDeliveries(deliveryQueue.toList());

        System.out.println("Delivery status updated.");
    }

    public void rerouteDelivery() {
        System.out.print("Enter Package ID to reroute: ");
        String packageId = scanner.nextLine().trim();

        var delivery = deliveryQueue.find(d -> d.getPackageId().equalsIgnoreCase(packageId));
        if (delivery == null) {
            System.out.println("Delivery not found.");
            return;
        }

        System.out.print("Enter new destination: ");
        String newDest = scanner.nextLine().trim();

        delivery.setDestination(newDest);
        FileHandler.saveDeliveries(deliveryQueue.toList());

        System.out.println("Delivery rerouted.");
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
        System.out.print("Enter Package ID to search: ");
        String packageId = scanner.nextLine().trim();

        var delivery = deliveryQueue.find(d -> d.getPackageId().equalsIgnoreCase(packageId));
        if (delivery != null) {
            System.out.println("Delivery Found:");
            System.out.println(delivery);
        } else {
            System.out.println("Delivery not found.");
        }
    }
}
