package com.example.authentication;

public class User {

    public User(){


    }


    private String name, email, pass, confirmpass;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.confirmpass = confirmpass;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getConfirmpass() {
        return confirmpass;
    }

}

