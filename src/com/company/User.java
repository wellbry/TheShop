package com.company;

import java.io.Serializable;

/**
 * The User superclass
 *
 * @author Magnus Wellbring
 */
public abstract class User implements Comparable<User>, Serializable {
    private UserType userType;
    private String name;
    private String logIn;
    private String password;

    /**
     * Constructs a User
     *
     * @param name     User name
     * @param logIn    User login name
     * @param password User password
     */
    public User(String name, String logIn, String password, UserType userType) {
        this.name = name;
        this.logIn = logIn;
        this.password = password;
        this.userType = userType;
    }

    /**
     * Get's the User's name
     *
     * @return the User's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns this User's login name
     *
     * @return The user's login name
     */
    public String getLogIn() {
        return logIn;
    }

    /**
     * Checks if the sent in string is equal to the password
     *
     * @param string
     * @return True if string is the same as the user password
     */
    public boolean isCorrectPassword(String string) {
        if (password.equals(string)) {
            return true;
        } else return false;
    }

    /**
     * Sets User password to newPass
     *
     * @param newPass Password to change to
     */
    public void setPassword(String newPass) {
        password = newPass;
    }

    /**
     * Tells you what subclass of User this user is
     *
     * @return The userType
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * This class' implementation of the Comparable interface, sorts Users alphabetically by name
     *
     * @param compareUser
     * @return
     */
    @Override
    public int compareTo(User compareUser) {
        return name.compareToIgnoreCase(compareUser.name);
    }

    /**
     * Returns a string representation of the User
     *
     * @return A string representation of the User
     */
    @Override
    public String toString() {
        return String.format("%10s: %10s: %10s", userType, name, logIn);
    }

    /**
     * The different kinds of User
     */
    enum UserType {
        CUSTOMER,
        EMPLOYEE,
    }
}
