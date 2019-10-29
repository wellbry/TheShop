package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Shop {

    View view = View.getInstance();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Item> inventory = new ArrayList<>();
    private Customer loggedInCustomer; //possible typecasting in methods
    private Employee loggedInEmployee;
    private User loggedInUser;

    //TODO code comments + JavaDoc

    public Shop() {
        users.add(new Employee("Admin", "admin", "password", 1));
    }

    public void loginMenu() {
        View.LoginMenuItem menuChoice = null;
        while (menuChoice != View.LoginMenuItem.QUIT) {
            menuChoice = view.showMenuAndGetChoice(View.LoginMenuItem.values());
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
                    view.printErrorMessage("Choose a valid alternative");
                    break;
            }
        }
    }

    private void customerMenu() {
        View.CustomerMenuItem menuChoice = null;
        while (menuChoice != View.CustomerMenuItem.LOGOUT) {
            menuChoice = view.showMenuAndGetChoice(View.CustomerMenuItem.values());
            switch (menuChoice) {
                case ITEMS_BY_NAME:
                    Collections.sort(inventory, new Item.SortAlphabetically());
                    view.printList(inventory);
                    break;
                case ITEMS_BY_PRICE:
                    Collections.sort(inventory, new Item.SortByPrice());
                    view.printList(inventory);
                    break;
                case ADD_OR_REMOVE_ITEM_TO_CART:
                    addOrRemoveItemsToCart();
                    break;
                case SHOW_CART:
                    view.printList(loggedInCustomer.getShoppingCart());
                    break;
                case EMPTY_CART:
                    loggedInCustomer.emptyShoppingCart();
                    break;
                case CHECK_OUT:
                    checkOut();
                    break;
                case SHOW_BALANCE:
                    view.printLine("Your balance is " + loggedInCustomer.getBalance());
                    break;
                case DEPOSIT_CASH:
                    depositMoneyToCustomer();
                    break;
                case CHANGE_PASSWORD:
                    changePassword();
                    break;
                case LOGOUT:
                    loggedInCustomer = null;
                    loggedInUser = null;
                    loginMenu();
                    break;
            }
        }
    }

    private void employeeMenu() {
        View.EmployeeMenuItem menuChoice = null;
        while (menuChoice != View.EmployeeMenuItem.LOGOUT) {
            menuChoice = view.showMenuAndGetChoice(View.EmployeeMenuItem.values());
            switch (menuChoice) {
                case HANDLE_ACCOUNTS:
                    handleAccountMenu();
                    break;
                case HANDLE_INVENTORY:
                    handleInventoryMenu();
                    break;
                case CHANGE_PASSWORD:
                    changePassword();
                    break;
                case LOGOUT:
                    loggedInEmployee = null;
                    loggedInUser = null;
                    loginMenu();
                    break;
                default:
                    view.printErrorMessage("Choose a valid alternative");
                    break;
            }
        }
    }

    private void handleAccountMenu() {
        View.HandleAccountsMenuItem menuChoice = null;
        while (menuChoice != View.HandleAccountsMenuItem.RETURN) {
            menuChoice = view.showMenuAndGetChoice(View.HandleAccountsMenuItem.values());
            switch (menuChoice) {
                case ADD_EMPLOYEE:
                    createEmployeeAccount();
                    break;
                case DELETE_ACCOUNT:
                    deleteAccount();
                    break;
                case SET_EMPLOYEE_SALARY:
                    setEmployeeSalary();
                    break;
                case PRINT_EMPLOYEE_ARRAY:
                    printEmployees();
                    break;
                case ADD_CUSTOMER:
                    createCustomerAccount();
                    break;
                case PRINT_CUSTOMER_ARRAY:
                    printCustomers();
                    break;
                case PRINT_USER_ARRAY:
                    view.printList(users);
                    break;
                case WRITE_USERS_TO_FILE:
                    //TODO write methods for these or move their inventory respectives
                    FileUtils.saveObject(users, "Users.ser");
                    break;
                case READ_USERS_FROM_FILE:
                    users = (ArrayList<User>) FileUtils.loadObject("Users.ser");
                    break;
                case RETURN:
                    employeeMenu();
                    break;
                default:
                    view.printErrorMessage("Invalid alternative");
            }
        }
    }

    private void handleInventoryMenu() {
        View.HandleInventoryMenuItem menuChoice = null;
        while (menuChoice != View.HandleInventoryMenuItem.RETURN) {
            menuChoice = view.showMenuAndGetChoice(View.HandleInventoryMenuItem.values());
            switch (menuChoice) {
                case ADD_ITEM:
                    addInventoryItem();
                    break;
                case CHANGE_ITEM_STOCK:
                    changeStockOfItem();
                    break;
                case VIEW_ITEMS_BY_NAME:
                    Collections.sort(inventory, new Item.SortAlphabetically());
                    view.printList(inventory);
                    break;
                case VIEW_ITEMS_BY_PRICE:
                    Collections.sort(inventory, new Item.SortByPrice());
                    view.printList(inventory);
                    break;
                case VIEW_ITEMS_BY_STOCK:
                    Collections.sort(inventory, new Item.SortByStock());
                    view.printList(inventory);
                    break;
                case WRITE_ITEMS_TO_FILE:
                    writeInventoryToFile();
                    break;
                case READ_ITEMS_FROM_FILE:
                    readInventoryFromFile();
                    break;
                case RETURN:
                    employeeMenu();
                    break;
                default:
                    view.printErrorMessage("Invalid choice");
            }
        }
    }

    void logIn() {
        view.printLine("Enter login");
        boolean userFound = false;
        int indexOfFoundUser = -1;
        String login = view.readString();
        for (int i = 0; i < users.size(); i++) {
            if (login.equals(users.get(i).getLogin())) {
                userFound = true;
                indexOfFoundUser = i;
            }
        }
        if (!userFound) {
            view.printErrorMessage("User not found");
            return;
        }
        view.printLine("Enter password");
        String password = view.readString();
        if (users.get(indexOfFoundUser).isCorrectPassword(password)
                && users.get(indexOfFoundUser).getUserType().equals(User.UserType.CUSTOMER)) {
            loggedInCustomer = (Customer) users.get(indexOfFoundUser);
            loggedInUser = users.get(indexOfFoundUser);
            customerMenu();
        } else if (users.get(indexOfFoundUser).isCorrectPassword(password)
                && users.get(indexOfFoundUser).getUserType().equals(User.UserType.EMPLOYEE)) {
            loggedInEmployee = (Employee) users.get(indexOfFoundUser);
            loggedInUser = users.get(indexOfFoundUser);
            employeeMenu();
        } else {
            view.printErrorMessage("Wrong password");
        }
    }

    void changePassword() {
        view.printLine("Enter old password");
        String oldPass = view.readString();
        if (loggedInUser.isCorrectPassword(oldPass)) {
            view.printLine("Enter new password");
            String newPass = view.readString();
            loggedInUser.setPassword(newPass);
        } else {
            view.printErrorMessage("Wrong password");
        }
    }

    void createCustomerAccount() {
        String login;
        boolean isName;
        String name;
        do {
            view.printLine("Enter name");
            name = view.readString();
            isName = InputSanitizers.isAlphabet(name);
            if (!isName) {
                view.printErrorMessage("Name may only contain letters. Please try again");
            }
        } while (!isName);
        view.printLine("Enter login");
        boolean loginTaken = false;
        do {
            login = view.readString();
            for (User user : users) {
                if (login.equals(user.getLogin())) {
                    loginTaken = true;
                    view.printLine("Login taken, please try again");
                }
            }
        } while (loginTaken);
        view.printLine("Choose password");
        String password = view.readString();
        users.add(new Customer(name, login, password));
        view.printLine("Account created, you may now log in with your new account");
    }

    void setEmployeeSalary() {
        view.printLine("Enter employee login");
        String employeeEntered = view.readString();
        boolean employeeFound = false;
        int indexOfFound = -1;
        int newSalary = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(1).getName().equals(employeeEntered) && users.get(1).getUserType() == User.UserType.EMPLOYEE) {
                employeeFound = true;
                indexOfFound = i;
            }
        }
        if (employeeFound) {
            view.printLine("Enter new salary");
            newSalary = InputSanitizers.convertToIntPositive(view.readString());
            if (newSalary < 0) {
                Employee temp = (Employee) users.get(indexOfFound);
                temp.setSalary(newSalary);
                users.set(indexOfFound, temp);
            } else {
                view.printErrorMessage("Salary can't be negative");
            }
        }
    }

    void createEmployeeAccount() {
        String login;
        view.printLine("Enter name");
        String name = view.readString();
        view.printLine("Enter login");
        boolean loginTaken = false;
        do {
            login = view.readString();
            for (User user : users) {
                if (login.equals(user.getLogin())) {
                    loginTaken = true;
                    view.printErrorMessage("Login taken, please try again");
                }
            }
        } while (loginTaken);

        view.printLine("Choose password");
        String password = view.readString();
        view.printLine("Enter Salary");
        int salary = -1;
        while (salary < 0) {
            salary = InputSanitizers.convertToIntPositive(view.readString());
            if (salary < 0) {
                view.printErrorMessage("Salary may not be negative. Please try again.");
            }
        }
        users.add(new Employee(name, login, password, salary));
    }

    void deleteAccount() {
        view.printLine("Enter login");
        String userToRemoveLogin = view.readString();
        boolean userFound = false;
        int indexOfFound = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equalsIgnoreCase(userToRemoveLogin)) {
                userFound = true;
                indexOfFound = i;
            }
        }
        if (userFound) {
            if (users.get(indexOfFound) == loggedInUser) {
                view.printErrorMessage("May not remove logged in account");
            } else {
                users.remove(indexOfFound);
                view.printLine("Account removed");
            }
        } else {
            view.printErrorMessage("User not found");
        }
    }

    void addInventoryItem() {
        view.printLine("Enter name of product");
        String name = view.readString();
        view.printLine("Enter price");

        int price = InputSanitizers.convertToIntPositive(view.readString());

        if (price <= 0) {
            view.printErrorMessage("Price must be a positive number");
        } else {
            inventory.add(new Item(name, price));
        }
    }

    void changeStockOfItem() {
        view.printLine("Enter name of Item");
        String itemToIncrease = view.readString();
        boolean itemFound = false;
        int indexOfItemFound = -1;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equalsIgnoreCase(itemToIncrease)) {
                itemFound = true;
                indexOfItemFound = i;
            }
        }
        if (itemFound) {
            view.printLine("Change by how much?");
            String changeInput = view.readString();
            if (InputSanitizers.isNumber(changeInput)) {
                int change = InputSanitizers.convertToInt(changeInput);
                if ((0 - change) > inventory.get(indexOfItemFound).getAmount()) {
                    inventory.get(indexOfItemFound).changeStock(0 - inventory.get(indexOfItemFound).getAmount());
                    view.printLine("Stock set to 0");
                } else {
                    inventory.get(indexOfItemFound).changeStock(change);
                }
            } else {
                view.printErrorMessage("Input must be a number");
            }
        } else {
            view.printErrorMessage("Item not found");
        }
    }

    private void writeInventoryToFile() {
        FileUtils.saveObject(inventory, "Inventory.ser");
    }

    private void readInventoryFromFile() {
        inventory = (ArrayList<Item>) FileUtils.loadObject("Inventory.ser");
    }

    private void printEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals(User.UserType.EMPLOYEE)) {
                employees.add((Employee) user);
            }
        }
        Collections.sort(employees);
        view.printList(employees);
    }

    private void printCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals(User.UserType.CUSTOMER)) {
                customers.add((Customer) user);
            }
        }
        Collections.sort(customers);
        view.printList(customers);
    }

    void addOrRemoveItemsToCart() {
        view.printLine("Enter name of purchase");
        String itemNameToSearchFor = view.readString();
        String itemName = "";
        int itemPrice = -1;
        int amountInCart = 0;
        boolean itemFound = false;
        boolean existsInCart = false;
        int indexOfFound = 0;
        ArrayList<Item> cart = loggedInCustomer.getShoppingCart();
        for (int i = 0; i < cart.size(); i++) {
            if (itemNameToSearchFor.equalsIgnoreCase(cart.get(i).getName())) {
                existsInCart = true;
                itemFound = true;
                amountInCart = cart.get(i).getAmount();
                indexOfFound = i;
            }
        }
        if (!existsInCart) {
            for (int i = 0; i < inventory.size(); i++) {
                if (itemNameToSearchFor.equalsIgnoreCase(inventory.get(i).getName())) {
                    itemName = inventory.get(i).getName();
                    itemPrice = inventory.get(i).getPrice();
                    itemFound = true;
                    indexOfFound = i;
                }
            }
        }
        if (!itemFound) {
            view.printErrorMessage("Item not found");
            return;
        }
        view.printLine("Enter amount");
        String amountString = view.readString();
        if (InputSanitizers.isNumber(amountString)) { //error handling
            int amount = InputSanitizers.convertToInt(amountString);
            if ((amount + amountInCart) > inventory.get(indexOfFound).getAmount()) {
                view.printErrorMessage("Amount in cart can't exceed amount in stock.");
            } else if (existsInCart) {
                if ((amount + amountInCart) < 0) { //if amount of item in cart would be less than 0, set it to 0
                    loggedInCustomer.changeAmountInCart(indexOfFound, -amountInCart);
                } else {
                    loggedInCustomer.changeAmountInCart(indexOfFound, amount);
                }
            } else {
                loggedInCustomer.addItemToCart(itemName, itemPrice, amount);
            }
        } else {
            view.printErrorMessage("Amount must be a number");
        }
    }

    void checkOut() {
        int sumOfCart = 0;
        for (Item item : loggedInCustomer.getShoppingCart()) {
            int price = item.getPrice();
            int amount = item.getAmount();
            sumOfCart += (price * amount);
        }
        if (sumOfCart > loggedInCustomer.getBalance()) {
            view.printErrorMessage("Cost of cart exceeds balance");
        } else {
            for (Item item : loggedInCustomer.getShoppingCart()) { // for each item in cart iterates through the stock and removes that amount from stock
                for (int i = 0; i < inventory.size(); i++) {
                    if (item.getName().equals(inventory.get(i).getName())) {
                        inventory.get(i).changeStock(0 - item.getAmount());
                    }
                }
            }
            loggedInCustomer.emptyShoppingCart();
            loggedInCustomer.pay(sumOfCart);
            view.printLine(String.format("Checkout successful. Current balance: %d\n", loggedInCustomer.getBalance()));
        }
    }

    void depositMoneyToCustomer() { //TODO clean up
        view.printLine("Enter amount");
        int deposit = InputSanitizers.convertToIntPositive(view.readString());
        if (deposit < 0) {
            view.printErrorMessage("Deposit must be a positive number");
        } else {
            int balance = loggedInCustomer.depositMoney(deposit);
            view.printLine("Deposit successful. Current balance: " + balance);
        }
    }

    ArrayList<Item> getInventory() { //only for test methods access
        return inventory;
    }

    ArrayList<User> getUsers() { //only for test methods access
        return users;
    }

    void setLoggedInCustomer(Customer customer) { //only for test methods access
        loggedInCustomer = customer;
        loggedInUser = customer;
    }

    Customer getLoggedInCustomer() { //only for test methods access
        return loggedInCustomer;
    }

    User getLoggedInUser(){
        return loggedInUser;
    }

    void nukeInventory() {
        inventory.clear();
    }

    //TODO remove before final commit
    public void test() {
        users.add(new Customer("Ludvig", "customer", "password"));
        users.add(new Customer("Örjan", "öjje", "password"));
        users.add(new Customer("Anders", "anders1234", "password"));
        users.add(new Employee("Alban", "admin2", "password", 1));

        Collections.sort(users);

        Random rand = new Random();


        inventory.add(new Item("Widgets", 1 + rand.nextInt(15)));
        inventory.add(new Item("Doodads", 1 + rand.nextInt(15)));
        inventory.add(new Item("Thingamajigs", 1 + rand.nextInt(15)));
        inventory.add(new Item("Gizmos", 1 + rand.nextInt(15)));
        inventory.add(new Item("Thingamabobs", 1 + rand.nextInt(15)));
        inventory.add(new Item("Doohickey", 1 + rand.nextInt(15)));
        inventory.add(new Item("Gadgets", 1 + rand.nextInt(15)));
        inventory.add(new Item("Contraptions", 1 + rand.nextInt(15)));
        inventory.add(new Item("Whatchamacallits", 1 + rand.nextInt(15)));
        inventory.add(new Item("Whatnots", 1 + rand.nextInt(15)));
        inventory.add(new Item("Baubles", 1 + rand.nextInt(15)));
        inventory.add(new Item("Geegaws", 1 + rand.nextInt(15)));
        inventory.add(new Item("Curios", 1 + rand.nextInt(15)));


        for (Item item : inventory) {
            item.changeStock(75 + rand.nextInt(75));
        }

        inventory.add(new Item("McGuffin", 100));
        inventory.get( inventory.size() -1).changeStock(1);
    }

}
