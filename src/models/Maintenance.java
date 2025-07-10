package models;

import java.util.Arrays;
import java.util.List;

public class Maintenance {
    private String regNo;           // Vehicle registration number
    private String serviceType;     // e.g., "Oil Change", "Brake Replacement"
    private String serviceDate;     // Date maintenance was done (yyyy-MM-dd)
    private int mileageAtService;   // Mileage when service was done
    private String partsReplaced;   // Summary of parts changed
    private double cost;            // Total cost
    private String nextServiceDate; // Optional (for scheduling)

    public Maintenance(String regNo, String serviceType, String serviceDate, int mileageAtService,
                       String partsReplaced, double cost, String nextServiceDate) {
        this.regNo = regNo;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.mileageAtService = mileageAtService;
        this.partsReplaced = partsReplaced;
        this.cost = cost;
        this.nextServiceDate = nextServiceDate;
    }

    // Constructor used by FileHandler when reading from flat file
    public Maintenance(String regNo, String serviceDate, String description, List<String> partsList, double cost) {
        this.regNo = regNo;
        this.serviceDate = serviceDate;
        this.serviceType = description; // Assuming 'description' is actually the service type
        this.partsReplaced = String.join(";", partsList);
        this.cost = cost;
        this.mileageAtService = -1; // Unknown, optional
        this.nextServiceDate = "";
    }

    // Compatibility for FileHandler
    public String getVehicleRegNo() {
        return regNo;
    }

    public String getDate() {
        return serviceDate;
    }

    public String getDescription() {
        return serviceType;
    }

    public List<String> getPartsReplacedList() {
        return Arrays.asList(partsReplaced.split(";"));
    }

    // Getters
    public String getRegNo() {
        return regNo;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public int getMileageAtService() {
        return mileageAtService;
    }

    public String getPartsReplaced() {
        return partsReplaced;
    }

    public double getCost() {
        return cost;
    }

    public String getNextServiceDate() {
        return nextServiceDate;
    }

    @Override
    public String toString() {
        return String.format(
            "📄 Vehicle: %s\n🔧 Service: %s\n📅 Date: %s\n📍 Mileage: %dkm\n🧩 Parts: %s\n💰 Cost: GHS %.2f\n📆 Next Service: %s\n",
            regNo, serviceType, serviceDate, mileageAtService, partsReplaced, cost,
            (nextServiceDate == null || nextServiceDate.isEmpty()) ? "N/A" : nextServiceDate
        );
    }
}
