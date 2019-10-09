package com.company;

public class Employee extends User {
    private int salary;

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
