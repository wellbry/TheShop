package com.company;

import java.io.Serializable;
import java.util.Comparator;

public class Item implements Serializable {
    private String name;
    private int price;
    private int amount;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
        amount = 0;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void changeStock(int change) {
        amount += change;
    }

    public String toString() {
        return String.format("%20s %6d:- %5d pieces", name, price, amount);
    }
}

    class SortItemsAlphabetically implements Comparator<Item> {
        public int compare(Item a, Item b) {
            return a.getName().compareTo(b.getName());
        }
    }

    class SortItemsByPrice implements Comparator<Item> {
        public int compare(Item a, Item b) {
            return a.getPrice() - b.getPrice();
        }
    }

    class SortItemsByStock implements Comparator<Item> {
        public int compare(Item a, Item b) {
            return a.getAmount() - b.getAmount();
        }
    }
