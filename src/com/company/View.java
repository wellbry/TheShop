package com.company;

import java.util.Scanner;

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

    public String readString(){
        String string = scanner.nextLine();
        return string;
    }

    public int readInt(){
        return InputSanitizers.convertToInt(scanner.nextLine());
    }

    public void printLine(String string){
        System.out.println(string);
    }

    public void printErrorMessage(String errorMessage){
        System.out.println("Error: " + errorMessage);
    }

    public LoginMenuItem showLoginGetChoice() {
        System.out.println("Welcome to the Store.\nPlease make a choice:");
        int i = 1;
        for (LoginMenuItem menuItem : LoginMenuItem.values()) {
            System.out.println(i + " " + menuItem.menuItemString);
            i++;
        }
        int choiceIndex = scanner.nextInt();
        return LoginMenuItem.values()[choiceIndex - 1];
    }

    public CustomerMenuItem showCustomerMenuGetChoice() {
        System.out.println();
        int i = 1;
        for (CustomerMenuItem menuItem : CustomerMenuItem.values()) {
            System.out.println(i + " " + menuItem.menuItemString);
            i++;
        }
        int choiceIndex = scanner.nextInt();
        return CustomerMenuItem.values()[choiceIndex - 1];
    }

    public EmployeeMenuItem showEmployeeMenuGetChoice() {
        System.out.println();
        int i = 1;
        for (EmployeeMenuItem menuItem : EmployeeMenuItem.values()) {
            System.out.println(i + " " + menuItem.menuItemString);
            i++;
        }
        int choiceIndex = scanner.nextInt();
        return EmployeeMenuItem.values()[choiceIndex - 1];
    }

    public HandleAccountsMenuItem showHandleAccountsMenuGetChoice() {
        System.out.println();
        int i = 1;
        for (HandleAccountsMenuItem menuItem : HandleAccountsMenuItem.values()) {
            System.out.println(i + " " + menuItem.menuItemString);
            i++;
        }
        int choiceIndex = scanner.nextInt();
        return HandleAccountsMenuItem.values()[choiceIndex - 1];
    }

    public HandleInventoryMenuItem showHandleInventoryMenuGetChoice() {
        System.out.println();
        int i = 1;
        for (HandleInventoryMenuItem menuItem : HandleInventoryMenuItem.values()) {
            System.out.println(i + " " + menuItem.menuItemString);
            i++;
        }
        int choiceIndex = scanner.nextInt();
        return HandleInventoryMenuItem.values()[choiceIndex - 1];
    }






    public enum LoginMenuItem {
        LOGIN("Log in"),
        CREATE_CUSTOMER_ACCOUNT("Create new account"),
        QUIT("Quit");

        public String menuItemString;

        LoginMenuItem(String menuItemString) {
            this.menuItemString = menuItemString;
        }
    }

    public enum CustomerMenuItem {
        ITEMS_BY_NAME("Show items by name"),
        ITEMS_BY_PRICE("Show items by price"),
        ADD_ITEM_TO_CART("Add item to cart"),
        SHOW_CART("Show Cart"),
        LOGOUT("Log out");

        public String menuItemString;

        CustomerMenuItem(String menuItemString) {
            this.menuItemString = menuItemString;
        }
    }

    public enum EmployeeMenuItem{
        HANDLE_ACCOUNTS("Handle user accounts"),
        HANDLE_INVENTORY("Handle inventory"),
        LOGOUT("Log out");

        public String menuItemString;
        EmployeeMenuItem(String menuItemString) {
            this.menuItemString = menuItemString;
        }
    }

    public enum HandleAccountsMenuItem {
        ADD_EMPLOYEE("Add new employee account"),
        DELETE_EMPLOYEE_ACCOUNT("Delete employee account"),
        PRINT_EMPLOYEE_ARRAY("Display employees"),
        ADD_CUSTOMER("Add new customer account"),
        DELETE_CUSTOMER_ACCOUNT("Delete customer account"),
        PRINT_CUSTOMER_ARRAY("Display customers"),
        PRINT_USER_ARRAY("Display all users"),
        WRITE_USERS_TO_FILE("Save all users to file"),
        READ_USERS_FROM_FILE("Read users from file"),
        RETURN("Return to menu");

        public String menuItemString;
        HandleAccountsMenuItem(String menuItemString){
            this.menuItemString = menuItemString;
        }
    }

    public enum HandleInventoryMenuItem{
        ADD_ITEM("Add new item for sale"),
        INCREASE_ITEM_STOCK("Increase item stock"),
        VIEW_ITEMS_BY_NAME("Display item in alphabetical order"),
        VIEW_ITEMS_BY_PRICE("Display items in order of price"),
        VIEW_ITEMS_BY_STOCK("Display item in order of current stock"),
        WRITE_ITEMS_TO_FILE("Write inventory to file"),
        READ_ITEMS_FROM_FILE("Read inventory from file"),
        RETURN("Return to menu");


        public String menuItemString;
        HandleInventoryMenuItem(String menuItemString){
            this.menuItemString = menuItemString;
        }
    }


}
