package utils;

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
     * Get a valid string input from a list of allowed values
     */
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

            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else {
                return false;
            }
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
