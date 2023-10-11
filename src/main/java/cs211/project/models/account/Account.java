package cs211.project.models.account;

import java.util.SplittableRandom;

public class Account {

    private String name;
    private String username;
    private String password;
    private String image;
    private String status;
    private boolean banned;

    public Account(String name, String username, String password, String image, String status) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.image = image;
        this.status = status;
        this.banned = false;
    }

    public String getName() { return name; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() { return status; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String name) {
        this.image = name;
    }
}
