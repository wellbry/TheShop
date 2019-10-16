package com.company;

public abstract class User implements Comparable<User>{
    private String name;
    private String login;
    private String password;
    UserType userType;

    public User(String name, String login, String password){
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

//    public String getPassword() {
//        return password;
//    }

    public boolean isCorrectPassword(String string){
        if (password.equals(string)){
            return true;
        } else return false;
    }

    public void setPassword(String newPass){
        password = newPass;
    }

    public UserType getUserType(){
        return userType;
    }

    @Override
    public int compareTo(User compareUser) {
        return name.compareToIgnoreCase(compareUser.name);
    }

    @Override
    public String toString() {
       return String.format("%10s: %10s: %10s", userType, name, login);
        // return userType + ": " + name + " " + login;
    }

    enum UserType {
        CUSTOMER,
        EMPLOYEE,
    }
}
