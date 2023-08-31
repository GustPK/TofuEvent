package cs211.project.models.account;

import java.util.SplittableRandom;

public class Account {

    private  String name;
    private String username;
    private String password;


    public Account(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() { return name; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }



}
