package com.company;

import java.io.Serializable;

/**
 * The User superclass
 *
 * @author Magnus Wellbring
 */
public abstract class User implements Comparable<User>, Serializable {
    UserType userType;
    private String name;
    private String login;
    private String password;
    //TODO userType shouldn't be available;

    /**
     * Constructor
     *
     * @param name     User name
     * @param login    User login name
     * @param password User password
     */
    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    /**
     * Retunrns this Users login name
     *
     * @return The users login name
     */
    public String getLogin() {
        return login;
    }

//    public String getPassword() {
//        return password;
//    }

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
     * Sets User password to the sent string
     *
     * @param newPass Password to change to
     */
    public void setPassword(String newPass) {
        password = newPass;
    }
    // TODO make change password method

    /**
     * Tells you what subclass of User this user is
     *
     * @return The User subclass
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
        return String.format("%10s: %10s: %10s", userType, name, login);
        // return userType + ": " + name + " " + login;
    }

    /**
     * The different kinds of User
     */
    enum UserType {
        CUSTOMER,
        EMPLOYEE,
    }
}
