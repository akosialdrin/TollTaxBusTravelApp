package com.example.authentication;

public class Admins {

    public Admins(){


    }
    private String name, email, pass, confirmpass;

    public Admins(String name,String email, String pass, String confirmpass) {
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
