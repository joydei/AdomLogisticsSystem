# Adom Logistics - Vehicle Tracking & Maintenance System

---

## 🚀 Project Overview

Adom Logistics in Tema currently relies on manual processes for managing their fleet, driver assignments, and delivery tracking. This **console-based Java application** offers an offline solution that simulates dispatcher decisions using fundamental data structures and algorithms. Crucially, it achieves this **without any external libraries or APIs**, demonstrating a strong command of core computer science principles.

---

## ✨ Core Features (Modules)

### 1. 🚛 Vehicle Management

* **Functionality:** Add, remove, and search for vehicles. Stores essential details like registration number, type, mileage, fuel usage, and assigned driver ID. Enables organization by mileage or type for efficient retrieval.
* **Data Structures Used:**
    * **`HashTable`**: For fast lookup and retrieval of vehicles by registration number.
    * **`BST (Binary Search Tree)`**: For efficient sorting and searching by mileage.

### 2. 👷 Driver Management

* **Functionality:** Handles all operations related to drivers: adding, listing, searching, and assigning. Manages the order of available drivers for assignment and ensures fair assignment.
* **Data Structures Used:**
    * **`HashMap`**: Stores all registered drivers as a key-value data structure.
        * **Purpose:** Allows for quick search by driver ID (O(1) average case), efficient duplicate ID checks before adding a new driver, and easy access to all drivers for file saving operations.
    * **`Queue`**: Manages the order of available drivers for assignment, ensuring a First-In-First-Out (FIFO) assignment approach.
        * **Operations:** `enqueue(driver)` (add to back), `dequeue()` (remove from front for assignment), `peek()` (view the next driver without removing), `printAll()` (display available drivers).
    * **`ArrayList`**: A resizable array-based list that maintains insertion order and allows indexed access.
        * **Purpose:** Temporarily holds drivers when reading from or writing to `drivers.txt`, facilitating file I/O operations.

### 3. 📦 Delivery Tracking

* **Functionality:** Schedules deliveries with origin, destination, and ETA. Assigns vehicles and drivers, and supports dynamic rerouting and status updates.
* **Data Structures Used:**
    * **`LinkedList`**: For a flexible delivery queue, allowing efficient insertion, deletion, and mid-way updates.

### 4. 🔧 Maintenance Scheduling

* **Functionality:** Flags vehicles needing service based on mileage or service dates. Tracks detailed records of parts replaced and associated costs.
* **Data Structures Used:**
    * **`MinHeap`**: For priority scheduling of vehicle maintenance.
    * **`Map (nested)`**: To store comprehensive maintenance history per vehicle.

### 5. 📊 Fuel Efficiency Reports

* **Functionality:** Calculates average fuel usage per vehicle, flags outliers, and sorts vehicles by efficiency for comparative analysis.
* **Data Structures Used:**
    * **`ArrayList`**: With custom sorting algorithms (e.g., MergeSort, QuickSort) for fuel efficiency analysis.

### 6. 💾 File Handling

* **Functionality:** Persistently saves and loads all application data to and from dedicated text files: `vehicles.txt`, `drivers.txt`, `deliveries.txt`, and `maintenance.txt`.
* **Java I/O Used:**
    * **`BufferedReader`** and **`BufferedWriter`**: For efficient line-by-line reading and writing.

### 7. 🖥️ Command Line Interface (CLI)

* **Functionality:** Provides a text-based user interface with an intuitive input-driven menu system for all operations.

---

## 🛠️ Technologies & Tools

* **Platform:** Console-based Java application (Offline only)
* **Java Version:** Java 17+
* **Development Environment:** VS Code
* **Data Management:** Manual Data Structures (No external libraries or APIs for core logic)