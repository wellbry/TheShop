package com.company;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.function.ToDoubleBiFunction;

public class Shop {
    Scanner scan = new Scanner(System.in);
    //  ArrayList<Customer> customers = new ArrayList<>();
    //  ArrayList<Employee> employees = new ArrayList<>();
    View view = View.getInstance();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Item> inventory = new ArrayList<>();
    private Customer loggedInCustomer;
    private Employee loggedInEmployee;

    public Shop() {
        users.add(new Employee("Magnus", "admin", "password", 1));
    }

    public void loginMenu() {
        View.LoginMenuItem menuChoice = null;
        do {
            menuChoice = view.showLoginGetChoice();
            switch (menuChoice) {
                case LOGIN:
                    logIn();
                    break;
                case CREATE_CUSTOMER_ACCOUNT:
                    createCustomerAccount();
                    break;
                case QUIT:
                    System.exit(0);
                default:
                    // TODO
                    System.out.println("Choose a valid alternative");
                    break;
            }
        } while (menuChoice != View.LoginMenuItem.QUIT);
    }

    public void customerMenu() {
        View.CustomerMenuItem menuChoice = null;
        do {
            menuChoice = view.showCustomerMenuGetChoice();
            switch (menuChoice) {
                case ITEMS_BY_NAME:
                    Collections.sort(inventory, new Item.SortAlphabetically());
                    printInventory();
                    break;
                case ITEMS_BY_PRICE:
                    Collections.sort(inventory, new Item.SortByPrice());
                    printInventory();
                    break;
                case ADD_ITEM_TO_CART:
                    addItemToCart();
                    break;
                case SHOW_CART:
                    loggedInCustomer.printShoppingCart();
                    break;
                case LOGOUT:
                    loggedInCustomer = null;
                    loginMenu();
                    break;
            }
        } while (menuChoice != View.CustomerMenuItem.LOGOUT);
    }

    public void employeeMenu() {
        View.EmployeeMenuItem menuChoice = null;
        while (menuChoice != View.EmployeeMenuItem.LOGOUT) {
            menuChoice = view.showEmployeeMenuGetChoice();
            switch (menuChoice) {
                case HANDLE_ACCOUNTS:
                    handleAccountMenu();
                    break;
                case HANDLE_INVENTORY:
                    handleInventoryMenu();
                    break;
                case LOGOUT:
                    loggedInEmployee = null;
                    loginMenu();
                    break;
                default:
                    view.printErrorMessage("Choose a valid alternative");
                    break;
            }
        }
    }

    public void handleAccountMenu() {
        View.HandleAccountsMenuItem menuChoice = null;
        while (menuChoice != View.HandleAccountsMenuItem.RETURN) {
            menuChoice = view.showHandleAccountsMenuGetChoice();
            switch (menuChoice) {
                case ADD_EMPLOYEE:
                    createEmployeeAccount();
                    break;
                case DELETE_EMPLOYEE_ACCOUNT:
                    //TODO
                    break;
                case PRINT_EMPLOYEE_ARRAY:
                    printEmployees();
                    break;
                case ADD_CUSTOMER:
                    createCustomerAccount();
                    break;
                case DELETE_CUSTOMER_ACCOUNT:
                    //TODO
                    break;
                case PRINT_CUSTOMER_ARRAY:
                    printCustomers();
                    break;
                case PRINT_USER_ARRAY:
                    printUsers();
                    break;
                case WRITE_USERS_TO_FILE:
                    FileUtils.saveObject(users, "Users.ser");
                    break;
                case READ_USERS_FROM_FILE:
                    FileUtils.loadObject("Users.ser");
                    break;
                case RETURN:
                    employeeMenu();
                    break;
                default:
                    view.printErrorMessage("Invalid alternative");
            }
        }
    }

    public void handleInventoryMenu() {
        View.HandleInventoryMenuItem menuChoice = null;
        while (menuChoice != View.HandleInventoryMenuItem.RETURN) {
            menuChoice = view.showHandleInventoryMenuGetChoice();
            switch (menuChoice) {
                case ADD_ITEM:
                    addInventoryItem();
                    break;
                case INCREASE_ITEM_STOCK:
                    //TODO
                    break;
                case VIEW_ITEMS_BY_NAME:
                    //TODO
                    break;
                case VIEW_ITEMS_BY_PRICE:
                    //TODO
                    break;
                case VIEW_ITEMS_BY_STOCK:
                    //TODO
                    break;
                case WRITE_ITEMS_TO_FILE:
                    writeInventoryToFile();
                    break;
                case READ_ITEMS_FROM_FILE:
                    readInventoryFromFile();
                    break;
                default:
                    view.printErrorMessage("Invalid choice");
            }
        }
    }


