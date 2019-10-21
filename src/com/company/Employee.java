package com.company;

/**
 * The Employee subclass of User
 * @author Magnus Wellbring
 */
public class Employee extends User {
    private int salary;

    /**
     * The Employee constructor
     * @param name Employee name
     * @param login Employee log in name
     * @param password Employee password
     * @param salary Employee salary
     */
    public Employee(String name, String login, String password, int salary){
        super(name, login, password);
        this.salary = salary;
        userType = UserType.EMPLOYEE;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
