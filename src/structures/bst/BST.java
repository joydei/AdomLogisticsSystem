package structures.bst;

import models.Vehicle;

public class BST {
    private static class Node {
        Vehicle vehicle;
        Node left;
        Node right;

        Node(Vehicle vehicle) {
            this.vehicle = vehicle;
        }
    }

    private Node root;

    // Insert vehicle by mileage
    public void insert(Vehicle vehicle) {
        root = insertRecursive(root, vehicle);
    }

    private Node insertRecursive(Node node, Vehicle vehicle) {
        if (node == null) return new Node(vehicle);

        if (vehicle.getMileage() < node.vehicle.getMileage()) {
            node.left = insertRecursive(node.left, vehicle);
        } else {
            node.right = insertRecursive(node.right, vehicle);
        }

        return node;
    }

    // In-order traversal (sorted by mileage)
    public void printInOrder() {
        System.out.println("\n--- Vehicles Sorted by Mileage ---");
        if (root == null) {
            System.out.println("No vehicles in BST.");
        } else {
            printInOrderRecursive(root);
        }
    }

    private void printInOrderRecursive(Node node) {
        if (node != null) {
            printInOrderRecursive(node.left);
            System.out.println(node.vehicle);
            printInOrderRecursive(node.right);
        }
    }

    // Search for vehicles with a specific mileage
    public void searchByMileage(int mileage) {
        System.out.println("\n--- Vehicles with Mileage: " + mileage + " ---");
        boolean found = searchRecursive(root, mileage);
        if (!found) {
            System.out.println("No vehicles found with mileage: " + mileage);
        }
    }

    private boolean searchRecursive(Node node, int mileage) {
        if (node == null) return false;

        boolean found = false;

        if (node.vehicle.getMileage() == mileage) {
            System.out.println(node.vehicle);
            found = true;
        }

        // Search both sides since there can be multiple with same mileage
        boolean leftFound = searchRecursive(node.left, mileage);
        boolean rightFound = searchRecursive(node.right, mileage);

        return found || leftFound || rightFound;
    }
}