    public void logIn() {
        view.printLine("Enter login");
        String login = scan.nextLine();
        boolean found = false; // needs to be used, if !found can't log in
        // TODO
        int indexOfFound = -1;
        for (int i = 0; i < users.size(); i++) {
            if (login.equals(users.get(i).getLogin())) {
                found = true;
                indexOfFound = i;
            }
        }
        view.printLine("Enter password");
        String password = scan.nextLine();
        if (password.equals(users.get(indexOfFound).getPassword())
                && users.get(indexOfFound).getUserType().equals(User.UserType.CUSTOMER)) {
            loggedInCustomer = (Customer) users.get(indexOfFound);
            customerMenu();
        } else if (password.equals(users.get(indexOfFound).getPassword())
                && users.get(indexOfFound).getUserType().equals(User.UserType.EMPLOYEE)) {
            loggedInEmployee = (Employee) users.get(indexOfFound);
            employeeMenu();
        } else {
            view.printErrorMessage("Wrong password");
        }
    }

    public void createCustomerAccount() {
        String login;
        boolean isName;
        String name;
        do {
            System.out.println("Enter name");
            name = scan.nextLine();
            isName = InputSanitizers.isAlphabet(name);
        } while (!isName);
        System.out.println("Enter login");
        boolean loginTaken = false;
        do {
            login = scan.nextLine();
            for (User user : users) {
                if (login.equals(user.getLogin())) {
                    loginTaken = true;
                    System.out.println("Login taken, please try again");
                }
            }
        } while (loginTaken);
        System.out.println("Choose password");
        String password = scan.nextLine();
        users.add(new Customer(name, login, password));
        System.out.println("Account created, you may now log in with your new account");
    }

    public void createEmployeeAccount() {
        String login;
        System.out.println("Enter name");
        String name = scan.nextLine();
        System.out.println("Enter login");
        boolean loginTaken = false;
        do {
            login = scan.nextLine();
            for (User user : users) {
                if (login.equals(user.getLogin())) {
                    loginTaken = true;
                    System.out.println("Login taken, please try again");
                }
            }
        } while (loginTaken);

        System.out.println("Choose password");
        String password = scan.nextLine();
        System.out.println("Enter Salary");
        int salary = InputSanitizers.convertToInt(scan.nextLine());
        users.add(new Employee(name, login, password, salary));
    }

    public void addInventoryItem() {
        System.out.println("Enter name of product");
        String name = scan.nextLine();
        System.out.println("Enter price");
        int price = InputSanitizers.convertToInt(scan.nextLine());
        inventory.add(new Item(name, price));
    }

    public void writeInventoryToFile() {
        FileUtils.saveObject(inventory, "Inventory.ser");
    }

    public void readInventoryFromFile() {
        inventory = (ArrayList<Item>) FileUtils.loadObject("Inventory.ser");
    }

    public void printUsers() {
        for (User user : users) {
            System.out.println(user);
        }
    }

    public void printEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals(User.UserType.EMPLOYEE)) {
                employees.add((Employee) user);
            }
        }
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public void printCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals(User.UserType.CUSTOMER)) {
                customers.add((Customer) user);
            }
        }
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public void printInventory() {
        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    public void nukeInventory() {
        inventory.clear();
    }

    public void addItemToCart() { // needs method to check for amount in cart vs amount in stock
        System.out.println("Enter name of purchase");
        String input = scan.nextLine();
        String itemName = "";
        int itemPrice = -1;
        boolean itemFound = false;
        for (int i = 0; i < inventory.size(); i++) {
            if (input.equalsIgnoreCase(inventory.get(i).getName())) {
                itemName = inventory.get(i).getName();
                itemPrice = inventory.get(i).getPrice();
                itemFound = true;
            }

        }
        if (!itemFound) {
            System.out.println("Item not found, try again");
            return;
        }
        System.out.println("Enter amount");
        int amount = InputSanitizers.convertToInt(scan.nextLine());

        loggedInCustomer.addItemToCart(itemName, itemPrice, amount);
    }

    public void test() {
        users.add(new Customer("Ludvig", "customer", "password"));
        users.add(new Customer("Örjan", "bl", "password"));
        users.add(new Customer("Anders", "ad", "password"));

        Collections.sort(users);
        //   printUsers();


        inventory.add(new Item("Tuggummi", 1));
        inventory.add(new Item("Stol", 80));
        inventory.add(new Item("Kaffe", 5));
        inventory.add(new Item("Cola", 8));
        inventory.add(new Item("Kanelbulle", 12));
        inventory.add(new Item("Te", 5));

        for (Item item : inventory) {
            item.addStock(20);
        }

    /*    Collections.sort(items, new Item.SortAlphabetically());
        printGoods();

        System.out.println();

        Collections.sort(items, new Item.SortByPrice());
        printGoods();
*/
        // printEmployees();
    }

}
