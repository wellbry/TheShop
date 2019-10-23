package com.company;

import java.util.ArrayList;

/**
 * The Customer subclass of User
 * @author Magnus Wellbring
 */
public class Customer extends User {
    private int balance;
    private ArrayList<Item> shoppingCart = new ArrayList<>();

    /**
     * Create a new customer
     * @param name Customer name
     * @param login Customer login name, used when logging in
     * @param password  Customer password
     */
    public Customer(String name, String login, String password) {
        super(name, login, password);
        balance = 200;
        userType = UserType.CUSTOMER;
    }

    /**
     * Return this Customers current balance
     * @return This customers current balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets Customer balance
     * @param balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Subtracts the sent in int from the Customers balance and returns the balance after it's been subtracted
     * @param cost The amount subtracted from this Customers current balance
     * @return The balance of this customer after the cost has been subtracted
     */
    public int pay(int cost) {
        balance -= cost;
        return balance;
    }

    /**
     * Adds money to Customer account
     * @param deposit Amount added
     * @return Balance after amount being added
     */
    public int depositMoney(int deposit) {
        balance += deposit;
        return balance;
    }

    /**
     * Adds a new item to the Customer's shopping cart or increases the amount of the specific item if it's already present int the shopping cart
     * @param itemName Name of the Item
     * @param itemPrice Price of the item, used to create new Item if the Item is not present in the shopping cart
     * @param amount Amount of the Item
     */
    public void addItemToCart(String itemName, int itemPrice, int amount) {
        int indexOfItem = -1;
        boolean itemFound = false;
        for (int i = 0; i<shoppingCart.size(); i++){
            if (itemName.equalsIgnoreCase(shoppingCart.get(i).getName())){
                indexOfItem = i;
                itemFound = true;
            }
        }
        if (itemFound) {
            shoppingCart.get(indexOfItem).addStock(amount);
        } else {
            Item item = new Item(itemName, itemPrice);
            shoppingCart.add(item);
            shoppingCart.get(shoppingCart.lastIndexOf(item)).addStock(amount);
        }
    }

    public int getAmountInCart(String itemName){
        int indexOfItem = -1;
        boolean itemFound = false;
        for (int i = 0; i<shoppingCart.size(); i++){
            if (itemName.equalsIgnoreCase(shoppingCart.get(i).getName())){
                indexOfItem = i;
                itemFound = true;
            }
        }
        if (itemFound){
            return shoppingCart.get(indexOfItem).getAmount();
        }
        return 0;
    }

    /**
     * Returns this Customer's shopping cart
     * @return This Customer's shopping cart
     */
    public ArrayList<Item> getShoppingCart(){
        return shoppingCart;
    }

    /**
     * Clears the Customers shopping cart
     */
    public void emptyShoppingCart(){
        shoppingCart.clear();
    }

    // TODO move to view
    public void printShoppingCart(){
        for (Item item: shoppingCart){
            System.out.println(item.toString());
        }
    }
}
