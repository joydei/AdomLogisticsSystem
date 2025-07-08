package modules;

import models.Driver;
import utils.FileHandler;
import structures.queue.Queue;

import java.util.*;

public class DriverManager {

    private final Queue<Driver> availableDrivers = new Queue<>();
    private final Map<String, Driver> driverMap = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public DriverManager() {
        List<Driver> loaded = FileHandler.loadDrivers();
        for (Driver d : loaded) {
            driverMap.put(d.getDriverId(), d);
            availableDrivers.enqueue(d); // 
        }

        if (!loaded.isEmpty()) {
            System.out.println("Loaded " + loaded.size() + " drivers from file.");
        }
    }

    // Add a new driver
    public void addDriver() {
        System.out.println("\n--- Add New Driver ---");

        System.out.print("Enter Driver ID: ");
        String id = scanner.nextLine().trim();

        if (driverMap.containsKey(id)) {
            System.out.println("Driver ID already exists.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Years of Experience: ");
        int exp = Integer.parseInt(scanner.nextLine().trim());

        Driver driver = new Driver(id, name, exp);
        driverMap.put(id, driver);
        availableDrivers.enqueue(driver);

        FileHandler.saveDrivers(getAllDrivers());

        System.out.println("Driver added successfully!");
    }

    // View all drivers
    public void listDrivers() {
        System.out.println("\n--- All Registered Drivers ---");

        if (driverMap.isEmpty()) {
            System.out.println("No drivers found.");
            return;
        }

        for (Driver d : driverMap.values()) {
            System.out.println(d);
        }
    }

    // Search by ID
    public void searchDriverById() {
        System.out.println("\n--- Search Driver by ID ---");
        System.out.print("Enter Driver ID: ");
        String id = scanner.nextLine().trim();

        Driver found = driverMap.get(id);
        if (found != null) {
            System.out.println("Driver Found:\n" + found);
        } else {
            System.out.println("Driver not found.");
        }
    }

    // Assign next available driver (FIFO)
    public Driver assignDriver() {
        if (availableDrivers.isEmpty()) {
            System.out.println("No available drivers.");
            return null;
        }

        Driver assigned = availableDrivers.dequeue();
        System.out.println("Assigned Driver: " + assigned.getName());
        return assigned;
    }

    public void showAvailableDrivers() {
        System.out.println("\n--- Available Drivers (Queue Order) ---");
        availableDrivers.printAll();
    }

    public List<Driver> getAllDrivers() {
        return new ArrayList<>(driverMap.values());
    }
}
