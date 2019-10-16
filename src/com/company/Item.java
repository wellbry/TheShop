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

 /*   public Item(String name, int price, int amount) {
        this.name = name;
        this.price = price;
        inStock = amount;
    }*/

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void addStock(int increase){
        amount += increase;
    }

    public String toString() {
        return String.format("%20s %6d:- %5d pieces", name, price, amount);
    }

    static class SortAlphabetically implements Comparator<Item> {
        public int compare(Item a, Item b) {
            return a.name.compareTo(b.name);
        }
    }

    static class SortByPrice implements Comparator<Item> {
        public int compare(Item a, Item b) {
            return a.price - b.price;
        }
    }

    static class SortByStock implements Comparator<Item> {
        public int compare(Item a, Item b) {
            return a.amount - b.amount;
        }
    }
}