package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Shop {

    private View view = View.getInstance();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Item> inventory = new ArrayList<>();
    private Customer loggedInCustomer;
    private User loggedInUser;

    //TODO JavaDoc

    public Shop() {
        users.add(new Employee("Admin", "admin", "password", 1));
//        if (FileUtils.loadObject("Users.ser") != null) { // Can't have an empty users, there'd be no Employee to access the employee menu
//            try {
//                users = (ArrayList<User>) FileUtils.loadObject("Users.ser");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            inventory = (ArrayList<Item>) FileUtils.loadObject("Inventory.ser");
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public void loginMenu() {
        View.LogInMenuItem menuChoice = null;
        while (menuChoice != View.LogInMenuItem.QUIT) {
            menuChoice = view.showMenuAndGetChoice(View.LogInMenuItem.values());
            switch (menuChoice) {
                case LOGIN:
                    logIn();
                    break;
                case CREATE_CUSTOMER_ACCOUNT:
                    createCustomerAccount();
                    break;
                case QUIT:
//                    FileUtils.saveObject(users, "Users.ser");
//                    FileUtils.saveObject(inventory, "Inventory.ser");
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
                    Collections.sort(inventory, new SortItemsAlphabetically());
                    view.printList(inventory);
                    break;
                case ITEMS_BY_PRICE:
                    Collections.sort(inventory, new SortItemsByPrice());
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
                    view.printLine(String.format("Your balance is: %d" + loggedInCustomer.getBalance()));
                    break;
                case DEPOSIT_MONEY:
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
                default:
                    view.printErrorMessage("Choose a valid alternative");
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
                    view.printErrorMessage("Choose a valid alternative");
                    break;
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
                    Collections.sort(inventory, new SortItemsAlphabetically());
                    view.printList(inventory);
                    break;
                case VIEW_ITEMS_BY_PRICE:
                    Collections.sort(inventory, new SortItemsByPrice());
                    view.printList(inventory);
                    break;
                case VIEW_ITEMS_BY_STOCK:
                    Collections.sort(inventory, new SortItemsByStock());
                    view.printList(inventory);
                    break;
                case WRITE_ITEMS_TO_FILE:
                    FileUtils.saveObject(inventory, "Inventory.ser");
                    break;
                case READ_ITEMS_FROM_FILE:
                    inventory = (ArrayList<Item>) FileUtils.loadObject("Inventory.ser");
                    break;
                case RETURN:
                    employeeMenu();
                    break;
                default:
                    view.printErrorMessage("Choose a valid alternative");
                    break;
            }
        }
    }

    void logIn() {
        view.printLine("Enter login");
        boolean userFound = false;
        int indexOfFoundUser = -1;
        String login = view.readString();
        for (int i = 0; i < users.size(); i++) { //Looks through users to see if there's a match for the entered logIn
            if (login.equals(users.get(i).getLogIn())) {
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
                && users.get(indexOfFoundUser).getUserType().equals(User.UserType.CUSTOMER)) { //The found User was a customer and the entered password was correct
            loggedInCustomer = (Customer) users.get(indexOfFoundUser);
            loggedInUser = users.get(indexOfFoundUser);
            customerMenu();
        } else if (users.get(indexOfFoundUser).isCorrectPassword(password)
                && users.get(indexOfFoundUser).getUserType().equals(User.UserType.EMPLOYEE)) { //The found User was an Employee and the entered password was correct
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
        boolean nameIsAlphabetic;
        String name;
        do {
            view.printLine("Enter name");
            name = view.readString();
            nameIsAlphabetic = InputSanitizers.isAlphabetic(name);
            if (!nameIsAlphabetic) {
                view.printErrorMessage("Name may only contain letters. Please try again");
            }
        } while (!nameIsAlphabetic);
        view.printLine("Enter login");
        boolean loginTaken = false;
        do {
            login = view.readString();
            for (User user : users) { //Search through users to ensure there are no duplicate logIns
                if (login.equals(user.getLogIn())) {
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
        for (int i = 0; i < users.size(); i++) { //Search through users for entered login
            if (users.get(i).getName().equals(employeeEntered) && users.get(i).getUserType() == User.UserType.EMPLOYEE) { //Entered User has been found and is an Employee
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
                view.printErrorMessage("Salary must be a positive number");
            }
        } else {
            view.printErrorMessage("Employee not found");
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
                if (login.equals(user.getLogIn())) {
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
        for (int i = 0; i < users.size(); i++) { //Search through users for the entered logIn
            if (users.get(i).getLogIn().equalsIgnoreCase(userToRemoveLogin)) {
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
        String itemName = view.readString();
        for (Item item:inventory){ // Search through inventory to ensure no duplicate product names
            if (item.getName().equalsIgnoreCase(itemName)){
                view.printErrorMessage("Product with this name already exists");
                return;
            }
        }
        view.printLine("Enter price");
        int price = InputSanitizers.convertToIntPositive(view.readString());
        if (price <= 0) {
            view.printErrorMessage("Price must be a positive number");
        } else {
            inventory.add(new Item(itemName, price));
        }
    }

    void changeStockOfItem() {
        view.printLine("Enter name of Item");
        String itemToIncrease = view.readString();
        boolean itemFound = false;
        int indexOfItemFound = -1;
        for (int i = 0; i < inventory.size(); i++) { //Search through inventory for the entered item
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
                if ((0 - change) > inventory.get(indexOfItemFound).getAmount()) { // If decrease would set stock to a negative number set it to 0
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

    private void printEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals(User.UserType.EMPLOYEE)) { //Sorts through users for Employees
                employees.add((Employee) user);
            }
        }
        Collections.sort(employees);
        view.printList(employees);
    }

    private void printCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType().equals(User.UserType.CUSTOMER)) { //Sorts through users for Customers
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
        for (int i = 0; i < cart.size(); i++) { // Search through the customers cart to see if it already includes the entered item
            if (itemNameToSearchFor.equalsIgnoreCase(cart.get(i).getName())) {
                existsInCart = true;
                itemFound = true;
                amountInCart = cart.get(i).getAmount();
                indexOfFound = i;
            }
        }
        if (!existsInCart) {
            for (int i = 0; i < inventory.size(); i++) { // If item is not in cart search through inventory to fetch details needed to add it to cart
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
        if (InputSanitizers.isNumber(amountString)) { //Error handling
            int amount = InputSanitizers.convertToInt(amountString);
            if ((amount + amountInCart) > inventory.get(indexOfFound).getAmount()) { //Ensures that the amount in the cart can't exceed the amount in stock
                view.printErrorMessage("Amount in cart can't exceed amount in stock.");
            } else if (existsInCart) {
                if ((amount + amountInCart) <= 0) { // If amount of item in cart would be 0 or less remove it from cart
                    loggedInCustomer.getShoppingCart().remove(indexOfFound);
                } else { // Else increase or decrease by entered amount
                    loggedInCustomer.changeAmountInCart(indexOfFound, amount);
                }
            } else { // If item was not found in cart add it
                loggedInCustomer.addItemToCart(itemName, itemPrice, amount);
            }
        } else {
            view.printErrorMessage("Amount must be a number");
        }
    }

    void checkOut() {
        int sumOfCart = 0;
        for (Item item : loggedInCustomer.getShoppingCart()) { // Sum up cost of all items in cart
            int price = item.getPrice();
            int amount = item.getAmount();
            sumOfCart += (price * amount);
        }
        if (sumOfCart > loggedInCustomer.getBalance()) {
            view.printErrorMessage("Cost of cart exceeds balance");
        } else {
            for (Item item : loggedInCustomer.getShoppingCart()) { // for each item in cart iterates through the inventory and removes that amount from stock
                for (int i = 0; i < inventory.size(); i++) {
                    if (item.getName().equals(inventory.get(i).getName())) {
                        inventory.get(i).changeStock(0 - item.getAmount());
                    }
                }
            }
            view.printLine("Purchase:");
            view.printList(loggedInCustomer.getShoppingCart());
            loggedInCustomer.emptyShoppingCart();
            loggedInCustomer.pay(sumOfCart);
            view.printLine(String.format("Checkout successful. Current balance: %d\n", loggedInCustomer.getBalance()));
        }
    }

    void depositMoneyToCustomer() {
        view.printLine("Enter amount");
        int deposit = InputSanitizers.convertToIntPositive(view.readString());
        if (deposit < 0) {
            view.printErrorMessage("Deposit must be a positive number");
        } else {
            int balance = loggedInCustomer.depositMoney(deposit);
            view.printLine(String.format("Deposit successful. Current balance: %d\n", balance));
        }
    }

    ArrayList<Item> getInventory() { //only for test methods access
        return inventory;
    }

    ArrayList<User> getUsers() { //only for test methods access
        return users;
    }

    Customer getLoggedInCustomer() { //only for test methods access
        return loggedInCustomer;
    }

    void setLoggedInCustomer(Customer customer) { //only for test methods access
        loggedInCustomer = customer;
        loggedInUser = customer;
    }

    //TODO remove before final commit
    public void test() {
        users.add(new Customer("Ludvig", "customer", "password"));
        users.add(new Customer("Urban", "urre", "password"));
        users.add(new Customer("Anders", "anders1234", "password"));
        users.add(new Employee("Alban", "admin2", "password", 1));

        Collections.sort(users);

        Random rand = new Random();


        inventory.add(new Item("Widgets", 3 + rand.nextInt(13)));
        inventory.add(new Item("Doodads", 3 + rand.nextInt(13)));
        inventory.add(new Item("Thingamajigs", 3 + rand.nextInt(13)));
        inventory.add(new Item("Gizmos", 3 + rand.nextInt(13)));
        inventory.add(new Item("Thingamabobs", 3 + rand.nextInt(13)));
        inventory.add(new Item("Doohickey", 3 + rand.nextInt(13)));
        inventory.add(new Item("Gadgets", 3 + rand.nextInt(13)));
        inventory.add(new Item("Contraptions", 3 + rand.nextInt(13)));
        inventory.add(new Item("Whatchamacallits", 3 + rand.nextInt(13)));
        inventory.add(new Item("Whatnots", 3 + rand.nextInt(13)));
        inventory.add(new Item("Baubles", 3 + rand.nextInt(13)));
        inventory.add(new Item("Geegaws", 3 + rand.nextInt(13)));
        inventory.add(new Item("Curios", 3 + rand.nextInt(13)));


        for (Item item : inventory) {
            item.changeStock(75 + rand.nextInt(75));
        }

        inventory.add(new Item("McGuffin", 100));
        inventory.get(inventory.size() - 1).changeStock(1);
    }

}
