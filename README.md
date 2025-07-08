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

* **Functionality:** Manages a pool of available drivers, facilitates assignment based on criteria like proximity or experience, and tracks driver routes, delays, and infractions.
* **Data Structures Used:**
    * **`Queue`**: For FIFO (First-In, First-Out) assignment of available drivers.
    * **`Stack`**: Potentially for LIFO (Last-In, First-Out) tracking of driver activities or historical assignments.

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