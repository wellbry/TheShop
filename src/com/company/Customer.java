package com.company;

import java.util.ArrayList;

public class Customer extends User {
    private int balance;
    private ArrayList<Item> shoppingCart = new ArrayList<>();

    public Customer(String name, String login, String password) {
        super(name, login, password);
        balance = 200;
        userType = UserType.CUSTOMER;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void pay(int cost) {
        balance -= cost;
    }

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

    public ArrayList<Item> getShoppingCart(){
        return shoppingCart;
    }

    public void printShoppingCart(){
        for (Item item: shoppingCart){
            System.out.println(item.toString());
        }
    }
}
