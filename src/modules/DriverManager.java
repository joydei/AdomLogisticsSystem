package modules;

import models.Driver;
import utils.FileHandler;
import utils.InputValidator;
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

        while (true) {
            // Get driver ID
            String id = InputValidator.getValidString("Enter Driver ID: ", 2, 15);
            if (id.equals("BACK")) {
                return;
            }

            if (driverMap.containsKey(id)) {
                if (!InputValidator.handleErrorAndAskRetry("Driver ID already exists.")) {
                    return;
                }
                continue;
            }

            // Get driver name
            String name = InputValidator.getValidString("Enter Name: ", 2, 50);
            if (name.equals("BACK")) {
                return;
            }

            // Get years of experience
            int exp = InputValidator.getValidInteger("Enter Years of Experience: ", 0, 50);
            if (exp == -999) {
                return;
            }

            try {
                Driver driver = new Driver(id, name, exp);
                driverMap.put(id, driver);
                availableDrivers.enqueue(driver);

                FileHandler.saveDrivers(getAllDrivers());

                InputValidator.showSuccess("Driver added successfully!");
                return;

            } catch (Exception e) {
                if (!InputValidator.handleErrorAndAskRetry("Error creating driver: " + e.getMessage())) {
                    return;
                }
            }
        }
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

        while (true) {
            String id = InputValidator.getValidString("Enter Driver ID: ", 1, 15);
            if (id.equals("BACK")) {
                return;
            }

            Driver found = driverMap.get(id);
            if (found != null) {
                System.out.println("Driver Found:");
                System.out.println(found);
                return;
            } else {
                if (!InputValidator.handleErrorAndAskRetry("Driver not found.")) {
                    return;
                }
            }
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
