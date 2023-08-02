package com.example.project;


public class Users {
    private final String my_user;
    private final String my_password;

    private final int my_id;

    static int id = 1;


    public Users(String my_user, String my_password) {
        this.my_user = my_user;
        this.my_password = my_password;
        this.my_id = id++;
    }

    public String getMy_user() {
        return my_user;
    }

    public String getMy_password() {
        return my_password;
    }

    public int getMy_id() {
        return my_id;
    }

    @Override
    public String toString() {
        return my_user + "," + my_password + "," + my_id;
    }
}
