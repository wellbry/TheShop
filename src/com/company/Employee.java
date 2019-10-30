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
        super(name, login, password, UserType.EMPLOYEE);
        this.salary = salary;
    }

    /**
     * Set's the Employee's salary
     * @param salary The int to set the Employee's salary to
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * Returns a String representation of the Employee
     * @return A String representation of the Employee
     */
    @Override
    public String toString() {
        return super.toString() + String.format("%10d SEK/month", salary);
    }
}
