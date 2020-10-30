package com.solutionplanets.navkar;

public class User {
    private String username;
    private String emailId;
    private String password;

    public User(){
        //empty constructor is needed
    }

    public User(String username, String emailId, String password) {
        this.username = username;
        this.emailId = emailId;
        this.password = password;
    }

}
