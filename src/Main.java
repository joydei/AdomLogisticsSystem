// File: src/Main.java

import cli.MainMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("  Adom Logistics Management System  ");
        System.out.println("=========================================\n");

        // Start the menu
        MainMenu menu = new MainMenu();
        menu.launch();
    }
}
