package com.company;

import java.util.ArrayList;

/**
 * The Customer subclass of User
 * @author Magnus Wellbring
 */
public class Customer extends User {
    private int balance = 0;
    private ArrayList<Item> shoppingCart = new ArrayList<>();

    /**
     * Create a new customer
     * @param name Customer name
     * @param login Customer login name, used when logging in
     * @param password  Customer password
     */
    public Customer(String name, String login, String password) {
        super(name, login, password, UserType.CUSTOMER);
    }

    /**
     * Return this Customers current balance
     * @return This customers current balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Subtracts cost from the Customers balance
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
     * Adds a new item to the Customer's shopping cart
     * @param itemName Name of the Item
     * @param itemPrice Price of the item
     * @param amount Amount of the Item
     */
    public void addItemToCart(String itemName, int itemPrice, int amount) {
            Item item = new Item(itemName, itemPrice);
            shoppingCart.add(item);
            shoppingCart.get(shoppingCart.lastIndexOf(item)).changeStock(amount);
    }

    /**
     * Changes the amount of an Item in the Customer's shoppingCart
     * @param index Where in the shoppingCart the item is
     * @param change The amount to change by
     */
    public void changeAmountInCart(int index, int change){
        shoppingCart.get(index).changeStock(change);
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

}
