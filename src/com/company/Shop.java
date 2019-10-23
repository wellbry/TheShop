package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Shop {
    //  Scanner scan = new Scanner(System.in);
    //  ArrayList<Customer> customers = new ArrayList<>();
    //  ArrayList<Employee> employees = new ArrayList<>();

    View view = View.getInstance();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Item> inventory = new ArrayList<>();
    private Customer loggedInCustomer;
    private Employee loggedInEmployee;
    private User loggedInUser;

    public Shop() {
        users.add(new Employee("Magnus", "admin", "password", 1));
    }

    public void loginMenu() {
        View.LoginMenuItem menuChoice = null;
        do {
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
                    // TODO
                    view.printErrorMessage("Choose a valid alternative");
                    break;
            }
        } while (menuChoice != View.LoginMenuItem.QUIT);
    }

    public void customerMenu() {
        View.CustomerMenuItem menuChoice = null;
        do {
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
                case ADD_ITEM_TO_CART:
                    addItemToCart();
                    break;
                case SHOW_CART:
                    loggedInCustomer.printShoppingCart();
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
        } while (menuChoice != View.CustomerMenuItem.LOGOUT);
    }

    public void employeeMenu() {
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

    public void handleAccountMenu() {
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
            menuChoice = view.showMenuAndGetChoice(View.HandleInventoryMenuItem.values());
            switch (menuChoice) {
                case ADD_ITEM:
                    addInventoryItem();
                    break;
                case INCREASE_ITEM_STOCK:
                    increaseStockOfItem();
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

    public void logIn() {
        view.printLine("Enter login");
        boolean userFound = false;
        int indexOfFoundUser = -1;
        while (!userFound) {
            String login = view.readString();
            for (int i = 0; i < users.size(); i++) {
                if (login.equals(users.get(i).getLogin())) {
                    userFound = true;
                    indexOfFoundUser = i;
                }
            }
            if (!userFound) {
                view.printErrorMessage("User not found");
                //  return;
            }
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

    public void changePassword(){
        //TODO while?
        view.printLine("Enter old password");
        String oldPass = view.readString();
        if (loggedInUser.isCorrectPassword(oldPass)){
            view.printLine("Enter new password");
            String newPass = view.readString();
            loggedInUser.setPassword(newPass);
        }else {
            view.printErrorMessage("Wrong password");
        }
    }

    public void createCustomerAccount() {
        String login;
        boolean isName;
        String name;
        do {
            view.printLine("Enter name");
            name = view.readString();
            isName = InputSanitizers.isAlphabet(name);
            //TODO first name; last name?
        } while (!isName);
        view.printLine("Enter login");
        boolean loginTaken = false;
        //TODO clean up do while? IntelliJ throws errors
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

    public void createEmployeeAccount() {
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
            salary = InputSanitizers.convertToInt(view.readString());
            if (salary < 0) {
                view.printErrorMessage("Salary may not be negative");
            }
        }
        users.add(new Employee(name, login, password, salary));
    }

    public void deleteAccount() { //TODO merge with delete customer, can't delete self (loggedInUser?)
        view.printLine("Enter login");
        String userToRemoveLogin = view.readString();
        boolean userFound = false;
        int indexOfFound = -1;
        for (int i = 0; i < users.size(); i++) { //for loop?
            if (users.get(i).getLogin().equalsIgnoreCase(userToRemoveLogin)) {
                userFound = true;
                indexOfFound = i;
            }
        }
        if (users.get(indexOfFound) == loggedInUser){
            view.printErrorMessage("May not remove logged in account");
        } else if (userFound) {
            users.remove(indexOfFound);
            view.printLine("Account removed");
        } else {
            view.printErrorMessage("User not found");
        }
    }

    public void addInventoryItem() {
        view.printLine("Enter name of product");
        String name = view.readString();
        view.printLine("Enter price");
        int price = InputSanitizers.convertToInt(view.readString());
        //TODO no negative prices
        inventory.add(new Item(name, price));
    }

    public void increaseStockOfItem() { // TODO clean up, maybe while loop, no negative increases
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
            view.printLine("Increase by how much?");
            int increase = view.readInt();
            inventory.get(indexOfItemFound).addStock(increase);
        } else {
            view.printErrorMessage("Item not found");
        }
    }

    public void writeInventoryToFile() {
        FileUtils.saveObject(inventory, "Inventory.ser");
    }

    public void readInventoryFromFile() {
        inventory = (ArrayList<Item>) FileUtils.loadObject("Inventory.ser");
    }

    public void printEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals(User.UserType.EMPLOYEE)) {
                employees.add((Employee) user);
            }
        }
        for (Employee employee : employees) {
            view.printLine(employee.toString());
        }
        //TODO throw into view
    }

    public void printCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals(User.UserType.CUSTOMER)) {
                customers.add((Customer) user);
            }
        }
        for (Customer customer : customers) {
            view.printLine(customer.toString());
        }
        //TODO throw into View
    }


    public void addItemToCart() { // needs method to check for amount in cart vs amount in stock
        view.printLine("Enter name of purchase");
        String input = view.readString();
        String itemName = "";
        int itemPrice = -1;
        boolean itemFound = false;
        int indexOfFound = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (input.equalsIgnoreCase(inventory.get(i).getName())) {
                itemName = inventory.get(i).getName();
                itemPrice = inventory.get(i).getPrice();
                itemFound = true;
                indexOfFound = i;
            }

        }
        if (!itemFound) {
            view.printErrorMessage("Item not found, try again");
            return;
        }
        view.printLine("Enter amount");
        int amount = InputSanitizers.convertToInt(view.readString());
        //TODO no negative amounts/check cart, cart inventory may not exceed store inventory
        if ((amount + loggedInCustomer.getAmountInCart(itemName)) > inventory.get(indexOfFound).getAmount()){
            view.printErrorMessage("Amount in cart can't exceed amount in stock.");
            return; //TODO consider while loop
        }
        loggedInCustomer.addItemToCart(itemName, itemPrice, amount);
    }

    public void checkOut() {
        //TODO
        int sumOfCart = 0;
        for (Item item:loggedInCustomer.getShoppingCart()){
            int price = item.getPrice();
            int amount = item.getAmount();
            sumOfCart += price*amount;
        }
        if (sumOfCart > loggedInCustomer.getBalance()){
            view.printErrorMessage("Cost of cart exceeds balance");
        } else {
            for (Item item:loggedInCustomer.getShoppingCart()){
                for (int i = 0; i < inventory.size(); i++) {
                    if (item.getName().equals(inventory.get(i).getName())){
                        inventory.get(i).removeStock(item.getAmount());
                    }
                }
            }
            loggedInCustomer.emptyShoppingCart();
            loggedInCustomer.pay(sumOfCart);
            view.printLine(String.format("Checkout successful. Current balance: %d\n", loggedInCustomer.getBalance()));
        }

    }

    public void depositMoneyToCustomer() { //TODO clean up
        view.printLine("Enter amount");
        int deposit = -1;
        while (deposit < 0) {
            deposit = InputSanitizers.convertToInt(view.readString());
            if (deposit < 0) view.printErrorMessage("Deposit must be a positive number");
        }
        int balance = loggedInCustomer.depositMoney(deposit);
        view.printLine("Deposit successful. Current balance: " + balance);
    }

    public void nukeInventory() {
        inventory.clear();
    }

    public void test() {
        users.add(new Customer("Ludvig", "customer", "password"));
        users.add(new Customer("Örjan", "bl", "password"));
        users.add(new Customer("Anders", "ad", "password"));
        users.add(new Employee("Alban", "admin2", "password", 1));

        Collections.sort(users);
        //   printUsers();


        inventory.add(new Item("Tuggummi", 1));
        inventory.add(new Item("Stol", 80));
        inventory.add(new Item("Kaffe", 5));
        inventory.add(new Item("Cola", 8));
        inventory.add(new Item("Kanelbulle", 12));
        inventory.add(new Item("Te", 5));

        Random rand = new Random();

        for (Item item : inventory) { // TODO use rand to generate for final save
            item.addStock(10 + rand.nextInt(11));
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
