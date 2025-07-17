package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Utility class for robust input validation and error handling
 */
public class InputValidator {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Get a valid integer input with retry mechanism and special command
     * handling
     */
    public static int getValidInteger(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();

                // Check for special commands
                String command = input.toLowerCase();
                if (command.equals("help") || command.equals("h")) {
                    System.out.println("HELP: Use menu numbers to navigate. Type 'back' to return.");
                    continue;
                }
                if (command.equals("quit") || command.equals("q")) {
                    System.out.println("SUCCESS: Thank you for using Adom Logistics System!");
                    System.exit(0);
                }

                // Check for back command
                if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                    return -999; // Special value to indicate user wants to go back
                }

                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + " (or 'back' to return)");
                    System.out.println("INFO: Valid range: " + min + " to " + max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number (or 'back' to return)");
                System.out.println("INFO: Enter only numeric values (e.g., 123)");
            }
        }
    }

    /**
     * Get a valid double input with retry mechanism
     */
    public static double getValidDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();

                // Check for back command
                if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                    return -999.0; // Special value to indicate user wants to go back
                }

                double value = Double.parseDouble(input);
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + " (or 'back' to return)");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number (or 'back' to return)");
            }
        }
    }

    /**
     * Get a valid name input (alphabetic characters and spaces only)
     */
    public static String getValidName(String prompt, int minLength, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK";
            }

            if (input.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again (or 'back' to return)");
                continue;
            }

            if (input.length() < minLength) {
                System.out.println("Name must be at least " + minLength + " characters long (or 'back' to return)");
                continue;
            }

            if (input.length() > maxLength) {
                System.out.println("Name must be no more than " + maxLength + " characters long (or 'back' to return)");
                continue;
            }

            // Check if name contains only letters, spaces, hyphens and apostrophes
            if (!input.matches("[a-zA-Z\\s\\-']+")) {
                System.out.println("Name can only contain letters, spaces, hyphens, and apostrophes (or 'back' to return)");
                continue;
            }

            return input;
        }
    }

    /**
     * Get a valid date input (YYYY-MM-DD format)
     */
    public static String getValidDate(String prompt) {
        while (true) {
            System.out.print(prompt + " (Format: YYYY-MM-DD, e.g., 2025-07-16): ");
            String input = scanner.nextLine().trim();

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK";
            }

            try {
                LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD (e.g., 2025-07-16) or 'back' to return");
            }
        }
    }

    /**
     * Get a valid future date input (current date or later)
     */
    public static String getValidFutureDate(String prompt) {
        while (true) {
            System.out.print(prompt + " (Format: YYYY-MM-DD, must be today or later): ");
            String input = scanner.nextLine().trim();

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK";
            }

            try {
                LocalDate inputDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate today = LocalDate.now();

                if (inputDate.isBefore(today)) {
                    System.out.println("Date cannot be in the past. Please enter today's date ("
                            + today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ") or later (or 'back' to return)");
                    continue;
                }

                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD (e.g., 2025-07-16) or 'back' to return");
            }
        }
    }

    /**
     * Get a valid past or current date input (not future)
     */
    public static String getValidPastDate(String prompt) {
        while (true) {
            System.out.print(prompt + " (Format: YYYY-MM-DD, cannot be in the future): ");
            String input = scanner.nextLine().trim();

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK";
            }

            try {
                LocalDate inputDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate today = LocalDate.now();

                if (inputDate.isAfter(today)) {
                    System.out.println("Date cannot be in the future. Please enter today's date ("
                            + today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ") or earlier (or 'back' to return)");
                    continue;
                }

                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD (e.g., 2025-07-16) or 'back' to return");
            }
        }
    }

    /**
     * Get a valid future date-time input for ETA
     */
    public static String getValidFutureDateTime(String prompt) {
        while (true) {
            System.out.print(prompt + " (Format: yyyy-MM-dd HH:mm, must be in the future): ");
            String input = scanner.nextLine().trim();

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK";
            }

            try {
                LocalDateTime inputDateTime = LocalDateTime.parse(input, DATE_FORMATTER);
                LocalDateTime now = LocalDateTime.now();

                if (inputDateTime.isBefore(now)) {
                    System.out.println("ETA cannot be in the past. Please enter a future date and time (or 'back' to return)");
                    continue;
                }

                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm (e.g., 2025-07-16 14:30) or 'back' to return");
            }
        }
    }

    /**
     * Get a valid cost input (positive decimal)
     */
    public static double getValidCost(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();

                // Check for back command
                if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                    return -999.0;
                }

                double cost = Double.parseDouble(input);
                if (cost < 0) {
                    System.out.println("Cost cannot be negative. Please enter a positive value (or 'back' to return)");
                    continue;
                }

                return cost;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid cost (e.g., 150.50) or 'back' to return");
            }
        }
    }

    /**
     * Get a valid phone number input (numbers, spaces, hyphens, parentheses,
     * plus sign)
     */
    public static String getValidPhoneNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK";
            }

            if (input.isEmpty()) {
                System.out.println("Phone number cannot be empty. Please try again (or 'back' to return)");
                continue;
            }

            // Remove all non-digit characters to check length
            String digitsOnly = input.replaceAll("[^0-9]", "");

            if (digitsOnly.length() < 10) {
                System.out.println("Phone number must contain at least 10 digits (or 'back' to return)");
                continue;
            }

            if (digitsOnly.length() > 15) {
                System.out.println("Phone number must contain no more than 15 digits (or 'back' to return)");
                continue;
            }

            // Check if phone contains only valid characters (numbers, spaces, hyphens, parentheses, plus)
            if (!input.matches("[0-9\\s\\-()\\+]+")) {
                System.out.println("Phone number can only contain numbers, spaces, hyphens, parentheses, and plus sign");
                System.out.println("Examples: +1-234-567-8900, (234) 567-8900, 234-567-8900 (or 'back' to return)");
                continue;
            }

            return input;
        }
    }

    /**
     * Get a valid license number input (alphanumeric with hyphens)
     */
    public static String getValidLicenseNumber(String prompt, int minLength, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase(); // Convert to uppercase for consistency

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK";
            }

            if (input.isEmpty()) {
                System.out.println("License number cannot be empty. Please try again (or 'back' to return)");
                continue;
            }

            if (input.length() < minLength) {
                System.out.println("License number must be at least " + minLength + " characters long (or 'back' to return)");
                continue;
            }

            if (input.length() > maxLength) {
                System.out.println("License number must be no more than " + maxLength + " characters long (or 'back' to return)");
                continue;
            }

            // Check if license contains only letters, numbers, and hyphens
            if (!input.matches("[A-Z0-9\\-]+")) {
                System.out.println("License number can only contain letters, numbers, and hyphens (e.g., ABC-123-DEF or DL123456) (or 'back' to return)");
                continue;
            }

            // Ensure it contains at least one letter and one number for realism
            boolean hasLetter = input.matches(".*[A-Z].*");
            boolean hasNumber = input.matches(".*[0-9].*");

            if (!hasLetter || !hasNumber) {
                System.out.println("License number must contain at least one letter and one number (e.g., ABC-123-DEF or DL123456) (or 'back' to return)");
                continue;
            }

            return input;
        }
    }

    /**
     * Get a valid location input (city/location names - letters, spaces, and
     * basic punctuation only)
     */
    public static String getValidLocation(String prompt, int minLength, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK";
            }

            if (input.isEmpty()) {
                System.out.println("Location cannot be empty. Please try again (or 'back' to return)");
                continue;
            }

            if (input.length() < minLength) {
                System.out.println("Location must be at least " + minLength + " characters long (or 'back' to return)");
                continue;
            }

            if (input.length() > maxLength) {
                System.out.println("Location must be no more than " + maxLength + " characters long (or 'back' to return)");
                continue;
            }

            // Check if location contains only letters, spaces, commas, periods, hyphens, apostrophes
            // No pure numbers allowed - must start with a letter
            if (!input.matches("[a-zA-Z][a-zA-Z\\s,.'\\-]*")) {
                System.out.println("Location must start with a letter and contain only letters, spaces, commas, periods, apostrophes, and hyphens (or 'back' to return)");
                System.out.println("Examples: 'Accra', 'Cape Coast', 'Ho Central', 'St. Mary's District'");
                continue;
            }

            // Additional check: reject if it's purely numeric or starts with numbers
            if (input.matches("^[0-9].*") || input.matches("^[0-9\\s,.\\-']+$")) {
                System.out.println("Location cannot be purely numeric or start with numbers (or 'back' to return)");
                System.out.println("Please enter a proper location name like 'Accra' or 'Cape Coast'");
                continue;
            }

            return input;
        }
    }

    public static String getValidChoice(String prompt, String[] allowedValues, boolean caseSensitive) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK"; // Special value to indicate user wants to go back
            }

            for (String allowed : allowedValues) {
                if (caseSensitive ? input.equals(allowed) : input.equalsIgnoreCase(allowed)) {
                    return caseSensitive ? input : allowed;
                }
            }

            System.out.print("Invalid choice. Please select from: ");
            for (int i = 0; i < allowedValues.length; i++) {
                System.out.print(allowedValues[i]);
                if (i < allowedValues.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" (or 'back' to return)");
        }
    }

    /**
     * Get a non-empty string input
     */
    public static String getValidString(String prompt, int minLength, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK"; // Special value to indicate user wants to go back
            }

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again (or 'back' to return)");
                continue;
            }

            if (input.length() < minLength) {
                System.out.println("Input must be at least " + minLength + " characters long (or 'back' to return)");
                continue;
            }

            if (input.length() > maxLength) {
                System.out.println("Input must be no more than " + maxLength + " characters long (or 'back' to return)");
                continue;
            }

            return input;
        }
    }

    /**
     * Get a valid date-time input
     */
    public static String getValidDateTime(String prompt) {
        while (true) {
            System.out.print(prompt + " (Format: yyyy-MM-dd HH:mm, e.g., 2025-07-16 14:30): ");
            String input = scanner.nextLine().trim();

            // Check for back command
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                return "BACK"; // Special value to indicate user wants to go back
            }

            try {
                LocalDateTime.parse(input, DATE_FORMATTER);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm (e.g., 2025-07-16 14:30) or 'back' to return");
            }
        }
    }

    /**
     * Get a valid menu choice with retry mechanism
     */
    public static int getValidMenuChoice(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();

                // Check for back command
                if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
                    return -1; // Special value to indicate user wants to go back
                }

                int choice = Integer.parseInt(input);
                if (choice < min || choice > max) {
                    System.out.println("Please enter a valid option between " + min + " and " + max + " (or 'back' to return)");
                    continue;
                }
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (or 'back' to return)");
            }
        }
    }

    /**
     * Ask user if they want to continue or go back
     */
    public static boolean askContinueOrBack(String message) {
        while (true) {
            System.out.println(message);
            System.out.print("Continue? (y/yes to continue, any other key to go back): ");
            String input = scanner.nextLine().trim().toLowerCase();

            return input.equals("y") || input.equals("yes");
        }
    }

    /**
     * Display error message and ask if user wants to retry
     */
    public static boolean handleErrorAndAskRetry(String errorMessage) {
        System.out.println("\n[ERROR] " + errorMessage);
        return askContinueOrBack("Would you like to try again?");
    }

    /**
     * Wait for user to press Enter
     */
    public static void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Display success message
     */
    public static void showSuccess(String message) {
        System.out.println("\n[SUCCESS] " + message);
    }

    /**
     * Display info message
     */
    public static void showInfo(String message) {
        System.out.println("\n[INFO] " + message);
    }
}
