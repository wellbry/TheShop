package com.company;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {
    Shop shop = new Shop();

    @org.junit.jupiter.api.Test
    void changePassword() {
        String input = "bla\nblabla";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.getInstance().resetScanner();

        shop.setLoggedInCustomer(new Customer("bla", "bla", "bla"));
        shop.changePassword();
        assertTrue(shop.getLoggedInCustomer().isCorrectPassword("blabla"));
    }

    @org.junit.jupiter.api.Test
    void createCustomerAccount() {
        String input = "Magnus\ntestCustomer\npassword\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.getInstance().resetScanner();

        int expected = shop.getUsers().size() + 1;
        shop.createCustomerAccount();
        assertEquals(expected, shop.getUsers().size());
    }

    @org.junit.jupiter.api.Test
    void createEmployeeAccount() {
        String input = "Magnus\ntestAdmin\npassword\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.getInstance().resetScanner();

        int expected = shop.getUsers().size() + 1;
        shop.createEmployeeAccount();
        assertEquals(expected, shop.getUsers().size());
    }

    @org.junit.jupiter.api.Test
    void deleteAccount() {
        String input = "admin";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.getInstance().resetScanner();

        int expected = shop.getUsers().size();
        assertEquals(expected, shop.getUsers().size());

        expected -= 1;
        shop.deleteAccount();
        assertEquals(expected, shop.getUsers().size());
    }

    @org.junit.jupiter.api.Test
    void addInventoryItem() {
        String input = "testItem\n5\ntestItem2\n10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.getInstance().resetScanner();

        int testIndex = shop.getInventory().size();
        shop.addInventoryItem();
        shop.addInventoryItem();
        assertEquals(5, shop.getInventory().get(testIndex).getPrice());
        testIndex += 1;
        assertEquals(10, shop.getInventory().get(testIndex).getPrice());
    }

    @org.junit.jupiter.api.Test
    void changeStockOfItem() {
        String input = "testItem\n5\ntestItem\n7\ntestItem\n-5\ntestItem\n-9";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.getInstance().resetScanner();

        int testIndex = shop.getInventory().size();
        shop.addInventoryItem();
        shop.changeStockOfItem();
        assertEquals(7, shop.getInventory().get(testIndex).getAmount());

        shop.changeStockOfItem();
        assertEquals(2, shop.getInventory().get(testIndex).getAmount());

        shop.changeStockOfItem();
        assertEquals(0, shop.getInventory().get(testIndex).getAmount());
    }

    @org.junit.jupiter.api.Test
    void addItemToCart() {
        String input = "testItem\n5\ntestItem\n7\ntestItem\n5\ntestItem\n-1\ntestItem\n-10";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.getInstance().resetScanner();

        shop.addInventoryItem();
        shop.changeStockOfItem();
        shop.setLoggedInCustomer(new Customer("bla", "bla", "bla"));

        shop.addOrRemoveItemsToCart();
        assertEquals(5, shop.getLoggedInCustomer().getShoppingCart().get(0).getAmount());

        shop.addOrRemoveItemsToCart();
        assertEquals(4, shop.getLoggedInCustomer().getShoppingCart().get(0).getAmount());

        shop.addOrRemoveItemsToCart();
        assertEquals(0, shop.getLoggedInCustomer().getShoppingCart().size());
    }

    @org.junit.jupiter.api.Test
    void checkOut() {
        String input = "200\ntestItem\n5\ntestItem\n7\ntestItem2\n2\ntestItem2\n7\ntestItem\n5\ntestItem2\n5";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.getInstance().resetScanner();

        shop.setLoggedInCustomer(new Customer("bla", "bla", "bla"));
        shop.depositMoneyToCustomer();

        shop.addInventoryItem();
        shop.changeStockOfItem();
        shop.addInventoryItem();
        shop.changeStockOfItem();

        shop.addOrRemoveItemsToCart();
        shop.addOrRemoveItemsToCart();

        shop.checkOut();

        assertEquals(165, shop.getLoggedInCustomer().getBalance());
    }

    @org.junit.jupiter.api.Test
    void depositMoneyToCustomer() {
        String input = "100\n-50";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        View.getInstance().resetScanner();

        shop.setLoggedInCustomer(new Customer("bla", "bla", "bla"));
        shop.depositMoneyToCustomer();
        assertEquals(100, shop.getLoggedInCustomer().getBalance());

        shop.depositMoneyToCustomer();
        assertEquals(100, shop.getLoggedInCustomer().getBalance());
    }

}