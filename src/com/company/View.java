package com.company;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The View class
 *
 * @author Magnus Wellbring
 */
public class View {
    private static View instance = null;
    Scanner scanner = new Scanner(System.in);

    private View() {
    }

    public static View getInstance() {
        if (instance == null) {
            instance = new View();
        }
        return instance;
    }

    public void resetScanner(){
        scanner.close();
        scanner = new Scanner(System.in);
    }

    public String readString() {
        String string = scanner.nextLine();
        return string;
    }

    public void printLine(String string) {
        System.out.println(string);
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }

    public <E> void printList(ArrayList<E> list) {
        for (E listItem : list) {
            System.out.println(listItem.toString());
        }
    }

    public <T extends HasDescription> T showMenuAndGetChoice(T[] menuItems) {
        System.out.println("\nMake a choice.");
        for (int i = 0; i < menuItems.length; i++) {
            System.out.println((i + 1) + ". " + menuItems[i].getDescription());
        }
        int menuChoice = -1;
        do {
            try {
                menuChoice = Integer.parseInt(scanner.nextLine());
                return menuItems[menuChoice - 1];
            } catch (Exception e) {
                printErrorMessage("Invalid choice, try again.");
            }
        } while (menuChoice != (menuItems.length - 1));
        return null;
    }


    /**
     * The available options in the log in menu
     */
    public enum LoginMenuItem implements HasDescription {
        LOGIN("Log in"),
        CREATE_CUSTOMER_ACCOUNT("Create new account"),
        QUIT("Quit");

        private String description;

        LoginMenuItem(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /**
     * The available options in the Customer menu
     */
    public enum CustomerMenuItem implements HasDescription {
        ITEMS_BY_NAME("Show items by name"),
        ITEMS_BY_PRICE("Show items by price"),
        ADD_OR_REMOVE_ITEM_TO_CART("Add items to cart, or change amount of items in cart"),
        SHOW_CART("Show Cart"),
        EMPTY_CART("Empty shopping cart"),
        CHECK_OUT("Check out shopping cart"),
        SHOW_BALANCE("Display account balance"),
        DEPOSIT_CASH("Deposit money"),
        CHANGE_PASSWORD("Change password"),
        LOGOUT("Log out");
        //TODO add money, check out, change password

        public String description;

        CustomerMenuItem(String menuItemString) {
            this.description = menuItemString;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /**
     * The available options in the Employee main menu
     */
    public enum EmployeeMenuItem implements HasDescription {
        HANDLE_ACCOUNTS("Handle user accounts"),
        HANDLE_INVENTORY("Handle inventory"),
        CHANGE_PASSWORD("Change password"),
        LOGOUT("Log out");

        private String description;

        EmployeeMenuItem(String menuItemString) {
            this.description = menuItemString;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /**
     * The available options in the Handle Accounts submenu
     */
    public enum HandleAccountsMenuItem implements HasDescription { //TODO consolidate create methods?
        ADD_EMPLOYEE("Add a new employee account"),
        PRINT_EMPLOYEE_ARRAY("Display employees"),
        SET_EMPLOYEE_SALARY("Set employee salary"),
        ADD_CUSTOMER("Add new customer account"),
        PRINT_CUSTOMER_ARRAY("Display customers"),
        PRINT_USER_ARRAY("Display all users"),
        DELETE_ACCOUNT("Delete account"),
        WRITE_USERS_TO_FILE("Save all users to file"),
        READ_USERS_FROM_FILE("Read users from file"),
        RETURN("Return to menu");

        private String menuItemString;

        HandleAccountsMenuItem(String menuItemString) {
            this.menuItemString = menuItemString;
        }


        @Override
        public String getDescription() {
            return menuItemString;
        }
    }

    /**
     * The available options in the Handle Inventory submenu
     */
    public enum HandleInventoryMenuItem implements HasDescription {
        ADD_ITEM("Add a new item for sale"),
        CHANGE_ITEM_STOCK("Increase or decrease item stock"),
        VIEW_ITEMS_BY_NAME("Display inventory in alphabetical order"),
        VIEW_ITEMS_BY_PRICE("Display inventory in order of price"),
        VIEW_ITEMS_BY_STOCK("Display inventory in order of current stock"),
        WRITE_ITEMS_TO_FILE("Write inventory to file"),
        READ_ITEMS_FROM_FILE("Read inventory from file"),
        RETURN("Return to menu");

        private String description;

        HandleInventoryMenuItem(String menuItemString) {
            this.description = menuItemString;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }


}
