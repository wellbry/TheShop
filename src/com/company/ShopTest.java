package com.company;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {
    Shop shop = new Shop();



    @org.junit.jupiter.api.Test
    void logIn() {
        String input = "admin\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @org.junit.jupiter.api.Test
    void changePassword() {
    }

    @org.junit.jupiter.api.Test
    void createCustomerAccount() {
        String input = "admin\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @org.junit.jupiter.api.Test
    void createEmployeeAccount() {
    }

    @org.junit.jupiter.api.Test
    void deleteAccount() {

        assertEquals(2, shop.getUsers().size());

//        String input = "admin";
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//        shop.deleteAccount();
//        assertEquals(2, shop.getUsers().size());

        String input = "customer";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        shop.deleteAccount();
        assertEquals(shop.getUsers().size(), 1);
    }

    @org.junit.jupiter.api.Test
    void addInventoryItem() {
        String input = "testItem\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        shop.addInventoryItem();
        assertEquals(shop.getInventory().get(0).getPrice(), 5);
    }

    @org.junit.jupiter.api.Test
    void increaseStockOfItem() {
        String input = "testItem\n5\ntestItem\n5";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        shop.addInventoryItem();
        shop.increaseStockOfItem();
        assertEquals(shop.getInventory().get(0).getAmount(), 5);
    }

    @org.junit.jupiter.api.Test
    void writeInventoryToFile() {
    }

    @org.junit.jupiter.api.Test
    void readInventoryFromFile() {
    }


    @org.junit.jupiter.api.Test
    void addItemToCart() {
    }

    @org.junit.jupiter.api.Test
    void checkOut() {
    }

    @org.junit.jupiter.api.Test
    void depositMoneyToCustomer() {
        String input = "100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));


    }

    @org.junit.jupiter.api.Test
    void nukeInventory() {
    }
}